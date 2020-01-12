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
}
