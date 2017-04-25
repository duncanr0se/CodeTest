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
    public static BigDecimal getCartTotalCost(List<String> shoppingCart) {
        long costAsLong = shoppingCart.stream()
                .mapToLong(Application::itemCost)
                .sum();

        // Assumption: this method returns pounds + pence. Could be a SRP
        // violation, perhaps this should return total in pence and that
        // should be formatted elsewhere.
        return BigDecimal.valueOf(costAsLong).divide(BigDecimal.valueOf(100)).setScale(2);
    }
}
