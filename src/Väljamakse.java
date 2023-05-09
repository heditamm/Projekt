import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Väljamakse extends JFrame implements ActionListener {
    private final String välja_fail = "tegevuste_logi.txt";
    private Klient sisselogitu;
    JTextField kelleleField;
    JTextField paljuField;
    JButton edasiNupp;

    public Väljamakse(Klient sisselogitu){
        this.sisselogitu=sisselogitu;
        setTitle("Väljamakse tegemine");

        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel kelleleLabel = new JLabel("Kellele: ");
        kelleleField = new JTextField(10);

        JLabel väljaLabel = new JLabel("Ülekantav summa: ");
        paljuField = new JTextField(10);
        paljuField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edasiNupp.doClick();
            }
        });
        edasiNupp = new JButton("Edasi");
        edasiNupp.addActionListener(this);

        JPanel väljaPanel = new JPanel();
        väljaPanel.setLayout(new GridLayout(3, 1));

        väljaPanel.add(kelleleLabel);
        väljaPanel.add(kelleleField);
        väljaPanel.add(väljaLabel);
        väljaPanel.add(paljuField);
        väljaPanel.add(new JLabel());
        väljaPanel.add(edasiNupp);

        add(väljaPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == edasiNupp) {
            String inimene = kelleleField.getText();
            double summa = Double.parseDouble(paljuField.getText());
            if (summa > sisselogitu.getKontojääk()) {
                System.out.println(sisselogitu.getKontojääk());
                JOptionPane.showMessageDialog(this, "Kontol pole piisavalt vahendeid!");
                setVisible(false);
                new Pank(sisselogitu);
                dispose();
            } else {
                sisselogitu.setKontojääk(sisselogitu.getKontojääk() - summa);
                JOptionPane.showMessageDialog(this, "Ülekanne kasutajale " + inimene + " tehtud!");//ei muuda selle teise inimese jääki

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                    String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    bw.write(sisselogitu.getKliendiNimi() + " teostas ülekande summas: " + summa + " EUR. " + "inimesle " + inimene  + aeg + "\n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
                new Pank(sisselogitu);
                dispose();
            }
        }
    }

}
