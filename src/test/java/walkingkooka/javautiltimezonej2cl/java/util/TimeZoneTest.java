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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class TimeZoneTest  extends TimeZoneTestCase<TimeZone> {

    // constants........................................................................................................

    @Test
    public void testShort() {
        assertEquals(java.util.TimeZone.SHORT, TimeZone.SHORT);
    }

    @Test
    public void testLong() {
        assertEquals(java.util.TimeZone.LONG, TimeZone.LONG);
    }

    // getAvailableIDs..................................................................................................

    @Test
    public void testGetAvailableIDs() {
        final String[] ids = TimeZone.getAvailableIDs();
        final Set<String> uniques = new HashSet<>(Arrays.asList(ids));

        assertEquals(uniques.size(),
                ids.length,
                () -> "ids contains duplicates ");
    }

    // getTimeZone......................................................................................................

    @Test
    public void testGetTimeZoneNullFails() {
        assertThrows(NullPointerException.class, () -> TimeZone.getTimeZone(null));
    }

    @Test
    public void testGetTimeZoneUnknownNull() {
        assertEquals(null, TimeZone.getTimeZone("unknown123"));
    }

    @Test
    public void testGetTimeZone() {
        final String hobart = "Australia/Hobart";
        final TimeZone timeZone = TimeZone.getTimeZone(hobart);
        assertEquals(hobart, timeZone.getID(), "id");
    }

    // default .........................................................................................................

    @Test
    public void testDefaultTimeZoneWithoutSystemPropertyFails() {
        System.clearProperty(TimeZone.DEFAULT_TIMEZONE_SYSTEM_PROPERTY);
        TimeZone.DEFAULT = null;

        assertThrows(IllegalStateException.class, () -> TimeZone.getDefault());
    }

    @Test
    public void testDefaultTimeZoneEmptySystemPropertyFails() {
        System.setProperty(TimeZone.DEFAULT_TIMEZONE_SYSTEM_PROPERTY, "");
        TimeZone.DEFAULT = null;

        assertThrows(IllegalStateException.class, () -> TimeZone.getDefault());
    }

    @Test
    public void testGetDefault() {
        final String hobart = "Australia/Hobart";
        System.setProperty(TimeZone.DEFAULT_TIMEZONE_SYSTEM_PROPERTY, hobart);
        TimeZone.DEFAULT = null;

        final TimeZone timeZone = TimeZone.getDefault();
        assertEquals(hobart, timeZone.getID(), "id");
    }

    @Test
    public void testSetDefaultNullTimeZoneFails() {
        assertThrows(NullPointerException.class, () -> TimeZone.setDefault(null));
    }

    @Test
    public void testSetDefault() {
        final String hobart = "Australia/Hobart";
        System.setProperty(TimeZone.DEFAULT_TIMEZONE_SYSTEM_PROPERTY, hobart);

        final TimeZone newDefault = new SimpleTimeZone(123, "newDefault1");
        TimeZone.setDefault(newDefault);
        assertSame(newDefault, TimeZone.getDefault());
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<TimeZone> type() {
        return TimeZone.class;
    }
}
