#!/usr/bin/env python3
"""Regenerate the notable-players section of README.md from notable_players.json.

Replaces the block between BEGIN PLAYERS and END PLAYERS markers with a
markdown list grouped by category. Jagex Mods are not stored in the JSON —
they're matched by name prefix in the plugin and shown as a static note.
"""
import json
import re
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent
DATA_PATH = REPO_ROOT / "src/main/resources/com/notableplayers/notable_players.json"
README_PATH = REPO_ROOT / "README.md"

BEGIN = "<!-- BEGIN PLAYERS -->"
END = "<!-- END PLAYERS -->"

SECTIONS = [
    ("streamers", "Content Creators"),
    ("uniques",   "Unique Accounts"),
]


def escape_cell(text):
    """Escape pipe and backslash so user content can't break the table."""
    return text.replace("\\", "\\\\").replace("|", "\\|")


def render(data):
    parts = []
    for key, heading in SECTIONS:
        entries = data.get(key) or []
        if not entries:
            continue
        parts.append(f"### {heading}")
        parts.append("")
        parts.append("| Player | Description |")
        parts.append("| --- | --- |")
        for e in sorted(entries, key=lambda x: (x.get("name") or "").lower()):
            name = escape_cell((e.get("name") or "").strip())
            reason = escape_cell((e.get("reason") or "").strip())
            parts.append(f"| **{name}** | {reason} |")
        parts.append("")

    parts.append("### Jagex Mods")
    parts.append("")
    parts.append("Any player whose name starts with `Mod ` is highlighted automatically.")
    parts.append("")

    return "\n".join(parts).rstrip() + "\n"


def main():
    data = json.loads(DATA_PATH.read_text())
    block = render(data)

    readme = README_PATH.read_text()
    pattern = re.compile(re.escape(BEGIN) + r".*?" + re.escape(END), re.DOTALL)
    if not pattern.search(readme):
        sys.exit(f"README.md is missing the '{BEGIN} ... {END}' markers")

    new = pattern.sub(f"{BEGIN}\n{block}{END}", readme)
    if new == readme:
        print("README.md already up to date")
        return
    README_PATH.write_text(new)
    print("README.md updated")


if __name__ == "__main__":
    main()
