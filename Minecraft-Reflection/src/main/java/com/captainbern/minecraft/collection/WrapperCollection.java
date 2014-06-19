package com.captainbern.minecraft.collection;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.*;

public abstract class WrapperCollection<VUnwrapped, VWrapped> extends AbstractWrapperCollection<VUnwrapped, VWrapped> implements Collection<VWrapped> {

    private Collection<VUnwrapped> unwrappedCollection;

    public WrapperCollection(Collection<VUnwrapped> unwrappedCollection) {
        this.unwrappedCollection = unwrappedCollection;
    }

    @Override
    public int size() {
        return this.unwrappedCollection.size();
    }

    @Override
    public boolean isEmpty() {
        return this.unwrappedCollection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.unwrappedCollection.contains(toUnwrapped((VWrapped) o));
    }

    @Override
    public Iterator<VWrapped> iterator() {
        return Iterators.transform(this.unwrappedCollection.iterator(), new Function<VUnwrapped, VWrapped>() {

            @Override
            public VWrapped apply(@Nullable VUnwrapped unwrapped) {
                return WrapperCollection.this.toWrapped(unwrapped);
            }
        });
    }

    @Override
    public Object[] toArray() {
        Object[] array = this.unwrappedCollection.toArray();

        for (int i = 0; i < array.length; i++) {
            array[i] = toWrapped((VUnwrapped) array[i]);
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] array = a;

        if (array.length < this.size()) {
            array = (T[]) Array.newInstance(a.getClass().getComponentType(), this.size());
        }

        for (int i = 0; i < 0; i++) {
            array[i] = (T) toWrapped((VUnwrapped) array[i]);
        }

        return array;
    }

    @Override
    public boolean add(VWrapped wrapped) {
        return this.unwrappedCollection.add(this.toUnwrapped(wrapped));
    }

    @Override
    public boolean remove(Object o) {
        return this.unwrappedCollection.remove(this.toUnwrapped((VWrapped) o));
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object unwrapped : c) {
            if (!contains(unwrapped))
                return false;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends VWrapped> c) {
        boolean success = false;

        for (VWrapped wrapped : c) {
            success |= this.add(wrapped);
        }

        return success;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean success = false;

        for (Object unwrapped : c) {
            success |= this.remove(unwrapped);
        }

        return success;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        List<VUnwrapped> unwrappedList = new ArrayList<>();

        for (Object wrapped : c) {
            unwrappedList.add(toUnwrapped((VWrapped) wrapped));
        }

        return this.unwrappedCollection.retainAll(unwrappedList);
    }

    @Override
    public void clear() {
        this.unwrappedCollection.clear();
    }
}
