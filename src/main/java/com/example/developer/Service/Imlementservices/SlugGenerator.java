package com.example.developer.Service.Imlementservices;
//import java.Security
import java.security.SecureRandom;

public class SlugGenerator {
    private static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateBaseSlug(String name) {
        if (name == null || name.isBlank()) {
            name = "user";
        }
        String cleaned = name.trim().toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
        return cleaned.isBlank() ? "user" : cleaned;
    }

    public static String generateRandomSuffix(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }
}
