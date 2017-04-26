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

    private Application() {
        throw new IllegalAccessError("Utility class");
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
            case "apple":
                return 60L;
            case "orange":
                return 25L;
            default:
                throw new RuntimeException("Unrecognised item in cart: " + item);
        }
    }

    /**
     * Given a list containing the names of known items, return the total cost
     * of those items.
     * Throws a {@code RuntimeException} if any of the items are not recognised.
     *
     * @param shoppingCart The names of the items to total; each element must
     *                     be one of {@code "apple"} or {@code "orange"}.
     * @return The cost of the items with the fractional part being the pence
     * and the integer part being the pounds.
     * @throws RuntimeException if any item is not known (i.e., is not
     * either "apple" or "orange")
     */
    public static long getCartTotalCost(List<String> shoppingCart) {
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
    public static String formatCurrency(long pence) {
        return "£" + BigDecimal.valueOf(pence).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_UNNECESSARY).setScale(2, BigDecimal.ROUND_UNNECESSARY);
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
            case "apple":
                // Integer division is exactly what is wanted here. Offer is
                // buy one get one free, need to know how many complete pairs
                // of apples there are in the cart n and offer a discount of
                // n apples
                return (count / 2) * itemCost(item);
            case "orange":
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
}
