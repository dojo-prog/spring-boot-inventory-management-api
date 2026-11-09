package com.doj0.inventory_management_api.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugUtils {

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]+");
    private static final Pattern MULTIPLE_HYPHENS = Pattern.compile("-+");

    public static String toSlug(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        
        String nowhitespace = WHITESPACE.matcher(input.trim()).replaceAll("-");
        
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        
        slug = MULTIPLE_HYPHENS.matcher(slug).replaceAll("-");
        
        return slug.toLowerCase(Locale.ENGLISH);
    }
}