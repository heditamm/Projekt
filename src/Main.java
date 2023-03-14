import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Tegevused testKasutaja = new Tegevused("Peeter Meeter", 100.0);
        System.out.println("Tere, " +testKasutaja.getKontonimi()+"!");
        String tegevus;
        Scanner tegu = new Scanner(System.in);

        while(true) {
            System.out.println("""
            Millist tegevust soovite teha?\s
             [J] vaata kontojääki
             [S] sularaha sissemaks
             [V] sularaha väljavõte
             [Ü] ülekanne (pole valmis)
             [I] investeeri
             [Q] lõpeta tegevus""");
            tegevus = tegu.nextLine();
            if (tegevus.equalsIgnoreCase("J")) {
                System.out.println(testKasutaja.vaataJääki());
            }
            if (tegevus.equalsIgnoreCase("S")) {
                String automaat;
                Scanner koht = new Scanner(System.in);
                System.out.println("Millises pangaautomaadis soovite sissemakset sooritada? ");
                automaat = koht.nextLine();

                testKasutaja.sissemaks(automaat);
            }
            if (tegevus.equalsIgnoreCase("V")) {
                testKasutaja.väljavõtt();
            }
        /*if (tegevus.equalsIgnoreCase("Ü")){
            testKasutaja;
        }*/
            if (tegevus.equalsIgnoreCase("I")){
                testKasutaja.investeerimine();
            }
            if (tegevus.equalsIgnoreCase("Q")){
                System.out.println("Head aega!");
                break;

            }
        }
    }
}
