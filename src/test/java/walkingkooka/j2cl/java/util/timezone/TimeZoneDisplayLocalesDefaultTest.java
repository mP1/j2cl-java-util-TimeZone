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

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class TimeZoneDisplayLocalesDefaultTest extends TimeZoneDisplayLocalesTestCase2<TimeZoneDisplayLocalesDefault> {

    @Test
    public void testContainsTrue() {
        assertTrue(this.create().contains(EN_AU));
    }

    @Test
    public void testContainsFalse() {
        assertTrue(this.create().contains(Locale.ITALIAN));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.create(), DISPLAY.toString());
    }

    private TimeZoneDisplayLocalesDefault create() {
        return TimeZoneDisplayLocalesDefault.with(DISPLAY);
    }

    @Override
    public Class<TimeZoneDisplayLocalesDefault> type() {
        return TimeZoneDisplayLocalesDefault.class;
    }
}