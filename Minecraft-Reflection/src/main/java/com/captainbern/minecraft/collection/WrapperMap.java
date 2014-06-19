package com.captainbern.minecraft.collection;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class WrapperMap<K, VUnwrapped, VWrapped> extends AbstractWrapperCollection<VUnwrapped, VWrapped> implements Map<K, VWrapped> {

    private Map<K, VUnwrapped> unwrappedMap;

    public WrapperMap(Map<K, VUnwrapped> unwrappedMap) {
        this.unwrappedMap = unwrappedMap;
    }

    @Override
    public int size() {
        return this.unwrappedMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.unwrappedMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.unwrappedMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.unwrappedMap.containsKey(toUnwrapped((VWrapped) value));
    }

    @Override
    public VWrapped get(Object key) {
        return toWrapped(this.unwrappedMap.get(key));
    }

    @Override
    public VWrapped put(K key, VWrapped value) {
        return toWrapped(this.unwrappedMap.put(key, toUnwrapped(value)));
    }

    @Override
    public VWrapped remove(Object key) {
        return toWrapped(this.unwrappedMap.remove(key));
    }

    @Override
    public void putAll(Map<? extends K, ? extends VWrapped> m) {
        for (Entry<? extends K, ? extends VWrapped> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        this.unwrappedMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.unwrappedMap.keySet();
    }

    @Override
    public Collection<VWrapped> values() {
        return Collections2.transform(entrySet(), new Function<Entry<K, VWrapped>, VWrapped>() {
            @Override
            public VWrapped apply(@Nullable java.util.Map.Entry<K, VWrapped> entry) {
                return entry.getValue();
            }
        });
    }

    @Override
    public Set<Entry<K, VWrapped>> entrySet() {
        return null;
    }
}
