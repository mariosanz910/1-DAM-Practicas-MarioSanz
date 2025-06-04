package llmservice;

import dao.ProductoDAO;
import modelo.ProductoOtaku;

public class FuncionesIA {

    public static void generarDescripcionIA(int idProducto) {
        ProductoDAO dao = new ProductoDAO();
        ProductoOtaku producto = dao.obtenerProductoPorId(idProducto);

        if (producto == null) {
            System.out.println("Producto no encontrado con ID: " + idProducto);
            return;
        }

        String prompt = "Genera una descripción de marketing breve y atractiva para el producto otaku, recuerda, no debe ocupar más que una simple linea: " +
                producto.getNombre() + " de la categoría " + producto.getCategoria() + ".";
        String resultado = null;
		try {
			resultado = LlmService.enviarPrompt(prompt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("\nDescripción generada:");
        System.out.println(resultado);
    }

    public static void sugerirCategoriaIA(String nombreProducto) {
        String prompt = "Para un producto otaku llamado '" + nombreProducto + "', sugiere una categoría adecuada de esta lista, recuerda, debe ser un mensaje simple, debe componerse de: Este producto puede pertenecer a la categoría: + categoría: " +
                "Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
        String resultado = null;
		try {
			resultado = LlmService.enviarPrompt(prompt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("\nCategoría sugerida por IA:");
        System.out.println(resultado);
    }
}