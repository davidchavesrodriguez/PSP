package downloadfromurl;

import java.io.*;
import java.net.*;
import java.nio.file.Path;

public class DownloadFromURL {

    public static void main(String[] args) throws IOException {

        // URL
        URL url = URI.create("https://api.slingacademy.com/v1/sample-data/blog-posts/1").toURL();
        URLConnection urlConnection = url.openConnection();
        InputStream stream = urlConnection.getInputStream();
        BufferedReader urlReader = new BufferedReader(new InputStreamReader(stream));




        // Index
        BufferedReader indexReader= new BufferedReader(new FileReader("src/main/resources/index.html"));

        String line;
        while ((line = indexReader.readLine()) != null) {
            System.out.println(line);
        }


//        System.out.println(urlConnection.getHeaderFields());

    }
}
