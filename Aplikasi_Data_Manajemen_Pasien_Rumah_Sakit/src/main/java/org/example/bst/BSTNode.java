package org.example.bst;

import org.example.model.Patient;

public class BSTNode {
    Patient data;
    BSTNode left;
    BSTNode right;

    BSTNode(Patient data) {
        this.data = data;
    }
}
