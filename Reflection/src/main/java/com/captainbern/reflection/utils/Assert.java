package com.captainbern.reflection.utils;

public abstract class Assert {

    public static void isTrue(boolean expression) {
        if(!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void isTrue(boolean expression, Object message) {
        if(!expression) {
            throw new IllegalArgumentException(format(String.valueOf(message)));
        }
    }

    public static <T> T checkNotNull(T type) {
        if(type == null) {
            throw new NullPointerException();
        }

        return type;
    }

    public static <T> T checkNotNull(T type, Object message) {
        if(type == null) {
            throw new NullPointerException(format(String.valueOf(message)));
        }

        return type;
    }

    public static void checkElementIndex(int index, int size) {
        checkElementIndex(index, size, "index");
    }

    public static void checkElementIndex(int index, int size, Object message) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(formatIndexError(index, size, String.valueOf(message)));
        }
    }

    private static String formatIndexError(int index, int size, String desc) {
        if(index < 0) {
            return format("%s \'%s\' must not be negative!", desc, index);
        } else if(size < 0) {
            throw new IllegalArgumentException("Negative size: " + size);
        } else if(index >= size) {
            return format("%s (%s) must be less then size (%s)", desc, index, size);
        }
        return null;
    }

    public static String format(String message, Object... params) {
        message = String.valueOf(message);

        StringBuilder builder = new StringBuilder(message.length() + (16 * params.length));

        int messageStart = 0;
        int index = 0;

        while (index < params.length) {
            int place = message.indexOf("%s");
            if(place == -1) {
               break;
            }
            builder.append(message.substring(messageStart, place));
            builder.append(params[index++]);
            messageStart = place + 2;
        }

        if(index < params.length) {
            builder.append(" {");
            while(index < params.length) {
                builder.append(", ");
                builder.append(params[index++]);
            }
            builder.append("}");
        }

        return builder.toString();
    }
}
