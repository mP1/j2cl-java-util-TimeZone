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

package walkingkooka.j2cl.java.util.timezone.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import walkingkooka.collect.set.Sets;
import walkingkooka.j2cl.java.util.locale.support.MultiLocaleValue;
import walkingkooka.j2cl.java.util.timezone.zonerulesreader.org.threeten.bp.zone.StandardZoneRules;
import walkingkooka.j2cl.locale.LocaleAware;
import walkingkooka.j2cl.locale.TimeZoneCalendar;
import walkingkooka.j2cl.locale.TimeZoneDisplay;
import walkingkooka.j2cl.locale.TimeZoneOffsetAndDaylightSavings;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@LocaleAware
public final class TimeZoneProviderReaderTest implements ClassTesting<TimeZoneProviderReader> {

    @Test
    public void testConsumeTimeZoneProviderData() {
        new TimeZoneProviderReader<TimeZoneOffsetAndDaylightSavings>() {

            @Override
            public StandardZoneRules readZoneRules(final DataInput data) throws IOException {
                return StandardZoneRules.readExternal(data);
            }

            @Override
            public void record(final String id,
                               final int rawOffset,
                               final TimeZoneOffsetAndDaylightSavings zoneRules,
                               final List<MultiLocaleValue<TimeZoneCalendar>> timeZoneCalendar,
                               final List<MultiLocaleValue<TimeZoneDisplay>> allDisplayLocales) {
                Assertions.assertEquals(true, ids.add(id));
            }

            private final Set<String> ids = Sets.ordered();
        }.read(walkingkooka.j2cl.java.util.timezone.generated.TimeZoneProvider.DATA);
    }

    @Override
    public Class<TimeZoneProviderReader> type() {
        return TimeZoneProviderReader.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
