package org.wolfinabukkit;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.Validate;

import com.google.common.collect.Maps;

public enum ChatColor {
    BLACK('0', 0x00),
    DARK_BLUE('1', 0x1),
    DARK_GREEN('2', 0x2),
    DARK_AQUA('3', 0x3),
    DARK_RED('4', 0x4),
    DARK_PURPLE('5', 0x5),
    GOLD('6', 0x6),
    GRAY('7', 0x7),
    DARK_GRAY('8', 0x8),
    BLUE('9', 0x9),
    GREEN('a', 0xA),
    AQUA('b', 0xB),
    RED('c', 0xC),
    LIGHT_PURPLE('d', 0xD),
    YELLOW('e', 0xE),
    WHITE('f', 0xF),
    MAGIC('k', 0x10, true),
    BOLD('l', 0x11, true),
    STRIKETHROUGH('m', 0x12, true),
    UNDERLINE('n', 0x13, true),
    ITALIC('o', 0x14, true),
    RESET('r', 0x15);

    public static final char COLOR_CHAR = '\u00A7';
    // private stuff
    private static final Pattern ColorStripper = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");
    private final int colorInt;
    private final char colorChar;
    private final boolean hasFormat;
    private final String result;
    private final static Map<Integer, ChatColor> COLORMAP_ID = Maps.newHashMap();
    private final static Map<Character, ChatColor> COLORMAP_CHAR = Maps.newHashMap();

    // init when hasformatting isnt provided. defaults to false
    private ChatColor(char charCode, int intCode) {
        this(charCode, intCode, false);
    }

    private ChatColor(char charCode, int intCode, boolean hasFormat) {
        this.colorChar = charCode;
        this.colorInt = intCode;
        this.hasFormat = hasFormat;
        this.result = new String(new char[]{COLOR_CHAR, charCode});
    }

    static {
        for (ChatColor color : values()) {
            COLORMAP_ID.put(color.colorInt, color);
            COLORMAP_CHAR.put(color.colorChar, color);
        }
    }

    public char getChar() {
        return this.colorChar;
    }

    @Override
    public String toString() {
        return result;
    }

    public boolean isFormat() {
        return hasFormat;
    }

    public boolean isColor() {
        return !hasFormat && this != RESET;
    }

    public static ChatColor getByChar(char charCode) {
        return COLORMAP_CHAR.get(charCode);
    }

    public static ChatColor getByChar(String charCode) {
        Validate.notNull(charCode, "Do not provide null value for character code");
        Validate.isTrue(charCode.length() > 0, "Do not provide empty strings for character code");
        return COLORMAP_CHAR.get(charCode.charAt(0));
    }

    public static String stripColor(final String str) {
        if (str == null) {
            return null;
        }
        // bit pointless to invoke a heavier method if not needed
        if (str == "") {
            return "";
        }
        return ColorStripper.matcher(str).toString();
    }

    // replace alternative characters with the correct character
    public static String translateAlternateColorCodes(char alternativeChar, String text) {
        Pattern pat = Pattern.compile("(?i)" + String.valueOf(alternativeChar) + "[0-9A-Fa-fK-Ok-oRr]");
        Matcher matches = pat.matcher(text);
        StringBuffer buf = new StringBuffer();
        while (matches.find()) {
            matches.appendReplacement(buf, COLOR_CHAR + matches.group(1));
        }
        matches.appendTail(buf);
        return buf.toString();
    }

    // you might have noticed these two methods are a bit very different from bukkit lol
    // imagine looping through a string to find the last appearance of a character...
    public static String getLastColors(String text) {
        String result = "";
        int lastColorCharIndex = text.lastIndexOf(COLOR_CHAR);
        if (lastColorCharIndex < text.length() - 1) {
            ChatColor color = getByChar(text.charAt(lastColorCharIndex + 1));
            if (color != null) {
                result = color.toString() + result;
            }
        }
        return result;
    }
}


