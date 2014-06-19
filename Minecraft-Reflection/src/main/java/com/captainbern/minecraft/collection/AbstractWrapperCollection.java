package com.captainbern.minecraft.collection;

public abstract class AbstractWrapperCollection<VUnwrapped, VWrapped> {

    public abstract VWrapped toWrapped(VUnwrapped unwrapped);

    public abstract VUnwrapped toUnwrapped(VWrapped wrapped);

}
