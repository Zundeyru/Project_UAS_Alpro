package org.example.ui.panels;

import org.example.model.Patient;
import org.example.util.UITheme;
import org.example.util.Validator;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FormPanel extends JPanel {
    private final JTextField tfId = new JTextField();
    private final JTextField tfName = new JTextField();
    private final JTextField tfAge = new JTextField();
    private final JComboBox<String> cbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
    private final JTextField tfDiagnosis = new JTextField();
    private final JTextField tfPhone = new JTextField();
    private final JTextArea taAddress = new JTextArea(3, 16);

    public FormPanel() {
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Form Pasien"));
        setBackground(Color.WHITE);

        UITheme.setFieldSize(tfId);
        UITheme.setFieldSize(tfName);
        UITheme.setFieldSize(tfAge);
        UITheme.setFieldSize(tfDiagnosis);
        UITheme.setFieldSize(tfPhone);
        UITheme.setTextArea(taAddress);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addRow(gc, row++, "ID Pasien", tfId);
        addRow(gc, row++, "Nama", tfName);
        addRow(gc, row++, "Umur", tfAge);
        addRow(gc, row++, "Gender", cbGender);
        addRow(gc, row++, "Diagnosis", tfDiagnosis);
        addRow(gc, row++, "Telepon", tfPhone);

        // Alamat (textarea)
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0;
        add(new JLabel("Alamat"), gc);
        gc.gridx = 1; gc.gridy = row; gc.weightx = 1;
        JScrollPane sp = new JScrollPane(taAddress);
        add(sp, gc);
    }

    private void addRow(GridBagConstraints gc, int row, String label, Component field) {
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0;
        add(new JLabel(label), gc);

        gc.gridx = 1; gc.gridy = row; gc.weightx = 1;
        add(field, gc);
    }

    public Patient buildPatientFromInput() {
        int id = Validator.requirePositiveInt(tfId.getText(), "ID Pasien");
        String name = Validator.requireNotBlank(tfName.getText(), "Nama");
        int age = Validator.requireAge(tfAge.getText());
        String gender = (String) cbGender.getSelectedItem();
        String diagnosis = Validator.requireNotBlank(tfDiagnosis.getText(), "Diagnosis");
        String phone = Validator.requireNotBlank(tfPhone.getText(), "Telepon");
        String address = Validator.requireNotBlank(taAddress.getText(), "Alamat");

        return new Patient(id, name, age, gender, diagnosis, phone, address);
    }

    public void fillFromPatient(Patient p) {
        tfId.setText(String.valueOf(p.getPatientId()));
        tfName.setText(p.getName());
        tfAge.setText(String.valueOf(p.getAge()));
        cbGender.setSelectedItem(p.getGender());
        tfDiagnosis.setText(p.getDiagnosis());
        tfPhone.setText(p.getPhone());
        taAddress.setText(p.getAddress());
    }

    public void reset() {
        tfId.setText("");
        tfName.setText("");
        tfAge.setText("");
        cbGender.setSelectedIndex(0);
        tfDiagnosis.setText("");
        tfPhone.setText("");
        taAddress.setText("");
        tfId.requestFocus();
    }
}
