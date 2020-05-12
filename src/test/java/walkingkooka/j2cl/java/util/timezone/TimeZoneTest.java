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

package walkingkooka.j2cl.java.util.timezone;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.JavaVisibility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    // getAvailableIDs..................................................................................................

    @Test
    public void testGetAvailableIDsWithOffset() {
        final int offset = 10;
        final String[] ids = TimeZone.getAvailableIDs(offset);
        assertNotEquals(0, ids.length, "expected some ids with offset " + offset);

        final List<TimeZone> timeZones = Arrays.stream(ids)
                .map(TimeZone::getTimeZone)
                .filter(t -> t.getRawOffset() == offset)
                .collect(Collectors.toList());

        assertEquals(Lists.empty(),
                timeZones,
                () -> "some ids have the wrong offset");
    }

    @Test
    public void testGetAvailableIDsWithOffsetNone() {
        final int offset = 0;
        final String[] ids = TimeZone.getAvailableIDs(offset);
        assertArrayEquals(new String[0], ids, () -> "expected NO ids with offset " + offset);
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

        final TimeZone newDefault = TimeZone.getTimeZone(hobart);
        TimeZone.setDefault(newDefault);
        assertSame(newDefault, TimeZone.getDefault());
    }

    // getDisplay.......................................................................................................

    @Test
    public void testGetDisplayNameAustraliaSydneyEnAU() {
        this.getDisplayAndCheck("Australia/Sydney", "EN-AU");
    }

    @Test
    public void testGetDisplayNameAustraliaAdelaideEnAU() {
        this.getDisplayAndCheck("Australia/Adelaide", "EN-AU");
    }

    @Test
    public void testGetDisplayNameAustraliaSydneyDE() {
        this.getDisplayAndCheck("Australia/Sydney", "DE-DE");
    }

    @Test
    public void testAll() {
        for (final String timeZoneId : TimeZone.getAvailableIDs()) {
            final TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
            for (final walkingkooka.j2cl.java.util.locale.Locale locale : walkingkooka.j2cl.java.util.locale.Locale.getAvailableLocales()) {
                getDisplayAndCheck(timeZone, Locale.forLanguageTag(locale.toLanguageTag()));
            }
        }
    }


    private void getDisplayAndCheck(final String timeZoneId,
                                    final String locale) {
        this.getDisplayAndCheck(TimeZone.getTimeZone(timeZoneId),
                Locale.forLanguageTag(locale));
    }

    private void getDisplayAndCheck(final TimeZone timeZone,
                                    final Locale locale) {
        getDisplayAndCheck(timeZone, TimeZone.SHORT, false, locale);
        getDisplayAndCheck(timeZone, TimeZone.SHORT, true, locale);
        getDisplayAndCheck(timeZone, TimeZone.LONG, false, locale);
        getDisplayAndCheck(timeZone, TimeZone.LONG, true, locale);
    }


    private void getDisplayAndCheck(final TimeZone timeZone,
                                    final int style,
                                    final boolean daylight,
                                    final Locale locale) {
        final java.util.TimeZone real = java.util.TimeZone.getTimeZone(timeZone.getID());
        assertEquals(real.getDisplayName(daylight, style, locale),
                timeZone.getDisplayName(daylight, style, locale),
                () -> "getDisplayName daylight=" + daylight + " style=" + (TimeZone.SHORT == style ? "SHORT" : "LONG") + " locale=" + locale);
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<TimeZone> type() {
        return TimeZone.class;
    }

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
