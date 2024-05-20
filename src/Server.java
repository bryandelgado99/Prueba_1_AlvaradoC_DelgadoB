import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final List<Quizz> questions;
    static {
        questions = new ArrayList<>();
        questions.add(new Quizz("¿A qué equivale la F = m*a en la atracción planetaria?", "gravedad", 4));
        questions.add(new Quizz("¿Cuál es la aceleración gravitatoria en la tierra, en m/s2?", "9.8", 4));
        questions.add(new Quizz("¿Cómo se escribe el “Coeficiente de rozamiento?", "Fc", 4));
        questions.add(new Quizz("¿El agua es menos densa que el aceite, pero más densa que el helio?", "falso", 4));
        questions.add(new Quizz("¿Dónde nación Gengis Kan?", "Mongolia", 4));
        questions.add(new Quizz("¿Cuándo se construyó el Coliseo Romano (año + d.c)?", "70 d.c", 4));
        questions.add(new Quizz("¿Quienes son los fundadores de Roma, según la mitología romana?", "Romulo y Remo", 4));
    }

    public static void main() {

        try (DatagramSocket socket_Client = new DatagramSocket(5000)) {
            System.out.println("\n -------- Bienvenido a QuizzON ------- \n");
            System.out.println("\n Las áreas disponibles son: \n");
            System.out.println("1. Física \n");
            System.out.println("2. Química \n");
            System.out.println("3. Matemática \n");

            System.out.println("Servidor esperando conexiones.....");

            while (true){
                byte[] bufferEntrada = new byte[1024];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket_Client.receive(paqueteEntrada);

                Thread Cliente = new HiloCliente(socket_Client, paqueteEntrada);
                Cliente.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<Quizz> getQuestions() {
        return questions;
    }
}