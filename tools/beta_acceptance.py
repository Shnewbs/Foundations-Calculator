"""Keep beta publication behind the existing documented acceptance gates."""
import hashlib
import json

GATES = ('client', 'copied_world', 'survival_multiplayer', 'performance', 'port_26_1_2', 'port_26_3')


def acceptance_status(root, evidence, source_sha256):
    path = evidence / 'acceptance.json'
    if not path.is_file():
        return False, list(GATES)
    try:
        record = json.loads(path.read_text())
        if record.get('source_sha256') != source_sha256:
            return False, list(GATES)
        pending = []
        for key in GATES:
            gate = record.get('gates', {}).get(key, {})
            artifact = (root / gate.get('evidence', '')).resolve()
            if (gate.get('status') != 'PASS' or not artifact.is_relative_to(root.resolve())
                    or not artifact.is_file()
                    or hashlib.sha256(artifact.read_bytes()).hexdigest() != gate.get('sha256')):
                pending.append(key)
        return not pending, pending
    except (OSError, ValueError, TypeError, AttributeError):
        return False, list(GATES)
