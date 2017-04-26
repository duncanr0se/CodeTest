package net.gundabad.hmrc.shipley;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

        long cost = Application.getCartTotalCost(shoppingCart);

        assertThat(cost).isEqualTo(60L);
    }

    @Test
    public void given_shopping_cart_list_when_single_orange_only_then_total_cost_is_25p() {
        List<String> shoppingCart = Collections.singletonList("orange");

        long cost = Application.getCartTotalCost(shoppingCart);

        assertThat(cost).isEqualTo(25L);
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

        long cost = Application.getCartTotalCost(shoppingCart);

        assertThat(cost).isEqualTo(205L);
    }

    @Test
    public void given_long_value_when_formatted_as_currency_then_result_as_expected() {

        assertThat(Application.formatCurrency(0L)).isEqualTo("£0.00");
        assertThat(Application.formatCurrency(60L)).isEqualTo("£0.60");
        assertThat(Application.formatCurrency(205L)).isEqualTo("£2.05");
    }

    @Test
    public void given_cart_when_contains_3_apples_then_count_of_apples_returns_3() {
        List<String> shoppingCart = Arrays.asList("apple", "apple", "orangutan", "apple");

        long count = Application.getItemCount(shoppingCart, "apple");

        assertThat(count).isEqualTo(3L);
    }

    @Test
    public void given_cart_when_contains_no_oranges_then_count_of_oranges_is_0() {
        List<String> shoppingCart = Arrays.asList("apple", "apple", "orangutan", "apple");

        long count = Application.getItemCount(shoppingCart, "orange");

        assertThat(count).isEqualTo(0L);
    }

    @Test
    public void given_cart_when_contains_only_apples_then_count_is_as_expected() {
        List<String> shoppingCart = Arrays.asList("apple", "apple", "apple", "apple");

        long count = Application.getItemCount(shoppingCart, "apple");

        assertThat(count).isEqualTo(4L);
    }

    @Test
    public void given_cart_containing_4_oranges_when_offer_adjustment_calculated_then_value_is_25p() {
        List<String> cart = Arrays.asList("apple", "orange", "pear", "orange", "orange", "ferrari", "orange");

        long count = Application.getItemCount(cart, "orange");
        long offer = Application.calculateOfferDiscountInPence("orange", count);

        assertThat(offer).isEqualTo(25L);
    }

    @Test
    public void given_cart_containing_6_oranges_when_offer_adjustment_calculated_then_value_is_50p() {
        List<String> cart = Arrays.asList("apple", "orange", "pear", "apple", "orange", "ferrari", "apple",
                "orange", "orange", "orange", "orange");

        long count = Application.getItemCount(cart, "orange");
        long offer = Application.calculateOfferDiscountInPence("orange", count);

        assertThat(offer).isEqualTo(50L);
    }

    @Test
    public void given_cart_containing_3_oranges_when_offer_adjustment_calculated_then_value_is_25p() {
        List<String> cart = Arrays.asList("apple", "orange", "pear", "orange", "orange", "ferrari", "apple");

        long count = Application.getItemCount(cart, "orange");
        long offer = Application.calculateOfferDiscountInPence("orange", count);

        assertThat(offer).isEqualTo(25L);
    }

    @Test
    public void given_cart_containing_1_orange_when_offer_adjustment_calculated_then_value_is_0p() {
        List<String> cart = Arrays.asList("orange", "pear", "apple", "ferrari");

        long count = Application.getItemCount(cart, "orange");
        long offer = Application.calculateOfferDiscountInPence("orange", count);

        assertThat(offer).isEqualTo(0L);
    }

    @Test
    public void given_cart_containing_3_apples_when_offer_adjustment_calculated_then_value_is_60p() {
        List<String> cart = Arrays.asList("apple", "orange", "pear", "apple", "apple", "ferrari");

        long count = Application.getItemCount(cart, "apple");
        long offer = Application.calculateOfferDiscountInPence("apple", count);

        assertThat(offer).isEqualTo(60L);
    }

    @Test
    public void given_cart_containing_4_apples_when_offer_adjustment_calculated_then_value_is_120p() {
        List<String> cart = Arrays.asList("apple", "orange", "pear", "apple", "apple", "ferrari", "apple");

        long count = Application.getItemCount(cart, "apple");
        long offer = Application.calculateOfferDiscountInPence("apple", count);

        assertThat(offer).isEqualTo(120L);
    }

    @Test
    public void given_cart_containing_2_apples_when_offer_adjustment_calculated_then_value_is_60p() {
        List<String> cart = Arrays.asList("orange", "pear", "apple", "apple", "ferrari");

        long count = Application.getItemCount(cart, "apple");
        long offer = Application.calculateOfferDiscountInPence("apple", count);

        assertThat(offer).isEqualTo(60L);
    }

    @Test
    public void given_cart_containing_1_apple_when_offer_adjustment_calculated_then_value_is_0p() {
        List<String> cart = Arrays.asList("orange", "pear", "apple", "ferrari");

        long count = Application.getItemCount(cart, "apple");
        long offer = Application.calculateOfferDiscountInPence("apple", count);

        assertThat(offer).isEqualTo(0L);
    }

}
