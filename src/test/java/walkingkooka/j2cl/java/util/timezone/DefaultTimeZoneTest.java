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
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.j2cl.locale.GregorianCalendar;
import walkingkooka.j2cl.locale.TimeZoneCalendar;
import walkingkooka.j2cl.locale.org.threeten.bp.zone.ZoneRules;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.CharSequences;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class DefaultTimeZoneTest extends TimeZoneTestCase<DefaultTimeZone>
        implements ToStringTesting<DefaultTimeZone> {

    @Test
    public void testAllConstructorsVisibility() {
    }

    @Test
    public void testIfClassIsFinalIfAllConstructorsArePrivate() {
    }

    @Test
    public void testAllMethodsVisibility() {
        // complains that DefaultTimeZone.timeZoneCalendar
    }

    @Test
    public void testTimeZoneProviderLocalesFilter() {
        assertEquals(",EN-*,DE-DE", TimeZoneProvider.ANNOTATION_PROCESSOR_LOCALES_FILTER);
    }

    @Test
    public void testTimeZoneProviderSelectedLocales() {
        assertEquals(Lists.empty(),
                Arrays.stream(TimeZoneProvider.SELECTED_LOCALES.split(","))
                        .filter(tag -> {
                            return (false == (tag.startsWith("en-") || tag.equals("de-DE")));
                        })
                        .collect(Collectors.toList()),
                "invalid locales");
    }

    // timeZoneCalendar.....................................................................................................

    @Test
    public void testtimeZoneCalendarENAU() {
        timeZoneCalendarAndCheck("Australia/Sydney", "EN-AU");
    }

    @Test
    public void testtimeZoneCalendarENNZ() {
        timeZoneCalendarAndCheck("Australia/Sydney", "EN-NZ");
    }

    @Test
    public void testtimeZoneCalendarDEDE() {
        timeZoneCalendarAndCheck("Australia/Sydney", "DE-DE");
    }

    private void timeZoneCalendarAndCheck(final String timeZoneId, final String... locales) {
        final DefaultTimeZone defaultTimeZone = DefaultTimeZone.getDefaultTimeZone(timeZoneId);
        final java.util.TimeZone jreTimeZone = java.util.TimeZone.getTimeZone(timeZoneId);

        for (final String languageTag : locales) {
            final Locale locale = Locale.forLanguageTag(languageTag);
            final TimeZoneCalendar data = defaultTimeZone.timeZoneCalendar(locale);

            final Calendar calendar = Calendar.getInstance(jreTimeZone, locale);
            assertEquals(calendar.getFirstDayOfWeek(), data.firstDayOfWeek, () -> "firstDayOfWeek timeZone: " + timeZoneId + " locale: " + locale);
            assertEquals(calendar.getMinimalDaysInFirstWeek(), data.minimalDaysInFirstWeek, () -> "minimalDaysInFirstWeek timeZone: " + timeZoneId + " locale: " + locale);
        }
    }

    // getOffset........................................................................................................

    @Test
    public void testGetOffsetEraInvalidFails() {
        this.getOffsetFails(Integer.MAX_VALUE, 1999, 12, 31, 1, 9999);
    }

    @Test
    public void testGetOffsetEraBC() {
        this.getOffsetFails(GregorianCalendar.BC, 1999, 12, 31, 1, 9999);
    }

    @Test
    public void testGetOffsetEraAD() {
        this.getOffsetFails(GregorianCalendar.AD, 1999, 12, 31, 1, 9999);
    }

    @Test
    public void testGetOffsetMonthInvalid() {
        this.getOffsetFails(GregorianCalendar.AD, 1999, -1, 31, 1, 9999);
    }

    @Test
    public void testGetOffsetMonthInvalid2() {
        this.getOffsetFails(GregorianCalendar.AD, 1999, 13, 31, 1, 9999);
    }

    @Test
    public void testGetOffsetDayInvalid() {
        this.getOffsetFails(GregorianCalendar.AD, 1999, 0, -1, 1, 9999);
    }

    @Test
    public void testGetOffsetDayInvalid2() {
        this.getOffsetFails(GregorianCalendar.AD, 1999, 0, 32, 1, 9999);
    }

    private void getOffsetFails(final int era,
                                final int year,
                                final int month,
                                final int day,
                                final int dayOfWeek,
                                final int time) {
        assertThrows(IllegalArgumentException.class,
                () -> DefaultTimeZone.getDefaultTimeZone("Australia/Sydney").getOffset(era, year, month, day, dayOfWeek, time));
    }

    // getOffset........................................................................................................

    @Test
    public void testGetOffset5IntAustraliaSydney202005181432() {
        this.getOffset5IntAndCheck("Australia/Sydney",
                GregorianCalendar.AD,
                2020,
                Calendar.MAY,
                18,
                Calendar.MONDAY, // day of Week guessed
                0);
    }

    @Test
    public void testGetOffset5IntEuropeParis202005181432() {
        this.getOffset5IntAndCheck("Australia/Perth",
                GregorianCalendar.AD,
                2020,
                Calendar.MAY,
                18,
                Calendar.MONDAY, // day of Week guessed
                0);
    }

    private void getOffset5IntAndCheck(final String timeZone,
                                       final int era,
                                       final int year,
                                       final int month,
                                       final int day,
                                       final int dayOfWeek,
                                       final int time) {
        assertEquals(java.util.TimeZone.getTimeZone(timeZone)
                        .getOffset(era, year, month, day, dayOfWeek, time),
                TimeZone.getTimeZone(timeZone)
                        .getOffset(era, year, month, day, dayOfWeek, time),
                () -> "TimeZone " + CharSequences.quote(timeZone) + " getOffset(era=" + era + ", year=" + year + ", month=" + month + ", day=" + day + ", dayOfWeek=" + dayOfWeek + ", time=" + time + ")");
    }

    // getDisplayName....................................................................................................

    @Test
    public void testGetDisplayNameInvalidStyleFails() {
        assertThrows(IllegalArgumentException.class,
                () -> TimeZone.getTimeZone("Australia/Sydney")
                        .getDisplayName(false, 2, Locale.ENGLISH));
    }

    @Test
    public void testGetDisplayNameAustraliaSydneyEnglish() {
        this.getDisplayNameAndCheck("Australia/Sydney", Locale.ENGLISH);
    }

    @Test
    public void testGetDisplayNameAustraliaAdelaideEnglish() {
        this.getDisplayNameAndCheck("Australia/Adelaide", Locale.ENGLISH);
    }

    @Test
    public void testGetDisplayNameAustraliaAdelaideGerman() {
        this.getDisplayNameAndCheck("Australia/Adelaide", Locale.forLanguageTag("DE-DE"));
    }

    @Test
    public void testGetDisplayNameAll() {
        for (final String zoneId : TimeZone.getAvailableIDs()) {
            for (final walkingkooka.j2cl.java.util.locale.Locale locale : walkingkooka.j2cl.java.util.locale.Locale.getAvailableLocales()) {
                final String tag = locale.toLanguageTag();
                if(tag.equals("") || locale.getLanguage().equals("en") || tag.equals("de-DE")) {
                    this.getDisplayNameAndCheck(zoneId, Locale.forLanguageTag(tag));
                    continue;
                }
                throw new AssertionError("Unexpected locale " + CharSequences.quote(tag) + " LocaleProvider");
            }
        }
    }

    private void getDisplayNameAndCheck(final String zoneId,
                                        final Locale locale) {
        final TimeZone emulated = TimeZone.getTimeZone(zoneId);
        final java.util.TimeZone jre = java.util.TimeZone.getTimeZone(zoneId);

        this.getDisplayNameAndCheck0(emulated,
                jre,
                false,
                TimeZone.SHORT,
                locale);
        this.getDisplayNameAndCheck0(emulated,
                jre,
                true,
                TimeZone.SHORT,
                locale);
        this.getDisplayNameAndCheck0(emulated,
                jre,
                false,
                TimeZone.LONG,
                locale);
        this.getDisplayNameAndCheck0(emulated,
                jre,
                true,
                TimeZone.LONG,
                locale);
    }

    private void getDisplayNameAndCheck0(final TimeZone emulated,
                                         final java.util.TimeZone jre,
                                         final boolean daylight,
                                         final int style,
                                         final Locale locale) {
        assertEquals(jre.getDisplayName(daylight, style, locale),
                emulated.getDisplayName(daylight, style, locale),
                () -> "getDisplayName zoneId=" + emulated.getID() + " daylight=" + daylight + " style=" + (java.util.TimeZone.SHORT == style ? "SHORT" : "LONG") + " locale=" + locale);
    }

    // useDaylightTime..................................................................................................

    @Test
    public void testUseDaylightTimeAustraliaSydney() throws Exception {
        this.useDaylightTimeAndCheck("Australia/Sydney");
    }

    @Test
    public void testUseDaylightTimeAustraliaPerth() throws Exception {
        this.useDaylightTimeAndCheck("Australia/Perth");
    }

    private void useDaylightTimeAndCheck(final String zoneId) throws Exception {
        assertEquals(java.util.TimeZone.getTimeZone(zoneId).useDaylightTime(),
                ZoneRules.of(ZoneId.of(zoneId)).useDaylightTime(),
                () -> "useDaylightTime for zoneId " + CharSequences.quoteAndEscape(zoneId));
    }

    // ToString.........................................................................................................

    @Test
    public void testToString() {
        final String id = "Australia/Sydney";
        this.toStringAndCheck(DefaultTimeZone.getDefaultTimeZone(id), id);
    }

    @Test
    public void testToString2() {
        final String id = "Australia/Melbourne";
        this.toStringAndCheck(DefaultTimeZone.getDefaultTimeZone(id), id);
    }

    // ClassTesting2....................................................................................................

    @Override
    public Class<DefaultTimeZone> type() {
        return DefaultTimeZone.class;
    }

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
