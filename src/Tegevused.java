import java.util.Scanner;

public class Tegevused {
    private String kontonimi;
    private double kontojääk;

    public Tegevused(String kontonimi, double kontojääk) {
        this.kontonimi = kontonimi;
        this.kontojääk = kontojääk;
    }

    public String getKontonimi() {
        return kontonimi;
    }

    public double getKontojääk() {
        return kontojääk;
    }

    // [j]
    String vaataJääki() {//saad vaadata kontojääki
        return "Teie konto jääk on: " + kontojääk+" EUR.";
    }

    // [s]
    void sissemaks(String pangaautomaat) {//kogus mida soovid kontole juurde panna pangaautomaadist
        int kogus;

        Scanner sc = new Scanner(System.in);
        System.out.println("Sisestage summa, mida soovite juurde kontole lisada: ");
        kogus = sc.nextInt();

        kontojääk += kogus;
        System.out.println(kogus + " EUR sissemakse teostatud " +
                pangaautomaat + " pangaautomaadis");
    }

    // [v]
    void väljavõtt() {//sularaha väljavõtt pangaautomaadist
        int kogus;
        Scanner sc = new Scanner(System.in);
        System.out.println("Sisestage summa, mida soovite välja võtta: ");
        kogus = sc.nextInt();

        if ((kontojääk - kogus) < 0) {
            System.out.println("Kontol pole piisavalt vahendeid, et summat välja võtta.");
        } else {
            kontojääk -= kogus;
            System.out.println("Sularaha väljavõtt " + kogus + " EUR.");
        }
    }
    // [ü]


    // [i] sisaldab math randomit
    void investeerimine() {
        String[] fondid = {"Elukestev", "Kogumisfond 10", "Kogumisfond 30", "Kogumisfond 60", "Kogumisfond 100"};
        System.out.println("Valikus olevad fondid on: ");
        for (String fond:fondid){
            System.out.print(fond+"; ");
        }
        System.out.println("\n");

        String jahEi;
        Scanner sc = new Scanner(System.in);
        System.out.println("Kas teate millisesse fondi soovite investeerida? ");
        jahEi = sc.nextLine();

        if (jahEi.equalsIgnoreCase("Jah")) {
            String fond;
            double summa;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Millisesse fondi soovite investeerida? ");
            fond = scanner.nextLine();

            Scanner scan = new Scanner(System.in);
            System.out.println("Kui palju soovite investeerida? ");
            summa = scan.nextDouble();

            if ((kontojääk - summa) < 0) {
                System.out.println("Pole piisvaid vahendeid, et investeerida.");
            } else {
                kontojääk -= summa;
                fond = fond.substring(0,1).toUpperCase() + fond.substring(1).toLowerCase();
                System.out.println(fond + " edukalt investeeritud " + summa + " EUR.");
            }

        }
        if (jahEi.equalsIgnoreCase("Ei")) {
            double summa;
            int indeks = (int) (Math.random() * ((fondid.length) + 1));
            System.out.println("Suvaliselt valitud fond on " + fondid[indeks] + ".");
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Kui palju soovite investeerida?");
            summa = scanner1.nextDouble();

            if ((kontojääk - summa) < 0) {
                System.out.println("Pole piisvaid vahendeid, et investeerida.");
            } else {
                kontojääk -= summa;
                System.out.println(fondid[indeks] + " edukalt investeeritud " + summa + " EUR.");
            }
        }
    }
}
