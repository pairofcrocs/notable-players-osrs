#!/usr/bin/env bash
# Launch the dev Notable Players plugin with Jagex credentials borrowed
# from a currently-running official RuneLite launcher process.
#
# Usage:
#   1. Open the official Jagex Launcher and click Play (the standard RuneLite
#      client window must be running).
#   2. ./run-jagex.sh
#
# The session ID we copy is short-lived. Logging into the same character from
# this dev client will boot the official one. Easiest workflow: leave the
# official client at the world-select screen (don't log into a world), then
# run this script and log in here.

set -euo pipefail

cd "$(dirname "$0")"

PID="$(pgrep -f '/Applications/RuneLite.app/Contents/MacOS/RuneLite' | head -1 || true)"
if [[ -z "${PID:-}" ]]; then
  echo "error: no official RuneLite launcher process found." >&2
  echo "       open the Jagex Launcher and click Play, then re-run." >&2
  exit 1
fi

while IFS=$'\t' read -r k v; do
  export "$k=$v"
done < <(python3 scripts/read_proc_env.py "$PID" JX_)

if [[ -z "${JX_SESSION_ID:-}" ]]; then
  echo "error: JX_SESSION_ID not found on PID $PID — is the launcher fully started?" >&2
  exit 1
fi

echo "Using Jagex session for: ${JX_DISPLAY_NAME:-?} (character ${JX_CHARACTER_ID:-?})"
exec ./gradlew --no-daemon run
