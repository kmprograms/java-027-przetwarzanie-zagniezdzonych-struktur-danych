import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {

        /*
        Customer("A") -> Product("PR1") - 1, Product("PR2") - 2

        Customer("A"), Product("PR1")
        Customer("A"), Product("PR2")
        Customer("A"), Product("PR2")
        */

        var customerWithProducts = Map.ofEntries(
                Map.entry(
                        new Customer("C1", 19),
                        Map.of(
                                new Product("P1", new BigDecimal("100")), 2,
                                new Product("P2", new BigDecimal("200")), 1,
                                new Product("P3", new BigDecimal("300")), 3
                        )
                ),
                Map.entry(
                        new Customer("C2", 20),
                        Map.of(
                                new Product("P4", new BigDecimal("400")), 2,
                                new Product("P5", new BigDecimal("500")), 1,
                                new Product("P6", new BigDecimal("600")), 3
                        )
                )
        );

        var customerWithProductCollection = customerWithProducts
                .entrySet()
                .stream()
                .flatMap(e -> e
                        .getValue()
                        .entrySet()
                        .stream()
                        .flatMap(ee -> Collections
                                // Product("PR2") - 2
                                // W wyniku otrzymujemy
                                // { Product("PR2"), Product("PR2") }
                                .nCopies(ee.getValue(), ee.getKey())
                                .stream()
                                .map(product -> new CustomerWithProduct(e.getKey(), product))))
                .collect(Collectors.toList());
        customerWithProductCollection.forEach(System.out::println);
    }
}
