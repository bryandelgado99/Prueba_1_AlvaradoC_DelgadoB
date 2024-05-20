import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Client {

    int puerto = 5000;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String question = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Display question with keyboard input prompt (formatted)
                System.out.printf("Server: %s (Escriba su respuesta):\n", question);

                String answer = scanner.readLine();
                byte[] sendData = answer.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);

                if (question.equals("No more questions")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}