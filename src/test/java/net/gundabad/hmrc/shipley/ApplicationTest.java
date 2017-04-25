package net.gundabad.hmrc.shipley;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test application functionality
 */
public class ApplicationTest {

    @Test
    public void given_application_type_exists_when_requested_by_classloader_then_is_resolved() {
        String applicationClassName = "net.gundabad.hmrc.shipley.Application";
        Class<?> applicationClass = null;
        try {
            applicationClass = Class.forName(applicationClassName);
        } catch (ClassNotFoundException e) {
            // ignore exception
        }
        assertThat(applicationClass).isNotNull();
    }

    // method to return price for named item; note that this does not need to be exposed...
    // apple => 60p
    // orange => 25p
    // check function takes list of names of items, returns total cost for items
    // given single apple its cost is 60p
    // given single orange its cost is 25p
    // given any other name, its cost is 0p
    // given [apple, apple, orange, apple] => 2.05
    // assume cost needs to be formatted as a price
}
