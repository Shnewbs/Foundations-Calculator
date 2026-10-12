"""Content fingerprint of the source, resources, fixtures and pinned build inputs."""
from pathlib import Path
import hashlib
import json


def source_fingerprint(root):
    paths = set()
    for name in ['src', 'guide-api', 'gradle', 'tools']:
        paths.update(p for p in (root / name).rglob('*') if p.is_file()
                     and '__pycache__' not in p.parts
                     and p.suffix not in {'.pyc', '.log'}
                     and not p.name.endswith('_validation.json'))
    for name in ['build.gradle', 'settings.gradle', 'gradle.properties', 'LICENSE', 'NOTICE.md']:
        paths.add(root / name)
    records = {p.relative_to(root).as_posix(): hashlib.sha256(p.read_bytes()).hexdigest()
               for p in sorted(paths)}
    return hashlib.sha256(json.dumps(records, sort_keys=True, separators=(',', ':')).encode()).hexdigest()
