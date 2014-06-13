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

import java.io.Serializable;
import java.util.List;

/**
 * @author CaptainBern
 */
public interface ClassTemplate<T> extends Access<T>, Serializable {

    /**
     * Created a new instance of this (reflected) class.
     *
     * @return
     */
    public T newInstance();

    /**
     * Returns a List of all the Super classes of this class.
     *
     * @return
     */
    public List<ClassTemplate> getSuperClasses();
}
