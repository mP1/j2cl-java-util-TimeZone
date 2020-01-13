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

    SimpleTimeZone(final int offset,
                   final String name) {
        this(offset,
                name,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0);
    }

    SimpleTimeZone(final int offset,
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

    SimpleTimeZone(final int offset,
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
        this(offset,
                name,
                startMonth,
                startDay,
                startDayOfWeek,
                startTime,
                0,
                endMonth,
                endDay,
                endDayOfWeek,
                endTime,
                0,
                daylightSavings);
    }

    SimpleTimeZone(final int offset,
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
        super(name, offset);
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.startDayOfWeek = startDayOfWeek;
        this.startTime = startTime;
        this.startTimeMode = startTimeMode;

        this.endMonth = endMonth;
        this.endDay = endDay;
        this.endDayOfWeek = endDayOfWeek;
        this.endTime = endTime;
        this.endTimeMode = endTimeMode;

        this.daylightSavings = daylightSavings;
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
    public boolean useDaylightTime() {
        throw new UnsupportedOperationException();
    }

    private final int startMonth;
    private final int startDay;
    private final int startDayOfWeek;
    private final int startTime;
    private final int startTimeMode;

    private final int endMonth;
    private final int endDay;
    private final int endDayOfWeek;
    private final int endTime;
    private final int endTimeMode;

    private final int daylightSavings;
}
