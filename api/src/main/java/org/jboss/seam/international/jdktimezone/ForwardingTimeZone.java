/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
