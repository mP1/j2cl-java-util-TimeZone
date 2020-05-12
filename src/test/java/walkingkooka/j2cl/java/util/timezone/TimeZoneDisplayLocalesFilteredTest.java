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

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class TimeZoneDisplayLocalesFilteredTest extends TimeZoneDisplayLocalesTestCase2<TimeZoneDisplayLocalesFiltered> {

    @Test
    public void testContainsTrue() {
        assertTrue(this.create().contains(EN_AU));
    }

    @Test
    public void testContainsFalse() {
        assertFalse(this.create().contains(Locale.ENGLISH));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.create(), "\"short1\" \"short2\" \"long3\" \"long4\" en_AU, en_NZ");
    }

    private TimeZoneDisplayLocalesFiltered create() {
        return TimeZoneDisplayLocalesFiltered.with(DISPLAY, Lists.of(EN_AU, EN_NZ));
    }

    @Override
    public Class<TimeZoneDisplayLocalesFiltered> type() {
        return TimeZoneDisplayLocalesFiltered.class;
    }
}
