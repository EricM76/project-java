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

    private void clearScreen() {

        // forzar compatibilidad UTF-8 y modo ANSI
        System.setProperty("terminal.encoding", "UTF-8");

        // secuencia ANSI para limpiar pantalla
        String clearCommand = "\033[H\033[2J";

        try {
            System.out.print(clearCommand);
            System.out.flush();
            
        } catch (Exception e) {
            System.err.println("Error al intentar limpiar la pantalla:");
            System.err.println(e.getMessage());
        }

    }

    public void mostrarMenu() {
        clearScreen();
        System.out.println("\n=== SISTEMA DE GESTIÓN DE PRODUCTOS ===\n");
        System.out.println("1. Agregar nuevo producto");
        System.out.println("2. Listar todos los productos");
        System.out.println("3. Buscar productos");
        System.out.println("4. Actualizar producto existente");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Salir");
        System.out.print("\nSeleccione una opción: ");
    }

    public void addNewProduct() {
        scanner.nextLine();

        try {
            System.out.println("\n=== AGREGAR NUEVO PRODUCTO ===\n");
            
            System.out.print("Ingrese el nombre del producto (OBLIGATORIO): ");
            String name = scanner.nextLine();
            
            System.out.print("Ingrese el precio del producto (OBLIGATORIO): ");
            String inputPrice = scanner.nextLine();
            double price =  !inputPrice.isEmpty() ? Double.parseDouble(inputPrice) : 0;

            System.out.print("Ingrese la cantidad del producto (OBLIGATORIO): ");
            String inputStock = scanner.nextLine();
            int stock = !inputStock.isEmpty() ? Integer.parseInt(inputStock) : 0;

            System.out.print("Ingrese la descripción del producto (OPCIONAL): ");
            String description = scanner.nextLine();

            manager.addProduct(name, price, stock, description);
            System.out.println("\nProducto agregado exitosamente!");
        } catch (InvalidValueException error) {
            clearScreen();
            System.out.println("\n=== INFORME DE ERROR ===\n");
            System.out.println(error.getMessage());
        }
    }

    public void listAllProducts() {
        try {
            System.out.println("\n=== LISTAR PRODUCTOS ===\n");
            manager.listProducts();
        } catch (ProductException error) {
            System.out.println(error.getMessage());
        }
    }

    public void findProducts() {
        try {
            System.out.println("\n=== BUSCAR PRODUCTOS ===\n");
            System.out.print("Ingrese el criterio de búsqueda: ");
            String keyword = scanner.next();

            System.out.println("\n=== RESULTADO DE LA BÚSQUEDA ===\n");
            manager.findProducts(keyword);

        } catch (ProductException error) {
            clearScreen();
            System.out.println("\n=== INFORME DE ERROR ===\n");
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
            
            productModify.setId(product.getId());
            productModify.setSku(product.getSku());
            manager.updateProduct(productModify);
            
            System.out.println("\nProducto actualizado exitosamente!");

        } catch (ProductException error) {
            clearScreen();
            System.out.println("\n=== INFORME DE ERROR ===\n");
            System.out.println(error.getMessage());
        }
    }

    public void removeProduct() {
        System.out.print("Ingrese el código del producto a eliminar: ");
        int id = scanner.nextInt();
        
        try {
            manager.removeProduct(id);
            System.out.println("\nProducto eliminado exitosamente!");
        } catch (ProductException error) {
            clearScreen();
            System.out.println("\n=== INFORME DE ERROR ===\n");
            System.out.println(error.getMessage());
        }
    }

    public void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            clearScreen();
            switch (opcion) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    listAllProducts();
                    break;
                case 3:
                    findProducts();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    removeProduct();
                    break;
                case 6:
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }

            if(opcion != 6){
                scanner.nextLine();
                System.out.print("\nPresione cualquier tecla para continuar... ");
                scanner.nextLine();
            }
        } while (opcion != 6);
    }

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.ejecutar();
    }
}