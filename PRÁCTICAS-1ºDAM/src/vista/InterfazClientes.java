package vista;

import java.util.List;
import java.util.Scanner;

import dao.ClienteDAO;
import modelo.*;

public class InterfazClientes {

    private Scanner scanner;

    public InterfazClientes() {
        scanner = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Menú Clientes ---");
            System.out.println("1. Agregar nuevo cliente");
            System.out.println("2. Consultar cliente por ID");
            System.out.println("3. Ver todos los clientes");
            System.out.println("4. Actualizar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            ClienteDAO dao = new ClienteDAO();

            switch (opcion) {
                case 1:
                    System.out.println("\n> Agregar nuevo cliente");

                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();

                    ClienteOtaku nuevoCliente = new ClienteOtaku(nombre, email, telefono);
                    dao.agregarCliente(nuevoCliente);

                    System.out.println("Cliente agregado correctamente.");
                    break;

                case 2:
                    System.out.println("\n> Consultar cliente por ID");
                    System.out.print("Introduce el ID del cliente: ");
                    int idBuscar = scanner.nextInt();
                    scanner.nextLine();

                    ClienteOtaku clienteEncontrado = dao.obtenerClientePorId(idBuscar);

                    if (clienteEncontrado != null) {
                        System.out.println("\nCliente encontrado:");
                        System.out.println("ID: " + clienteEncontrado.getId());
                        System.out.println("Nombre: " + clienteEncontrado.getNombre());
                        System.out.println("Email: " + clienteEncontrado.getEmail());
                        System.out.println("Teléfono: " + clienteEncontrado.getTelefono());
                        System.out.println("Fecha de registro: " + clienteEncontrado.getFechaRegistro());
                    } else {
                        System.out.println("No se encontró ningún cliente con ese ID.");
                    }
                    break;

                case 3:
                    System.out.println("\n> Listar todos los clientes");

                    List<ClienteOtaku> listaClientes = dao.obtenerTodosLosClientes();

                    if (listaClientes.isEmpty()) {
                        System.out.println("No hay clientes registrados.");
                    } else {
                        for (ClienteOtaku c : listaClientes) {
                            System.out.println("---------------------------");
                            System.out.println("ID: " + c.getId());
                            System.out.println("Nombre: " + c.getNombre());
                            System.out.println("Email: " + c.getEmail());
                            System.out.println("Teléfono: " + c.getTelefono());
                            System.out.println("Fecha de registro: " + c.getFechaRegistro());
                        }
                        System.out.println("---------------------------");
                    }
                    break;

                case 4:
                    System.out.println("\n> Actualizar cliente");
                    System.out.print("ID del cliente a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine();

                    ClienteOtaku clienteExistente = dao.obtenerClientePorId(idActualizar);

                    if (clienteExistente != null) {
                        System.out.println("\nCliente actual:");
                        System.out.println("Nombre: " + clienteExistente.getNombre());
                        System.out.println("Email: " + clienteExistente.getEmail());
                        System.out.println("Teléfono: " + clienteExistente.getTelefono());

                        System.out.println("\nIntroduce los nuevos datos:");

                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();

                        System.out.print("Nuevo email: ");
                        String nuevoEmail = scanner.nextLine();

                        System.out.print("Nuevo teléfono: ");
                        String nuevoTelefono = scanner.nextLine();

                        ClienteOtaku clienteActualizado = new ClienteOtaku(idActualizar, nuevoNombre, nuevoEmail, nuevoTelefono, clienteExistente.getFechaRegistro());

                        boolean actualizado = dao.actualizarCliente(clienteActualizado);

                        if (actualizado) {
                            System.out.println("Cliente actualizado correctamente.");
                        } else {
                            System.out.println("Error: no se pudo actualizar el cliente.");
                        }
                    } else {
                        System.out.println("Error: no existe un cliente con ese ID.");
                    }
                    break;

                case 5:
                    System.out.println("\n> Eliminar cliente");
                    System.out.print("ID del cliente a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine();

                    ClienteOtaku clienteAEliminar = dao.obtenerClientePorId(idEliminar);

                    if (clienteAEliminar != null) {
                        System.out.println("\nCliente a eliminar:");
                        System.out.println("Nombre: " + clienteAEliminar.getNombre());
                        System.out.println("Email: " + clienteAEliminar.getEmail());
                        System.out.println("Teléfono: " + clienteAEliminar.getTelefono());

                        System.out.print("\n¿Estás seguro de que deseas eliminar este cliente? (s/n): ");
                        String confirmacion = scanner.nextLine();

                        if (confirmacion.equalsIgnoreCase("s")) {
                            boolean eliminado = dao.eliminarCliente(idEliminar);
                            if (eliminado) {
                                System.out.println("Cliente eliminado correctamente.");
                            } else {
                                System.out.println("Error: no se pudo eliminar el cliente.");
                            }
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    } else {
                        System.out.println("Error: no existe un cliente con ese ID.");
                    }
                    break;

                case 0:
                    System.out.println("\n> Saliendo del sistema de clientes. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("\n> Opción no válida. Intenta de nuevo.");
            }

        } while (opcion != 0);
    }
}
