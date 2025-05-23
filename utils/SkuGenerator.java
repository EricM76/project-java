package utils;

import models.Product;

import java.util.List;
import java.util.Random;

public class SkuGenerator {
    private Random random;
    private StringBuilder sku;

    public SkuGenerator() {
        this.random = new Random();
        this.sku = new StringBuilder();
    }

    public String generateSKU(List<Product> products) {
        sku.setLength(0);

        for (int i = 0; i < 2; i++) {
            char letra = (char)('A' + random.nextInt(26)); // A-Z
            sku.append(letra);
        }

        for (int i = 0; i < 3; i++) {
            sku.append(random.nextInt(10)); // 0-9
        }

        while (verifyUniqueness(products, sku.toString())) {
            sku.setLength(0);

            for (int i = 0; i < 2; i++) {
                char letra = (char)('A' + random.nextInt(26));
                sku.append(letra);
            }
            for (int i = 0; i < 3; i++) {
                sku.append(random.nextInt(10));
            }
        }

        return sku.toString();
    }

    private boolean verifyUniqueness(List<Product> products, String sku) {
        for (Product product : products) {
            if (product.getSku().equals(sku)) {
                return true;
            }
        }
        return false;
    }
}
