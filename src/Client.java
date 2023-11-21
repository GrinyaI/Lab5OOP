import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Client extends JFrame {
    private JButton showButton;
    private JTextArea textArea;
    private String sonnet;
    private Socket socket;
    public String Find_Sonnet() {
        try {
            socket = new Socket("localhost", 12345);
            System.out.println("Подключение к серверу: " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return sonnet = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Client() {
        setTitle("Соннеты Шекспира");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLayout(null);

        textArea = new JTextArea();
        textArea.setBounds(20, 20, 750, 600);
        add(textArea);

        showButton = new JButton("Показать");
        showButton.setBounds(350, 625, 100, 30);
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sonnet = Find_Sonnet();
                textArea.setText(Sonnet);
            }
        });
        add(showButton);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    socket.close();
                    System.out.println("Отключено от сервера: " + socket);
                    System.exit(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Client client = new Client();
                client.setVisible(true);
            }
        });
    }
}