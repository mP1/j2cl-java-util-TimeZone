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

import java.util.Date;

public class SimpleTimeZone extends TimeZone {

    public SimpleTimeZone(final int offset,
                          final String name) {
    }

    public SimpleTimeZone(final int offset,
                          final String name,
                          final int startMonth,
                          final int startDay,
                          final int startDayOfWeek,
                          final int startTime,
                          final int endMonth,
                          final int endDay,
                          final int endDayOfWeek,
                          final int endTime) {
        this(offset,
                name,
                startMonth,
                startDay,
                startDayOfWeek,
                startTime,
                endMonth,
                endDay,
                endDayOfWeek,
                endTime,
                TimeZones.ONE_HOUR);
    }

    public SimpleTimeZone(final int offset,
                          final String name,
                          final int startMonth,
                          final int startDay,
                          final int startDayOfWeek,
                          final int startTime,
                          final int endMonth,
                          final int endDay,
                          final int endDayOfWeek,
                          final int endTime,
                          final int daylightSavings) {

    }

    public SimpleTimeZone(final int offset,
                          final String name,
                          final int startMonth,
                          final int startDay,
                          final int startDayOfWeek,
                          final int startTime,
                          final int startTimeMode,
                          final int endMonth,
                          final int endDay,
                          final int endDayOfWeek,
                          final int endTime,
                          final int endTimeMode,
                          final int daylightSavings) {
        this(offset,
                name,
                startMonth,
                startDay,
                startDayOfWeek,
                startTime,
                endMonth,
                endDay,
                endDayOfWeek,
                endTime,
                daylightSavings);
    }

    @Override
    public int getOffset(final int era,
                         final int year,
                         final int month,
                         final int day,
                         final int dayOfWeek,
                         final int time) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getRawOffset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inDaylightTime(Date time) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRawOffset(int offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean useDaylightTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the rule which specifies the start of daylight savings time.
     *
     * @param month
     *            the {@code Calendar} month in which daylight savings time starts.
     * @param dayOfMonth
     *            the {@code Calendar} day of the month on which daylight savings time
     *            starts.
     * @param time
     *            the time of day in milliseconds on which daylight savings time
     *            starts.
     */
    public void setStartRule(final int month,
                             final int dayOfMonth,
                             final int time) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the rule which specifies the start of daylight savings time.
     *
     * @param month
     *            the {@code Calendar} month in which daylight savings time starts.
     * @param day
     *            the occurrence of the day of the week on which daylight
     *            savings time starts.
     * @param dayOfWeek
     *            the {@code Calendar} day of the week on which daylight savings time
     *            starts.
     * @param time
     *            the time of day in milliseconds on which daylight savings time
     *            starts.
     */
    public void setStartRule(final int month,
                             final int day,
                             final int dayOfWeek,
                             final int time) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the rule which specifies the start of daylight savings time.
     *
     * @param month
     *            the {@code Calendar} month in which daylight savings time starts.
     * @param day
     *            the {@code Calendar} day of the month.
     * @param dayOfWeek
     *            the {@code Calendar} day of the week on which daylight savings time
     *            starts.
     * @param time
     *            the time of day in milliseconds on which daylight savings time
     *            starts.
     * @param after
     *            selects the day after or before the day of month.
     */
    public void setStartRule(final int month,
                             final int day,
                             final int dayOfWeek,
                             final int time,
                             final boolean after) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the starting year for daylight savings time in this {@code SimpleTimeZone}.
     * Years before this start year will always be in standard time.
     *
     * @param year
     *            the starting year.
     */
    public void setStartYear(final int year) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the rule which specifies the end of daylight savings time.
     *
     * @param month
     *            the {@code Calendar} month in which daylight savings time ends.
     * @param dayOfMonth
     *            the {@code Calendar} day of the month on which daylight savings time
     *            ends.
     * @param time
     *            the time of day in milliseconds standard time on which
     *            daylight savings time ends.
     */
    public void setEndRule(final int month,
                           final int dayOfMonth,
                           final int time) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the rule which specifies the end of daylight savings time.
     *
     * @param month
     *            the {@code Calendar} month in which daylight savings time ends.
     * @param day
     *            the occurrence of the day of the week on which daylight
     *            savings time ends.
     * @param dayOfWeek
     *            the {@code Calendar} day of the week on which daylight savings time
     *            ends.
     * @param time
     *            the time of day in milliseconds standard time on which
     *            daylight savings time ends.
     */
    public void setEndRule(final int month,
                           final int day,
                           final int dayOfWeek,
                           final int time) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the rule which specifies the end of daylight savings time.
     *
     * @param month
     *            the {@code Calendar} month in which daylight savings time ends.
     * @param day
     *            the {@code Calendar} day of the month.
     * @param dayOfWeek
     *            the {@code Calendar} day of the week on which daylight savings time
     *            ends.
     * @param time
     *            the time of day in milliseconds on which daylight savings time
     *            ends.
     * @param after
     *            selects the day after or before the day of month.
     */
    public void setEndRule(final int month,
                           final int day,
                           final int dayOfWeek,
                           final int time,
                           final boolean after) {
        throw new UnsupportedOperationException();
    }
}
