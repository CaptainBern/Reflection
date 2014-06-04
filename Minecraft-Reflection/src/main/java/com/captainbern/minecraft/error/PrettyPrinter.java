package com.captainbern.minecraft.error;

import com.google.common.primitives.Primitives;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class PrettyPrinter {

    public static int MAX_RECURSION_DEPTH = 3;

    private static class PrinterContext {
        private static class Builder {

            private Class<?> top;
            private int maxRecursions;
            private StringBuilder builder;

            private Builder() {}

            public Builder withTopClass(Class<?> topClass) {
                this.top = topClass;
                return this;
            }

            public Builder withMaxRecursions(int maxRecursions) {
                this.maxRecursions = maxRecursions;
                return this;
            }

            public Builder withStringBuilder(StringBuilder builder) {
                this.builder = builder;
                return this;
            }

            public PrinterContext build() {
                return new PrinterContext(this);
            }
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        private Class<?> top;
        private int maxRecursions;
        private Set<Object> previous;

        private int rowCount;
        private StringBuilder builder;

        private PrinterContext(Builder builder) {
            this.top = builder.top;
            this.maxRecursions = builder.maxRecursions;
            this.builder = builder.builder;
            this.rowCount = 0;
        }
    }

    public static String print(Object object) {
        return print(object, object.getClass(), Object.class);
    }

    public static String print(Object object, Class<?> start, Class<?> top) {
        return print(object, start, top, MAX_RECURSION_DEPTH);
    }

    public static String print(Object object, Class<?> start, Class<?> top, int maxRecursions) {
        PrinterContext context = new PrinterContext.Builder()
                .withTopClass(top)
                .withMaxRecursions(maxRecursions)
                .withStringBuilder(new StringBuilder())
                .build();

        print(object, context);

        return context.builder.toString();
    }

    public static void print(Object object, PrinterContext context) {
        context.rowCount++;

        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < context.rowCount; i++) {
            tabs.append("    ");
        }

        // Parse everything
        Class<?> currentClass = object.getClass();
        Object currentObject = object;
        Class<?> top = context.top;

        if (currentClass == null || currentObject == Object.class || (top != null && currentClass == top))
            return;

        context.builder.append("\n");
        context.builder.append(tabs.toString().substring(4));
        context.builder.append("{\n");

        // Print info ^^
        context.builder.append(tabs.toString());
        context.builder.append("hashCode = " + currentObject.hashCode());
        context.builder.append("\n");

        while (currentClass != null && currentClass != Object.class && (context.top != null && currentClass != context.top)) {
            if (currentObject.getClass() != currentClass) {
                context.builder.append(tabs.toString().substring(4));
                context.builder.append(" Inherited from super-class " + currentClass.getCanonicalName() + ":\n");
            }

            for (Field field : currentClass.getDeclaredFields()) {
                if (!field.isAccessible())
                    field.setAccessible(true);

                context.builder.append(tabs.toString());
                context.builder.append(field.getName());
                context.builder.append(" = ");

                try {
                    Object value = field.get(currentObject);
                    printValue(value, context);
                } catch (Exception e) {
                    context.builder.append(e.getMessage());
                }

                context.builder.append("\n");
            }

            currentClass = currentClass.getSuperclass();
        }

        context.builder.append(tabs.toString().substring(4));
        context.builder.append("}");
        context.rowCount--;
        context.maxRecursions--;
    }

    private static void printValue(Object value, PrinterContext context) {
        Class<?> currentClass = value.getClass();

        if (value == null) {
            context.builder.append("<NULL>");
        } else if (currentClass.isPrimitive() || Primitives.isWrapperType(currentClass)) {
            context.builder.append(value.toString());
        } else if (Map.class.isAssignableFrom(currentClass)) {

        } else if (currentClass.isArray()) {

        } else {
            if (context.maxRecursions < 0) {
                context.builder.append("<Reached max recursion depth>");
                return;
            }

            print(value, context);
        }
    }

    protected void printMap(Object value, PrinterContext context) {

    }

    protected void printArray(Object value, PrinterContext context) {

    }

    protected void printIterables(Object value, PrinterContext context) {

    }
}
