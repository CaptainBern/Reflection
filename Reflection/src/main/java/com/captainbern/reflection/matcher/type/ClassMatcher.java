package com.captainbern.reflection.matcher.type;

import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.matcher.AbstractMatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Based off the Fuzzy-matchers found in ProtocolLib
 */
public class ClassMatcher extends AbstractMatcher<Class<?>> {

    public static class Builder {
        private List<AbstractMatcher<Field>> fields = new ArrayList<>();
        private List<AbstractMatcher<Method>> methods = new ArrayList<>();
        private List<AbstractMatcher<Constructor>> constructors = new ArrayList<>();

        private List<AbstractMatcher<Class<?>>> baseClasses = new ArrayList<>();
        private List<AbstractMatcher<Class<?>>> interfaces = new ArrayList<>();

        public Builder withField(AbstractMatcher<Field> fieldMatcher) {
            this.fields.add(fieldMatcher);
            return this;
        }

        public Builder withField(FieldMatcher.Builder builder) {
            return withField(builder.build());
        }

        public Builder withMethod(AbstractMatcher<Method> methodMatcher) {
            this.methods.add(methodMatcher);
            return this;
        }

        public Builder withMethod(MethodMatcher.Builder builder) {
            return withMethod(builder.build());
        }

        public Builder withConstructor(AbstractMatcher<Constructor> constructorMatcher) {
            this.constructors.add(constructorMatcher);
            return this;
        }

        public Builder withConstructor(MemberMatcher.Builder builder) {
            return withConstructor(builder.build());
        }

        public Builder withBaseClass(AbstractMatcher<Class<?>> baseClassMatcher) {
            this.baseClasses.add(baseClassMatcher);
            return this;
        }

        public Builder withBaseClass(ClassMatcher.Builder builder) {
            return withBaseClass(builder.build());
        }

        public Builder withInterface(AbstractMatcher<Class<?>> interfaceMatcher) {
            this.interfaces.add(interfaceMatcher);
            return this;
        }

        public Builder withInterface(ClassMatcher.Builder builder) {
            return withInterface(builder.build());
        }

        public ClassMatcher build() {
            return new ClassMatcher(this);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    protected final List<AbstractMatcher<Field>> fields;
    protected final List<AbstractMatcher<Method>> methods;
    protected final List<AbstractMatcher<Constructor>> constructors;

    protected final List<AbstractMatcher<Class<?>>> baseClasses;
    protected final List<AbstractMatcher<Class<?>>> interfaces;

    private ClassMatcher(Builder builder) {
        super();
        this.fields = builder.fields;
        this.methods = builder.methods;
        this.constructors = builder.constructors;
        this.baseClasses = builder.baseClasses;
        this.interfaces = builder.interfaces;
    }

    public List<AbstractMatcher<Field>> getFieldMatchers() {
        return Collections.unmodifiableList(this.fields);
    }

    public List<AbstractMatcher<Method>> getMethodMatchers() {
        return Collections.unmodifiableList(this.methods);
    }

    public List<AbstractMatcher<Constructor>> getConstructorMatchers() {
        return Collections.unmodifiableList(this.constructors);
    }

    public List<AbstractMatcher<Class<?>>> getBaseClassMatchers() {
        return Collections.unmodifiableList(this.baseClasses);
    }

    public List<AbstractMatcher<Class<?>>> getInterfaceMatchers() {
        return Collections.unmodifiableList(this.interfaces);
    }

    @Override
    public boolean matches(Class<?> type) {
        ClassTemplate<?> template = new Reflection().reflect(type);

        return (getFieldMatchers().size() == 0 || processElements(getFieldMatchers(), template.getFields())) &&
                (getMethodMatchers().size() == 0 || processElements(getMethodMatchers(), template.getMethods())) &&
                (getConstructorMatchers().size() == 0 || processElements(getConstructorMatchers(), template.getConstructors())) &&
                (getBaseClassMatchers().size() == 0 || processElements(getBaseClassMatchers(), template.getAllSuperClasses())) &&
                (getInterfaceMatchers().size() == 0 || processElements(getInterfaceMatchers(), Arrays.asList(template.getReflectedClass().getInterfaces())));
    }

    private <T> boolean processElements(List<AbstractMatcher<T>> matchers, Collection<T> values) {
        boolean[] accepted = new boolean[matchers.size()];
        int count = accepted.length;

        // Process every value in turn
        for (T value : values) {
            int index = processValue(value, accepted, matchers);

            // See if this worked
            if (index >= 0) {
                accepted[index] = true;
                count--;
            }

            // Break early
            if (count == 0)
                return true;
        }
        return count == 0;
    }

    private <T> boolean processValue(T value, List<AbstractMatcher<T>> matchers) {
        for (int i = 0; i < matchers.size(); i++) {
            if (matchers.get(i).matches(value)) {
                return true;
            }
        }

        // No match
        return false;
    }

    private <T> int processValue(T value, boolean accepted[], List<AbstractMatcher<T>> matchers) {
        // The order matters
        for (int i = 0; i < matchers.size(); i++) {
            if (!accepted[i]) {
                AbstractMatcher<T> matcher = matchers.get(i);

                // Mark this as detected
                if (matcher.matches(value)) {
                    return i;
                }
            }
        }

        // Failure
        return -1;
    }
}
