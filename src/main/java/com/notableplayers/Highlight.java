package com.notableplayers;

import java.awt.Color;

final class Highlight
{
    final Color color;
    final String label;
    final String reason;

    Highlight(Color color, String label, String reason)
    {
        this.color = color;
        this.label = label;
        this.reason = reason;
    }
}
