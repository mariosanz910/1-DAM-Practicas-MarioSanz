package interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import dao.ClienteDAO;
import modelo.ClienteOtaku;

public class InterfazClientes extends JFrame {

    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;

    public InterfazClientes() {
        super("Gestión de Clientes Otaku");
        clienteDAO = new ClienteDAO();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));

        JButton btnAgregar = new JButton("Agregar cliente");
        JButton btnBuscarId = new JButton("Buscar cliente por ID");
        JButton btnListar = new JButton("Listar todos los clientes");
        JButton btnActualizar = new JButton("Actualizar cliente");
        JButton btnEliminar = new JButton("Eliminar cliente");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnAgregar);
        panel.add(btnBuscarId);
        panel.add(btnListar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnSalir);

        add(panel);

        btnAgregar.addActionListener(e -> agregarCliente());
        btnBuscarId.addActionListener(e -> buscarClientePorId());
        btnListar.addActionListener(e -> listarClientes());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void agregarCliente() {
        JTextField nombre = new JTextField();
        JTextField email = new JTextField();
        JTextField telefono = new JTextField();

        Object[] mensaje = {
            "Nombre:", nombre,
            "Email:", email,
            "Teléfono:", telefono
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            ClienteOtaku nuevo = new ClienteOtaku(nombre.getText(), email.getText(), telefono.getText());
            clienteDAO.agregarCliente(nuevo);
            JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.");
        }
    }

    private void buscarClientePorId() {
        String input = JOptionPane.showInputDialog(this, "Introduce el ID del cliente:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                ClienteOtaku cliente = clienteDAO.obtenerClientePorId(id);
                if (cliente != null) {
                    String info = String.format("Nombre: %s\nEmail: %s\nTeléfono: %s",
                            cliente.getNombre(), cliente.getEmail(), cliente.getTelefono());
                    JOptionPane.showMessageDialog(this, info, "Cliente encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún cliente con ese ID.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listarClientes() {
        List<ClienteOtaku> clientes = clienteDAO.obtenerTodosLosClientes();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clientes registrados.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (ClienteOtaku c : clientes) {
                sb.append(String.format("ID: %d\nNombre: %s\nEmail: %s\nTeléfono: %s\n-------------------\n",
                        c.getId(), c.getNombre(), c.getEmail(), c.getTelefono()));
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Lista de clientes", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarCliente() {
        String inputId = JOptionPane.showInputDialog(this, "ID del cliente a actualizar:");
        if (inputId != null) {
            try {
                int id = Integer.parseInt(inputId);
                ClienteOtaku cliente = clienteDAO.obtenerClientePorId(id);
                if (cliente == null) {
                    JOptionPane.showMessageDialog(this, "No existe un cliente con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JTextField nombre = new JTextField(cliente.getNombre());
                JTextField email = new JTextField(cliente.getEmail());
                JTextField telefono = new JTextField(cliente.getTelefono());

                Object[] mensaje = {
                    "Nuevo nombre:", nombre,
                    "Nuevo email:", email,
                    "Nuevo teléfono:", telefono
                };

                int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Cliente", JOptionPane.OK_CANCEL_OPTION);
                if (opcion == JOptionPane.OK_OPTION) {
                    ClienteOtaku actualizado = new ClienteOtaku(nombre.getText(), email.getText(), telefono.getText());
                    boolean exito = clienteDAO.actualizarCliente(actualizado);

                    if (exito) {
                        JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al actualizar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarCliente() {
        String inputId = JOptionPane.showInputDialog(this, "ID del cliente a eliminar:");
        if (inputId != null) {
            try {
                int id = Integer.parseInt(inputId);
                ClienteOtaku cliente = clienteDAO.obtenerClientePorId(id);
                if (cliente == null) {
                    JOptionPane.showMessageDialog(this, "No existe un cliente con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirmar = JOptionPane.showConfirmDialog(this,
                        String.format("¿Seguro que quieres eliminar al cliente '%s'?", cliente.getNombre()),
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmar == JOptionPane.YES_OPTION) {
                    boolean eliminado = clienteDAO.eliminarCliente(id);
                    if (eliminado) {
                        JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazClientes().setVisible(true);
        });
    }
}
