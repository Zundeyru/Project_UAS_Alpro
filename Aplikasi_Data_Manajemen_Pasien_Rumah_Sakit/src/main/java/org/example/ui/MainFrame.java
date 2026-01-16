package org.example.ui;

import org.example.model.Patient;
import org.example.service.PatientService;
import org.example.service.PatientService.TraversalMode;
import org.example.ui.dialogs.PatientDetailDialog;
import org.example.ui.panels.ActionPanel;
import org.example.ui.panels.FormPanel;
import org.example.ui.panels.TablePanel;
import org.example.util.UITheme;
import org.example.util.Validator;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final PatientService service = new PatientService();

    private final FormPanel formPanel = new FormPanel();
    private final TablePanel tablePanel = new TablePanel();
    private final ActionPanel actionPanel = new ActionPanel();

    // Search bar & Mode analisis
    private final JTextField tfSearchId = new JTextField();
    private final JButton btnSearch = new JButton("🔍 Cari");

    private final JComboBox<String> cbViewMode = new JComboBox<>(new String[]{
            "Tampilan Data: Terurut (Default)",
            "Tampilan Data: Analisis Struktur"
    });
    private final JComboBox<String> cbTraversal = new JComboBox<>(new String[]{
            "Urutan Node: Terurut (InOrder)",
            "Urutan Kunjungan Node (PreOrder)",
            "Urutan Kunjungan Node (PostOrder)"
    });

    public MainFrame() {
        setTitle("Aplikasi Manajemen Data Pasien Rumah Sakit");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1150, 680);
        setLocationRelativeTo(null);

        setContentPane(buildRoot());
        UITheme.applyDefaultFont(getContentPane());

        refreshTable(TraversalMode.SORTED);
        wireEvents();
    }

    private JPanel buildRoot() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(UITheme.padding());
        root.setBackground(new Color(250, 250, 250));

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildCenter(), BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);

        return root;
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(12, 10));
        header.setBackground(new Color(250, 250, 250));

        JLabel title = new JLabel("🏥 Manajemen Data Pasien Rumah Sakit");
        title.setFont(UITheme.titleFont());
        header.add(title, BorderLayout.WEST);

        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(new Color(250, 250, 250));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(2, 6, 2, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy = 0;

        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));
        searchPanel.setBorder(new TitledBorder("Cari ID Pasien"));
        searchPanel.setBackground(Color.WHITE);
        tfSearchId.setColumns(10);
        UITheme.setBetterButton(btnSearch);
        searchPanel.add(tfSearchId, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        JPanel modePanel = new JPanel(new GridBagLayout());
        modePanel.setBorder(new TitledBorder("Mode Tampilan"));
        modePanel.setBackground(Color.WHITE);

        GridBagConstraints gm = new GridBagConstraints();
        gm.insets = new Insets(4, 6, 4, 6);
        gm.fill = GridBagConstraints.HORIZONTAL;
        gm.gridx = 0; gm.gridy = 0; gm.weightx = 1;
        modePanel.add(cbViewMode, gm);

        gm.gridy = 1;
        modePanel.add(cbTraversal, gm);

        cbTraversal.setEnabled(false);
        cbTraversal.setSelectedIndex(0);

        gc.gridx = 0; gc.weightx = 0.55;
        right.add(searchPanel, gc);

        gc.gridx = 1; gc.weightx = 0.45;
        right.add(modePanel, gc);

        header.add(right, BorderLayout.CENTER);
        return header;
    }

    private JPanel buildCenter() {
        JPanel center = new JPanel(new BorderLayout(12, 12));
        center.setBackground(new Color(250, 250, 250));

        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(new Color(250, 250, 250));
        left.add(formPanel, BorderLayout.CENTER);

        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(new Color(250, 250, 250));
        right.add(tablePanel, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.35);
        split.setBorder(null);

        center.add(split, BorderLayout.CENTER);
        return center;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(250, 250, 250));
        footer.add(actionPanel, BorderLayout.CENTER);
        return footer;
    }

    private void wireEvents() {
        // Double click row -> detail (ambil detail asli via service)
        tablePanel.setRowClickListener(rowPatient -> {
            Patient full = service.findPatientById(rowPatient.getPatientId());
            if (full != null) showPatientDetail(full);
        });

        actionPanel.setActionListener(new ActionPanel.ActionListener() {
            @Override public void onAdd() { handleAdd(); }
            @Override public void onDelete() { handleDelete(); }
            @Override public void onResetForm() { formPanel.reset(); }
            @Override public void onSeedData() { handleSeedData(); }
            @Override public void onExportCsv() { handleExportCsv(); }
        });

        btnSearch.addActionListener(e -> handleSearch());
        tfSearchId.addActionListener(e -> handleSearch());

        cbViewMode.addActionListener(e -> {
            boolean analysis = cbViewMode.getSelectedIndex() == 1;
            cbTraversal.setEnabled(analysis);

            if (!analysis) {
                cbTraversal.setSelectedIndex(0);
                refreshTable(TraversalMode.SORTED);
            } else {
                refreshTable(TraversalMode.SORTED);
            }
        });

        cbTraversal.addActionListener(e -> {
            if (!cbTraversal.isEnabled()) return;
            refreshTable(traversalFromDropdown());
        });
    }

    private TraversalMode traversalFromDropdown() {
        int idx = cbTraversal.getSelectedIndex();
        if (idx == 1) return TraversalMode.PRE_ORDER;
        if (idx == 2) return TraversalMode.POST_ORDER;
        return TraversalMode.SORTED;
    }

    private void handleAdd() {
        try {
            Patient p = formPanel.buildPatientFromInput();
            service.addPatient(
                    p.getPatientId(),
                    p.getName(),
                    p.getAge(),
                    p.getGender(),
                    p.getDiagnosis(),
                    p.getPhone(),
                    p.getAddress()
            );

            JOptionPane.showMessageDialog(this,
                    "Pasien berhasil ditambahkan.\n\nID: " + p.getPatientId() + "\nNama: " + p.getName(),
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

            formPanel.reset();
            refreshTable(traversalFromDropdown());
            tablePanel.highlightByPatientId(p.getPatientId());

        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void handleSearch() {
        try {
            int id = Validator.requirePositiveInt(tfSearchId.getText(), "ID Pasien");
            Patient found = service.findPatientById(id);

            if (found == null) {
                JOptionPane.showMessageDialog(this,
                        "Pasien dengan ID " + id + " tidak ditemukan.",
                        "Tidak Ditemukan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            tablePanel.highlightByPatientId(id);
            showPatientDetail(found);

        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    // ✅ DELETE WAJIB DARI TABEL: kalau belum pilih -> peringatan pilih pasien
    private void handleDelete() {
        try {
            Integer selectedId = tablePanel.getSelectedPatientId();

            if (selectedId == null) {
                JOptionPane.showMessageDialog(this,
                        "Pilih salah satu pasien di tabel terlebih dahulu,\n" +
                                "lalu tekan tombol 🗑️ Hapus.",
                        "Belum Memilih Pasien",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Patient candidate = service.findPatientById(selectedId);
            if (candidate == null) {
                // jarang terjadi (misalnya tabel belum refresh)
                JOptionPane.showMessageDialog(this,
                        "Data pasien yang dipilih tidak ditemukan lagi. Silakan refresh dengan mengganti mode tampilan.",
                        "Data Tidak Sinkron",
                        JOptionPane.WARNING_MESSAGE);
                refreshTable(traversalFromDropdown());
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Yakin ingin menghapus pasien berikut?\n\n" +
                            "ID: " + candidate.getPatientId() + "\n" +
                            "Nama: " + candidate.getName(),
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) return;

            boolean ok = service.removePatient(selectedId);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                        "Pasien ID " + selectedId + " berhasil dihapus.",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);

                refreshTable(traversalFromDropdown());
                tablePanel.clearSelection();
            } else {
                showError("Hapus gagal: ID tidak ditemukan.");
            }

        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void handleSeedData() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Ini akan menambahkan 12 pasien contoh dengan ID acak (unik).\nLanjutkan?",
                "Isi Contoh Data",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        seedData();

        JOptionPane.showMessageDialog(this,
                "Contoh data berhasil ditambahkan.\nTabel otomatis ditampilkan terurut berdasarkan ID.",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);

        cbViewMode.setSelectedIndex(0);
        cbTraversal.setSelectedIndex(0);
        cbTraversal.setEnabled(false);
        refreshTable(TraversalMode.SORTED);
    }

    private void handleExportCsv() {
        try {
            List<Patient> patients = service.getPatientsSorted();
            if (patients.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Tidak ada data untuk diexport.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Simpan CSV");
            chooser.setSelectedFile(new java.io.File("patients.csv"));

            int result = chooser.showSaveDialog(this);
            if (result != JFileChooser.APPROVE_OPTION) return;

            java.io.File file = chooser.getSelectedFile();
            exportToCsv(file.getAbsolutePath(), patients);

            JOptionPane.showMessageDialog(this,
                    "Export berhasil:\n" + file.getAbsolutePath(),
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void exportToCsv(String path, List<Patient> patients) throws Exception {
        try (FileWriter fw = new FileWriter(path, StandardCharsets.UTF_8)) {
            fw.write("patientId,name,age,gender,diagnosis,phone,address\n");
            for (Patient p : patients) {
                fw.write(csv(p.getPatientId()) + "," +
                        csv(p.getName()) + "," +
                        csv(p.getAge()) + "," +
                        csv(p.getGender()) + "," +
                        csv(p.getDiagnosis()) + "," +
                        csv(p.getPhone()) + "," +
                        csv(p.getAddress()) + "\n");
            }
        }
    }

    private String csv(Object v) {
        String s = String.valueOf(v);
        s = s.replace("\"", "\"\"");
        return "\"" + s + "\"";
    }

    private void showPatientDetail(Patient p) {
        new PatientDetailDialog(this, p).setVisible(true);
    }

    private void refreshTable(TraversalMode mode) {
        List<Patient> data = service.getPatientsTraversal(mode);
        tablePanel.refreshTable(data);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Input Tidak Valid / Error",
                JOptionPane.ERROR_MESSAGE);
    }

    // Seed Data (12 pasien)
    private void seedData() {
        Random rnd = new Random();
        Set<Integer> usedIds = new HashSet<>();

        String[] names = {
                "Andi Pratama", "Budi Santoso", "Citra Lestari", "Dewi Anggraini",
                "Eko Saputra", "Fajar Hidayat", "Gita Permata", "Hana Ramadhani",
                "Indra Wijaya", "Joko Susilo", "Kiki Amalia", "Lukman Hakim",
                "Maya Sari", "Nanda Putri", "Rizky Maulana"
        };

        String[] diagnoses = {
                "Demam", "Flu", "Gastritis", "Hipertensi",
                "Diabetes", "Asma", "Migraine", "Alergi",
                "Infeksi Saluran Pernapasan", "Nyeri Punggung"
        };

        String[] streets = {
                "Jl. Merdeka", "Jl. Sudirman", "Jl. Diponegoro", "Jl. Ahmad Yani",
                "Jl. Gatot Subroto", "Jl. Pahlawan"
        };

        for (int i = 0; i < 12; i++) {
            int id;
            do {
                id = 1000 + rnd.nextInt(9000);
            } while (usedIds.contains(id) || service.findPatientById(id) != null);
            usedIds.add(id);

            String name = names[rnd.nextInt(names.length)];
            int age = 15 + rnd.nextInt(70);
            String gender = rnd.nextBoolean() ? "Male" : "Female";
            String diagnosis = diagnoses[rnd.nextInt(diagnoses.length)];
            String phone = "08" + (100000000 + rnd.nextInt(900000000));
            String address = streets[rnd.nextInt(streets.length)] + " No. " + (1 + rnd.nextInt(200)) + ", Indonesia";

            try {
                service.addPatient(id, name, age, gender, diagnosis, phone, address);
            } catch (Exception ignored) {
                // kalau duplikat (sangat kecil), skip
            }
        }
    }
}
