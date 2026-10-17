#!/usr/bin/env python3
"""Require a real dedicated-server startup and successful recipe loading."""
import json
import os
from pathlib import Path
import re
import selectors
import signal
import subprocess
import sys
import time

run = Path('run')
run.mkdir(exist_ok=True)
(run / 'eula.txt').write_text('eula=true\n')
(run / 'server.properties').write_text('online-mode=false\nlevel-name=port-smoke\nview-distance=2\nsimulation-distance=2\n')
Path('build').mkdir(exist_ok=True)
integrations = '--with-integrations' in sys.argv
suffix = '-integrations' if integrations else ''
arguments = ['bash', 'gradlew', 'runServer', '-PportSmoke', '--console=plain']
if integrations:
    arguments.append('-PwithIntegrations')
process = subprocess.Popen(arguments,
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
    Path(f'build/port-startup{suffix}.log').write_text(log)
    errors = [line for line in log.splitlines() if re.search(
        r"Couldn't parse data file|Parsing error loading recipe|Error parsing recipe|Failed to parse recipe|Error loading recipe|Failed to load datapacks|Failed to start the minecraft server|Mod loading error|Failed to load registries", line, re.I)]
    counts = [int(x) for x in re.findall(r'Loaded (\d+) recipes', log)]
    native_assertions = [int(x) for x in re.findall(r'FOUNDATIONS_NATIVE_PORT_SMOKE_PASSED assertions=(\d+)', log)]
    tree_counts = [int(x) for x in re.findall(r'Calculator native tree features: (\d+)', log)]
    native_counts = [int(x) for x in re.findall(r'Calculator native recipe inventory: (\d+)', log)]
    recipe_files = list(Path('src/main/resources/data/foundations_calculator/recipe').rglob('*.json'))
    expected = len(recipe_files)
    conditional = sum(bool(json.loads(path.read_text()).get('neoforge:conditions')) for path in recipe_files)
    required = expected - conditional
    report = {'native_tree_features': max(tree_counts, default=0), 'native_regression_assertions': max(native_assertions, default=0), 'integrations_enabled': integrations, 'server_started': started, 'exit_code': process.returncode,
              'loaded_recipes': max(counts, default=0), 'calculator_recipes': max(native_counts, default=0), 'authored_calculator_recipes': expected, 'required_calculator_recipes': required, 'conditional_calculator_recipes': conditional, 'resource_errors': errors,
              'passed': started and process.returncode == 0 and not errors and required <= max(native_counts, default=0) <= expected and max(native_assertions, default=0) >= 220 and max(tree_counts, default=0) == 4}
    Path(f'build/port-startup{suffix}.json').write_text(json.dumps(report, indent=2) + '\n')
if not report['passed']:
    raise SystemExit('Native server startup or recipe loading failed; see build/port-startup.log')
