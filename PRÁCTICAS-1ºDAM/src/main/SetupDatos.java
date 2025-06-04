package main;

import java.util.List;

import dao.ProductoDAO;
import modelo.ProductoOtaku;

public class SetupDatos {
	public void DatosPrueba() {	
		ProductoDAO productoDAO = new ProductoDAO();

	    // Verificamos si hay productos en la base de datos
	    List<ProductoOtaku> productos = productoDAO.obtenerTodosLosProductos();

	    if (productos.isEmpty()) {
	        System.out.println("Base de datos vacía. Insertando productos de ejemplo...");

	        ProductoOtaku p1 = new ProductoOtaku("Figura de Anya Forger", "Figura", 59.95, 8);
	        ProductoOtaku p2 = new ProductoOtaku("Manga Chainsaw Man Vol.1", "Manga", 9.99, 20);
	        ProductoOtaku p3 = new ProductoOtaku("Póster Studio Ghibli Colección", "Póster", 15.50, 15);

	        productoDAO.agregarProducto(p1);
	        productoDAO.agregarProducto(p2);
	        productoDAO.agregarProducto(p3);

	        System.out.println("Productos insertados correctamente.");
	    } else {
	        System.out.println("La base de datos ya contiene productos.");
	    }

	    // Mostrar todos los productos
	    System.out.println("Lista actual de productos:");
	    for (ProductoOtaku producto : productoDAO.obtenerTodosLosProductos()) {
	    	System.out.println("Nombre: " + producto.getNombre() + ", Categoría: " + producto.getCategoria() + ", Precio: " + producto.getPrecio() + ", Stock: " + producto.getStock());
	    }
	}

}
