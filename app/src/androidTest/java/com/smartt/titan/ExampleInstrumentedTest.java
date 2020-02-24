package com.smartt.titan;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {

        String s = "7F:C5:7A:76:AC:57:61:58:F0:37:A4:81:88:DA:E5:B8";

        s = s.replaceAll(":","");

        System.out.println(s.toLowerCase());
    }
}
