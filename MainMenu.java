import models.Product;
import services.ProductService;
import exceptions.*;
import java.util.Scanner;

public class MainMenu {
    private Scanner scanner;
    private ProductService manager;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.manager = new ProductService();
    }

    public void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE GESTIÓN DE PRODUCTOS ===");
        System.out.println("1. Agregar nuevo producto");
        System.out.println("2. Listar todos los productos");
        System.out.println("3. Actualizar producto existente");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void addNewProduct() {
        try {
            System.out.print("Ingrese el nombre del producto: ");
            String name = scanner.next();
            System.out.print("Ingrese el precio del producto: ");
            double price = scanner.nextDouble();
            System.out.print("Ingrese la cantidad del producto: ");
            int stock = scanner.nextInt();
            System.out.print("Ingrese la descripción del producto: ");
            String description = scanner.next();

            manager.addProduct(name, price, stock, description);
            System.out.println("Producto agregado exitosamente!");
        } catch (InvalidValueException error) {
            System.out.println(error.getMessage());
        }
    }

    public void listAllProducts() {
        try {
            manager.listProducts();
        } catch (ProductException error) {
            System.out.println(error.getMessage());
        }
    }

    public void updateProduct() {
        System.out.print("Ingrese el ID del producto a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Product product = manager.getProductById(id);

            System.out.print("Ingrese el nuevo nombre del producto (ENTER para mantener el actual): ");
            String inputName = scanner.nextLine();
            String name = inputName.isEmpty() ? product.getName() : inputName;

            System.out.print("Ingrese el nuevo precio del producto: ");
            String inputPrice = scanner.nextLine();
            double price =  inputPrice.isEmpty() ? product.getPrice() : Double.parseDouble(inputPrice);

            System.out.print("Ingrese la nueva cantidad del producto: ");
            String inputStock = scanner.nextLine();
            int stock = inputStock.isEmpty() ? product.getStock() : Integer.parseInt(inputStock);

            System.out.print("Ingrese la nueva descripción del producto: ");
            String inputDescription = scanner.nextLine();
            String description = inputDescription.isEmpty() ? product.getDescription() : inputDescription;

            Product productModify = new Product(name, price, stock, description);
            productModify.setSku(product.getSku());
            manager.updateProduct(productModify);
            System.out.println("Producto actualizado exitosamente!");

        } catch (ProductException error) {
            System.out.println(error.getMessage());
        }
    }

    public void removeProduct() {
        System.out.print("Ingrese el código del producto a eliminar: ");
        int id = scanner.nextInt();
        try {
            manager.removeProduct(id);
        } catch (ProductException error) {
            System.out.println(error.getMessage());
        }
    }

    public void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    listAllProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    removeProduct();
                    break;
                case 5:
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
        } while (opcion != 5);
    }

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.ejecutar();
    }
}