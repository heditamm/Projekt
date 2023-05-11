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
    private final Klient sisselogitu;
    private JTextField kelleleField;
    private JTextField paljuField;
    private JButton edasiNupp;
    private JButton tagasiNupp;

    public Väljamakse(Klient sisselogitu) {
        this.sisselogitu = sisselogitu;
        setTitle("Väljamakse tegemine");

        setSize(300, 150);
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
        tagasiNupp = new JButton("Tagasi");
        tagasiNupp.addActionListener(this);


        JPanel väljaPanel = new JPanel();
        väljaPanel.setLayout(new GridLayout(4, 1));

        väljaPanel.add(kelleleLabel);
        väljaPanel.add(kelleleField);
        väljaPanel.add(väljaLabel);
        väljaPanel.add(paljuField);
        väljaPanel.add(new JLabel());
        väljaPanel.add(new JLabel());
        väljaPanel.add(edasiNupp);
        väljaPanel.add(tagasiNupp);

        add(väljaPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == edasiNupp) {
            String inimene = kelleleField.getText();
            String summaTekstina = paljuField.getText();
            double summa = Double.parseDouble(summaTekstina);
            try {
                int integerPlaces = summaTekstina.indexOf('.');
                int decimalPlaces = summaTekstina.length() - integerPlaces - 1;
                //mõned errorid mis võivad tulla
                if (decimalPlaces > 2 && integerPlaces != -1) {
                    JOptionPane.showMessageDialog(this, "Summa saab olla kuni kaks komakohta");
                } else if (summa <= 0) {
                    JOptionPane.showMessageDialog(this, "Summa ei saa olla väiksem või võrdne kui null");
                } else if (summa > sisselogitu.getKontojääk()) {
                    System.out.println(sisselogitu.getKontojääk());
                    JOptionPane.showMessageDialog(this, "Kontol pole piisavalt vahendeid!");
                } else {
                    sisselogitu.setKontojääk(sisselogitu.getKontojääk() - summa);
                    JOptionPane.showMessageDialog(this, "Ülekanne kasutajale " + inimene + " tehtud!");//ei muuda selle teise inimese jääki

                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                        String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        bw.write(sisselogitu.getKliendiNimi() + " teostas ülekande summas: " + summa + " EUR. " + "inimesle " + inimene + " " + aeg + "\n");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            }
            setVisible(false);
            new Pank(sisselogitu);
            dispose();
        }
        if (e.getSource() == tagasiNupp) {
            setVisible(false);
            new Pank(sisselogitu);
            dispose();
        }
    }
}
