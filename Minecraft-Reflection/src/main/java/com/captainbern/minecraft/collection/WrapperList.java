package com.captainbern.minecraft.collection;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public abstract class WrapperList<VUnwrapped, VWrapped> extends WrapperCollection<VUnwrapped, VWrapped> implements List<VWrapped> {

    private List<VUnwrapped> unwrappedList;

    public WrapperList(List<VUnwrapped> vUnwrapped) {
        super(vUnwrapped);
        this.unwrappedList = unwrappedList;
    }

    @Override
    public boolean addAll(int index, Collection<? extends VWrapped> c) {
        return false;
    }

    @Override
    public VWrapped get(int index) {
        return toWrapped(this.unwrappedList.get(index));
    }

    @Override
    public VWrapped set(int index, VWrapped element) {
        return toWrapped(this.unwrappedList.set(index, toUnwrapped(element)));
    }

    @Override
    public void add(int index, VWrapped element) {
        this.unwrappedList.add(index, toUnwrapped(element));
    }

    @Override
    public VWrapped remove(int index) {
        return toWrapped(this.unwrappedList.remove(index));
    }

    @Override
    public int indexOf(Object o) {
        return this.unwrappedList.indexOf(toUnwrapped((VWrapped) o));
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.unwrappedList.lastIndexOf(toUnwrapped((VWrapped) o));
    }

    @Override
    public ListIterator<VWrapped> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<VWrapped> listIterator(int index) {
        final ListIterator<VUnwrapped> unwrappedListIterator = this.unwrappedList.listIterator();

        return new ListIterator<VWrapped>() {
            @Override
            public boolean hasNext() {
                return unwrappedListIterator.hasNext();
            }

            @Override
            public VWrapped next() {
                return WrapperList.this.toWrapped(unwrappedListIterator.next());
            }

            @Override
            public boolean hasPrevious() {
                return unwrappedListIterator.hasPrevious();
            }

            @Override
            public VWrapped previous() {
                return WrapperList.this.toWrapped(unwrappedListIterator.previous());
            }

            @Override
            public int nextIndex() {
                return unwrappedListIterator.nextIndex();
            }

            @Override
            public int previousIndex() {
                return unwrappedListIterator.previousIndex();
            }

            @Override
            public void remove() {
                unwrappedListIterator.remove();
            }

            @Override
            public void set(VWrapped wrapped) {
                unwrappedListIterator.set(WrapperList.this.toUnwrapped(wrapped));
            }

            @Override
            public void add(VWrapped wrapped) {
                unwrappedListIterator.add(WrapperList.this.toUnwrapped(wrapped));
            }
        };
    }

    @Override
    public List<VWrapped> subList(int fromIndex, int toIndex) {
        return new WrapperList<VUnwrapped, VWrapped>(this.unwrappedList.subList(fromIndex, toIndex)) {
            @Override
            public VWrapped toWrapped(VUnwrapped unwrapped) {
                return WrapperList.this.toWrapped(unwrapped);
            }

            @Override
            public VUnwrapped toUnwrapped(VWrapped wrapped) {
                return WrapperList.this.toUnwrapped(wrapped);
            }
        };
    }
}
