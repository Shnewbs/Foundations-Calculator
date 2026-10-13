#!/usr/bin/env python3
"""Require a real dedicated-server startup and successful recipe loading."""
import json
import os
from pathlib import Path
import re
import selectors
import signal
import subprocess
import time

run = Path('run')
run.mkdir(exist_ok=True)
(run / 'eula.txt').write_text('eula=true\n')
(run / 'server.properties').write_text('online-mode=false\nlevel-name=port-smoke\nview-distance=2\nsimulation-distance=2\n')
Path('build').mkdir(exist_ok=True)
process = subprocess.Popen(['bash', 'gradlew', 'runServer', '--console=plain'],
                           stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.STDOUT,
                           start_new_session=True)
selector = selectors.DefaultSelector()
selector.register(process.stdout, selectors.EVENT_READ)
output = bytearray()
deadline = time.monotonic() + 300
started = False
try:
    while time.monotonic() < deadline:
        for key, _ in selector.select(1):
            chunk = os.read(key.fileobj.fileno(), 65536)
            if not chunk:
                selector.unregister(key.fileobj)
                continue
            output.extend(chunk)
            print(chunk.decode(errors='replace'), end='', flush=True)
            if not started and re.search(rb'Done \([0-9.]+s\)!', output):
                started = True
                process.stdin.write(b'stop\n')
                process.stdin.flush()
                deadline = time.monotonic() + 45
        if process.poll() is not None:
            break
    if process.poll() is None:
        os.killpg(process.pid, signal.SIGTERM)
        process.wait(timeout=15)
finally:
    if process.poll() is None:
        os.killpg(process.pid, signal.SIGKILL)
        process.wait()
    selector.close()
    log = output.decode(errors='replace')
    Path('build/port-startup.log').write_text(log)
    errors = [line for line in log.splitlines() if re.search(
        r'Parsing error loading recipe|Error parsing recipe|Failed to parse recipe|Error loading recipe|Failed to load datapacks|Failed to start the minecraft server|Mod loading error|Failed to load registries', line, re.I)]
    counts = [int(x) for x in re.findall(r'Loaded (\d+) recipes', log)]
    report = {'server_started': started, 'exit_code': process.returncode,
              'loaded_recipes': max(counts, default=0), 'resource_errors': errors,
              'passed': started and process.returncode == 0 and not errors and max(counts, default=0) >= 910}
    Path('build/port-startup.json').write_text(json.dumps(report, indent=2) + '\n')
if not report['passed']:
    raise SystemExit('Native server startup or recipe loading failed; see build/port-startup.log')
