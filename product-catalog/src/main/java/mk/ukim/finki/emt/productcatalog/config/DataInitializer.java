package mk.ukim.finki.emt.productcatalog.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.productcatalog.domain.models.Product;
import mk.ukim.finki.emt.productcatalog.domain.repository.ProductRepository;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {
    private final ProductRepository productRepository;


    @PostConstruct
    public void initData() {
        Product product1 = Product.build("Pizza", "pizza dough, tomato sauce, cheese, pepperoni", Money.valueOf(Currency.MKD, 500), 10);
        Product product2 = Product.build("Spaghetti bolognese", "spaghetti, bolognese sauce", Money.valueOf(Currency.MKD, 300), 5);
        if(productRepository.findAll().isEmpty()) {
            productRepository.saveAll(Arrays.asList(product1, product2));
        }
    }
}
