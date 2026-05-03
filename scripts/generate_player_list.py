#!/usr/bin/env python3
"""Regenerate the notable-players section of README.md from notable_players.json.

Replaces the block between BEGIN PLAYERS and END PLAYERS markers with a
markdown list grouped by category. Names in the "mods" group get the "Mod "
prefix that the plugin applies in-game.
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
    ("creators",  "Plugin Creator",  ""),
    ("streamers", "Streamers",       ""),
    ("mods",      "Jagex Mods",      "Mod "),
    ("uniques",   "Unique Accounts", ""),
]


def render(data):
    parts = []
    for key, heading, prefix in SECTIONS:
        entries = data.get(key) or []
        if not entries:
            continue
        parts.append(f"### {heading}")
        parts.append("")
        for e in sorted(entries, key=lambda x: (x.get("name") or "").lower()):
            name = (prefix + (e.get("name") or "")).strip()
            reason = (e.get("reason") or "").strip()
            parts.append(f"- **{name}** — {reason}" if reason else f"- **{name}**")
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
