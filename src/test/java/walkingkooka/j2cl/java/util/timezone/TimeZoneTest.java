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
import walkingkooka.j2cl.locale.Calendar;
import walkingkooka.javashader.ShadedClassTesting;
import walkingkooka.predicate.Predicates;
import walkingkooka.reflect.PackageName;
import walkingkooka.text.CharSequences;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class TimeZoneTest extends TimeZoneTestCase<TimeZone> implements ShadedClassTesting<TimeZone> {

    // constants........................................................................................................

    @Test
    public void testShort() {
        this.checkEquals(java.util.TimeZone.SHORT, TimeZone.SHORT);
    }

    @Test
    public void testLong() {
        this.checkEquals(java.util.TimeZone.LONG, TimeZone.LONG);
    }

    // getAvailableIDs..................................................................................................

    @Test
    public void testGetAvailableIDs() {
        final String[] ids = TimeZone.getAvailableIDs();
        final Set<String> uniques = new HashSet<>(Arrays.asList(ids));

        this.checkEquals(uniques.size(),
                ids.length,
                () -> "ids contains duplicates ");
    }

    // getAvailableIDs..................................................................................................

    @Test
    public void testGetAvailableIDsWithOffset() {
        final int offset = 10 * 60 * 60 * 1000;
        final String[] ids = TimeZone.getAvailableIDs(offset);
        this.checkNotEquals(0, ids.length, "expected some ids with offset " + offset);

        final List<TimeZone> timeZones = Arrays.stream(ids)
                .map(TimeZone::getTimeZone)
                .filter(t -> t.getRawOffset() == offset)
                .collect(Collectors.toList());

        this.checkNotEquals(Lists.empty(),
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
        this.checkEquals(null, TimeZone.getTimeZone("unknown123"));
    }

    @Test
    public void testGetTimeZone() {
        final String hobart = "Australia/Hobart";
        final TimeZone timeZone = TimeZone.getTimeZone(hobart);
        this.checkEquals(hobart, timeZone.getID(), "id");
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
        this.checkEquals(hobart, timeZone.getID(), "id");
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

    // getOffset........................................................................................................

    @Test
    public void testGetOffsetAustraliaSydney202005181432() {
        this.getOffsetAndCheck("Australia/Sydney",
                2020,
                Calendar.MAY,
                18,
                14,
                32);
    }

    @Test
    public void testGetOffsetAustraliaPerth202005181432() {
        this.getOffsetAndCheck("Australia/Perth",
                2020,
                Calendar.MAY,
                18,
                14,
                32);
    }

    private void getOffsetAndCheck(final String timeZoneId,
                                   final int year,
                                   final int month,
                                   final int day,
                                   final int hours,
                                   final int minutes) {
        final int era = GregorianCalendar.AD;
        final java.util.TimeZone jreTimeZone = java.util.TimeZone.getTimeZone(timeZoneId);

        final int dayOfWeek = LocalDate.of(year, 1 + month, day).getDayOfWeek().getValue();
        final int millis = (int)(Duration.ofHours(hours).toMillis() + Duration.ofMinutes(minutes).toMillis());

        final long jreRawOffset = jreTimeZone.getRawOffset();
        final TimeZone emulatedTimeZone = TimeZone.getTimeZone(timeZoneId);
        this.checkNotEquals(null,
                emulatedTimeZone,
                () -> "Unknown timeZoneId: " + CharSequences.quoteAndEscape(timeZoneId));

        final Date date = new Date(millis);
        {
            final long jreOffset = jreTimeZone.getOffset(era,
                    year - YEAR_BIAS,
                    month,
                    day,
                    dayOfWeek,
                    millis);
            final long emulatedOffset = emulatedTimeZone.getOffset(era,
                    year,
                    month,
                    day,
                    dayOfWeek,
                    millis);

            final Supplier<String> message = () -> CharSequences.quoteAndEscape(timeZoneId) +
                    " jreRawOffset: " + duration(jreRawOffset).toString().substring(2) +
                    " getOffset " + year + "/" + month + "/" + day + " " + hours + ":" + minutes + " dow: " + dayOfWeek + " date: " + date;

            this.checkEquals(duration(jreOffset),
                    duration(emulatedOffset),
                    message);
        }
    }

    private final static int YEAR_BIAS = 1900;

    private Duration duration(final long millis) {
        return Duration.ofMillis(millis);
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
        this.checkEquals(real.getDisplayName(daylight, style, locale),
                timeZone.getDisplayName(daylight, style, locale),
                () -> "getDisplayName daylight=" + daylight + " style=" + (TimeZone.SHORT == style ? "SHORT" : "LONG") + " locale=" + locale);
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<TimeZone> type() {
        return TimeZone.class;
    }

    // ShadedClassTesting................................................................................................

    @Override
    public final Predicate<Constructor<?>> requiredConstructors() {
        return Predicates.always();
    }

    @Override
    public final Predicate<Method> requiredMethods() {
        return (m) -> {
            final boolean required;

            switch (m.getName()) {
                case "clone":
                case "getRawOffset":
                case "observesDaylightTime":
                    required = false;
                    break;
                default:
                    required = true;
                    break;
            }

            return required;
        };
    }

    @Override
    public final Predicate<Field> requiredFields() {
        return Predicates.always();
    }

    @Override
    public final UnaryOperator<Class<?>> typeMapper() {
        return ShadedClassTesting.typeMapper(PackageName.from(this.getClass().getPackage()),
                PackageName.from(java.util.TimeZone.class.getPackage()));
    }
}
