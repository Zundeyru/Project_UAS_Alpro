package org.example.util;

public class Validator {

    public static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " wajib diisi.");
        }
        return value.trim();
    }

    public static int requireInt(String value, String fieldName) {
        requireNotBlank(value, fieldName);
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " harus berupa angka.");
        }
    }

    public static int requirePositiveInt(String value, String fieldName) {
        int v = requireInt(value, fieldName);
        if (v <= 0) throw new IllegalArgumentException(fieldName + " harus > 0.");
        return v;
    }

    public static int requireAge(String value) {
        int age = requireInt(value, "Umur");
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Umur harus di antara 0 sampai 120.");
        }
        return age;
    }

    public static String safeString(String value) {
        return value == null ? "" : value.trim();
    }
}
