package gal.dchaves.p4_09;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadFTP {
    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();

        try {
            // Conectar al servidor FTP
            ftpClient.connect("ftp.scene.org");
            ftpClient.login("anonymous", "");

            // Verificar si la conexión fue exitosa
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.err.println("Error al conectar al servidor FTP. Código de respuesta: " + replyCode);
                return;
            }

            // Configurar el modo pasivo y el tipo de archivo
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            // Cambiar al directorio remoto
            ftpClient.changeWorkingDirectory("/pub");

            // Listar archivos en el directorio remoto (opcional)
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                System.out.println(file.getName());
            }

            // Nombre del archivo remoto y ruta local para guardar
            String remoteFile = "index.txt";
            File downloadFile = new File("src/main/resources/ftpText.txt");

            // Descargar el archivo
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile))) {
                boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
                if (success) {
                    System.out.println("Archivo descargado exitosamente: " + downloadFile.getAbsolutePath());
                } else {
                    System.err.println("Error al descargar el archivo.");
                }
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Cerrar la conexión FTP
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                System.err.println("Error al cerrar la conexión FTP: " + e.getMessage());
            }
        }
    }
}