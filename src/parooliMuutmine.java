import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class parooliMuutmine extends JFrame implements ActionListener {
    private final String välja_fail = "tegevuste_logi.txt";
    JTextField uusParool;
    JButton edasiNupp;
    private Klient sisselogitu;


    public parooliMuutmine(Klient sisselogitu) {
        this.sisselogitu = sisselogitu;
        setTitle("Parooli muutmine");

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel kelleleLabel = new JLabel("Sisestage uus parool: ");
        uusParool = new JTextField(10);

        uusParool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edasiNupp.doClick();
            }
        });
        edasiNupp = new JButton("Salvsta");
        edasiNupp.addActionListener(this);

        JPanel väljaPanel = new JPanel();
        väljaPanel.setLayout(new GridLayout(3, 1));

        väljaPanel.add(kelleleLabel);
        väljaPanel.add(uusParool);
        väljaPanel.add(new JLabel());
        väljaPanel.add(edasiNupp);

        add(väljaPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == edasiNupp) {
            String uus = (uusParool.getText());
            if (uus.length() < 3) {
                JOptionPane.showMessageDialog(this, "Parool peab pikem kui 3 numbrit olema!");
            } else {
                JOptionPane.showMessageDialog(this, "Parool muudetud!");
                //muudab parooli (POOLIK)
                String searchWord = sisselogitu.getParool(); // otsitav sõna
                List<String> failiSisu = new ArrayList<>();
                //uus parool hashiks
                String salt = sisselogitu.getParooliHash();
                String regeneratedPassowrdToVerify =
                        SHAExample.getSecurePassword(uus, salt);

                try {
                    // Faili lugemiseks kasutatav reader
                    BufferedReader reader = new BufferedReader(new FileReader("src/Kasutajad.txt"));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        failiSisu.add(line);
                    }

                    reader.close();

                    // Asenda kindel sõna
                    for (int i = 0; i < failiSisu.size(); i++) {
                        String modifiedLine = failiSisu.get(i).replace(searchWord, regeneratedPassowrdToVerify);
                        failiSisu.set(i, modifiedLine);
                    }

                    // Kirjuta muudetud sisu tagasi faili
                    BufferedWriter writer = new BufferedWriter(new FileWriter("src/Kasutajad.txt"));

                    for (String modifiedLine : failiSisu) {
                        writer.write(modifiedLine);
                        writer.newLine();
                    }

                    writer.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                //kirjutab logi
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                    String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    bw.write(sisselogitu.getKliendiNimi() + " muutis parooli " + regeneratedPassowrdToVerify + "\n");
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