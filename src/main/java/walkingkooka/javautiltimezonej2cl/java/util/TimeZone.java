/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package walkingkooka.javautiltimezonej2cl.java.util;

import walkingkooka.text.CharSequences;

import java.util.Date;
import java.util.Objects;

public abstract class TimeZone {

    /**
     * The SHORT display name style.
     */
    public static final int SHORT = 0;

    /**
     * The LONG display name style.
     */
    public static final int LONG = 1;

    // available ids....................................................................................................

    /**
     * Gets the available time zone IDs. Any one of these IDs can be passed to
     * {@code get()} to create the corresponding {@code TimeZone} instance.
     *
     * @return an array of time zone ID strings.
     */
    public static synchronized String[] getAvailableIDs() {
        return SimpleTimeZone.getAvailableIDs0();
    }

    /**
     * Gets the available time zone IDs which match the specified offset from
     * GMT. Any one of these IDs can be passed to {@code get()} to create the corresponding
     * {@code TimeZone} instance.
     *
     * @param offset the offset from GMT in milliseconds.
     * @return an array of time zone ID strings.
     */
    public static synchronized String[] getAvailableIDs(int offset) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the {@code TimeZone} with the specified ID.
     *
     * @param name a time zone string ID.
     * @return the {@code TimeZone} with the specified ID or null if no {@code TimeZone} with
     * the specified ID exists.
     */
    public static TimeZone getTimeZone(final String name) {
        return SimpleTimeZone.getTimeZone0(name);
    }

    // default .........................................................................................................

    /**
     * Gets the default time zone.
     *
     * @return the default time zone.
     */
    public static synchronized TimeZone getDefault() {
        if(null == DEFAULT) {
            DEFAULT = getTimeZoneFromSystemProperty();
        }
        return DEFAULT;
    }

    /**
     * Sets the default time zone. If passed {@code null}, then the next
     * time {@link #getDefault} is called, the default time zone will be
     * determined. This behavior is slightly different than the canonical
     * description of this method, but it follows the spirit of it.
     *
     * @param timezone a {@code TimeZone} object.
     */
    public static synchronized void setDefault(final TimeZone timezone) {
        Objects.requireNonNull(timezone, "timezone");

        DEFAULT = timezone;
    }

    // @VisibleForTesting
    static TimeZone DEFAULT;

    static String DEFAULT_TIMEZONE_SYSTEM_PROPERTY = "walkingkooka.javautiltimezonej2cl.TimeZone";

    private static TimeZone getTimeZoneFromSystemProperty() {
        final String defaultTimeZone = System.getProperty(DEFAULT_TIMEZONE_SYSTEM_PROPERTY);
        if (CharSequences.isNullOrEmpty(defaultTimeZone)) {
            throw new IllegalStateException("Default timezone system property " + CharSequences.quote(DEFAULT_TIMEZONE_SYSTEM_PROPERTY) + " missing");
        }
        return getTimeZone(defaultTimeZone);
    }

    // ctor.............................................................................................................

    TimeZone(final String id,
             final int rawOffset) {
        super();
        this.id = id;
        this.rawOffset = rawOffset;
    }


    /**
     * Gets the ID of this {@code TimeZone}.
     *
     * @return the time zone ID string.
     */
    public final String getID() {
        return id;
    }

    final String id;

    /**
     * Gets the daylight savings offset in milliseconds for this {@code TimeZone}.
     * <p>
     * This implementation returns 3600000 (1 hour), or 0 if the time zone does
     * not observe daylight savings.
     * <p>
     * Subclasses may override to return daylight savings values other than 1
     * hour.
     * <p>
     *
     * @return the daylight savings offset in milliseconds if this {@code TimeZone}
     * observes daylight savings, zero otherwise.
     */
    public final int getDSTSavings() {
        return this.useDaylightTime() ?
                TimeZones.ONE_HOUR :
                0;
    }

    /**
     * Gets the offset from GMT of this {@code TimeZone} for the specified date. The
     * offset includes daylight savings time if the specified date is within the
     * daylight savings time period.
     *
     * @param time the date in milliseconds since January 1, 1970 00:00:00 GMT
     * @return the offset from GMT in milliseconds.
     */
    public final int getOffset(final long time) {
        final int offset = this.getRawOffset();

        return this.inDaylightTime(new Date(time)) ?
                offset + getDSTSavings() :
                offset;
    }

    /**
     * Gets the offset from GMT of this {@code TimeZone} for the specified date and
     * time. The offset includes daylight savings time if the specified date and
     * time are within the daylight savings time period.
     *
     * @param era       the {@code GregorianCalendar} era, either {@code GregorianCalendar.BC} or
     *                  {@code GregorianCalendar.AD}.
     * @param year      the year.
     * @param month     the {@code Calendar} month.
     * @param day       the day of the month.
     * @param dayOfWeek the {@code Calendar} day of the week.
     * @param time      the time of day in milliseconds.
     * @return the offset from GMT in milliseconds.
     */
    abstract public int getOffset(int era, int year, int month, int day,
                                  int dayOfWeek, int time);

    /**
     * Gets the offset for standard time from GMT for this {@code TimeZone}.
     *
     * @return the offset from GMT in milliseconds.
     */
    public final int getRawOffset() {
        return this.rawOffset;
    }

    /**
     * The raw offset.
     */
    final int rawOffset;

    /**
     * Returns whether the specified {@code TimeZone} has the same raw offset as this
     * {@code TimeZone}.
     *
     * @param other a {@code TimeZone}.
     * @return {@code true} when the {@code TimeZone} have the same raw offset, {@code false}
     * otherwise.
     */
    public final boolean hasSameRules(final TimeZone other) {
        return null != other && this.getRawOffset() == other.getRawOffset();
    }

    /**
     * Returns whether the specified {@code Date} is in the daylight savings time period for
     * this {@code TimeZone}.
     *
     * @param time a {@code Date}.
     * @return {@code true} when the {@code Date} is in the daylight savings time period, {@code false}
     * otherwise.
     */
    abstract public boolean inDaylightTime(Date time);

    /**
     * Returns whether this {@code TimeZone} has a daylight savings time period.
     *
     * @return {@code true} if this {@code TimeZone} has a daylight savings time period, {@code false}
     * otherwise.
     */
    abstract public boolean useDaylightTime();
}
