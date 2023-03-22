import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Klient> loeFailist(String failinimi) throws FileNotFoundException {
        ArrayList<Klient> kliendid = new ArrayList<>();

        File file = new File(failinimi);
        Scanner sc = new Scanner(file, "UTF-8");

        while (sc.hasNextLine()) {
            String rida = sc.nextLine();
            String[] ajutineOsad = rida.split(", ");

            int kontonumber = Integer.parseInt(ajutineOsad[0]);
            String kliendiNimi = ajutineOsad[1];
            int parool = Integer.parseInt(ajutineOsad[2]);
            double summa = Double.parseDouble(ajutineOsad[3]);

            if (summa > 5000) {
                Kuldklient ajutine = new Kuldklient(kontonumber, kliendiNimi, summa);
                kliendid.add(ajutine);
            } else {
                Klient ajutineTava = new Klient(kontonumber, kliendiNimi, summa);
                kliendid.add(ajutineTava);
            }

        }
        return kliendid;
    }

    public static void main(String[] args) throws FileNotFoundException {
        //failist kasutajad
        System.out.println(loeFailist("src/Kasutajad.txt"));

        //testid
        Klient testKlient = new Klient(456733456, "Peeter A", 12345);
        Tegevused testTegevus = new Tegevused(testKlient, 123);

        System.out.println("Tere, " + testTegevus.getKontonimi() + "!");

        while (true) {
            Scanner paroolScanner = new Scanner(System.in);
            int sisestatudParool;
            int parool = testTegevus.getParool(); //õige parool
            System.out.println("Palun sisestage oma parool: ");
            if (paroolScanner.hasNextInt()) {
                sisestatudParool = paroolScanner.nextInt();
                if (sisestatudParool == parool) {
                    System.out.println("Tere tulemast!");
                    break;
                } else {
                    System.out.println("Vale parool! Palun proovige uuesti.");
                }
            } else {
                System.out.println("Vale parooli formaat! Palun proovige uuesti.");
            }
        }

        String tegevus;
        Scanner tegu = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    Millist tegevust soovite teha?\s
                     [J] Vaata kontojääki
                     [P] Vaata konto parooli (poolik)
                     [S] Sularaha sissemaks
                     [V] Sularaha väljavõte
                     [Ü] Ülekanne
                     [I] Investeeri
                     [Q] Lõpeta tegevus""");
            tegevus = tegu.nextLine();
            if (tegevus.equalsIgnoreCase("J")) {
                System.out.println(testTegevus.vaataJääki());
            }
            if (tegevus.equalsIgnoreCase("P")) {//poolik
                System.out.println(testTegevus.vaataParooli() + "\nKas soovite parooli muuta?" +
                        "\n[J] Jah\n" +
                        "[E] Ei\n");
                String tegevusParool;
                Scanner teguParool = new Scanner(System.in);
                tegevusParool = teguParool.nextLine();
                if (tegevusParool.equalsIgnoreCase("J")) {
                    System.out.println("Sisestage uus parool: ");
                    int tegevusParoolMuutmine;
                    Scanner teguParoolMuutmine = new Scanner(System.in);
                    tegevusParoolMuutmine = teguParoolMuutmine.nextInt();
                    testTegevus.setParool(tegevusParoolMuutmine);
                    System.out.println("Teie uus parool on: " + testTegevus.getParool());
                }
            }
            if (tegevus.equalsIgnoreCase("S")) {
                testTegevus.sissemaks();
                System.out.println(testTegevus.vaataJääki());
            }
            if (tegevus.equalsIgnoreCase("V")) {
                testTegevus.väljavõtt();
                System.out.println(testTegevus.vaataJääki());
            }
            if (tegevus.equalsIgnoreCase("Ü")) {
                testTegevus.ülekanne();
            }
            if (tegevus.equalsIgnoreCase("I")) {
                testTegevus.investeerimine();
            }
            if (tegevus.equalsIgnoreCase("Q")) {
                System.out.println("Head aega " + testTegevus.getKontonimi() + "!");
                break;

            }
        }
    }
}