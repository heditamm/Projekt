import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Sissemakse extends JFrame implements ActionListener {
    private final String välja_fail = "tegevuste_logi.txt";
    private final Klient sisselogitu;
    JTextField sisseField;
    JButton edasiNupp;

    /*public Sissemakse(Klient sisselogitu) throws HeadlessException {
        this.sisselogitu = sisselogitu;
    }*/

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

        JPanel sissePanel = new JPanel();
        sissePanel.setLayout(new GridLayout(2, 1));


        sissePanel.add(sisseLabel);
        sissePanel.add(sisseField);
        sissePanel.add(new JLabel());
        sissePanel.add(edasiNupp);

        add(sissePanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == edasiNupp) {
            double summa = Double.parseDouble(sisseField.getText());
            sisselogitu.setKontojääk((sisselogitu.getKontojääk() + summa));
            JOptionPane.showMessageDialog(this, "Sissemakse tehtud!");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                bw.write(sisselogitu.getKliendiNimi() + " teostas sisssemakse summas: " + summa + " EUR. " + aeg + "\n");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            setVisible(false);
            new Pank(sisselogitu);
            dispose();
        }
    }
}