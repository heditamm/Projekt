import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Sissemakse extends JFrame implements ActionListener{
    private Klient sisselogitu;
    JLabel sisseField;
    JButton edasiNupp;

    /*public Sissemakse(Klient sisselogitu) throws HeadlessException {
        this.sisselogitu = sisselogitu;
    }*/

    public Sissemakse(Klient sisselogitu){
        this.sisselogitu=sisselogitu;
        setTitle("Sissemakse tegemine");
        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel sisseLabel = new JLabel("Sissemakstav summa: ");
        JTextField sisseField = new JTextField(10);
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
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("Sissemakstav summa: ")){
            double summa = Double.parseDouble(sisseField.getText());
            sisselogitu.setKontojääk((sisselogitu.getKontojääk()+summa));
            System.out.println(sisselogitu.getKontojääk());
        }
        if (e.getSource()==edasiNupp){
            JOptionPane.showMessageDialog(this, "Sissemakse tehtud!");
            new Pank(new Sissemakse(sisselogitu), sisselogitu);
            dispose();
        }
    }
}