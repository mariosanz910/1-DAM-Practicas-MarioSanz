	package llmservice;
	
	import java.io.IOException;
	import java.io.InputStream;
	import java.util.Properties;
	import java.net.URI;
	import java.net.http.HttpClient;
	import java.net.http.HttpRequest;
	import java.net.http.HttpResponse;
	
	import com.google.gson.*;
	
	public class LlmService {
	
	    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
	    private static final String MODEL = "deepseek/deepseek-r1-distill-llama-70b:free";
	    private static final String CONFIG_FILE = "config.properties";
	
	    private static String apiKey;
	
	    // Bloque estático para cargar apiKey al cargar la clase
	    static {
	        apiKey = cargarApiKeyEstatico();
	    }
	
	    // Método estático para cargar API key desde el classpath
	    private static String cargarApiKeyEstatico() {
	        Properties props = new Properties();
	        try (InputStream is = LlmService.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
	            if (is == null) {
	                System.err.println("No se encontró " + CONFIG_FILE + " en el classpath.");
	                return null;
	            }
	            props.load(is);
	            return props.getProperty("openrouter.api.key");
	        } catch (IOException e) {
	            System.err.println("Error cargando " + CONFIG_FILE + ": " + e.getMessage());
	            return null;
	        }
	    }
	
	    public static String enviarPrompt(String prompt) {
	        if (apiKey == null || apiKey.isEmpty()) {
	            return "Error: API Key no configurada.";
	        }
	
	        JsonObject mensajeUsuario = new JsonObject();
	        mensajeUsuario.addProperty("role", "user");
	        mensajeUsuario.addProperty("content", prompt);
	
	        JsonArray mensajes = new JsonArray();
	        mensajes.add(mensajeUsuario);
	
	        JsonObject cuerpo = new JsonObject();
	        cuerpo.addProperty("model", MODEL);
	        cuerpo.add("messages", mensajes);
	
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(API_URL))
	                .header("Authorization", "Bearer " + apiKey)
	                .header("Content-Type", "application/json")
	                .POST(HttpRequest.BodyPublishers.ofString(cuerpo.toString()))
	                .build();
	
	        HttpClient client = HttpClient.newHttpClient();
	
	        try {
	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	
	            if (response.statusCode() != 200) {
	                return "Error: Respuesta " + response.statusCode() + "\n" + response.body();
	            }
	
	            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
	            JsonArray choices = jsonResponse.getAsJsonArray("choices");
	            JsonObject primeraOpcion = choices.get(0).getAsJsonObject();
	            JsonObject mensaje = primeraOpcion.getAsJsonObject("message");
	            return mensaje.get("content").getAsString().trim();
	
	        } catch (IOException | InterruptedException e) {
	            return "Error de conexión: " + e.getMessage();
	        } catch (Exception e) {
	            return "Error al procesar la respuesta: " + e.getMessage();
	        }
	    }
	}
