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

import walkingkooka.collect.list.Lists;
import walkingkooka.j2cl.java.io.string.StringDataInputDataOutput;
import walkingkooka.j2cl.java.util.locale.support.LocaleSupport;
import walkingkooka.j2cl.java.util.locale.support.MultiLocaleValue;
import walkingkooka.j2cl.locale.TimeZoneCalendar;
import walkingkooka.j2cl.locale.TimeZoneDisplay;
import walkingkooka.predicate.Predicates;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Consumes {@link TimeZoneProvider#DATA} calling a method with each record. {@link walkingkooka.j2cl.java.util.timezone.DefaultTimeZone}
 * and a ZoneRUleProvider in j2cl-java-time will sub class. This class exists in this package so it is not shaded.
 */
public abstract class TimeZoneProviderReader<R> {

    protected TimeZoneProviderReader() {
        super();
    }

    public final void read(final String data) {
        try {
            this.read0(StringDataInputDataOutput.input(data));
        } catch (final IOException cause) {
            throw new Error(cause);
        }
    }

    private void read0(final DataInput data) throws IOException {
        final int count = data.readInt();

        for (int z = 0; z < count; z++) {
            final String timeZoneId = data.readUTF();
            final int rawOffset = data.readInt();

            final R zoneRules = this.readZoneRules(data);

            final List<MultiLocaleValue<TimeZoneCalendar>> timeZoneCalendar = Lists.array();
            {
                final TimeZoneCalendar calendar = TimeZoneCalendar.read(data);

                final int calendarToLocalesCount = data.readInt();
                for (int c = 0; c < calendarToLocalesCount; c++) {
                    final Set<Locale> locales = LocaleSupport.readLocales(data);
                    timeZoneCalendar.add(multiLocaleValue(TimeZoneCalendar.read(data),
                            locales::contains));
                }

                timeZoneCalendar.add(multiLocaleValue(calendar, Predicates.always())); // default goes last and matches any locale
            }

            final List<MultiLocaleValue<TimeZoneDisplay>> displayLocales = Lists.array();
            final TimeZoneDisplay defaultDisplay = TimeZoneDisplay.read(data);

            final int displayCount = data.readInt();
            for (int d = 0; d < displayCount; d++) {
                final Set<Locale> locales = LocaleSupport.readLocales(data);
                final MultiLocaleValue<TimeZoneDisplay> displayAndLocales = multiLocaleValue(TimeZoneDisplay.read(data),
                        locales::contains);
                displayLocales.add(displayAndLocales);
            }

            displayLocales.add(multiLocaleValue(defaultDisplay,
                    Predicates.always()));
            record(timeZoneId,
                    rawOffset,
                    zoneRules,
                    timeZoneCalendar,
                    displayLocales);
        }
    }

    public abstract R readZoneRules(final DataInput data) throws IOException;

    private static <T> MultiLocaleValue<T> multiLocaleValue(final T calendar,
                                                            final Predicate<Locale> locales) {
        return MultiLocaleValue.with(calendar,
                locales,
                LocaleSupport.INCLUDE_NORWAY);
    }

    /**
     * A {@link walkingkooka.j2cl.java.util.timezone.support.TimeZoneProvider} record.
     */
    public abstract void record(final String id,
                                final int rawOffset,
                                final R zoneRules,
                                final List<MultiLocaleValue<TimeZoneCalendar>> timeZoneCalendar,
                                final List<MultiLocaleValue<TimeZoneDisplay>> allDisplayLocales);
}
