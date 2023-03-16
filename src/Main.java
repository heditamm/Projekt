import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        //failist kasutajad
        ArrayList<ArrayList> kuldklients=new ArrayList<>();
        ArrayList<ArrayList> klients=new ArrayList<>();

        File fail = new File("src/Kasutajad.txt");
        try (Scanner sc = new Scanner(fail, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                ArrayList list = new ArrayList();
                String rida = sc.nextLine();
                String[] osad = rida.split(", ");
                int kontonumber = Integer.parseInt(osad[0]);
                String nimi = osad[1];
                int paroolFailist = Integer.parseInt(osad[2]);
                double rahasumma = Double.parseDouble(osad[3]);
                System.out.println(kontonumber+nimi+paroolFailist+rahasumma);
                list.add((kontonumber, nimi, paroolFailist, rahasumma));
                if (rahasumma >5000){
                    kuldklients.add(list);
                }else{
                    klients.add(list);
                }
                /////////////
                Tegevused testKasutaja = new Tegevused("Peeter Meeter", 100.0, 123, 12344567);
                System.out.println("Tere, " + testKasutaja.getKontonimi() + "!");

                while (true) {
                    Scanner paroolScanner = new Scanner(System.in);
                    int sisestatudParool;
                    int parool = testKasutaja.getParool(); //õige parool
                    System.out.println("Palun sisestage oma parool: ");
                    sisestatudParool = paroolScanner.nextInt();

                    if (sisestatudParool == parool) {
                        System.out.println("Tere tulemast!");
                        break;
                    } else {
                        System.out.println("Vale parool! Palun proovige uuesti.");
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
                             [Ü] Ülekanne (pole valmis)
                             [I] Investeeri
                             [Q] Lõpeta tegevus""");
                    tegevus = tegu.nextLine();
                    if (tegevus.equalsIgnoreCase("J")) {
                        System.out.println(testKasutaja.vaataJääki());
                    }
                    if (tegevus.equalsIgnoreCase("P")) {
                        System.out.println(testKasutaja.vaataParooli() + "\nKas soovite parooli muuta?" +
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
                            testKasutaja.setParool(tegevusParoolMuutmine);
                            System.out.println("Teie uus parool on: " + testKasutaja.getParool());
                        }
                    }
                    if (tegevus.equalsIgnoreCase("S")) {
                        testKasutaja.sissemaks();
                        System.out.println(testKasutaja.vaataJääki());
                    }
                    if (tegevus.equalsIgnoreCase("V")) {
                        testKasutaja.väljavõtt();
                        System.out.println(testKasutaja.vaataJääki());
                    }
                    if (tegevus.equalsIgnoreCase("Ü")) {
                        testKasutaja.ülekanne();
                    }
                    if (tegevus.equalsIgnoreCase("I")) {
                        testKasutaja.investeerimine();
                    }
                    if (tegevus.equalsIgnoreCase("Q")) {
                        System.out.println("Head aega " + testKasutaja.getKontonimi() + "!");
                        break;

                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
