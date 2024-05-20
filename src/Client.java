import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class Client {

    public static void main(String[] args) {
        DatagramSocket socket_Client = null;
        InetAddress serverAddress = null;
        int serverPort = 5000;
        try {
            // Crear un socket UDP
            socket_Client = new DatagramSocket();
            serverAddress = InetAddress.getByName("localhost");

            // Entrada estándar
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

            byte[] receiveData = new byte[1024];
            byte[] sendData;

            String datosRecibidos;

            while (true) {
                // Envío de datos
                System.out.print("Escriba su respuesta: ");
                String answers = scanner.readLine();
                sendData = answers.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                socket_Client.send(sendPacket);

                // Recepción de datos
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket_Client.receive(receivePacket);
                datosRecibidos = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server: " + datosRecibidos);

                if (datosRecibidos.equals("No more questions")) {
                    break;
                }
            }
        } catch (IOException e) {
           throw new RuntimeException();
        } finally {
            if (socket_Client != null) {
                socket_Client.close();
            }
        }
    }
}

