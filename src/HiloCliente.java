import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class HiloCliente extends Thread {
    DatagramSocket socket_Client;
    DatagramPacket receivePacket;

    public HiloCliente(DatagramSocket socket_Client, DatagramPacket receivePacket) {
        this.socket_Client = socket_Client;
        this.receivePacket = receivePacket;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);

            // Recepción de datos
            byte[] receiveData = new byte[1024]; // Buffer for incoming data
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket_Client.receive(receivePacket);

            String datosRecibidos = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Los datos recibidos son: " + datosRecibidos);

            // Envío de datos
            System.out.print("Escriba su respuesta: ");
            String mensajeSalida = scanner.nextLine();
            byte[] sendData = mensajeSalida.getBytes();

            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            socket_Client.send(sendPacket);

        } catch (IOException e) {
            throw new RuntimeException(e); // Throwing the actual exception
        }
    }
}