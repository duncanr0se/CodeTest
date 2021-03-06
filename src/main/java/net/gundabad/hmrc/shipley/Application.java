package net.gundabad.hmrc.shipley;

import java.math.BigDecimal;
import java.util.List;

/**
 * Shopping cart application for pre-interview coding test.
 *
 * Assumption: this will be accessed via tests or other code and no command-
 * line interface is needed
 */
class Application {

    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";

    private Application() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Given a list containing the names of known items, return the total cost
     * of those items before any discount is applied.
     * Throws a {@code RuntimeException} if any of the items are not recognised.
     *
     * @param shoppingCart The names of the items to total; each element must
     *                     be one of {@code "apple"} or {@code "orange"}.
     * @return the cost of the items in a textual format with currency symbol.
     * @throws RuntimeException if any item is not known (i.e., is not
     * either "apple" or "orange")
     */
    public static String getCartTotalCostBeforeDiscount(List<String> shoppingCart) {
        return formatCurrency(getCartTotalCostWithoutDiscount(shoppingCart));
    }

    /**
     * Given a list containing the names of known items, return the total cost
     * of those items including any discount that has been applied.
     * Throws a {@code RuntimeException} if any of the items are not recognised.
     *
     * @param shoppingCart The names of the items to total; each element must
     *                     be one of {@code "apple"} or {@code "orange"}.
     * @return the cost of the items in a textual format with currency symbol.
     * @throws RuntimeException if any item is not known (i.e., is not
     * either "apple" or "orange")
     */
    public static String getCartTotalCostAfterDiscount(List<String> shoppingCart) {
        long preDiscountTotal = getCartTotalCostWithoutDiscount(shoppingCart);
        long discount = getDiscountForItem(shoppingCart, APPLE);
        discount += getDiscountForItem(shoppingCart, ORANGE);
        return formatCurrency(preDiscountTotal - discount);
    }

    /**
     * Return the discount amount for the item type in the cart supplied
     *
     * @param cart shopping cart
     * @param item name of item to calculate discount of
     * @return value of discount in pence
     */
    private static long getDiscountForItem(List<String> cart, String item) {
        long count = getItemCount(cart, item);
        return calculateOfferDiscountInPence(item, count);
    }

    /**
     * Given a list containing the names of known items, return the total cost
     * of those items before any discount is applied.
     * Throws a {@code RuntimeException} if any of the items are not recognised.
     *
     * @param shoppingCart The names of the items to total; each element must
     *                     be one of {@code "apple"} or {@code "orange"}.
     * @return The cost of the items with the fractional part being the pence
     * and the integer part being the pounds.
     * @throws RuntimeException if any item is not known (i.e., is not
     * either "apple" or "orange")
     */
    private static long getCartTotalCostWithoutDiscount(List<String> shoppingCart) {
        return shoppingCart.stream()
                .mapToLong(Application::itemCost)
                .sum();
    }

    /**
     * Format a value in pence into pounds and pence.
     *
     * @param pence value in pence
     * @return A representation of the monetary value in pounds and pence of
     * the penny amount provided
     */
    private static String formatCurrency(long pence) {
        return "£" + BigDecimal.valueOf(pence).divide(BigDecimal.valueOf(100)).setScale(2);
    }

    /**
     * Return the number of item that is contained within the shopping cart
     *
     * @param shoppingCart list of names of items
     * @param item name of item to count
     * @return number of items named item in cart
     */
    public static long getItemCount(List<String> shoppingCart, String item) {
        return shoppingCart.stream()
                .filter(item::equals)
                .count();
    }

    /**
     * Calculate the discount offered based on the number of specific items
     * in the cart.
     *
     * @param item the name of the item; dictates which offer is calculated
     * @param count the number of specified item in the cart
     * @return the discount offered, in pence
     */
    public static long calculateOfferDiscountInPence(String item, long count) {
        switch(item) {
            case APPLE:
                // Integer division is exactly what is wanted here. Offer is
                // buy one get one free, need to know how many complete pairs
                // of apples there are in the cart n and offer a discount of
                // n apples
                return (count / 2) * itemCost(item);
            case ORANGE:
                // Again, integer division is wanted. Offer is three for the
                // price of two, need to know how many complete triplets of
                // oranges there are in the cart n and offer a discount of
                // n oranges.
                return (count / 3) * itemCost(item);
            default:
                // No offers on unknown items, so no discount
                return 0L;
        }
    }

    /**
     * Given the name of an item, return the cost in pence of that item.
     * Throws a {@code RuntimeException} if the item is not recognised.
     *
     * @param item The name of an item; one of {@code "apple"} or {@code "orange"}
     * @return The cost in pence of the item
     * @throws RuntimeException if the item is not known (i.e., is not
     * either "apple" or "orange")
     */
    private static long itemCost(String item) {
        // options:
        // 1) could use list of enum (name, price)
        // 2) could use list of [name_1, price_1, ..., name_n, price_n]
        // 3) could use map of name : price
        switch(item) {
            case APPLE:
                return 60L;
            case ORANGE:
                return 25L;
            default:
                throw new RuntimeException("Unrecognised item in cart: " + item);
        }
    }
}
