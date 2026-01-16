package org.example.ui.panels;

import org.example.util.UITheme;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ActionPanel extends JPanel {

    public interface ActionListener {
        void onAdd();
        void onDelete();
        void onResetForm();
        void onSeedData();
        void onExportCsv();
    }

    private ActionListener listener;

    private final JButton btnAdd = new JButton("➕ Tambah");
    private final JButton btnDelete = new JButton("🗑️ Hapus");
    private final JButton btnReset = new JButton("♻️ Reset Form");
    private final JButton btnSeed = new JButton("🧪 Isi Contoh Data");
    private final JButton btnExport = new JButton("⬇ Export CSV");

    public ActionPanel() {
        setLayout(new GridLayout(1, 5, 10, 8));
        setBorder(new TitledBorder("Aksi"));
        setBackground(Color.WHITE);

        UITheme.setBetterButton(btnAdd);
        UITheme.setBetterButton(btnDelete);
        UITheme.setBetterButton(btnReset);
        UITheme.setBetterButton(btnSeed);
        UITheme.setBetterButton(btnExport);

        add(btnAdd);
        add(btnDelete);
        add(btnReset);
        add(btnSeed);
        add(btnExport);

        btnAdd.addActionListener(e -> { if (listener != null) listener.onAdd(); });
        btnDelete.addActionListener(e -> { if (listener != null) listener.onDelete(); });
        btnReset.addActionListener(e -> { if (listener != null) listener.onResetForm(); });
        btnSeed.addActionListener(e -> { if (listener != null) listener.onSeedData(); });
        btnExport.addActionListener(e -> { if (listener != null) listener.onExportCsv(); });
    }

    public void setActionListener(ActionListener listener) {
        this.listener = listener;
    }
}
