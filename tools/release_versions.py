"""Candidate names shared by local validation and CurseForge publication."""
import re


def candidate_type(version):
    version=re.sub(r'^(?:26\.1\.2|26\.3)-(?=\d+\.\d+\.\d+(?:alpha|beta|-(?:alpha|beta|rc)))','',version)
    if re.fullmatch(r'\d+\.\d+\.\d+alpha(?:\.R\d+)?|\d+\.\d+\.\d+-alpha(?:[.-]\d+)?',version):
        return 'alpha'
    if re.fullmatch(r'0\.0\.\d+a(?:\.R\d+)?(?:-dev\.\d+)?|0\.1a(?:\.R\d+)?', version):
        return 'alpha'
    if re.fullmatch(r'0\.1b(?:\.R\d+)?|\d+\.\d+\.\d+beta(?:\.R\d+)?|\d+\.\d+\.\d+-(?:beta|rc)(?:[.-]\d+)?', version):
        return 'beta'
    if re.fullmatch(r'\d+\.\d+\.\d+', version):
        return 'release'
    raise ValueError('Unknown candidate naming; declare a supported version before publishing')


def candidate_stage(version):
    """CurseForge has no RC category: retain the RC stage separately from its beta upload type."""
    kind=candidate_type(version)
    return 'release_candidate' if re.search(r'-rc(?:[.-]\d+)?$',version) else kind
