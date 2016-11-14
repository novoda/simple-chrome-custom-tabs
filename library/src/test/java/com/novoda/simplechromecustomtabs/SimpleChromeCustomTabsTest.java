package com.novoda.simplechromecustomtabs;

import com.novoda.notils.exception.DeveloperError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

@RunWith(RobolectricTestRunner.class)
public class SimpleChromeCustomTabsTest {

    @Test
    public void givenSimpleChromeCustomTabsIsNotInitialised_whenGettingInstance_thenDeveloperErrorIsThrown() {
        /** Please forgive me for what you are seeing. Given some incompatibility issues between Robolectric 3.1.4 and some versions of
         *  Java 8, the @Test(expected = DeveloperError.class) wasn't working.
         *  Will fix when we figure out how.
         **/
        try {
            SimpleChromeCustomTabs.getInstance();
            fail("A Developer error exception was expected, but there was nothing");
        } catch (DeveloperError e) {
            // passes
        }
    }

    @Test
    public void givenSimpleChromeCustomTabsIsInitialised_whenGettingInstance_thenInstanceIsReturned() {
        SimpleChromeCustomTabs.initialize(RuntimeEnvironment.application);

        assertThat(SimpleChromeCustomTabs.getInstance()).isInstanceOf(SimpleChromeCustomTabs.class);
    }

}
