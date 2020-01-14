/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.javautiltimezonej2cl.java.util;

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class SimpleTimeZoneTest extends TimeZoneTestCase<SimpleTimeZone>
        implements HashCodeEqualsDefinedTesting2<SimpleTimeZone>,
        ToStringTesting<SimpleTimeZone> {

    private final static int OFFSET = 1;
    private final static String NAME = "Paris";
    private final static int START_MONTH = 1;
    private final static int START_DAY = 2;
    private final static int START_DAY_OF_WEEK = 3;
    private final static int START_TIME = 4;
    private final static int START_TIME_MODE = 5;
    private final static int END_MONTH = 1;
    private final static int END_DAY = 2;
    private final static int END_DAY_OF_WEEK = 3;
    private final static int END_TIME = 4;
    private final static int END_TIME_MODE = 5;
    private final static int DAYLIGHT_SAVING = 1;

    @Test
    public void testAllConstructorsVisibility() {
    }

    @Test
    public void testIfClassIsFinalIfAllConstructorsArePrivate() {
    }

    // new.............................................................................................................

    @Test
    public void testNewIdOffset() {
        this.checkIdAndOffset(new SimpleTimeZone(OFFSET, NAME),
                NAME,
                OFFSET);
    }

    private void checkIdAndOffset(final SimpleTimeZone timeZone,
                                  final String id,
                                  final int offset) {
        assertEquals(id, timeZone.getID(), "id");
        assertEquals(offset, timeZone.getRawOffset(), "rawOffset");
    }

    // hasSameRules......................................................................................................

    @Test
    public void testHasSameRules() {
        final java.util.SimpleTimeZone jre = new java.util.SimpleTimeZone(OFFSET, NAME);
        final SimpleTimeZone emulated = new SimpleTimeZone(OFFSET, NAME);

        assertEquals(jre.hasSameRules(jre),
                emulated.hasSameRules(emulated),
                () -> jre.toString());
    }

    @Test
    public void testHasSameRulesDifferentOffset() {
        assertEquals(new java.util.SimpleTimeZone(OFFSET, NAME).hasSameRules(new java.util.SimpleTimeZone(OFFSET + 1, NAME)),
                new SimpleTimeZone(OFFSET, NAME).hasSameRules(new SimpleTimeZone(OFFSET + 1, NAME)));
    }

    @Test
    public void testHasSameRulesDifferentName() {
        assertEquals(new java.util.SimpleTimeZone(OFFSET, NAME).hasSameRules(new java.util.SimpleTimeZone(OFFSET + 1, NAME)),
                new SimpleTimeZone(OFFSET, NAME).hasSameRules(new SimpleTimeZone(OFFSET + 1, NAME)));
    }

    @Test
    public void testHasSameRulesDifferentStartMonth() {
        assertEquals(new java.util.SimpleTimeZone(OFFSET, NAME, START_MONTH, 0, 0, 0, 0, 0, 0, 0).hasSameRules(new java.util.SimpleTimeZone(OFFSET + 1, NAME)),
                new SimpleTimeZone(OFFSET, NAME, START_MONTH, 0, 0, 0, 0, 0, 0, 0).hasSameRules(new SimpleTimeZone(OFFSET + 1, NAME)));
    }

    // equals.............................................................................................................

    @Test
    public void testDifferentOffset() {
        this.checkNotEquals(new SimpleTimeZone(9 + OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentName() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                "Different",
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentStartMonth() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH + 1,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentStartDay() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY + 1,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentStartDayOfWeek() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK + 1,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentStartTime() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME + 1,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentStartTimeNode() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE + 1,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentEndMonth() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH + 1,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentEndDay() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY + 1,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentEndDayOfWeek() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK + 1,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentEndTime() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME + 1,
                END_TIME_MODE,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentEndTimeMode() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE + 1,
                DAYLIGHT_SAVING));
    }

    @Test
    public void testDifferentDaylightSavings() {
        this.checkNotEquals(new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING + 1));
    }

    // ToString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(new SimpleTimeZone(0, "Abc123"),
                "Abc123 daylightSavings=3600000");
    }

    @Test
    public void testToString2() {
        this.toStringAndCheck(this.createObject(),
                "Paris offset=1 startMonth=1 startDay=2 startDayOfWeek=3 startTime=4 startTimeMode=5 endMonth=1 endDay=2 endDayOfWeek=3 endTime=4 endTimeMode=5 daylightSavings=1");
    }

    // ClassTesting2....................................................................................................

    @Override
    public Class<SimpleTimeZone> type() {
        return SimpleTimeZone.class;
    }

    // HashCodeEqualsDefinedTesting.....................................................................................

    @Override
    public SimpleTimeZone createObject() {
        return new SimpleTimeZone(OFFSET,
                NAME,
                START_MONTH,
                START_DAY,
                START_DAY_OF_WEEK,
                START_TIME,
                START_TIME_MODE,
                END_MONTH,
                END_DAY,
                END_DAY_OF_WEEK,
                END_TIME,
                END_TIME_MODE,
                DAYLIGHT_SAVING);
    }
}
