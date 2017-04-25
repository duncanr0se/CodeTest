package net.gundabad.hmrc.shipley;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

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

    @Test
    public void given_shopping_cart_list_when_single_apple_only_then_total_cost_is_60p() {
        List<String> shoppingCart = Collections.singletonList("apple");

        BigDecimal cost = Application.getCartTotalCost(shoppingCart);

        assertThat(cost).isEqualTo(new BigDecimal("0.60"));
    }

    @Test
    public void given_shopping_cart_list_when_single_orange_only_then_total_cost_is_25p() {
        List<String> shoppingCart = Collections.singletonList("orange");

        BigDecimal cost = Application.getCartTotalCost(shoppingCart);

        assertThat(cost).isEqualTo(new BigDecimal("0.25"));
    }

    @Test
    public void given_shopping_cart_list_when_unknown_item_only_then_exception_thrown() {
        List<String> shoppingCart = Collections.singletonList("appl");

        Throwable thrown = catchThrowable(() -> Application.getCartTotalCost(shoppingCart));

        assertThat(thrown).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void given_shopping_cart_list_when_contains_unknown_item_then_exception_thrown() {
        List<String> shoppingCart = Arrays.asList("apple", "apple", "orangutan", "apple");

        Throwable thrown = catchThrowable(() -> Application.getCartTotalCost(shoppingCart));

        assertThat(thrown).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void given_shopping_cart_list_when_contains_known_items_then_expected_total_returned() {
        List<String> shoppingCart = Arrays.asList("apple", "apple", "orange", "apple");

        BigDecimal cost = Application.getCartTotalCost(shoppingCart);

        assertThat(cost).isEqualTo(new BigDecimal("2.05"));

    }
}
