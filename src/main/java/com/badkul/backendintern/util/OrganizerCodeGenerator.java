package com.badkul.backendintern.util;

import org.springframework.stereotype.Component;

@Component
public class OrganizerCodeGenerator {
    private static final String PREFIX = "ORG";
    private static final int WIDTH = 5; // ORG00001

    public synchronized String nextCode(String lastCode) {
        int next = 1;
        if (lastCode != null && lastCode.startsWith(PREFIX)) {
            try {
                String num = lastCode.substring(PREFIX.length());
                next = Integer.parseInt(num) + 1;
            } catch (Exception ignored) {}
        }
        return String.format("%s%0" + WIDTH + "d", PREFIX, next);
    }
}

