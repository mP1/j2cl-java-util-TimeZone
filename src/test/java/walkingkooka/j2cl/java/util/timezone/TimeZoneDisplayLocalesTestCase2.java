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

import walkingkooka.ToStringTesting;
import walkingkooka.j2cl.locale.TimeZoneDisplay;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TimeZoneDisplayLocalesTestCase2<D extends TimeZoneDisplayLocales> extends TimeZoneDisplayLocalesTestCase<D> implements ToStringTesting<D> {

    final static TimeZoneDisplay DISPLAY = TimeZoneDisplay.with("short1", "short2", "long3", "long4");

    final static Locale EN_AU = Locale.forLanguageTag("EN-AU");
    final static Locale EN_NZ = Locale.forLanguageTag("EN-NZ");

    TimeZoneDisplayLocalesTestCase2() {
        super();
    }

    final void containsAndCheck(final TimeZoneDisplayLocales displayLocales,
                                final Locale locales,
                                final boolean expected) {
        assertEquals(expected,
                displayLocales.contains(locales),
                () -> displayLocales + " contains " + locales);
    }
}
