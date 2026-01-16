package org.example.bst;

import org.example.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientBST {
    private BSTNode root;

    // Insert O(h). patientId harus unique.
    public void insert(Patient patient) {
        if (patient == null) throw new IllegalArgumentException("Patient tidak boleh null.");

        if (root == null) {
            root = new BSTNode(patient);
            return;
        }

        BSTNode current = root;
        while (true) {
            int id = patient.getPatientId();
            int curId = current.data.getPatientId();

            if (id == curId) {
                throw new IllegalArgumentException("patientId " + id + " sudah ada. ID harus unik.");
            } else if (id < curId) {
                if (current.left == null) {
                    current.left = new BSTNode(patient);
                    return;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new BSTNode(patient);
                    return;
                }
                current = current.right;
            }
        }
    }

    public Patient search(int id) {
        BSTNode cur = root;
        while (cur != null) {
            int curId = cur.data.getPatientId();
            if (id == curId) return cur.data;
            cur = (id < curId) ? cur.left : cur.right;
        }
        return null;
    }

    // Delete O(h). Implementasi benar untuk 0/1/2 child.
    public boolean delete(int id) {
        int before = traversalInOrder().size();
        root = deleteRecursive(root, id);
        consideredBalanceNoOp(); // placeholder untuk jelasin bahwa BST bisa tidak seimbang (tidak melakukan balancing).
        int after = traversalInOrder().size();
        return after < before;
    }

    private BSTNode deleteRecursive(BSTNode node, int id) {
        if (node == null) return null;

        int nodeId = node.data.getPatientId();

        if (id < nodeId) {
            node.left = deleteRecursive(node.left, id);
            return node;
        }
        if (id > nodeId) {
            node.right = deleteRecursive(node.right, id);
            return node;
        }

        // Ketemu node yang dihapus
        // Case 1: 0 child
        if (node.left == null && node.right == null) {
            return null;
        }
        // Case 2: 1 child
        if (node.left == null) return node.right;
        if (node.right == null) return node.left;

        // Case 3: 2 child
        // Ambil successor = nilai terkecil di subtree kanan
        BSTNode successor = findMin(node.right);
        node.data = successor.data; // copy data successor ke node ini
        // hapus successor dari subtree kanan
        node.right = deleteRecursive(node.right, successor.data.getPatientId());
        return node;
    }

    private BSTNode findMin(BSTNode node) {
        BSTNode cur = node;
        while (cur.left != null) cur = cur.left;
        return cur;
    }

    public List<Patient> traversalInOrder() {
        List<Patient> out = new ArrayList<>();
        inOrder(root, out);
        return out;
    }

    public List<Patient> traversalPreOrder() {
        List<Patient> out = new ArrayList<>();
        preOrder(root, out);
        return out;
    }

    public List<Patient> traversalPostOrder() {
        List<Patient> out = new ArrayList<>();
        postOrder(root, out);
        return out;
    }

    private void inOrder(BSTNode node, List<Patient> out) {
        if (node == null) return;
        inOrder(node.left, out);
        out.add(node.data);
        inOrder(node.right, out);
    }

    private void preOrder(BSTNode node, List<Patient> out) {
        if (node == null) return;
        out.add(node.data);
        preOrder(node.left, out);
        preOrder(node.right, out);
    }

    private void postOrder(BSTNode node, List<Patient> out) {
        if (node == null) return;
        postOrder(node.left, out);
        postOrder(node.right, out);
        out.add(node.data);
    }

    // Tidak melakukan apa-apa. Ada untuk mengingatkan pembaca: BST bisa jadi tidak balance -> performa bergantung tinggi tree.
    private void consideredBalanceNoOp() {
        // intentionally empty
    }
}
