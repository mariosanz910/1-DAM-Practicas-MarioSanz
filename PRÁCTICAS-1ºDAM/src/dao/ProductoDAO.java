	package dao;
	import modelo.*;
	import conexion.Conexion;
	
	import java.sql.*;
	import java.util.ArrayList;
	import java.util.List;
	
	public class ProductoDAO {
	
	    public void agregarProducto(ProductoOtaku producto) {
	        String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
	        try (Connection conn = Conexion.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	
	            stmt.setString(1, producto.getNombre());
	            stmt.setString(2, producto.getCategoria());
	            stmt.setDouble(3, producto.getPrecio());
	            stmt.setInt(4, producto.getStock());
	            stmt.executeUpdate();
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	    public ProductoOtaku obtenerProductoPorId(int id) {
	        String sql = "SELECT * FROM productos WHERE id = ?";
	        try (Connection conn = Conexion.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	
	            stmt.setInt(1, id);
	            ResultSet rs = stmt.executeQuery();
	
	            if (rs.next()) {
	                return new ProductoOtaku(
	                    rs.getString("nombre"),
	                    rs.getString("categoria"),
	                    rs.getDouble("precio"),
	                    rs.getInt("stock")
	                );
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	
	    public List<ProductoOtaku> obtenerTodosLosProductos() {
	        List<ProductoOtaku> lista = new ArrayList<>();
	        String sql = "SELECT * FROM productos";
	
	        try (Connection conn = Conexion.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {
	
	            while (rs.next()) {
	                lista.add(new ProductoOtaku(
	                    rs.getString("nombre"),
	                    rs.getString("categoria"),
	                    rs.getDouble("precio"),
	                    rs.getInt("stock")
	                ));
	            }
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	
	        return lista;
	    }
	
	    public boolean actualizarProducto(ProductoOtaku producto) {
	        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
	        try (Connection conn = Conexion.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	
	            stmt.setString(1, producto.getNombre());
	            stmt.setString(2, producto.getCategoria());
	            stmt.setDouble(3, producto.getPrecio());
	            stmt.setInt(4, producto.getStock());
	            stmt.setInt(5, producto.getId());
	
	            return stmt.executeUpdate() > 0;
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	
	    public boolean eliminarProducto(int id) {
	        String sql = "DELETE FROM productos WHERE id = ?";
	        try (Connection conn = Conexion.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	
	            stmt.setInt(1, id);
	            return stmt.executeUpdate() > 0;
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	
	    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
	        List<ProductoOtaku> lista = new ArrayList<>();
	        String sql = "SELECT * FROM productos WHERE nombre LIKE ?";
	
	        try (Connection conn = Conexion.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	
	            stmt.setString(1, "%" + nombre + "%");
	            ResultSet rs = stmt.executeQuery();
	
	            while (rs.next()) {
	                lista.add(new ProductoOtaku(
	                    rs.getString("nombre"),
	                    rs.getString("categoria"),
	                    rs.getDouble("precio"),
	                    rs.getInt("stock")
	                ));
	            }
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	
	        return lista;
	    }
	}