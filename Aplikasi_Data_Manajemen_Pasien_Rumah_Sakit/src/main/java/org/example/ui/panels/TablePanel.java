package org.example.ui.panels;

import org.example.model.Patient;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TablePanel extends JPanel {
    private final DefaultTableModel model;
    private final JTable table;

    public interface RowClickListener {
        void onPatientClicked(Patient patient);
    }

    private RowClickListener rowClickListener;

    public TablePanel() {
        setLayout(new BorderLayout(8, 8));
        setBorder(new TitledBorder("Daftar Pasien"));
        setBackground(Color.WHITE);

        model = new DefaultTableModel(new Object[]{"ID", "Nama", "Umur", "Gender", "Diagnosis"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Zebra row sederhana
        table.setDefaultRenderer(Object.class, new ZebraRenderer());

        // Double click row -> detail
        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() >= 0) {
                    Patient p = getSelectedPatientFromTable();
                    if (p != null && rowClickListener != null) rowClickListener.onPatientClicked(p);
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setRowClickListener(RowClickListener listener) {
        this.rowClickListener = listener;
    }

    public void refreshTable(List<Patient> patients) {
        model.setRowCount(0);
        if (patients == null) return;

        for (Patient p : patients) {
            model.addRow(new Object[]{
                    p.getPatientId(),
                    p.getName(),
                    p.getAge(),
                    p.getGender(),
                    p.getDiagnosis()
            });
        }
    }

    public void highlightByPatientId(int id) {
        for (int r = 0; r < model.getRowCount(); r++) {
            int rowId = (int) model.getValueAt(r, 0);
            if (rowId == id) {
                table.setRowSelectionInterval(r, r);
                table.scrollRectToVisible(table.getCellRect(r, 0, true));
                return;
            }
        }
        table.clearSelection();
    }

    // Untuk kebutuhan dialog detail (ambil dari tabel lalu dibuat Patient minimal)
    // Detail lengkap biasanya dari hasil search service, bukan dari tabel.
    public Patient getSelectedPatientFromTable() {
        int r = table.getSelectedRow();
        if (r < 0) return null;

        int id = (int) model.getValueAt(r, 0);
        String name = (String) model.getValueAt(r, 1);
        int age = (int) model.getValueAt(r, 2);
        String gender = (String) model.getValueAt(r, 3);
        String diagnosis = (String) model.getValueAt(r, 4);

        // phone & address tidak ada di tabel -> isi kosong, nanti MainFrame akan ambil detail asli dari service.
        return new Patient(id, name, age, gender, diagnosis, "", "");
    }

    // ✅ BARU: ambil ID pasien dari row yang sedang dipilih
    public Integer getSelectedPatientId() {
        int r = table.getSelectedRow();
        if (r < 0) return null;
        return (Integer) model.getValueAt(r, 0); // kolom ID
    }

    // ✅ BARU: bersihkan selection setelah delete/refresh
    public void clearSelection() {
        table.clearSelection();
    }

    private static class ZebraRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int col) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
            }
            return c;
        }
    }
}
