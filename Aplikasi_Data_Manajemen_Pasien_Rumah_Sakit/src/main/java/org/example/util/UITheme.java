package org.example.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UITheme {

    private UITheme() {}

    public static final int PAD = 12;

    public static Font titleFont() {
        return new Font("SansSerif", Font.BOLD, 20);
    }

    public static Font normalFont() {
        return new Font("SansSerif", Font.PLAIN, 13);
    }

    public static EmptyBorder padding() {
        return new EmptyBorder(PAD, PAD, PAD, PAD);
    }

    public static void applyDefaultFont(Component c) {
        c.setFont(normalFont());
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                applyDefaultFont(child);
            }
        }
    }

    public static void setBetterButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setFont(normalFont());
    }

    public static void setFieldSize(JTextField tf) {
        tf.setColumns(16);
        tf.setFont(normalFont());
    }

    public static void setTextArea(JTextArea ta) {
        ta.setFont(normalFont());
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
    }
}
