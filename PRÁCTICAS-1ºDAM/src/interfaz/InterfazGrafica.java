package interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import dao.ProductoDAO;
import modelo.ProductoOtaku;
import llmservice.FuncionesIA;

public class InterfazGrafica extends JFrame {

	private static final long serialVersionUID = 1L;
	private ProductoDAO productoDAO;

    public InterfazGrafica() {
        super("Gestión de Productos Otaku");
        productoDAO = new ProductoDAO();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Panel principal con botones para cada opción
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1, 5, 5));

        JButton btnAgregar = new JButton("Agregar producto");
        JButton btnBuscarId = new JButton("Buscar producto por ID");
        JButton btnListar = new JButton("Listar todos los productos");
        JButton btnActualizar = new JButton("Actualizar producto");
        JButton btnEliminar = new JButton("Eliminar producto");
        JButton btnBuscarNombre = new JButton("Buscar productos por nombre");
        JButton btnDescripcionIA = new JButton("Generar descripción de producto con IA");
        JButton btnSugerirCat = new JButton("Sugerir categoría para producto con IA");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnAgregar);
        panel.add(btnBuscarId);
        panel.add(btnListar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnBuscarNombre);
        panel.add(btnDescripcionIA);
        panel.add(btnSugerirCat);
        panel.add(btnSalir);

        add(panel);

        // Listeners para los botones
        btnAgregar.addActionListener(e -> agregarProducto());
        btnBuscarId.addActionListener(e -> buscarProductoPorId());
        btnListar.addActionListener(e -> listarProductos());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnBuscarNombre.addActionListener(e -> buscarProductosPorNombre());
        btnDescripcionIA.addActionListener(e -> generarDescripcionIA());
        btnSugerirCat.addActionListener(e -> sugerirCategoriaIA());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void agregarProducto() {
        JTextField nombre = new JTextField();
        JTextField categoria = new JTextField();
        JTextField precio = new JTextField();
        JTextField stock = new JTextField();

        Object[] mensaje = {
            "Nombre:", nombre,
            "Categoría:", categoria,
            "Precio:", precio,
            "Stock:", stock
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Producto", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String nom = nombre.getText();
                String cat = categoria.getText();
                double pre = Double.parseDouble(precio.getText());
                int stk = Integer.parseInt(stock.getText());

                ProductoOtaku nuevo = new ProductoOtaku(nom, cat, pre, stk);
                productoDAO.agregarProducto(nuevo);

                JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Precio y stock deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarProductoPorId() {
        String input = JOptionPane.showInputDialog(this, "Introduce el ID del producto:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);
                if (producto != null) {
                    String info = String.format("Nombre: %s\nCategoría: %s\nPrecio: %.2f\nStock: %d",
                            producto.getNombre(), producto.getCategoria(), producto.getPrecio(), producto.getStock());
                    JOptionPane.showMessageDialog(this, info, "Producto encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún producto con ese ID.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listarProductos() {
        List<ProductoOtaku> productos = productoDAO.obtenerTodosLosProductos();
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos registrados.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (ProductoOtaku p : productos) {
                sb.append(String.format("Nombre: %s, Categoría: %s, Precio: %.2f, Stock: %d\n",
                        p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()));
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Lista de productos", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarProducto() {
        String inputId = JOptionPane.showInputDialog(this, "ID del producto a actualizar:");
        if (inputId != null) {
            try {
                int id = Integer.parseInt(inputId);
                ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);
                if (producto == null) {
                    JOptionPane.showMessageDialog(this, "No existe un producto con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JTextField nombre = new JTextField(producto.getNombre());
                JTextField categoria = new JTextField(producto.getCategoria());
                JTextField precio = new JTextField(String.valueOf(producto.getPrecio()));
                JTextField stock = new JTextField(String.valueOf(producto.getStock()));

                Object[] mensaje = {
                    "Nuevo nombre:", nombre,
                    "Nueva categoría:", categoria,
                    "Nuevo precio:", precio,
                    "Nuevo stock:", stock
                };

                int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Producto", JOptionPane.OK_CANCEL_OPTION);
                if (opcion == JOptionPane.OK_OPTION) {
                    String nuevoNombre = nombre.getText();
                    String nuevaCategoria = categoria.getText();
                    double nuevoPrecio = Double.parseDouble(precio.getText());
                    int nuevoStock = Integer.parseInt(stock.getText());

                    ProductoOtaku actualizado = new ProductoOtaku(id, nuevoNombre, nuevaCategoria, nuevoPrecio, nuevoStock);
                    boolean exito = productoDAO.actualizarProducto(actualizado);

                    if (exito) {
                        JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al actualizar producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID, precio y stock deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarProducto() {
        String inputId = JOptionPane.showInputDialog(this, "ID del producto a eliminar:");
        if (inputId != null) {
            try {
                int id = Integer.parseInt(inputId);
                ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);
                if (producto == null) {
                    JOptionPane.showMessageDialog(this, "No existe un producto con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirmar = JOptionPane.showConfirmDialog(this,
                        String.format("¿Seguro que quieres eliminar el producto '%s'?", producto.getNombre()),
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmar == JOptionPane.YES_OPTION) {
                    boolean eliminado = productoDAO.eliminarProducto(id);
                    if (eliminado) {
                        JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarProductosPorNombre() {
        String nombre = JOptionPane.showInputDialog(this, "Introduce el nombre o parte del nombre:");
        if (nombre != null) {
            List<ProductoOtaku> resultados = productoDAO.buscarProductosPorNombre(nombre);
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron productos que coincidan con ese nombre.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (ProductoOtaku p : resultados) {
                    sb.append(String.format("Nombre: %s\nCategoría: %s\nPrecio: %.2f\nStock: %d\n-----------------\n",
                            p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()));
                }
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 300));
                JOptionPane.showMessageDialog(this, scrollPane, "Resultados búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void generarDescripcionIA() {
        String input = JOptionPane.showInputDialog(this, "Introduce el ID del producto:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                FuncionesIA.generarDescripcionIA(id);
                JOptionPane.showMessageDialog(this, "Descripción generada con IA (ver consola o logs).");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sugerirCategoriaIA() {
        String nombre = JOptionPane.showInputDialog(this, "Introduce el nombre del nuevo producto:");
        if (nombre != null) {
            FuncionesIA.sugerirCategoriaIA(nombre);
            JOptionPane.showMessageDialog(this, "Categoría sugerida con IA (ver consola o logs).");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazGrafica().setVisible(true);
        });
    }
}
