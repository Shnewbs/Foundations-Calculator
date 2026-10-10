#!/usr/bin/env python3
"""Answer API questions against the real compile classpath.

Usage: ci_api_probe.py <classpath-file> <queries-file> <output-file>

The classpath file lists one jar or directory per line (written by the Gradle init
script tools/ci_print_classpath.init.gradle). The queries file has one query per line;
blank lines and lines starting with '#' are ignored:

  class <regex>                   list class names matching the regex
  api <class> [member-regex]      javap -public of one class; with a member regex, only
                                  the header and the matching members are shown
  apis <class-regex> [member-regex]
                                  the same, for every class whose name matches (capped)

<class> is a fully qualified name; use '$' for nested classes (or '.', both are tried).

This exists because the port compiles on a runner that has the 26.3 jars while the
developer environment may not, and a compiler error never says what replaced a removed
symbol. Output is capped so one careless query cannot flood the report.
"""
import os
import re
import subprocess
import sys
import zipfile

MAX_CLASS_LIST = 300
MAX_API_LINES = 400
MAX_APIS_CLASSES = 12


def read_classpath(path):
    entries = []
    if os.path.exists(path):
        with open(path, encoding='utf-8') as f:
            for line in f:
                p = line.strip()
                if p and os.path.exists(p):
                    entries.append(p)
    return entries


def build_index(entries):
    """Map dotted class name -> jar path for every .class entry on the classpath."""
    index = {}
    for entry in entries:
        if entry.endswith('.jar') or entry.endswith('.zip'):
            try:
                with zipfile.ZipFile(entry) as z:
                    for name in z.namelist():
                        if name.endswith('.class') and not name.startswith('META-INF/') \
                                and not name.endswith('module-info.class'):
                            index.setdefault(name[:-6].replace('/', '.'), entry)
            except (zipfile.BadZipFile, OSError):
                continue
        elif os.path.isdir(entry):
            for root, _dirs, files in os.walk(entry):
                for fn in files:
                    if fn.endswith('.class'):
                        full = os.path.join(root, fn)
                        rel = os.path.relpath(full, entry)[:-6].replace(os.sep, '.')
                        index.setdefault(rel, entry)
    return index


def resolve(index, name):
    if name in index:
        return name
    # allow Outer.Inner for Outer$Inner
    parts = name.split('.')
    for i in range(len(parts) - 1, 0, -1):
        cand = '.'.join(parts[:i]) + '$' + '$'.join(parts[i:])
        if cand in index:
            return cand
    return None


def javap(index, cls, member_regex, limit):
    jar = index[cls]
    try:
        res = subprocess.run(['javap', '-public', '-cp', jar, cls],
                             capture_output=True, text=True, timeout=90)
    except (OSError, subprocess.TimeoutExpired) as e:
        return [f'(javap failed: {e})']
    lines = [l.rstrip() for l in res.stdout.splitlines() if l.strip()]
    if res.returncode != 0 and not lines:
        return [f'(javap error: {res.stderr.strip()[:200]})']
    if member_regex:
        rx = re.compile(member_regex)
        # javap prints "Compiled from ..." first, then the class declaration.
        header = lines[:2]
        body = [l for l in lines[2:] if rx.search(l) and l.strip() != '}']
        lines = header + body
    if len(lines) > limit:
        lines = lines[:limit] + [f'... ({len(lines) - limit} more lines cut)']
    return lines


def main():
    if len(sys.argv) != 4:
        print(__doc__)
        return 2
    cp_file, q_file, out_file = sys.argv[1:4]
    entries = read_classpath(cp_file)
    index = build_index(entries)
    out = [f'# API probe', '', f'classpath entries: {len(entries)}; classes indexed: {len(index)}', '']
    if not index:
        out.append('No classes indexed: the classpath file was missing or empty.')

    queries = []
    if os.path.exists(q_file):
        with open(q_file, encoding='utf-8') as f:
            for line in f:
                s = line.strip()
                if s and not s.startswith('#'):
                    queries.append(s)

    for q in queries:
        out.append(f'## {q}')
        parts = q.split(None, 2)
        cmd = parts[0]
        try:
            if cmd == 'class' and len(parts) >= 2:
                rx = re.compile(parts[1])
                hits = sorted(n for n in index if rx.search(n))
                out.extend(hits[:MAX_CLASS_LIST])
                if len(hits) > MAX_CLASS_LIST:
                    out.append(f'... ({len(hits) - MAX_CLASS_LIST} more)')
                if not hits:
                    out.append('(no matches)')
            elif cmd == 'api' and len(parts) >= 2:
                cls = resolve(index, parts[1])
                if cls is None:
                    out.append('(class not found)')
                else:
                    out.extend(javap(index, cls, parts[2] if len(parts) > 2 else '', MAX_API_LINES))
            elif cmd == 'apis' and len(parts) >= 2:
                rx = re.compile(parts[1])
                hits = sorted(n for n in index if rx.search(n))
                if not hits:
                    out.append('(no matches)')
                for cls in hits[:MAX_APIS_CLASSES]:
                    out.append(f'### {cls}')
                    out.extend(javap(index, cls, parts[2] if len(parts) > 2 else '', 120))
                if len(hits) > MAX_APIS_CLASSES:
                    out.append(f'... ({len(hits) - MAX_APIS_CLASSES} more classes matched; narrow the regex)')
            else:
                out.append('(unrecognised query)')
        except re.error as e:
            out.append(f'(bad regex: {e})')
        out.append('')

    with open(out_file, 'w', encoding='utf-8') as f:
        f.write('\n'.join(out) + '\n')
    return 0


if __name__ == '__main__':
    sys.exit(main())
