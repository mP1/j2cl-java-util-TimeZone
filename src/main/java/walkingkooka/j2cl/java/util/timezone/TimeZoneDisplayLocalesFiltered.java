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

import walkingkooka.ToStringBuilder;
import walkingkooka.j2cl.locale.TimeZoneDisplay;

import java.util.List;
import java.util.Locale;

final class TimeZoneDisplayLocalesFiltered extends TimeZoneDisplayLocales {

    static TimeZoneDisplayLocalesFiltered with(final TimeZoneDisplay display, final List<Locale> locales) {
        return new TimeZoneDisplayLocalesFiltered(display, locales);
    }

    private TimeZoneDisplayLocalesFiltered(final TimeZoneDisplay display, final List<Locale> locales) {
        super(display);
        this.locales = locales;
    }

    @Override
    boolean contains(final Locale locale) {
        return this.locales.contains(locale);
    }

    final List<Locale> locales;

    @Override
    public String toString() {
        return ToStringBuilder.empty()
                .value(this.display)
                .value(this.locales)
                .build();
    }
}
