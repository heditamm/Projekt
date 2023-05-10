import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvesteerimineEbakindel extends JFrame implements ActionListener {
    private final String välja_fail = "tegevuste_logi.txt";
    private Klient sisselogitu;
    private String fond;
    private JButton edasinupp;
    private JTextField paljuField;


    public InvesteerimineEbakindel(Klient sisselogitu, String fond) {
        this.sisselogitu = sisselogitu;
        this.fond = fond;
        setTitle("Investeerimine");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel fondLabel = new JLabel("Fondi nimi: " + fond);
        JLabel väljaLabel = new JLabel("Investeeritav summa: ");
        paljuField = new JTextField(10);

        paljuField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edasinupp.doClick();
            }
        });
        edasinupp = new JButton("Edasi");
        edasinupp.addActionListener(this);

        JPanel investPanel = new JPanel();
        investPanel.setLayout(new GridLayout(3, 1));

        investPanel.add(fondLabel);
        investPanel.add(new JLabel());
        investPanel.add(väljaLabel);
        investPanel.add(paljuField);
        investPanel.add(new JLabel());
        investPanel.add(edasinupp);

        add(investPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==edasinupp) {
            double summa = Double.parseDouble(paljuField.getText());
            if (summa > sisselogitu.getKontojääk()) {
                System.out.println(sisselogitu.getKontojääk());
                JOptionPane.showMessageDialog(this, "Kontol pole piisavalt vahendeid!");
            } else {
                sisselogitu.setKontojääk(sisselogitu.getKontojääk() - summa);
                JOptionPane.showMessageDialog(this, "Investeeritud fondi " + fond + "!");

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(välja_fail, true))) {
                    String aeg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    bw.write(sisselogitu.getKliendiNimi() + " investeeris " + summa + " EUR. " + "fondi " + fond + " " + aeg + "\n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            setVisible(false);
            new Pank(sisselogitu);
            dispose();
        }
    }
}

