/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.international.jdktimezone;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Delegating TimeZone.
 * 
 * @author Ken Finnigan
 */
public abstract class ForwardingTimeZone extends TimeZone {
    /**
     * Abstract getter for the delegate
     * 
     * @return The delegate
     */
    protected abstract TimeZone delegate();

    protected ForwardingTimeZone(String id) {
        super();
        setID(id);
    }

    @Override
    public int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds) {
        return delegate().getOffset(era, year, month, day, dayOfWeek, milliseconds);
    }

    @Override
    public int getOffset(long date) {
        return delegate().getOffset(date);
    }

    @Override
    public int getRawOffset() {
        return delegate().getRawOffset();
    }

    @Override
    public String getID() {
        return delegate().getID();
    }

    @Override
    public String getDisplayName(boolean daylight, int style, Locale locale) {
        return delegate().getDisplayName(daylight, style, locale);
    }

    @Override
    public int getDSTSavings() {
        return delegate().getDSTSavings();
    }

    @Override
    public boolean useDaylightTime() {
        return delegate().useDaylightTime();
    }

    @Override
    public boolean inDaylightTime(Date date) {
        return delegate().inDaylightTime(date);
    }

    @Override
    public boolean hasSameRules(TimeZone other) {
        return delegate().hasSameRules(other);
    }

    @Override
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate().equals(obj);
    }

    @Override
    public String toString() {
        return delegate().toString();
    }

}