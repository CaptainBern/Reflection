package com.captainbern.reflection.utils;

public class Assert {

    private Assert() {
    }

    public static void assertTrue(boolean expression) {
        assertTrue(expression, null);
    }

    public static void assertTrue(boolean expression, String message) {
        if (!expression) {
            throwAssertionError(message);
        }
    }

    public static void assertFalse(boolean expression) {
        assertFalse(expression, null);
    }

    public static void assertFalse(boolean expression, String message) {
        assertTrue(!expression, message);
    }

    public static void throwAssertionError(String message) {
        if (message == null) {
            throw new AssertionError();
        } else {
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        if (expected == null && actual == null) {
            return;
        } else if (!actual.equals(expected)) {
            throwEqualsError(message, expected, actual);
        }
    }

    public static void throwEqualsError(String message, Object expected, Object actual) {
        if (message == null) {
            throw new AssertionError(format("Was given: %s<%s> but expected: %s<%s>!", expected, String.valueOf(expected), actual, String.valueOf(actual)));
        } else {
            throw new AssertionError(message);
        }
    }

    public static void assertNotNull(Object object, String message) {
        assertTrue(object != null, message);
    }

    public static void assertNotNull(Object object) {
        assertNotNull(object, null);
    }

    public static void assertNull(Object object, String message) {
        if (object != null) {
            throwNotNull(object, message);
        }
    }

    public static void assertNull(Object object) {
        assertNull(object, null);
    }

    private static void throwNotNull(Object object, String message) {
        String pretty = "";
        if (message != null) {
            pretty = message + " ";
        }
        throwAssertionError(format(pretty + "expected null but got: %s", object));
    }

    public static void assertElementIndex(int index, int size) {
        assertElementIndex(index, size, "index");
    }

    public static void assertElementIndex(int index, int size, String message) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(formatIndexError(index, size, String.valueOf(message)));
        }
    }

    private static String formatIndexError(int index, int size, String desc) {
        if (index < 0) {
            return format("%s \'%s\' must not be negative!", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("Negative size: " + size);
        } else if (index >= size) {
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
            if (place == -1) {
                break;
            }
            builder.append(message.substring(messageStart, place));
            builder.append(params[index++]);
            messageStart = place + 2;
        }

        if (index < params.length) {
            builder.append(" {");
            while (index < params.length) {
                builder.append(", ");
                builder.append(params[index++]);
            }
            builder.append("}");
        }

        return builder.toString();
    }
}
