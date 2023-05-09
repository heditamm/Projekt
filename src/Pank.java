import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pank extends JFrame implements ActionListener {
    private final String välja_fail = "tegevuste_logi.txt";
    JLabel jääkLabel;
    JButton jääkNupp;
    JButton paroolNupp;
    JButton sissemaksNupp;
    JButton ülekandeNupp;
    JButton investNupp;
    JButton logivälja;
    private Klient sisselogitu;

    private Sissemakse sissemakse;


    public Pank(Klient sisselogitu) {
        this.sisselogitu=sisselogitu;
        setTitle("Pangakonto");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*Millist tegevust soovite teha?s
                     [J] Vaata kontojääki
                     [P] Tegevused parooliga
                     [S] Sularaha sissemaks
                     [V] Sularaha väljavõte // pole vaja kui on nagu äpp
                     [Ü] Ülekanne
                     [I] Investeeri
                     [Q] Lõpeta tegevus*/

        jääkNupp = new JButton("Kontojääki vaatama");
        paroolNupp = new JButton("Parool");
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

    public void actionPerformed(ActionEvent e){
        if (e.getSource()==logivälja){
            Object[] valikud = {"Jah", "Ei"};
            int option = JOptionPane.showOptionDialog(Pank.this, "Kas olete kindlad, et te soovie välja logida?", "Logi välja", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, valikud, valikud[1]);

            if (option == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(Pank.this, "Head aega!");

                try(BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                    String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    bw.write(sisselogitu.getKliendiNimi() + " logis välja. " + aeg +"\n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
                System.exit(0);
            }
        }
        if (e.getSource()==sissemaksNupp){
            new Sissemakse(sisselogitu);
            dispose();
        }
        if (e.getSource()==jääkNupp){
            String jääk = "Kontojääk on " + sisselogitu.getKontojääk() + "eur";
            JOptionPane.showMessageDialog(this, jääk);

            try(BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                bw.write(sisselogitu.getKliendiNimi() + " vaatas kontojääki. " + aeg +"\n");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource()==ülekandeNupp){
            new Väljamakse(sisselogitu);
            dispose();
        }
    }
}
