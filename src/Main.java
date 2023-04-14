import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Klient> loeFailist(String failinimi) throws FileNotFoundException {
        //loeb failist sisse kliendid: täisnimi, parool, kontojääk ning lisab vastavasse klienditasemele
        //paroolide turvalisemat hoiustamist saab 2. rühmatöös katsetada
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
                Kuldklient ajutine = new Kuldklient(kontonumber, kliendiNimi, summa, parool);
                kliendid.add(ajutine);
            } else {
                Klient ajutineTava = new Klient(kontonumber, kliendiNimi, summa, parool);
                kliendid.add(ajutineTava);
            }

        }
        return kliendid;
    }

    public static void main(String[] args) throws FileNotFoundException {
        //failist kasutajad
        List<Klient> kliendid = loeFailist("src/Kasutajad.txt");

        System.out.println("Sisestage oma nimi: ");
        Scanner nimi = new Scanner(System.in);
        String sisestatudKasutajaNimi = nimi.nextLine();
        Klient sisenevKlient;
        Tegevused tegevKlient = null;

        //loopina vaatab läbi, kas on sellise nimega klienti peaks panema try catch juurde
        for (Klient klient : kliendid) {
            if (klient.getKliendiNimi().equalsIgnoreCase(sisestatudKasutajaNimi)) {
                System.out.println("Tere, " + klient.getKliendiNimi() + "!");
                sisenevKlient = klient;
                tegevKlient = new Tegevused(klient, klient.getParool());
                break;
            }
        }

        while (true) {
            Scanner paroolScanner = new Scanner(System.in);
            int sisestatudParool;
            assert tegevKlient != null;
            int parool = tegevKlient.getParool(); //õige parool
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
                     [P] Tegevused parooliga
                     [S] Sularaha sissemaks
                     [V] Sularaha väljavõte
                     [Ü] Ülekanne
                     [I] Investeeri
                     [Q] Lõpeta tegevus""");
            tegevus = tegu.nextLine();
            if (tegevus.equalsIgnoreCase("J")) {
                System.out.println(tegevKlient.vaataJääki());
            }
            if (tegevus.equalsIgnoreCase("P")) {//poolik
                tegevKlient.parooliTegevused();
            }
            if (tegevus.equalsIgnoreCase("S")) {
                tegevKlient.sissemaks();
                System.out.println(tegevKlient.vaataJääki());
            }
            if (tegevus.equalsIgnoreCase("V")) {
                tegevKlient.väljavõtt();
                System.out.println(tegevKlient.vaataJääki());
            }
            if (tegevus.equalsIgnoreCase("Ü")) {
                tegevKlient.ülekanne();
            }
            if (tegevus.equalsIgnoreCase("I")) {
                if (tegevKlient.getTase().equals("Tavakasutaja")) {
                    String[] fondid = {"Elukestev", "Kogumisfond 10", "Kogumisfond 30", "Kogumisfond 60", "Kogumisfond 100"};
                    System.out.println("Valikus olevad fondid on: ");
                    for (String fond : fondid) {
                        System.out.print(fond + "; ");
                    }
                }
                if (tegevKlient.getTase().equals("Kuldklient")) {
                    String[] fondidKuldklient = {"Rikkur", "Kogumisfond 5000", "Kogumisfond Lill"};
                    for (String fond : fondidKuldklient) {
                        System.out.print(fond + "; ");
                    }
                }
                tegevKlient.investeerimine();
            }
            if (tegevus.equalsIgnoreCase("Q")) {
                System.out.println("Head aega " + tegevKlient.getKontonimi() + "!");
                break;
            }
        }
    }
}