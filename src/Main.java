import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Tegevused testKasutaja = new Tegevused("Peeter Meeter", 100.0, 123);
        System.out.println("Tere, " + testKasutaja.getKontonimi() + "!" + "\nPalun sisestage oma parool: ");
        /* PANEB PAROOLI VALESTI SS EI SAA JÄTKATA NT
        int parool= testKasutaja.getParool();
        Scanner sisestatudParool = new Scanner(System.in);
        if (sisestatudParool != parol){
            System.out.println("Vale parool! Palun proovige uuesti.");
        }else{

        }
         */

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
                Scanner teguParool= new Scanner(System.in);
                tegevusParool = teguParool.nextLine();
                if (tegevusParool.equalsIgnoreCase("J")) {
                    System.out.println("Sisestage uus parool: ");
                    int tegevusParoolMuutmine;
                    Scanner teguParoolMuutmine= new Scanner(System.in);
                    tegevusParoolMuutmine = teguParoolMuutmine.nextInt();
                    testKasutaja.setParool(tegevusParoolMuutmine);
                    System.out.println("Teie uus parool on: " + testKasutaja.getParool());
                }
            }
            if (tegevus.equalsIgnoreCase("S")) {
                String automaat;
                Scanner koht = new Scanner(System.in);
                System.out.println("Millises pangaautomaadis soovite sissemakset sooritada? ");
                automaat = koht.nextLine();

                testKasutaja.sissemaks(automaat);
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
}
