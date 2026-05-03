#!/usr/bin/env python3
"""Read another process's environment on macOS via sysctl(KERN_PROCARGS2).

Usage: read_proc_env.py <pid> [prefix]
Prints "KEY\\tVALUE" lines, optionally filtered by prefix.
"""
import ctypes
import sys

CTL_KERN = 1
KERN_PROCARGS2 = 49


def read_proc_env(pid):
    libc = ctypes.CDLL("/usr/lib/libc.dylib", use_errno=True)
    mib = (ctypes.c_int * 3)(CTL_KERN, KERN_PROCARGS2, pid)

    size = ctypes.c_size_t(0)
    if libc.sysctl(mib, 3, None, ctypes.byref(size), None, 0) != 0:
        return None, ctypes.get_errno()

    buf = ctypes.create_string_buffer(size.value)
    if libc.sysctl(mib, 3, buf, ctypes.byref(size), None, 0) != 0:
        return None, ctypes.get_errno()

    data = buf.raw[: size.value]
    argc = int.from_bytes(data[:4], "little")
    pos = 4

    end = data.index(b"\0", pos)
    pos = end + 1
    while pos < len(data) and data[pos:pos + 1] == b"\0":
        pos += 1
    for _ in range(argc):
        end = data.index(b"\0", pos)
        pos = end + 1

    env = {}
    while pos < len(data):
        try:
            end = data.index(b"\0", pos)
        except ValueError:
            break
        kv = data[pos:end].decode("utf-8", errors="replace")
        if "=" in kv:
            k, v = kv.split("=", 1)
            env[k] = v
        pos = end + 1
    return env, 0


if __name__ == "__main__":
    if len(sys.argv) < 2:
        sys.exit("usage: read_proc_env.py <pid> [prefix]")
    pid = int(sys.argv[1])
    env, err = read_proc_env(pid)
    if env is None:
        sys.exit(f"sysctl failed, errno={err}")
    prefix = sys.argv[2] if len(sys.argv) > 2 else ""
    for k in sorted(env):
        if not prefix or k.startswith(prefix):
            print(f"{k}\t{env[k]}")
