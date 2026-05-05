#!/usr/bin/env python3
"""Apply a player submission from a GitHub issue body to notable_players.json.

Reads the issue body from $ISSUE_BODY, parses the issue-form fields, and
appends the entry to the appropriate category in the JSON file. Writes the
applied player name and category to $GITHUB_OUTPUT for downstream steps.

Exits non-zero with a clear message when the body is missing fields, the
category isn't recognized, or the player is already present.
"""
import json
import os
import re
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent
DATA_PATH = REPO_ROOT / "src/main/resources/com/notableplayers/notable_players.json"

CATEGORY_MAP = {
    "content creator": "streamers",
    "unique account":  "uniques",
}


def extract_field(body, label):
    pattern = re.compile(
        rf'^###\s+{re.escape(label)}\s*$\n+(.+?)(?=\n###\s|\Z)',
        re.MULTILINE | re.DOTALL,
    )
    m = pattern.search(body)
    if not m:
        return None
    text = m.group(1).strip()
    if not text or text == "_No response_":
        return None
    return text


def normalize(name):
    return " ".join(name.split()).lower()


def serialize(data):
    """Format JSON with one entry per line so diffs stay small."""
    lines = ["{"]
    keys = list(data.keys())
    for ki, key in enumerate(keys):
        entries = data[key] or []
        lines.append(f'  "{key}": [')
        for ei, entry in enumerate(entries):
            entry_json = json.dumps(entry, ensure_ascii=False)
            comma = "," if ei < len(entries) - 1 else ""
            lines.append(f"    {entry_json}{comma}")
        end_comma = "," if ki < len(keys) - 1 else ""
        lines.append(f"  ]{end_comma}")
    lines.append("}")
    return "\n".join(lines) + "\n"


def write_output(name, value):
    out = os.environ.get("GITHUB_OUTPUT")
    if not out:
        return
    with open(out, "a") as f:
        f.write(f"{name}={value}\n")


def main():
    body = os.environ.get("ISSUE_BODY", "")
    if not body.strip():
        sys.exit("Issue body is empty.")

    username = extract_field(body, "Username")
    category = extract_field(body, "Category")
    description = extract_field(body, "Short description")

    missing = [n for n, v in (("Username", username), ("Category", category), ("Short description", description)) if not v]
    if missing:
        sys.exit(f"Issue is missing required field(s): {', '.join(missing)}.")

    bucket_key = CATEGORY_MAP.get(category.lower().strip())
    if not bucket_key:
        sys.exit(f"Unrecognized category: {category!r}. Expected one of: {', '.join(c.title() for c in CATEGORY_MAP)}.")

    data = json.loads(DATA_PATH.read_text())
    bucket = data.setdefault(bucket_key, [])

    if any(normalize(e.get("name", "")) == normalize(username) for e in bucket):
        sys.exit(f"Player {username!r} is already in the {bucket_key} list.")

    bucket.append({"name": username.strip(), "reason": description.strip()})
    bucket.sort(key=lambda e: (e.get("name") or "").lower())

    DATA_PATH.write_text(serialize(data))

    write_output("player_name", username.strip())
    write_output("category", category.strip())
    print(f"Applied {username!r} -> {bucket_key}")


if __name__ == "__main__":
    main()
