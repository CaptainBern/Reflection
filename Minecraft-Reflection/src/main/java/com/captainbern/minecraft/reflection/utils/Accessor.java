package com.captainbern.minecraft.reflection.utils;

import com.captainbern.minecraft.conversion.Converter;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.accessor.FieldAccessor;

import java.lang.reflect.Field;
import java.util.*;

public class Accessor<T> {

    private Object handle;

    private ClassTemplate<?> template;

    private Class<?> fieldType;

    private List<Field> fields;

    private Map<Class, Accessor> cache;

    private Converter<T> converter;

    private Accessor() {}

    public Accessor(Object handle) {
        this(handle, new Reflection().reflect(handle.getClass()));
    }

    public Accessor(Object handle, ClassTemplate<?> template) {
        List<Field> fields = template.getFields();
        initialize(handle, template, fields, new HashMap<Class, Accessor>());
    }

    public Accessor(Object handle, ClassTemplate<?> template, List<Field> fields) {
        this(handle, template, fields, new HashMap<Class, Accessor>());
    }

    public Accessor(Object handle, ClassTemplate<?> template, List<Field> fields, Map<Class, Accessor> cache) {
        initialize(handle, template, fields, cache);
    }

    public T read(int index) {
        if (index < 0 || index > this.fields.size())
            throw new IndexOutOfBoundsException("Index out of bounds for: " + index);

        if (this.handle == null)
            throw new IllegalStateException("Handle is NULL!");

        try {
            FieldAccessor accessor = new Reflection().reflect(this.fields.get(index)).getAccessor();

            Object result = accessor.get(this.handle);
            return needConversion() ? this.converter.getWrapped(result) : (T) result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read field at index: " + index + " with accessor: " + this, e);
        }
    }

    public Accessor<T> write(int index, T value) {
        if (index < 0 || index > this.fields.size())
            throw new IndexOutOfBoundsException("Index out of bounds for: " + index);

        if (this.handle == null)
            throw new IllegalStateException("Handle is NULL!");

        SafeField safeField = new Reflection().reflect(this.fields.get(index));
        FieldAccessor accessor = safeField.getAccessor();

        Object object = needConversion() ? this.converter.getUnWrapped(safeField.member().getType(), value) : value;

        try {
            accessor.set(this.handle, object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write value: " + object + "to the field at index:" + index + " with accessor: " + this, e);
        }

        return this;
    }

    public <TType> Accessor<TType> withType(Class type) {
        return withType(type, null);
    }

    public <TType> Accessor<TType> withType(Class type, Converter<TType> converter) {
        Accessor<TType> accessor = this.cache.get(type);

        if (accessor == null) {
            LinkedList<Field> result = new LinkedList<>();

            for (Field field : this.fields) {
                if (type != null && type.isAssignableFrom(field.getType())) {
                    result.add(field);
                }
            }

            accessor = withFieldType(type, result);

            if (type != null) {
                this.cache.put(type, accessor);
            }
        }

        accessor = accessor.withHandle(handle);

        if (!Objects.equals(accessor.converter, converter)) {
            accessor = accessor.withConverter(converter);
        }

        return accessor;
    }

    public int size() {
        return this.fields.size();
    }

    protected void initialize(Object handle, ClassTemplate<?> template, List<Field> fields, Map<Class, Accessor> cache) {
        this.handle = handle;
        this.template = template;
        this.fields = fields;
        this.cache = cache;
    }

    protected void initialize(Accessor<T> other) {
        initialize(other.handle, other.template, other.fields, other.cache);
    }

    protected boolean needConversion() {
        return this.converter != null;
    }

    private <TType> Accessor<TType> withFieldType(Class<?> type, List<Field> fields) {
        Accessor<TType> accessor = new Accessor<>();
        accessor.initialize(this.handle, new Reflection().reflect(type), fields, this.cache);

        return accessor;
    }

    public Accessor<T> withHandle(Object handle) {
        Accessor<T> accessor = new Accessor<>();

        accessor.initialize(this);
        accessor.handle = handle;

        return accessor;
    }

    protected <T> Accessor<T> withConverter(Converter<T> converter) {
        Accessor accessor = withHandle(this.handle);
        accessor.setConverter(converter);
        return accessor;
    }

    private void setConverter(Converter<T> converter) {
        this.converter = converter;
    }

    public List<Field> getFields() {
        return this.fields;
    }
}
