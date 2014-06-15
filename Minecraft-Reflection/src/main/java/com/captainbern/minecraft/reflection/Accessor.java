package com.captainbern.minecraft.reflection;

import com.captainbern.minecraft.conversion.Converter;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.FieldAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Accessor<T> {

    private Object handle;

    private ClassTemplate<?> template;

    private List<FieldAccessor<?>> fields;

    private Map<Class<?>, Accessor> cache;

    private Converter<?> converter;

    public Accessor(Object handle) {
        this(handle, new Reflection().reflect(handle.getClass()));
    }

    public Accessor(Object handle, ClassTemplate<?> template) {
        this(handle, template, new ArrayList<FieldAccessor<?>>());
    }

    public Accessor(Object handle, ClassTemplate<?> template, List<FieldAccessor<?>> fields) {
        this(handle, template, fields, new HashMap<Class<?>, Accessor>());
    }

    public Accessor(Object handle, ClassTemplate<?> template, List<FieldAccessor<?>> fields, Map<Class<?>, Accessor> cache) {
         initialize(handle, template, fields, cache);
    }

    protected void initialize(Object instance, ClassTemplate<?> handle, List<FieldAccessor<?>> fields, Map<Class<?>, Accessor> cache) {
        this.handle = instance;
        this.template = handle;
        this.fields = fields;
        this.cache = cache;
    }

    public Object getHandle() {
        return this.handle;
    }

    public ClassTemplate<?> asClassTemplate() {
        return this.template;
    }

    public List<FieldAccessor<?>> getFields() {
        return this.fields;
    }

    public Map<Class<?>, Accessor> getCache() {
        return this.cache;
    }

    public Converter getConverter() {
        return this.converter;
    }

    public <TType> void setConverter(Converter<TType> converter) {
        this.converter = converter;
    }


}
