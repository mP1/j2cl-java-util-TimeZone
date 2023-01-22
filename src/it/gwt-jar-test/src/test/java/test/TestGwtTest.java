package test;

import com.google.gwt.junit.client.GWTTestCase;

import java.util.Locale;
import java.util.TimeZone;
import walkingkooka.j2cl.locale.LocaleAware;

public class TestGwtTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "test.Test";
    }

    public void testAssertEquals() {
        assertEquals(
                1,
                1
        );
    }

    public void testAustraliaHobartTimeZone() {
        final String hobart = "Australia/Hobart";
        final TimeZone timeZone = TimeZone.getTimeZone(hobart);

        assertEquals(
                hobart,
                timeZone.getID()
        );
    }
}
