import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HiloCliente extends Thread {
    private final DatagramSocket socket;
    private final DatagramPacket receivePacket;
    private final List<Quizz> questions;
    private final List<Integer> pointsPerQuestion; // Lista para almacenar los puntos ganados por cada pregunta

    public HiloCliente(DatagramSocket socket, DatagramPacket receivePacket) {
        this.socket = socket;
        this.receivePacket = receivePacket;
        this.questions = Server.getQuestions();
        this.pointsPerQuestion = new ArrayList<>();
    }

    @Override
    public void run() {
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
            int pointsEarned = question.getValue();
            pointsPerQuestion.add(pointsEarned); // Agregar puntos ganados al array
        } else {
            System.out.printf("Respuesta incorrecta. La respuesta correcta es: %s%n", correctAnswer);
        }

        System.out.println(STR."\n-----------------------------------------------");
        System.out.println(STR."Total de puntos obtenidos: \{getTotalPoints()}");
        System.out.println(STR."-----------------------------------------------\n");


        // Send acknowledgment to the server
        byte[] acknowledgment = "ACK".getBytes();
        InetAddress clientAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();
        DatagramPacket acknowledgmentPacket = new DatagramPacket(acknowledgment, acknowledgment.length, clientAddress, clientPort);

        try {
            socket.send(acknowledgmentPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener la suma total de puntos ganados
    public int getTotalPoints() {
        int totalPoints = 0;
        for (int points : pointsPerQuestion) {
            totalPoints += points;
        }
        return totalPoints;
    }
}