package mk.ukim.finki.emt.productcatalog.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.productcatalog.domain.exceptions.ProductIdDoesNotExistException;
import mk.ukim.finki.emt.productcatalog.domain.models.Product;
import mk.ukim.finki.emt.productcatalog.domain.models.ProductId;
import mk.ukim.finki.emt.productcatalog.domain.repository.ProductRepository;
import mk.ukim.finki.emt.productcatalog.service.ProductService;
import mk.ukim.finki.emt.productcatalog.service.forms.ProductForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public Product findById(ProductId productId) {
        return this.productRepository.findById(productId).orElseThrow(() -> new ProductIdDoesNotExistException());
    }

    @Override
    public Product createProduct(ProductForm productForm) {
        Product product = Product.build(productForm.getName(), productForm.getDescription(),productForm.getPrice(), productForm.getSales());
        this.productRepository.save(product);
        return product;
    }

    @Override
    public Product orderItemCreated(ProductId productId, int quantity) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductIdDoesNotExistException());
        product.addSales(quantity);
        this.productRepository.saveAndFlush(product);
        return product;
    }

    @Override
    public Product orderItemRemoved(ProductId productId, int quantity) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductIdDoesNotExistException());
        product.removeSales(quantity);
        this.productRepository.saveAndFlush(product);
        return product;
    }

    @Override
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }
}
