package minirelay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        try(InputStream input= new FileInputStream("src/main/resources/smtp.properties")){

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
