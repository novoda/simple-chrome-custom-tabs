package com.novoda.simplechromecustomtabs;

import com.novoda.notils.exception.DeveloperError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class SimpleChromeCustomTabsTest {

    @Test(expected = DeveloperError.class)
    public void givenSimpleChromeCustomTabsIsNotInitialised_whenGettingInstance_thenDeveloperErrorIsThrown() {
        SimpleChromeCustomTabs.getInstance();
    }

    @Test
    public void givenSimpleChromeCustomTabsIsInitialised_whenGettingInstance_thenInstanceIsReturned() {
        SimpleChromeCustomTabs.initialize(Robolectric.application);

        assertThat(SimpleChromeCustomTabs.getInstance()).isInstanceOf(SimpleChromeCustomTabs.class);
    }

}
