/*
 *  CaptainBern-Reflection-Framework contains several utils and tools
 *  to make Reflection easier.
 *  Copyright (C) 2014  CaptainBern
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

    /**
     * Returns whether or not this Class is public
     * @return
     */
    public boolean isPublic();

    /**
     * Returns whether or not this Class is private
     * @return
     */
    public boolean isPrivate();

    /**
     * Returns whether or not this Class is protected
     * @return
     */
    public boolean isProtected();

    /**
     * Returns whether or not this Class is static
     * @return
     */
    public boolean isStatic();

}
