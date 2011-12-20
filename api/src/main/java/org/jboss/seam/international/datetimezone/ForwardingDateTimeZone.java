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
package org.jboss.seam.international.datetimezone;

import java.util.Locale;
import java.util.TimeZone;

import org.jboss.solder.core.Requires;
import org.joda.time.DateTimeZone;

/**
 * Delegating TimeZone.
 *
 * @author Ken Finnigan
 */
@Requires({"org.joda.time.DateTimeZone"})
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
