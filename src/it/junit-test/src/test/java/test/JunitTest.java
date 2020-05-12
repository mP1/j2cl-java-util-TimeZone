/*
 * Copyright © 2020 Miroslav Pokorny
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
 */
package test;


import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Assert;
import org.junit.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.text.CharSequences;

import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

@J2clTestInput(JunitTest.class)
public class JunitTest {

    @Test
    public void testRawOffsetSydney() {
        this.getRawOffsetAndCheck("Australia/Sydney", 10);
    }

    @Test
    public void testRawOffsetPerth() {
        this.getRawOffsetAndCheck("Australia/Perth", 8);
    }

    @Test
    public void testRawOffsetMadrid() {
        this.getRawOffsetAndCheck("Europe/Madrid", 1);
    }

    private void getRawOffsetAndCheck(final String id, final int offset) {
        Assert.assertEquals("TimeZone " + CharSequences.quote(id),
                offset * 60 * 60 * 1000,
                TimeZone.getTimeZone(id).getRawOffset());
    }

    @Test
    public void testGetDisplayNameAustraliaSydneyEnAULong() {
        this.getDisplayNameAndCheck("Australia/Sydney",
                "EN-AU",
                false,
                TimeZone.LONG,
                "Australian Eastern Standard Time");
    }

    @Test
    public void testGetDisplayNameAustraliaSydneyEnAUShort() {
        this.getDisplayNameAndCheck("Australia/Sydney",
                "EN-AU",
                false,
                TimeZone.SHORT,
                "AEST");
    }

    @Test
    public void testGetDisplayNameAustraliaAdelaideEnAULong() {
        this.getDisplayNameAndCheck("Australia/Adelaide",
                "DE-DE",
                false,
                TimeZone.LONG,
                "Zentralaustralische Normalzeit");
    }

    private void getDisplayNameAndCheck(final String timeZoneId,
                                        final String locale,
                                        final boolean daylight,
                                        final int style,
                                        final String expected) {
        Assert.assertEquals(expected, TimeZone.getTimeZone(timeZoneId).getDisplayName(daylight, style, Locale.forLanguageTag(locale)));
    }

//    @Test
//    public void testTimeZoneSetDefault() {
//        final String hobart = "Australia/Hobart";
//        System.setProperty(TimeZone.DEFAULT_TIMEZONE_SYSTEM_PROPERTY, hobart);
//
//        final TimeZone newDefault = new SimpleTimeZone(123, "newDefault1");
//        TimeZone.setDefault(newDefault);
//        assertSame(newDefault, TimeZone.getDefault());
//    }
}
