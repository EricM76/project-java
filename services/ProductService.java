package services;
import exceptions.InvalidValueException;
import exceptions.ProductException;
import exceptions.ProductNotFoundException;
import models.Product;
import utils.SkuGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductService {
    private List<Product> products;
    private SkuGenerator skuGenerator;
    private AtomicInteger nexId;

    public ProductService() {
        this.products = new ArrayList<>();
        this.skuGenerator = new SkuGenerator();
        this.nexId = new AtomicInteger(1);
    }

    public void addProduct(String name, double price, int stock, String description) throws InvalidValueException {

        Product newProduct = new Product(name, price, stock, description);
        validateProduct(newProduct);

        newProduct.setId(nexId.getAndIncrement());
        newProduct.setSku(skuGenerator.generateSKU(products));

        products.add(newProduct);
    }

    public void listProducts() throws ProductException {
        if (products.isEmpty()) {
            throw new ProductException("No hay productos registrados");
        }

        for (Product product : products) {
            System.out.println("\n" + product.toString());
        }
    }

    public Product getProductById(int id) throws ProductException{
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        throw new ProductNotFoundException("No se encontró el producto con ID " + id);
    }

    public void updateProduct(Product modifyProduct) throws ProductException {

        validateProduct(modifyProduct);

        for (int i = 0; i < products.size(); i++) {
            if (Objects.equals(products.get(i).getSku(), modifyProduct.getSku())) {
                products.set(i, modifyProduct);
                return;
            }
        }

        throw new ProductNotFoundException("SKU inexistente");

    }

    public void removeProduct(int id) throws ProductException {

        if(products.removeIf(product -> product.getId() == id)){
            return;
        }

        throw new ProductNotFoundException("No se encontró el producto con ID " + id);

    }

    private void validateProduct(Product product) throws InvalidValueException {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new InvalidValueException("El nombre del producto no puede estar vacío");
        }
        if (product.getPrice() <= 0) {
            throw new InvalidValueException("El precio debe ser mayor a cero");
        }
        if (product.getStock() < 0) {
            throw new InvalidValueException("La cantidad no puede ser negativa");
        }
    }
}