import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.*;

class Sissemakse extends JFrame implements ActionListener {
    private final String välja_fail = "tegevuste_logi.txt";
    private final Klient sisselogitu;
    private JTextField sisseField;
    private JButton edasiNupp;
    private JButton tagasiNupp;

    public Sissemakse(Klient sisselogitu) {
        this.sisselogitu = sisselogitu;
        setTitle("Sissemakse tegemine");

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel sisseLabel = new JLabel("Sissemakstav summa: ");
        sisseField = new JTextField(10);
        sisseField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edasiNupp.doClick();
            }
        });
        edasiNupp = new JButton("Edasi");
        edasiNupp.addActionListener(this);
        tagasiNupp = new JButton("Tagasi");
       tagasiNupp.addActionListener(this);

        JPanel sissePanel = new JPanel();
        sissePanel.setLayout(new GridLayout(3, 1));

        sissePanel.add(sisseLabel);
        sissePanel.add(sisseField);
        sissePanel.add(new JLabel());
        sissePanel.add(new JLabel());
        sissePanel.add(edasiNupp);
        sissePanel.add(tagasiNupp);

        add(sissePanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == edasiNupp) {
            String summaTekstina = sisseField.getText();
            Double summa=Double.parseDouble(summaTekstina);
            try {
                int integerPlaces = summaTekstina.indexOf('.');
                int decimalPlaces = summaTekstina.length() - integerPlaces - 1;
                //mõned errorid mis võivad tulla
                if(decimalPlaces > 2 && integerPlaces != -1){
                    JOptionPane.showMessageDialog(this, "Summa saab olla kuni kaks komakohta");
                } else if (summa <= 0) {
                    JOptionPane.showMessageDialog(this, "Summa ei saa olla väiksem või võrdne kui null");
                }else {
                    vahetaSõna("src/Kasutajad.txt", String.valueOf(sisselogitu.getKontojääk()), String.valueOf(sisselogitu.getKontojääk() + summa));
                    sisselogitu.setKontojääk((sisselogitu.getKontojääk() + summa));
                    JOptionPane.showMessageDialog(this, "Sissemakse tehtud!");
                }
                } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                bw.write(sisselogitu.getKliendiNimi() + " teostas sisssemakse summas: " + summa + " EUR. " + aeg + "\n");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            setVisible(false);
            new Pank(sisselogitu);
            dispose();
        }if (e.getSource() == tagasiNupp) {
            setVisible(false);
            new Pank(sisselogitu);
            dispose();
        }
    }

    public void vahetaSõna(String failinimi, String sõna, String uus) throws IOException {
        List<String> failiSisu = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(failinimi));
        String line;
        while ((line = reader.readLine()) != null) {
            failiSisu.add(line);
        }
        reader.close();
        // Asenda kindel sõna
        for (int i = 0; i < failiSisu.size(); i++) {
            String modifiedLine2 = failiSisu.get(i);
            String[] osad= modifiedLine2.split(",");
            if(sisselogitu.getKliendiNimi().equals(osad[0])) {
                String modifiedLine = failiSisu.get(i).replace(sõna, uus);
                System.out.println(modifiedLine);
                failiSisu.set(i, modifiedLine);
            }
        }
        // Kirjuta muudetud sisu tagasi faili
        BufferedWriter writer = new BufferedWriter(new FileWriter(failinimi));
        for (String modifiedLine : failiSisu) {
            writer.write(modifiedLine);
            writer.newLine();
        }
        writer.close();
    }
}