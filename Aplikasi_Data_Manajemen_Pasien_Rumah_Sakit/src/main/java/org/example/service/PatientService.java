package org.example.service;

import org.example.bst.PatientBST;
import org.example.model.Patient;

import java.util.List;

public class PatientService {

    public enum TraversalMode {
        SORTED,          // InOrder (default)
        PRE_ORDER,       // mode analisis
        POST_ORDER       // mode analisis
    }

    private final PatientBST bst = new PatientBST();

    public void addPatient(int patientId, String name, int age, String gender,
                           String diagnosis, String phone, String address) {
        Patient p = new Patient(patientId, name, age, gender, diagnosis, phone, address);
        bst.insert(p);
    }

    public Patient findPatientById(int id) {
        return bst.search(id);
    }

    public boolean removePatient(int id) {
        return bst.delete(id);
    }

    public List<Patient> getPatientsSorted() {
        return bst.traversalInOrder();
    }

    public List<Patient> getPatientsTraversal(TraversalMode mode) {
        if (mode == null) return bst.traversalInOrder();

        switch (mode) {
            case PRE_ORDER:
                return bst.traversalPreOrder();
            case POST_ORDER:
                return bst.traversalPostOrder();
            case SORTED:
            default:
                return bst.traversalInOrder();
        }
    }
}
