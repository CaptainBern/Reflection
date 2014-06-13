package com.captainbern.minecraft.protocol;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FieldIterator<T> implements Iterable<T> {

    protected BiMap<T, String> values = HashBiMap.create();

    public FieldIterator(Class<T> type) {
        registerFields(type);
    }

    protected void registerFields(Class<T> clazz) {
        try {
            for (Field entry : this.getClass().getFields()) {
                if (Modifier.isStatic(entry.getModifiers()) && clazz.isAssignableFrom(entry.getType())) {
                    T value = (T) entry.get(null);
                    if (value == null) {
                        throw new IllegalArgumentException("Field " + entry + " was NULL. Remember to " +
                                "construct the object after the field has been declared.");
                    }
                    registerKeyWithValue(value, entry.getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean registerKeyWithValue(T key, String value) {
        if (!values.containsKey(key)) {
            values.put(key, value);
            return true;
        }
        return false;
    }

    public boolean hasKey(T key) {
        return values.containsKey(key);
    }

    public String getName(T key) {
        return values.get(key);
    }

    public T valueOf(String keyName) {
        return values.inverse().get(keyName);
    }

    public Set<T> values() {
        return new HashSet<T>(values.keySet());
    }

    @Override
    public Iterator<T> iterator() {
        return values.keySet().iterator();
    }
}