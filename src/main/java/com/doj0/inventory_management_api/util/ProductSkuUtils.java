package com.doj0.inventory_management_api.util;

import java.util.UUID;

public class ProductSkuUtils {

    private ProductSkuUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    public static String generateSku(String categoryName, String productName) {
        String catPart = sanitizeAndTruncate(categoryName, 3);
        String prodPart = sanitizeAndTruncate(productName, 4);
        String randomSuffix = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        
        return String.format("%s-%s-%s", catPart, prodPart, randomSuffix);
    }

    public static String generateRandomSku(String prefix) {
        String cleanPrefix = (prefix != null && !prefix.isBlank()) 
            ? prefix.replaceAll("[^a-zA-Z0-9]", "").toUpperCase() 
            : "PRD";
            
        String uniqueCode = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        return String.format("%s-%s", cleanPrefix, uniqueCode);
    }


    private static String sanitizeAndTruncate(String input, int length) {
        if (input == null || input.isBlank()) {
            return "GEN";
        }
        String sanitized = input.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        
        if (sanitized.length() <= length) {
            return sanitized;
        }
        return sanitized.substring(0, length);
    }
}