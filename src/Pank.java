import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pank extends JFrame implements ActionListener {
    private final String välja_fail = "tegevuste_logi.txt";
    private final Klient sisselogitu;
    JButton jääkNupp;
    JButton paroolNupp;
    JButton sissemaksNupp;
    JButton ülekandeNupp;
    JButton investNupp;
    JButton logivälja;

    public Pank(Klient sisselogitu) {
        this.sisselogitu = sisselogitu;
        setTitle("Pangakonto");

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jääkNupp = new JButton("Kontojääki vaatama");
        paroolNupp = new JButton("Tegevused parooliga");
        sissemaksNupp = new JButton("Sissemaksu tegema");
        ülekandeNupp = new JButton("Ülekannet tegema");
        investNupp = new JButton("Investeerima");
        logivälja = new JButton("Logi välja");

        jääkNupp.addActionListener(this);
        paroolNupp.addActionListener(this);
        sissemaksNupp.addActionListener(this);
        ülekandeNupp.addActionListener(this);
        investNupp.addActionListener(this);
        logivälja.addActionListener(this);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        panel.add(jääkNupp);
        panel.add(paroolNupp);
        panel.add(sissemaksNupp);
        panel.add(ülekandeNupp);
        panel.add(investNupp);
        panel.add(new JLabel());
        panel.add(logivälja);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logivälja) {
            Object[] valikud = {"Jah", "Ei"};
            int option = JOptionPane.showOptionDialog(Pank.this, "Kas olete kindlad, et te soovie välja logida?", "Logi välja", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, valikud, valikud[1]);

            if (option == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(Pank.this, "Head aega!");

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                    String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    bw.write(sisselogitu.getKliendiNimi() + " logis välja. " + aeg + "\n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
                System.exit(0);
            }
        }
        if (e.getSource() == sissemaksNupp) {
            new Sissemakse(sisselogitu);
            dispose();
        }
        if (e.getSource() == jääkNupp) {
            String jääk = "Kontojääk on " + sisselogitu.getKontojääk() + "eur";
            JOptionPane.showMessageDialog(this, jääk);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                bw.write(sisselogitu.getKliendiNimi() + " vaatas kontojääki. " + aeg + "\n");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == ülekandeNupp) {
            new Väljamakse(sisselogitu);
            dispose();
        }
        if (e.getSource() == investNupp) {
            Object[] valikud = {"Jah", "Ei"};
            int option = JOptionPane.showOptionDialog(Pank.this, "Kas teate millisesse fondi Te soovite investeerida?", "Küsimus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, valikud, valikud[1]);

            if (option == JOptionPane.YES_OPTION) {
                new Investeerimine(sisselogitu);
                dispose();
            }
            if (option == JOptionPane.NO_OPTION) {
                String[] fondid = {"Elukestev", "Kogumisfond 10", "Kogumisfond 30", "Kogumisfond 60", "Kogumisfond 100"}; //praegu need ei tee midagi, tulevikus võiks saada suvaliselt tasemele vastavalt investeerida
                int indeks = (int) (Math.random() * ((fondid.length)));//valib suvalise fondi listist
                String fond = fondid[indeks];
                JOptionPane.showMessageDialog(Pank.this, "Teile suvaliselt valitud fond on " + fond + ".");
                new InvesteerimineEbakindel(sisselogitu, fond);
                dispose();
            }

        }
        boolean kasParooliMuudeti = false;
        int parool = 0;
        if (e.getSource() == paroolNupp) {
            Object[] valikud = {"Vaatama", "Muutma"};
            int option = JOptionPane.showOptionDialog(Pank.this, "Kas soovite parooli vaadata või muuta?", "Parooliga", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, valikud, valikud[1]);
            if (option == JOptionPane.YES_OPTION) {//parooli vaatamine
                try {
                    List<String> failiSisu = new ArrayList<>();
                    BufferedReader reader = new BufferedReader(new FileReader("src/algne.txt"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        failiSisu.add(line);
                    }
                    reader.close();
                    for (int i = 0; i < failiSisu.size(); i++) {
                        if (failiSisu.get(i) == sisselogitu.getKliendiNimi()) {
                            parool = Integer.parseInt(failiSisu.get(i + 1));

                        }
                    }
                    if (kasParooliMuudeti) {
                        JOptionPane.showMessageDialog(this, "Teie parool on: " + parool);
                    } else {
                        JOptionPane.showMessageDialog(this, "Teie parool on: " + Login.getPasswordToHash());

                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //parooli vaatamine
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                    String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    bw.write(sisselogitu.getKliendiNimi() + " vaatas enda parooli. " + aeg + "\n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (option == JOptionPane.NO_OPTION) {//parooli muutmine
                new ParooliMuutmine(sisselogitu);
                dispose();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                    String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    bw.write(sisselogitu.getKliendiNimi() + " vahetas enda prooli. " + aeg + "\n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
