import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    static int puerto = 5000;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress server_IP = InetAddress.getByName("localhost");
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.printf("Cliente: ");
                String answer = scanner.readLine();
                byte[] sendData = answer.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, server_IP, puerto);
                socket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String question = new String(receivePacket.getData(), 0, receivePacket.getLength());
                // Display question with keyboard input prompt (formatted)

                if (question.equals("No more questions")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}