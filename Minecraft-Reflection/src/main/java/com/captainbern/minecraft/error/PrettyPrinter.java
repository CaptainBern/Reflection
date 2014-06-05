package com.captainbern.minecraft.error;

import com.google.common.primitives.Primitives;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;

public class PrettyPrinter {

    public static int MAX_RECURSION_DEPTH = 3;

    public static String print(Object object) {
        return print(object, object.getClass(), Object.class);
    }

    public static String print(Object object, Class<?> start, Class<?> top) {
        return print(object, start, top, MAX_RECURSION_DEPTH);
    }

    private static String print(Object object, Class<?> current, Class<?> top, int maxRecursions) {

        StringBuilder out = new StringBuilder();
        StringBuilder tabs = new StringBuilder();

        print(out, tabs, 0, object, current, top, maxRecursions, true);

        return out.toString();
    }

    protected static void print(StringBuilder out,  // The standard output
                                StringBuilder tabs, // Used to set the tabs
                                int rows,           // Used to detect how much tabs we need to place
                                Object object,      // The Object we're printing
                                Class<?> current,   // The Class we're printing
                                Class<?> top,       // The top class/we have to stop the recursion when we reach this class
                                int hierarchyIndex, // The current hierarchy/recursion-index
                                boolean first) {    // Whether or not this is the first object


        if (current == null || current == Object.class || (top != null && current == top))
            return;

        if (hierarchyIndex < 0) {
            out.append("<Reached max recursions>");
            return;
        }

        rows++;

        for (int i = 0; i < rows; i++) {
            tabs.append("    ");
        }

        out.append("\n");
        out.append(tabs.toString().substring(4));
        out.append("{\n");

        if (current != object.getClass()) {
            out.append(tabs.toString());
            out.append("Inherited from superclass " + current.getCanonicalName() + "\n");
        } else {
            out.append(tabs.toString());
            out.append("Class = " + current.getCanonicalName() + "\n");
        }

        out.append(tabs.toString());
        out.append("hashCode = " + Integer.toHexString(object.hashCode()) + "\n");

        if (current.getDeclaredFields().length > 0) {
            out.append(tabs.toString());
            out.append("=== Field-Dump ===" + "\n");

            for (Field field : current.getDeclaredFields()) {
                if (!field.isAccessible())
                    field.setAccessible(true);

                out.append(tabs.toString());
                out.append(field.getName());
                out.append(" = ");

                try {
                    Object value = field.get(object);
                    printValue(out, tabs, rows, value, top, hierarchyIndex);
                } catch (Exception e) {
                    out.append(e.getMessage());
                }

                if (first) {
                    first = false;
                } else {
                    out.append(", ");
                }

                out.append("\n");
            }
        }

        out.append(tabs.toString().substring(4));
        out.append("}\n");
        --rows;
        print(out, new StringBuilder(), rows, object, current.getSuperclass(), top, hierarchyIndex, first); // We have to clear the tabs
    }

    private static void printValue(StringBuilder out,
                                   StringBuilder tabs,
                                   int rows,
                                   Object object,
                                   Class<?> top,
                                   int hierarchyIndex) {

        Class<?> current = object.getClass();

        if (object == null) {
            out.append("<NULL>");
        } else if (current.isPrimitive() || Primitives.isWrapperType(current) || current == String.class) {
            out.append(object.toString());
        } else if (Map.class.isAssignableFrom(current)) {
            printMap(out, tabs, rows, (Map<Object, Object>) object, current, top, hierarchyIndex);
        } else if (current.isArray()) {
            printArray(out, tabs, rows, object, top, hierarchyIndex);
        } else if (Iterable.class.isAssignableFrom(current)) {
            printIterable(out, tabs, rows, (Iterable) object, top, hierarchyIndex);
        } else {
            print(out, tabs, rows, object, object.getClass(), top, hierarchyIndex - 1, true);
        }
    }

    private static void printMap(StringBuilder out, StringBuilder tabs, int rows, Map<Object, Object> map, Class<?> current, Class<?> top, int hierarchyIndex) {
        out.append("[\n");

        boolean first = true;

        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            out.append(tabs.toString());
            out.append(tabs.toString());
            printValue(out, tabs, rows, entry.getKey(), top, hierarchyIndex - 1);
            out.append(" = ");
            printValue(out, tabs, rows, entry.getValue(), top, hierarchyIndex - 1);

            if (first)
                first = false;
            else
                out.append(", ");

            out.append("\n");
        }

        out.append(tabs.toString());
        out.append("]");
    }

    private static void printArray(StringBuilder out, StringBuilder tabs, int rows, Object array, Class<?> top, int hierarchyIndex) {
        out.append("[\n");

        boolean first = true;

        for (int i = 0; i < Array.getLength(array); i++) {
            out.append(tabs.toString());
            out.append(tabs.toString());
            try {
                printValue(out, tabs, rows, Array.get(array, i), top, hierarchyIndex);
            } catch (Exception e) {
                out.append(e.getMessage());
            }

            if (first)
                first = false;
            else
                out.append(", ");

            out.append("\n");
        }

        out.append(tabs.toString());
        out.append("]");
    }

    private static void printIterable(StringBuilder out, StringBuilder tabs, int rows, Iterable iterable, Class<?> top, int hierarchyIndex) {
        out.append("(\n");

        boolean first = true;

        for (Object object : iterable) {
            out.append(tabs.toString());
            out.append(tabs.toString());
            printValue(out, tabs, rows, object, top, hierarchyIndex);

            if (first)
                first = false;
            else
                out.append(", ");

            out.append("\n");
        }

        out.append(tabs.toString());
        out.append(")");
    }
}