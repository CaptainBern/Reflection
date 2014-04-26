package com.captainbern.reflection;

import java.lang.reflect.Member;
import java.util.List;

public interface ReflectedMember {

    /**
     * Returns the underlying member.
     * @return
     */
    public Member member();

    /**
     * Returns the name of this member.
     * @return
     */
    public String name();

    /**
     * In case the member is a method or a constructor, this will return how many arguments it takes.
     * @return
     */
    public int getArgumentCount();

    /**
     * In case the member is a method or a constructor, this will return the arguments in
     * the form of a ReflectedClass.
     * @return
     */
    public List<ReflectedClass> getArguments();

    /**
     * Returns the type of this member as a ReflectedClass.
     * @return
     */
    public ReflectedClass getType();

    /**
     * Returns the Descriptor of this Member. (eg: V();)
     * @return
     */
    public String getDescriptor();

    /**
     * Returns the modifiers of this member.
     * @return
     */
    public int getModifiers();

}
