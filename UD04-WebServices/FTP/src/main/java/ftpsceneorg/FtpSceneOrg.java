package ftpsceneorg;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.net.SocketException;

public class FtpSceneOrg {
    static String server = "ftp.scene.org";
    static String user = "anonymous";
    static String pass = "";

    static FTPClient ftpClient = new FTPClient();

    public static void main(String[] args) {

        try {
            ftpClient.connect(server);
            ftpClient.login(user, pass);

            ftpClient.enterLocalPassiveMode();

            String remoteDir = "/pub";
            String[] fileNames = ftpClient.listNames(remoteDir);

            if (fileNames != null && fileNames.length > 0) {
                System.out.println("Files in " + remoteDir + ":");
                for (int i = 0; i < fileNames.length; i++) {
                    System.out.println((i + 1) + ". " + fileNames[i]);
                }
            } else {
                System.out.println("There is no files " + remoteDir);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
