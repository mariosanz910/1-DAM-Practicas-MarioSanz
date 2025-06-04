package vista;

import java.util.List;
import java.util.Scanner;

import dao.ProductoDAO;
import modelo.ProductoOtaku;
import llmservice.FuncionesIA;


public class InterfazConsola {

    private Scanner scanner;

    public InterfazConsola() {
        scanner = new Scanner(System.in);
    }

    // Método que muestra el menú, lee la opción y ejecuta el case
    public void iniciarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto por ID");
            System.out.println("3. Listar todos los productos");
            System.out.println("4. Actualizar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Buscar productos por nombre");
            System.out.println("7. Generar descripción de producto con IA");
            System.out.println("8. Sugerir categoría para producto con IA");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                	System.out.println("\n> Agregar nuevo producto");

                    System.out.print("Nombre del producto: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Categoría: ");
                    String categoria = scanner.nextLine();

                    System.out.print("Precio: ");
                    double precio = scanner.nextDouble();

                    System.out.print("Stock: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer

                    ProductoOtaku nuevo = new ProductoOtaku(nombre, categoria, precio, stock);
                    ProductoDAO dao = new ProductoDAO();
                    dao.agregarProducto(nuevo);

                    System.out.println("Producto agregado correctamente.");
                    break;
                case 2:
                    System.out.println("\n> Buscar producto por ID");

                    System.out.print("Introduce el ID del producto: ");
                    int idBuscar = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer

                    ProductoDAO daoBuscarPorId = new ProductoDAO();
                    ProductoOtaku productoEncontrado = daoBuscarPorId.obtenerProductoPorId(idBuscar);

                    if (productoEncontrado != null) {
                        System.out.println("\nProducto encontrado:");
                        System.out.println("Nombre: " + productoEncontrado.getNombre());
                        System.out.println("Categoría: " + productoEncontrado.getCategoria());
                        System.out.println("Precio: " + productoEncontrado.getPrecio());
                        System.out.println("Stock: " + productoEncontrado.getStock());
                    } else {
                        System.out.println("No se encontró ningún producto con ese ID.");
                    }
                    break;

                case 3:
                    System.out.println("\n> Listar todos los productos");

                    ProductoDAO daoListar = new ProductoDAO();
                    List<ProductoOtaku> listaProductos = daoListar.obtenerTodosLosProductos();

                    if (listaProductos.isEmpty()) {
                        System.out.println("No hay productos registrados.");
                    } else {
                        for (ProductoOtaku producto : listaProductos) {
                            System.out.println("---------------------------");
                	    	System.out.println("Nombre: " + producto.getNombre() + ", Categoría: " + producto.getCategoria() + ", Precio: " + producto.getPrecio() + ", Stock: " + producto.getStock());
                        }
                        System.out.println("---------------------------");
                    }
                    break;

                case 4:
                    System.out.println("\n> Actualizar producto");

                    System.out.print("ID del producto a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine(); 

                    ProductoDAO daoActualizar = new ProductoDAO();
                    ProductoOtaku productoExistente = daoActualizar.obtenerProductoPorId(idActualizar);

                    if (productoExistente != null) {
                        System.out.println("\nProducto actual:");
                        System.out.println("Nombre: " + productoExistente.getNombre());
                        System.out.println("Categoría: " + productoExistente.getCategoria());
                        System.out.println("Precio: " + productoExistente.getPrecio());
                        System.out.println("Stock: " + productoExistente.getStock());

                        System.out.println("\nIntroduce los nuevos datos:");

                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();

                        System.out.print("Nueva categoría: ");
                        String nuevaCategoria = scanner.nextLine();

                        System.out.print("Nuevo precio: ");
                        double nuevoPrecio = scanner.nextDouble();

                        System.out.print("Nuevo stock: ");
                        int nuevoStock = scanner.nextInt();
                        scanner.nextLine(); 

                        ProductoOtaku actualizado = new ProductoOtaku(idActualizar, nuevoNombre, nuevaCategoria, nuevoPrecio, nuevoStock);

                        boolean actualizadoConExito = daoActualizar.actualizarProducto(actualizado);

                        if (actualizadoConExito) {
                            System.out.println("Producto actualizado correctamente.");
                        } else {
                            System.out.println("Error: no se pudo actualizar el producto.");
                        }
                    } else {
                        System.out.println("Error: no existe un producto con ese ID.");
                    }
                    break;

                case 5:
                    System.out.println("\n> Eliminar producto");

                    System.out.print("ID del producto a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine(); 

                    ProductoDAO daoEliminar = new ProductoDAO();
                    ProductoOtaku productoAEliminar = daoEliminar.obtenerProductoPorId(idEliminar);

                    if (productoAEliminar != null) {
                        System.out.println("\nProducto a eliminar:");
                        System.out.println("Nombre: " + productoAEliminar.getNombre());
                        System.out.println("Categoría: " + productoAEliminar.getCategoria());
                        System.out.println("Precio: " + productoAEliminar.getPrecio());
                        System.out.println("Stock: " + productoAEliminar.getStock());

                        System.out.print("\n¿Estás seguro de que deseas eliminar este producto? (s/n): ");
                        String confirmacion = scanner.nextLine();

                        if (confirmacion.equalsIgnoreCase("s")) {
                            boolean eliminado = daoEliminar.eliminarProducto(idEliminar);
                            if (eliminado) {
                                System.out.println("Producto eliminado correctamente.");
                            } else {
                                System.out.println("Error: no se pudo eliminar el producto.");
                            }
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    } else {
                        System.out.println("Error: no existe un producto con ese ID.");
                    }
                    break;

                case 6:
                    System.out.println("\n> Buscar productos por nombre");

                    System.out.print("Introduce el nombre o parte del nombre a buscar: ");
                    String nombreBuscar = scanner.nextLine();

                    ProductoDAO daoBuscar = new ProductoDAO();
                    List<ProductoOtaku> resultados = daoBuscar.buscarProductosPorNombre(nombreBuscar);

                    if (resultados.isEmpty()) {
                        System.out.println("No se encontraron productos que coincidan con ese nombre.");
                    } else {
                        System.out.println("\nProductos encontrados:");
                        for (ProductoOtaku p : resultados) {
                            System.out.println("Nombre: " + p.getNombre());
                            System.out.println("Categoría: " + p.getCategoria());
                            System.out.println("Precio: " + p.getPrecio());
                            System.out.println("Stock: " + p.getStock());
                            System.out.println("---------------------------");
                        }
                    }
                    break;
                case 7:
                    System.out.println("\n> Generar descripción de producto con IA");
                    System.out.print("Introduce el ID del producto: ");
                    int idProductoIA = scanner.nextInt();
                    scanner.nextLine();
                    FuncionesIA.generarDescripcionIA(idProductoIA);
                    break;

                case 8:
                    System.out.println("\n> Sugerir categoría para producto con IA");
                    System.out.print("Introduce el nombre del nuevo producto: ");
                    String nombreNuevo = scanner.nextLine();
                    FuncionesIA.sugerirCategoriaIA(nombreNuevo);
                    break;

                case 0:
                    System.out.println("\n> Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("\n> Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 0);
    }
}
