import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Klasa rozszerzająca klasę JFrame, zawierająca szatę graficzną programu oraz jego działanie. Używana w klasie Start.java
 * @author Bartłomiej Kępka 4pP
 */
public class Swieta extends JFrame {
    /**
     * Główny panel programu.
     */
    JPanel panel;
    /**
     * 4 Główne przyciski w programie. Nasłuchują ActionListener listener.
     */
    JButton btnToday, btnMonth, btnAll, btnClear;
    /**
     * JTextArea używane w programie do wyświetlenia świąt na dany okres czasu.
     */
    JTextArea textarea;
    /**
     * ScrollPane dla JTextArea używanego w programie do wyświetlenia świąt na dany okres czasu.
     */
    JScrollPane scrollPane;
    /**
     * Główny ActionListener dla przycisków w programie. Przy użyciu, sprawdza który przycisk został wciśnięty oraz wykonuje polecenia wyznaczone dla tego przycisku.
     */
    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnToday) {
                if(searchFile(getDate())) textarea.setText(returnFile(getDate()));
                else textarea.setText("Brak świąt na dziś");
            }
            if (e.getSource() == btnMonth) {
                if (searchFile(getMonth())) {
                    textarea.setText(returnFile(getMonth()));
                }
            }
            if (e.getSource() == btnAll) {
                textarea.setText(returnFile(""));
            }
            if (e.getSource() == btnClear) {
                textarea.setText("");
            }
        }
    };

    /**
     * Konstruktor klasy. Ustawia podstawowe wartości dla JFrame, oraz używa metody initComponents by wczytać komponenty.
     * @author Bartłomiej Kępka 4pP
     */
    public Swieta() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setTitle("Bartłomiej Kępka");
        setSize(500,300);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    /**
     * Inicjalizuje wszystkie używane przez program komponenty graficzne oraz ich właściwości. Używane w konstruktorze klasy.
     * @author Bartłomiej Kępka 4pP
     */
    public void initComponents() {
        panel = new JPanel(null);
        panel.setBackground(Color.RED.darker());
        btnToday = new JButton("Święta dziś");
        btnMonth = new JButton("Święta w tym miesiącu");
        btnAll = new JButton("Wszystkie święta");
        btnClear = new JButton("Wyczyść");
        textarea = new JTextArea();
        textarea.setEditable(false);
        scrollPane = new JScrollPane(textarea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10,45,480,180);

        btnToday.setBounds(10,10,120,30);
        btnMonth.setBounds(145,10,180,30);
        btnAll.setBounds(340,10,150,30);
        btnClear.setBounds(10,230,480,30);
        btnToday.addActionListener(listener);
        btnMonth.addActionListener(listener);
        btnAll.addActionListener(listener);
        btnClear.addActionListener(listener);
        panel.add(btnToday);
        panel.add(btnMonth);
        panel.add(btnAll);
        panel.add(btnClear);
        panel.add(scrollPane);
        setContentPane(panel);
    }

    /**
     * Formatuje i zwraca nazwę miesiąca odpowiadającego podanej liczbie z zakresu od 1 do 12.
     * @param month
     * @return Zamienia wartość liczbową odpowiadającą danemu miesiącowi na polską nazwę danego miesiąca odmienioną przez dopełniacz.
     * @author Bartłomiej Kępka 4pP
     */
    public static String formatMonthName(int month) {
        switch (month) {
            case 1:
                return "stycznia";
            case 2:
                return "lutego";
            case 3:
                return "marca";
            case 4:
                return "kwiecień";
            case 5:
                return "maja";
            case 6:
                return "czerwca";
            case 7:
                return "lipca";
            case 8:
                return "sierpnia";
            case 9:
                return "wrzesnia";
            case 10:
                return "października";
            case 11:
                return "listopada";
            case 12:
                return "grudnia";
            default:
                return "Zły miesiąc";
        }
    }

    /**
     * Używa klasy LocalDate i jej metody now(), by otrzymać aktualną datę.
     * @return zwraca dzisiejszy dzień i miesiąc w formacie "dzień miesiąc_odmieniony_przez_dopełniacz".
     * @author Bartłomiej Kępka 4pP
     */
    public static String getDate() {
        LocalDate now = LocalDate.now();
        int dd = now.getDayOfMonth();
        String month = formatMonthName(now.getMonthValue());
        return dd + " " + month;
    }

    /**
     * Używa klasy LocalDate i jej metody now(), by otrzymać aktualną datę.
     * @return Zwraca aktualny miesiąc odmieniony przez dopełniacz.
     * @author Bartłomiej Kępka 4pP
     */
    public static String getMonth() {
        LocalDate now = LocalDate.now();
        String month = formatMonthName(now.getMonthValue());
        return month;
    }

    /**
     * Sprawdza po kolei każdą linijkę z pliku tekstowego swieta.txt znajdującego się w folderu src projektu.
     * @param searchString
     * @return Zwraca wartość true lub false odpowiadającą rezultatowi szukania podanego fragmentu tekstu w pliku. True jeśli znaleziono, w przeciwnym wypadku false.
     * @throws IOException
     * @author Bartłomiej Kępka 4pP
     */
    public static boolean searchFile(String searchString) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/swieta.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(searchString)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sprawdza po kolei każdą linijkę z pliku tekstowego swieta.txt znajdującego się w folderu src projektu.
     * @param compareText
     * @return Zwraca każdą linijkę pliku tekstowego w przypadku nie podania parametru compareText. W przeciwnym razie, zwraca wszystkie linijki tekstu, gdzie występuje dany fragment. Jeżeli wystąpi błąd przy łączeniu się z plikiem, zwraca "Błąd podczas połączenia z plikiem"
     * @throws IOException
     * @author Bartłomiej Kępka 4pP
     */
    public static String returnFile(String compareText) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/swieta.txt"))) {
            String text = "";
            String line;
            if (compareText.isBlank()) {
                while ((line = br.readLine()) != null) {
                    text += line+"\n";
                }
            } else {
                while ((line = br.readLine()) != null) {
                    if (line.contains(compareText)) {
                        text += line+"\n";
                    }
                }
            }

            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Błąd podczas połączenia z plikiem";
    }
}
