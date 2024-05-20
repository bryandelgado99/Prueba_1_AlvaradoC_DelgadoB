import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class Client {

    public static void main(String[] args) {
        InetAddress serverAddress;
        int serverPort = 5000;
        try (DatagramSocket socket_Client = new DatagramSocket()) {
            // Crear un socket UDP
            serverAddress = InetAddress.getByName("localhost");

            // Entrada estándar
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

            byte[] receiveData = new byte[1024];
            byte[] sendData;

            String datosRecibidos;

            do {
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

            } while (!datosRecibidos.equals("No more questions"));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

