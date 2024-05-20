import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;


public class HiloCliente extends Thread {
    private final DatagramSocket socket_Client;
    private final DatagramPacket receivePacket;
    private final List<Quizz> questions;

    public HiloCliente(DatagramSocket socket_Client, DatagramPacket receivePacket) {
        this.socket_Client = socket_Client;
        this.receivePacket = receivePacket;
        this.questions = Server.getQuestions();
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);

            // Receive question index from server
            String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
            int questionIndex = Integer.parseInt(receivedData.trim());
            Quizz question = questions.get(questionIndex);

            // Display the question
            System.out.printf("Pregunta: %s%n", question.getQuestion());

            // Receive answer from user
            System.out.print("Escriba su respuesta: ");
            String userAnswer = scanner.nextLine();

            // Compare user's answer with the correct answer
            String correctAnswer = question.getAnswer();
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                System.out.println("¡Respuesta correcta!");
            } else {
                System.out.printf("Respuesta incorrecta. La respuesta correcta es: %s%n", correctAnswer);
            }

            // Send acknowledgment to the server
            byte[] acknowledgment = "ACK".getBytes();
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            DatagramPacket acknowledgmentPacket = new DatagramPacket(acknowledgment, acknowledgment.length, clientAddress, clientPort);
            socket_Client.send(acknowledgmentPacket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}