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

import com.captainbern.reflection.accessor.FieldAccessor;
import com.captainbern.reflection.conversion.Converter;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author CaptainBern
 */
public interface SafeField<T> extends SafeMember, Serializable {

    public Field member();

    public FieldAccessor<T> getAccessor();

    public SafeField<T> withConverter(Converter<T> converter);

    public Converter<T> getConverter();
}
