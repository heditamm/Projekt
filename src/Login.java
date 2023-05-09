import jdk.jfr.Event;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Login extends JFrame {
    private final String välja_fail = "tegevuste_logi.txt";
    private JLabel kasutajanimi;
    private JLabel parool;
    private JTextField kasutajaTekst;
    private JPasswordField parooliTekst;
    private JButton loginNupp;
    private JFrame raam;
    private List<Klient> kliendid;
    private JButton resetButton;
    public Klient sisselogitu;

    static List<Klient> loeFailist(String failinimi) throws FileNotFoundException {
        //loeb failist sisse kliendid: täisnimi, parool, kontojääk ning lisab vastavasse klienditasemele
        //paroolide turvalisemat hoiustamist saab 2. rühmatöös katsetada
        ArrayList<Klient> kliendid = new ArrayList<>();

        File file = new File(failinimi);
        Scanner sc = new Scanner(file, "UTF-8");

        while (sc.hasNextLine()) {
            String rida = sc.nextLine();
            String[] ajutineOsad = rida.split(", ");

            int kontonumber = Integer.parseInt(ajutineOsad[0]);
            String kliendiNimi = ajutineOsad[1];
            String paroolFailistHash = ajutineOsad[2];
            double summa = Double.parseDouble(ajutineOsad[3]);

            if (summa > 5000) {
                Kuldklient ajutine = new Kuldklient(kliendiNimi, summa, paroolFailistHash);
                kliendid.add(ajutine);
            } else {
                Klient ajutineTava = new Klient(kliendiNimi, summa, paroolFailistHash);
                kliendid.add(ajutineTava);
            }

        }
        return kliendid;
    }


    public Login(List<Klient> kliendid) {
        // Set window title
        setTitle("Login");

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create UI components
        kasutajanimi = new JLabel("Kasutajanimi:");
        parool = new JLabel("Parool:");
        kasutajaTekst = new JTextField(20);
        parooliTekst = new JPasswordField(20);
        parooliTekst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginNupp.doClick();
            }
        });
        loginNupp = new JButton("Login");
        // Set layout manager
        setLayout(new GridLayout(3, 2));

        // Add components to the frame
        add(kasutajanimi);
        add(kasutajaTekst);
        add(parool);
        add(parooliTekst);
        add(new JLabel()); // Empty label for spacing
        add(loginNupp);
        // Add action listener to the login button
        loginNupp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = kasutajaTekst.getText();
                String passwordToHash = new String(parooliTekst.getPassword());

                //näed inputi
                //System.out.println( passwordToHash);
                boolean olemasolu = false;
                for (Klient klient : kliendid) {
                    if (klient.getKliendiNimi().equalsIgnoreCase(username)) {
                        String stored = klient.getParool();
                        boolean matched = true;
                        try {
                            /*
                            //Kontrollime, et kas sisestatud parool on õige
                            String generatedSecuredPasswordHash
                                    = SHAExample.generateStorngPasswordHash(passwordToHash);
                            System.out.println(generatedSecuredPasswordHash);
                            //kas on sama parool
                            //klient.getParool() on õige!
                            //teist alati muudab inputi prst
                            boolean matched = SHAExample.validatePassword(klient.getParool(), generatedSecuredPasswordHash);

*/
                            if (matched) {
                                sisselogitu = new Klient(klient.getKliendiNimi(), klient.getKontojääk(), klient.getParool());
                                JOptionPane.showMessageDialog(Login.this, "Sisselogimine õnnestus!");

                                try(BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))){
                                    String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                                    bw.write("Kasutaja " + sisselogitu.getKliendiNimi() + " logis sisse. " + aeg + "\n");
                                }
                                kasutajaTekst.setText("");
                                parooliTekst.setText("");
                                olemasolu = true;
                                new Pank(sisselogitu);
                                dispose();
                                //setVisible(false);
                                //System.exit(0);
                            } else {
                                JOptionPane.showMessageDialog(Login.this, "Vale parool. Proovige uuesti.");
                                parooliTekst.setText("");
                                olemasolu = true;
                            }
                        } catch (Exception viga) {
                            System.out.println("Vale parooliformaat!");
                        }
                    }
                }
                if (!olemasolu){
                    JOptionPane.showMessageDialog(Login.this, "Sellist kasutajat ei eksisteeri! Proovige uuesti.");
                    kasutajaTekst.setText("");
                    parooliTekst.setText("");
                }
            }
        });
        // Set window size and visibility
        setSize(300, 150);
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<Klient> kliendid = loeFailist("src/Kasutajad.txt");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login(kliendid);
            }
        });
    }
}