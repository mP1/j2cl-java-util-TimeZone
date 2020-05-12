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

import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.Sets;
import walkingkooka.j2cl.locale.TimeZoneDisplay;

import java.io.DataInput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The default {@link TimeZone} instances created from the data by {@link TimeZoneProvider#DATA}
 */
final class DefaultTimeZone extends TimeZone {

    /**
     * Intended to only be called by the static init above. A test exists to verify the {@link DataInput} is consumed
     * and further operations will fail with an {@link java.io.EOFException}.
     */
    static void register(final DataInput data) throws IOException {
        final Set<Locale> allLocales = Arrays.stream(Locale.getAvailableLocales())
                .collect(Collectors.toCollection(Sets::ordered));

        final int count = data.readInt();

        for (int z = 0; z < count; z++) {
            final String timeZoneId = data.readUTF();
            final int rawOffset = data.readInt();

            final List<TimeZoneDisplayLocales> displayLocales = Lists.array();
            final TimeZoneDisplay defaultDisplay = TimeZoneDisplay.read(data);

            final int displayCount = data.readInt();
            for (int d = 0; d < displayCount; d++) {
                final List<Locale> locales = Lists.array();
                final TimeZoneDisplayLocales displayAndLocales = TimeZoneDisplayLocales.filtered(TimeZoneDisplay.read(data), locales);

                final int localeCount = data.readInt();
                for (int ll = 0; ll < localeCount; ll++) {
                    final Locale locale = Locale.forLanguageTag(data.readUTF());
                    locales.add(locale);
                }

                displayLocales.add(displayAndLocales);
            }

            displayLocales.add(TimeZoneDisplayLocales.defaultTimeZoneDisplayLocales(defaultDisplay));
            new DefaultTimeZone(timeZoneId, rawOffset, displayLocales);
        }
    }

    /**
     * TimeZoneId to {@link DefaultTimeZone} instances.
     */
    private final static Map<String, DefaultTimeZone> ZONEID_TO_DEFAULT_TIME_ZONE = Maps.sorted();

    /**
     * Gets the {@code TimeZone} with the specified ID.
     *
     * @param name a time zone string ID.
     * @return the {@code TimeZone} with the specified ID or null if no {@code TimeZone} with
     * the specified ID exists.
     */
    static DefaultTimeZone getDefaultTimeZone(final String name) {
        return ZONEID_TO_DEFAULT_TIME_ZONE.get(name);
    }

    /**
     * Gets the available time zone IDs. Any one of these IDs can be passed to
     * {@code get()} to create the corresponding {@code TimeZone} instance.
     *
     * @return an array of time zone ID strings.
     */
    static String[] getDefaultTimeZoneAvailableIDs() {
        return ZONEID_TO_DEFAULT_TIME_ZONE.keySet()
                .stream()
                .toArray(String[]::new);
    }

    /**
     * Gets the available time zone IDs which match the specified offset from
     * GMT. Any one of these IDs can be passed to {@code get()} to create the corresponding
     * {@code TimeZone} instance.
     *
     * @param offset the offset from GMT in milliseconds.
     * @return an array of time zone ID strings.
     */
    static synchronized String[] getDefaultTimeZoneAvailableIDsWithRawOffset(final int offset) {
        return ZONEID_TO_DEFAULT_TIME_ZONE.values()
                .stream()
                .filter(d -> d.getRawOffset() == offset)
                .map(DefaultTimeZone::getID)
                .toArray(String[]::new);
    }

    /**
     * Constructor only called by {@link #register(DataInput)}.
     */
    private DefaultTimeZone(final String id,
                            final int rawOffset,
                            final List<TimeZoneDisplayLocales> allDisplayLocales) {
        super(id, rawOffset);
        this.allDisplayLocales = allDisplayLocales;

        ZONEID_TO_DEFAULT_TIME_ZONE.put(id, this);
    }

    @Override
    public String getDisplayName(final boolean daylightTime,
                                 final int style,
                                 final Locale locale) {
        final TimeZoneDisplayLocales displayLocales = allDisplayLocales.stream()
                .filter(d -> d.contains(locale))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException());

        final TimeZoneDisplay display = displayLocales.display;
        final String text;

        switch (style) {
            case SHORT:
                text = daylightTime ? display.shortDisplayNameDaylight : display.shortDisplayName;
                break;
            case LONG: // if not SHORT must be LONG.
                text = daylightTime ? display.longDisplayNameDaylight : display.longDisplayName;
                break;
            default:
                throw new IllegalArgumentException("Invalid style=" + style);
        }

        return text;
    }

    private final List<TimeZoneDisplayLocales> allDisplayLocales;

    @Override
    public int getOffset(int era, int year, int month, int day, int dayOfWeek, int time) {
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

    @Override
    public String toString() {
        return this.getID();
    }
}
