import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final List<Quizz> questions;

    static {
        questions = new ArrayList<>();
        questions.add(new Quizz("¿A qué equivale la F = m*a en la atracción planetaria?", "gravedad"));
        questions.add(new Quizz("¿Cuál es la aceleración gravitatoria en la tierra, en m/s2?", "9.8"));
        questions.add(new Quizz("¿Cómo se escribe el “Coeficiente de rozamiento?", "Fc"));
        questions.add(new Quizz("¿El agua es menos densa que el aceite, pero más densa que el helio?", "falso"));
        questions.add(new Quizz("¿Cuál es el metal que oxida al Aluminio?", "mercurio"));
        questions.add(new Quizz("¿Cuál es la fórmula de la Lluvia ácida?", "H2S04"));
        questions.add(new Quizz("¿Dónde nación Gengis Kan?", "Mongolia"));
        questions.add(new Quizz("¿Cuándo se construyó el Coliseo Romano (año + d.c)?", "70 d.c"));
        questions.add(new Quizz("¿Quienes son los fundadores de Roma, según la mitología romana?", "Romulo y Remo"));
    }

    public static void main(String[] args) {
        try {
            DatagramSocket socket_Server = new DatagramSocket(5000);
            System.out.println("Servidor esperando conexiones...\n");

            while (true) {
                byte[] bufferEntrada = new byte[1024];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket_Server.receive(paqueteEntrada);

                Thread hilocliente = new HiloCliente(socket_Server, paqueteEntrada);
                hilocliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Quizz> getQuestions() {
        System.out.println("\n -------- Bienvenido a QuizzON ------- \n");
        System.out.println("\n Las áreas disponibles son: \n");
        System.out.println("1. Física \n");
        System.out.println("2. Química \n");
        System.out.println("3. Matemática \n");

        return questions;
    }
}
