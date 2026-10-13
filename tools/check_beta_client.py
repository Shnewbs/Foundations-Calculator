#!/usr/bin/env python3
"""Run the existing real OpenGL client walkthrough on a freshly saved native world."""
import json
import os
from pathlib import Path
import re
import selectors
import shutil
import signal
import subprocess
import time


def launch(arguments, logfile, timeout, stop_on_ready=False):
    process = subprocess.Popen(['bash', 'gradlew', *arguments, '--console=plain'],
        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, start_new_session=True)
    selector = selectors.DefaultSelector()
    selector.register(process.stdout, selectors.EVENT_READ)
    output = bytearray()
    deadline = time.monotonic() + timeout
    ready = False
    try:
        while time.monotonic() < deadline:
            for key, _ in selector.select(1):
                chunk = os.read(key.fileobj.fileno(), 65536)
                if not chunk:
                    selector.unregister(key.fileobj)
                    continue
                output.extend(chunk)
                print(chunk.decode(errors='replace'), end='', flush=True)
                if stop_on_ready and not ready and re.search(rb'Done \([0-9.]+s\)!', output):
                    ready = True
                    process.stdin.write(b'stop\n')
                    process.stdin.flush()
                    deadline = time.monotonic() + 60
            if process.poll() is not None:
                break
        if process.poll() is None:
            os.killpg(process.pid, signal.SIGTERM)
            try:
                process.wait(timeout=15)
            except subprocess.TimeoutExpired:
                os.killpg(process.pid, signal.SIGKILL)
                process.wait()
    finally:
        if process.poll() is None:
            os.killpg(process.pid, signal.SIGKILL)
            process.wait()
        selector.close()
        text = output.decode(errors='replace')
        logfile.write_text(text)
    return process.returncode, text, ready


evidence = Path('validation/beta-client')
evidence.mkdir(parents=True, exist_ok=True)
run = Path('run')
run.mkdir(exist_ok=True)
(run / 'eula.txt').write_text('eula=true\n')
(run / 'server.properties').write_text('online-mode=false\nlevel-name=FoundationsSmoke\nview-distance=2\nsimulation-distance=2\n')
code, server_log, ready = launch(['runServer'], evidence / 'world-creation.log', 300, True)
if code != 0 or not ready or not (run / 'FoundationsSmoke/level.dat').is_file():
    raise SystemExit('The native smoke world failed to start and save.')
(run / 'options.txt').write_text('onboardAccessibility:false\ntutorialStep:none\nrenderDistance:6\nsimulationDistance:5\nmaxFps:60\n')
destination = run / 'saves/FoundationsSmoke'
destination.parent.mkdir(exist_ok=True)
shutil.copytree(run / 'FoundationsSmoke', destination, dirs_exist_ok=True)
code, client_log, _ = launch(['runClient', '-PclientSmoke'], evidence / 'client.log', 900)
markers = ['FOUNDATIONS_CLIENT_MACHINE_INTERACTIONS_PASSED',
           'FOUNDATIONS_CLIENT_CIRCUIT_PROCESSING_PASSED',
           'FOUNDATIONS_CLIENT_R4_FEATURES_PASSED', 'FOUNDATIONS_CLIENT_SMOKE_PASSED']
screenshots = sorted(str(path) for path in Path('validation/screenshots').glob('*.png'))
required_names = set(re.findall(r'capture\(mc,"([^"]+\.png)"\)', Path('src/gameTest/java/com/foundations/calculator/ClientSmoke.java').read_text())) - {'15-emi-process-recipes.png','16-jei-process-recipes.png','24-jade-machine-hud.png'}
missing = sorted(required_names - {Path(path).name for path in screenshots})
report = {'missing_screenshots': missing, 'client_exit_code': code, 'markers': {marker: marker in client_log for marker in markers},
          'screenshots': screenshots, 'passed': code == 0 and all(marker in client_log for marker in markers) and not missing}
(evidence / 'client-checks.json').write_text(json.dumps(report, indent=2) + '\n')
if not report['passed']:
    raise SystemExit('The real client walkthrough did not complete; inspect validation/beta-client/client.log.')
