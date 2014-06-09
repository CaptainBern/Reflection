package com.captainbern.minecraft.error;

import com.google.common.primitives.Primitives;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class used to print/parse objects
 *
 * Example output:
 {
     Class = com.captainbern.minecraft.error.PrettyPrinterTest
     hashCode = 433c675d
 }

 {
     Inherited from superclass com.captainbern.minecraft.error.TestClass
     hashCode = 433c675d
     === Field-Dump ===
     someValue = lol
     someInt = 15,
     bool = true,
     someLong = 1506877496847,
     LOOK_I_M_A_LIST = (

         {
             Class = com.captainbern.minecraft.Dumper
             hashCode = 377dca04
             === Field-Dump ===
             TAIL = └──
             BRANCH = ├── ,
             TREE = │,
             instance = <Previously visited Object - See hashCode 930990596>,
         }

         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         <Previously visited Object - See hashCode 930990596>,
         ),
 }
 */
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
        HashSet<Object> previous = new HashSet<>();

        print(out, tabs, 0, object, current, top, maxRecursions, true, previous);

        return out.toString();
    }

    protected static void print(StringBuilder out,  // The standard output
                                StringBuilder tabs, // Used to set the tabs
                                int rows,           // Used to detect how much tabs we need to place
                                Object object,      // The Object we're printing
                                Class<?> current,   // The Class we're printing
                                Class<?> top,       // The top class/we have to stop the recursion when we reach this class
                                int hierarchyIndex, // The current hierarchy/recursion-index
                                boolean first,
                                Set<Object> previous) {    // Whether or not this is the first object


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

        previous.add(object);

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
                    printValue(out, tabs, rows, value, top, hierarchyIndex, previous);
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

        print(out, new StringBuilder(), rows, object, current.getSuperclass(), top, hierarchyIndex, first, previous); // We have to clear the tabs
    }

    private static void printValue(StringBuilder out,
                                   StringBuilder tabs,
                                   int rows,
                                   Object object,
                                   Class<?> top,
                                   int hierarchyIndex,
                                   Set<Object> previous) {

        if (previous.contains(object)) {
            out.append("<Previously visited Object - See hashCode " + object.hashCode() + ">");
            return;
        }

        Class<?> current = object.getClass();

        if (object == null) {
            out.append("<NULL>");
        } else if (current.isPrimitive() || Primitives.isWrapperType(current) || current == String.class) {
            out.append(object.toString());
        } else if (Map.class.isAssignableFrom(current)) {
            printMap(out, tabs, rows, (Map<Object, Object>) object, current, top, hierarchyIndex, previous);
        } else if (current.isArray()) {
            printArray(out, tabs, rows, object, top, hierarchyIndex, previous);
        } else if (Iterable.class.isAssignableFrom(current)) {
            printIterable(out, tabs, rows, (Iterable) object, top, hierarchyIndex, previous);
        } else {
            print(out, tabs, rows, object, object.getClass(), top, hierarchyIndex - 1, true, previous);
        }
    }

    private static void printMap(StringBuilder out, StringBuilder tabs, int rows, Map<Object, Object> map, Class<?> current, Class<?> top, int hierarchyIndex, Set<Object> previous) {
        out.append("[\n");

        boolean first = true;

        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            out.append(tabs.toString());
            out.append(tabs.toString());
            printValue(out, tabs, rows, entry.getKey(), top, hierarchyIndex - 1, previous);
            out.append(" = ");
            printValue(out, tabs, rows, entry.getValue(), top, hierarchyIndex - 1, previous);

            if (first)
                first = false;
            else
                out.append(", ");

            out.append("\n");
        }

        out.append(tabs.toString());
        out.append("]");
    }

    private static void printArray(StringBuilder out, StringBuilder tabs, int rows, Object array, Class<?> top, int hierarchyIndex, Set<Object> previous) {
        out.append("[\n");

        boolean first = true;

        for (int i = 0; i < Array.getLength(array); i++) {
            out.append(tabs.toString());
            out.append(tabs.toString());
            try {
                printValue(out, tabs, rows, Array.get(array, i), top, hierarchyIndex, previous);
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

    private static void printIterable(StringBuilder out, StringBuilder tabs, int rows, Iterable iterable, Class<?> top, int hierarchyIndex, Set<Object> previous) {
        out.append("(\n");

        boolean first = true;

        for (Object object : iterable) {
            out.append(tabs.toString());
            out.append(tabs.toString());
            printValue(out, tabs, rows, object, top, hierarchyIndex, previous);

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