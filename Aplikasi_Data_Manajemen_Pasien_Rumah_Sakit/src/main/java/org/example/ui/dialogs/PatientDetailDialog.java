package org.example.ui.dialogs;

import org.example.model.Patient;
import org.example.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PatientDetailDialog extends JDialog {

    public PatientDetailDialog(Window owner, Patient patient) {
        super(owner, "Detail Pasien", ModalityType.APPLICATION_MODAL);
        setSize(520, 360);
        setLocationRelativeTo(owner);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.setBackground(Color.WHITE);

        JLabel title = new JLabel("Informasi Pasien");
        title.setFont(UITheme.titleFont());
        root.add(title, BorderLayout.NORTH);

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setFont(UITheme.normalFont());
        info.setText(buildText(patient));

        root.add(new JScrollPane(info), BorderLayout.CENTER);

        JButton ok = new JButton("Tutup");
        UITheme.setBetterButton(ok);
        ok.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.WHITE);
        bottom.add(ok);

        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private String buildText(Patient p) {
        if (p == null) return "(Data tidak tersedia)";
        return ""
                + "ID Pasien      : " + p.getPatientId() + "\n"
                + "Nama           : " + p.getName() + "\n"
                + "Umur           : " + p.getAge() + "\n"
                + "Gender         : " + p.getGender() + "\n"
                + "Diagnosis      : " + p.getDiagnosis() + "\n"
                + "Telepon        : " + p.getPhone() + "\n"
                + "Alamat         : " + p.getAddress() + "\n";
    }
}
