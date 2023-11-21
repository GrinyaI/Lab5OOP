import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Порт сервера
            System.out.println("Сервер запущен. Ожидание подключения клиента...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Ожидание подключения клиента
                System.out.println("Клиент подключен: " + clientSocket);

                // Чтение файла с сонетами Шекспира
                List<String> sonnets = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\grine\\IdeaProjects\\Lab5OOP\\src\\txt\\Sonnet.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sonnets.add(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String[] array = (String.join("\n", sonnets)).split("/next");
                Random random = new Random();
                int index = random.nextInt(array.length);
                String randomSonnet = array[index];

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(randomSonnet);

                clientSocket.close();
                System.out.println("Клиент отключен: " + clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}