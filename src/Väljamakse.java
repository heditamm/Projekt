import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Väljamakse extends JFrame implements ActionListener {
    private Klient sisselogitu;
    JLabel kelleleField;
    JLabel paljuField;
    JButton edasiNupp;

    public Väljamakse(Klient sisselogitu){
        this.sisselogitu=sisselogitu;
        setTitle("Väljamakse tegemine");

        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel kelleleLabel = new JLabel("Kellele: ");
        JTextField kelleleField = new JTextField(10);

        JLabel väljaLabel = new JLabel("Ülekantav summa: ");
        JTextField paljuField = new JTextField(10);
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
        if (e.getActionCommand().equals("Kellele: ")){
            String inimene = kelleleField.getText();

        }
        if (e.getActionCommand().equals("Ülekantav summa: ")){//ei kontrolli kas kontol piisavalt
            double summa = Double.parseDouble(paljuField.getText());
            if (summa>sisselogitu.getKontojääk()){
                System.out.println(sisselogitu.getKontojääk());
                JOptionPane.showMessageDialog(this, "Kontol pole piisavalt vahendeid!");
            }
            else {
                sisselogitu.setKontojääk(sisselogitu.getKontojääk() - summa);
            }
        }
        if (e.getSource()==edasiNupp){
            if (e.getSource()==edasiNupp) {
                JOptionPane.showMessageDialog(this, "Ülekanne tehtud!");
                setVisible(false);
                new Pank(sisselogitu);
                dispose();
            }
        }
    }
}
