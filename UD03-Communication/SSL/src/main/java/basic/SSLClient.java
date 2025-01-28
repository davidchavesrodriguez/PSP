package basic;

import javax.net.ssl.*;
import java.io.*;
import java.math.BigInteger;
import java.security.cert.X509Certificate;

public class SSLClient {
    public static void main(String[] args) throws IOException {
        SSLSocket SSLClient = null;
        PrintWriter out = null;
        System.setProperty("javax.net.ssl.trustStore", "src/main/resources/ClientKeys.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "87654321");
        //SSL ServerSocket Initialisation
        int port = 60000;
        String host = "localhost";
        System.out.println("Client program started....");
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory)
                SSLSocketFactory.getDefault();
        SSLClient = (SSLSocket) sslSocketFactory.createSocket(host, port);
        showSSLSessionInformation(SSLClient);
        out = new PrintWriter(SSLClient.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(SSLClient.getInputStream()));
        //sending a greeting to the server
        out.println("Greetings to the SERVER from the CLIENT");
        //The server responds with a message
        System.out.println("Receiving from the SERVER: \n\t" + in.readLine());
        //Closing streams and sockets
        in.close();
        out.close();
        SSLClient.close();
    }

    public static void showSSLSessionInformation(SSLSocket client) throws
            SSLPeerUnverifiedException {
        //----------------------------------------------------------------------------
        //Example of SSL session information
        SSLSession session = ((SSLSocket) client).getSession();
        System.out.println("Host: " + session.getPeerHost());
        System.out.println("Cipher: " + session.getCipherSuite());
        System.out.println("Protocol: " + session.getProtocol());
        System.out.println("IDentifier:" + new BigInteger(session.getId()));
        System.out.println("Creation of the session: " + session.getCreationTime());
        X509Certificate certificate = (X509Certificate)
                session.getPeerCertificates()[0];
        System.out.println("Owner: " + certificate.getSubjectDN());
        System.out.println("Algorithm: " + certificate.getSigAlgName());
        System.out.println("Type: " + certificate.getType());
        System.out.println("Sender: " + certificate.getIssuerDN());
        System.out.println("Serial Number: " + certificate.getSerialNumber());
    }
}