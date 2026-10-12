"""Candidate names shared by local validation and CurseForge publication."""
import re


def candidate_type(version):
    if re.fullmatch(r'0\.0\.\d+a(?:\.R\d+)?(?:-dev\.\d+)?|0\.1a(?:\.R\d+)?', version):
        return 'alpha'
    if re.fullmatch(r'0\.1b(?:\.R\d+)?|\d+\.\d+\.\d+beta(?:\.R\d+)?|\d+\.\d+\.\d+-(?:beta|rc)(?:[.-]\d+)?', version):
        return 'beta'
    if re.fullmatch(r'\d+\.\d+\.\d+', version):
        return 'release'
    raise ValueError('Unknown candidate naming; declare a supported version before publishing')
