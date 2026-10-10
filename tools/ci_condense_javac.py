#!/usr/bin/env python3
"""Condense javac diagnostics from a Gradle compile log into a small, readable report.

Usage: ci_condense_javac.py <compile.log> <output-dir>

Writes into <output-dir>:
  SUMMARY.md   totals, most common failures, per-file and per-package counts
  errors.txt   every diagnostic in full, with the CI workspace prefix removed

The 26.3 port compile produces hundreds of cascading diagnostics. Grouping them by
missing symbol or package shows which removed APIs are blocking the most code, so
the migration can be ordered by impact instead of file by file.
"""
import collections
import os
import re
import sys

# Live javac diagnostics start in column 0. Gradle's failure summary prints the same
# diagnostics again, indented, so an indented header is a duplicate and is ignored.
DIAG = re.compile(r'^(?P<path>\S.*?\.java):(?P<line>\d+): (?P<level>error|warning): (?P<msg>.*)$')
STOP = re.compile(r'^(\d+ errors?|\d+ warnings?|> Task |FAILURE:|\* What went wrong|BUILD )')
REPORTED = re.compile(r'^(?P<n>\d+) errors?$')
PKG_MISSING = re.compile(r'^package (?P<pkg>\S+) does not exist$')
REL = re.compile(r'(?:^|/)((?:src|platform|tools|guide-api)/.*)$')


def rel(path):
    ws = os.environ.get('GITHUB_WORKSPACE', '')
    if ws and path.startswith(ws):
        return path[len(ws):].lstrip('/')
    m = REL.search(path)
    return m.group(1) if m else path


def parse(lines):
    diags = []
    cur = None
    for raw in lines:
        line = raw.rstrip('\n')
        m = DIAG.match(line)
        if m:
            cur = {
                'path': rel(m.group('path')),
                'line': int(m.group('line')),
                'level': m.group('level'),
                'msg': m.group('msg').strip(),
                'body': [],
            }
            diags.append(cur)
        elif cur is not None:
            if STOP.match(line):
                cur = None
            else:
                cur['body'].append(line)
    return diags


def body_field(diag, label):
    for b in diag['body']:
        s = b.strip()
        if s.startswith(label):
            return s[len(label):].strip()
    return ''


def group_key(diag):
    msg = diag['msg']
    if msg == 'cannot find symbol':
        sym = body_field(diag, 'symbol:')
        return 'cannot find symbol: ' + (sym or '?')
    return msg


def top(counter, n):
    return counter.most_common(n)


def main():
    if len(sys.argv) != 3:
        print(__doc__)
        return 2
    log_path, out_dir = sys.argv[1], sys.argv[2]
    os.makedirs(out_dir, exist_ok=True)

    if os.path.exists(log_path):
        with open(log_path, encoding='utf-8', errors='replace') as f:
            lines = f.readlines()
    else:
        lines = []
    diags = parse(lines)
    reported = None
    for raw in lines:
        m = REPORTED.match(raw.rstrip('\n'))
        if m:
            reported = int(m.group('n'))
            break
    errors = [d for d in diags if d['level'] == 'error']
    warnings = [d for d in diags if d['level'] == 'warning']

    by_key = collections.Counter(group_key(d) for d in errors)
    by_file = collections.Counter(d['path'] for d in errors)
    by_dir = collections.Counter(os.path.dirname(d['path']) for d in errors)
    packages = collections.Counter()
    for d in errors:
        m = PKG_MISSING.match(d['msg'])
        if m:
            packages[m.group('pkg')] += 1

    sha = os.environ.get('GITHUB_SHA', 'unknown')
    run = os.environ.get('GITHUB_RUN_ID', 'unknown')
    out = []
    out.append('# 26.3 compile diagnostics')
    out.append('')
    out.append(f'Source commit: `{sha}`  ')
    out.append(f'CI run: {run}  ')
    if not os.path.exists(log_path):
        out.append('')
        out.append('No compile log was produced (the compile step did not run). See compile-tail.txt.')
    out.append('')
    out.append(f'- errors: **{len(errors)}**')
    if reported is not None and reported != len(errors):
        out.append(f'- WARNING: javac reported {reported} errors but {len(errors)} were parsed; check errors.txt')
    elif reported is not None:
        out.append(f'- javac reported {reported} errors (matches)')
    out.append(f'- warnings: {len(warnings)}')
    out.append(f'- distinct failure groups: {len(by_key)}')
    out.append(f'- files with errors: {len(by_file)}')
    out.append('')
    out.append('Many errors cascade from a small number of removed APIs. Work the groups')
    out.append('below from the top; re-run CI after each group to see the cascade collapse.')
    out.append('')

    out.append('## Most common failures')
    out.append('')
    for key, n in top(by_key, 60):
        out.append(f'- {n:>4}  {key}')
    out.append('')

    if packages:
        out.append('## Packages that no longer exist')
        out.append('')
        for pkg, n in sorted(packages.items(), key=lambda kv: (-kv[1], kv[0])):
            out.append(f'- {n:>4}  {pkg}')
        out.append('')

    out.append('## Errors per directory')
    out.append('')
    for d, n in top(by_dir, 40):
        out.append(f'- {n:>4}  {d}')
    out.append('')

    out.append('## Errors per file (top 60)')
    out.append('')
    for f, n in top(by_file, 60):
        out.append(f'- {n:>4}  {f}')
    out.append('')

    with open(os.path.join(out_dir, 'SUMMARY.md'), 'w', encoding='utf-8') as f:
        f.write('\n'.join(out) + '\n')

    with open(os.path.join(out_dir, 'errors.txt'), 'w', encoding='utf-8') as f:
        for d in diags:
            f.write(f"{d['path']}:{d['line']}: {d['level']}: {d['msg']}\n")
            for b in d['body']:
                f.write(b + '\n')
    return 0


if __name__ == '__main__':
    sys.exit(main())
