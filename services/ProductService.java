package services;
import exceptions.InvalidValueException;
import exceptions.ProductException;
import exceptions.ProductNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import models.Product;
import utils.SkuGenerator;

public class ProductService {
    private final List<Product> products;
    private final List<Product> matchingProducts;
    private final SkuGenerator skuGenerator;
    private final AtomicInteger nexId;

    public ProductService() {
        this.products = new ArrayList<>();
        this.matchingProducts = new ArrayList<>();
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

    public void findProducts(String keyword) throws ProductException{
        
        if (matchingProducts != null && !matchingProducts.isEmpty()) {
        matchingProducts.clear();
        }

        // intento de normalizacíon para optimizar la búsqueda
        String regex = "[^\\w\\s+]";
        String normalizedKeyword = keyword.replaceAll(regex, "").toLowerCase();
        String searchPhrase = normalizedKeyword.toLowerCase();
        

        for (Product product : products) {
            String productName = product.getName().replaceAll(regex, "").toLowerCase();
            String productDescription = product.getDescription().replaceAll(regex, "").toLowerCase();
        
            if (productName.contains(searchPhrase) || productDescription.contains(searchPhrase)) {
                matchingProducts.add(product);
            }
        }
        
        if (matchingProducts.isEmpty()) {
            throw new ProductNotFoundException("No se encontraron productos que contengan " + keyword);
        }

        for (Product product : matchingProducts) {
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