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


evidence=Path('validation/port-client');evidence.mkdir(parents=True,exist_ok=True)
run=Path('run');run.mkdir(exist_ok=True)
(run/'eula.txt').write_text('eula=true\n')
(run/'server.properties').write_text('online-mode=false\nlevel-name=FoundationsPortClient\nlevel-type=minecraft:flat\ngenerator-settings={"layers":[{"block":"minecraft:bedrock","height":1},{"block":"minecraft:dirt","height":2},{"block":"minecraft:grass_block","height":1}],"biome":"minecraft:plains","features":false,"lakes":false,"structure_overrides":[]}\nview-distance=2\nsimulation-distance=2\n')
code,log,ready=launch(['runServer'],evidence/'world-creation.log',300,True)
if code!=0 or not ready:raise SystemExit('Native client smoke world could not be saved')
(run/'options.txt').write_text('onboardAccessibility:false\nguiScale:2\ntutorialStep:none\nrenderDistance:4\nsimulationDistance:3\nmaxFps:60\n')
shutil.copytree(run/'FoundationsPortClient',run/'saves/FoundationsPortClient',dirs_exist_ok=True)
code,log,_=launch(['runClient','-PportClientSmoke'],evidence/'client.log',600)
markers=['FOUNDATIONS_PORT_RENDER_OVERLAYS_PASSED','FOUNDATIONS_PORT_ITEM_NAMES_PASSED','FOUNDATIONS_PORT_OUTPUT_MENU_PASSED','FOUNDATIONS_PORT_CLIENT_PASSED']
names=['01-native-chamber-colors.png','02-native-output-page.png']
report={'client_exit_code':code,'markers':{m:m in log for m in markers},'screenshots':names,'passed':code==0 and all(m in log for m in markers) and all((evidence/'screenshots'/n).exists() for n in names)}
(evidence/'client-checks.json').write_text(json.dumps(report,indent=2)+'\n')
if not report['passed']:raise SystemExit('Native real-client validation failed; inspect validation/port-client/client.log')
