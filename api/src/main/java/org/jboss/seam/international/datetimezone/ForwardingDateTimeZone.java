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
package org.jboss.seam.international.datetimezone;

import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTimeZone;

/**
 * Delegating TimeZone.
 * 
 * @author Ken Finnigan
 */
public abstract class ForwardingDateTimeZone extends DateTimeZone {
    /**
     * Abstract getter for the delegate
     * 
     * @return The delegate
     */
    protected abstract DateTimeZone delegate();

    protected ForwardingDateTimeZone(String id) {
        super(id);
    }

    @Override
    public long convertLocalToUTC(long instantLocal, boolean strict) {
        return delegate().convertLocalToUTC(instantLocal, strict);
    }

    @Override
    public long convertUTCToLocal(long instantUTC) {
        return delegate().convertUTCToLocal(instantUTC);
    }

    @Override
    public String getName(long instant, Locale locale) {
        return delegate().getName(instant, locale);
    }

    @Override
    public String getNameKey(long instant) {
        return delegate().getNameKey(instant);
    }

    @Override
    public int getOffset(long instant) {
        return delegate().getOffset(instant);
    }

    @Override
    public int getOffsetFromLocal(long instantLocal) {
        return delegate().getOffsetFromLocal(instantLocal);
    }

    @Override
    public String getShortName(long instant, Locale locale) {
        return delegate().getShortName(instant, locale);
    }

    @Override
    public int getStandardOffset(long instant) {
        return delegate().getStandardOffset(instant);
    }

    @Override
    public boolean isFixed() {
        return delegate().isFixed();
    }

    @Override
    public boolean isStandardOffset(long instant) {
        return delegate().isStandardOffset(instant);
    }

    @Override
    public long nextTransition(long instant) {
        return delegate().nextTransition(instant);
    }

    @Override
    public long previousTransition(long instant) {
        return delegate().previousTransition(instant);
    }

    @Override
    public TimeZone toTimeZone() {
        return delegate().toTimeZone();
    }

    /**
     * Compares an object with the delegate
     * 
     * @return True if equals, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return delegate().equals(obj);
    }

    /**
     * Gets the hash code of the delegate
     * 
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return delegate().hashCode();
    }

    /**
     * Returns a string representation
     * 
     * @return The string representation
     */
    @Override
    public String toString() {
        return delegate().toString();
    }
}
