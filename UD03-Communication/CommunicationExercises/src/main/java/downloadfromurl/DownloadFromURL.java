package downloadfromurl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.Map;

public class DownloadFromURL {

    public static void main(String[] args) throws IOException {
        // URL de la API (puedes pedir el ID al usuario si es necesario)
        int articleId = 1; // Cambia esto o pide al usuario que ingrese el ID
        URL url = URI.create("https://api.slingacademy.com/v1/sample-data/blog-posts/" + articleId).toURL();

        // Conexión y lectura del JSON
        URLConnection urlConnection = url.openConnection();
        try (InputStream stream = urlConnection.getInputStream();
             BufferedReader urlReader = new BufferedReader(new InputStreamReader(stream))) {

            // Convertir JSON a un Map usando GSON
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> post = gson.fromJson(urlReader, mapType);

            // Reemplazar el contenido en el archivo HTML
            updateHtmlFile(post);
        }
    }

    // Método para actualizar el archivo HTML
    private static void updateHtmlFile(Map<String, Object> post) throws IOException {
        // Leer el archivo index.html
        File file = new File("src/main/resources/index.html");
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        // Reemplazar "CAMBIAR ESTO" con el contenido del artículo
        String updatedContent = content.toString().replace(
                "CAMBIAR ESTO",
                "<p>" + post.get("content") + "</p>" +
                        "<time datetime=\"" + post.get("date") + "\">" + post.get("date") + "</time>" +
                        "<address class=\"fs-6 text-secondary\">Written by TrendTribe</address>"
        );

        // Escribir el contenido actualizado en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(updatedContent);
        }

        System.out.println("Archivo index.html actualizado correctamente.");
    }
}
