import java.util.Scanner;

public class Tegevused {
    private Klient klient;
    private int parool;

    public Tegevused(Klient klient, int parool) {
        this.klient = klient;
        this.parool = parool;
    }

    public String getKontonimi() {
        return klient.getKliendiNimi();
    }

    public double getKontojääk() {
        return klient.getKontojääk();
    }

    public int getParool() {
        return parool;
    }

    public void setParool(int parool) {
        this.parool = parool;
    }

    // [j] vaata kontojääki
    String vaataJääki() {//saad vaadata kontojääki
        return "Teie konto jääk on: " + klient.getKontojääk() + " EUR.";
    }

    // [p] vaata parooli
    String vaataParooli() {//saad vaadata oma parooli
        return "Teie konto parool on: " + parool;
    }

    boolean piisavSumma(double kogus) {//kontrollib, kas on piisavalt raha, et tegevust teha (korduvate kontojääk-kogus<0 asemel saab seda kasutada, ei ole igalepoole seda veel pannud)
        return !((klient.getKontojääk() - kogus) < 0);
    }

    // [s] sularaha sissemaks
    void sissemaks() {//kogus mida soovid kontole juurde lisada
        Scanner sc = new Scanner(System.in);
        System.out.println("Sisestage summa, mida soovite juurde kontole lisada: ");
        try {
            int kogus = sc.nextInt();
            klient.setKontojääk(klient.getKontojääk() + kogus);
            System.out.println(kogus + " EUR sissemakse teostatud ");
        }catch (Exception e){
            System.out.println("Vigane summa!");
        }
    }

    // [v] sularaha väljavõte
    void väljavõtt() {//sularaha väljavõtt pangaautomaadist
        Scanner sc = new Scanner(System.in);
        System.out.println("Sisestage summa, mida soovite välja võtta: ");
        try{
        int kogus = sc.nextInt();

        if (piisavSumma(kogus)) {
            klient.setKontojääk(klient.getKontojääk() - kogus);
            System.out.println("Sularaha väljavõtt " + kogus + " EUR.");
        } else {
            System.out.println("Kontol pole piisavalt vahendeid, et summat välja võtta.");
        }}catch (Exception e){
            System.out.println("Vigane summa!");
        }
    }

    // [ü] ülekanne
    void ülekanne() { //ülekanne kellegi teisele kontole
        Scanner sc = new Scanner(System.in);
        System.out.println("Sisestage kasutajanimi, kellele soovite ülekannet teha: ");
        try{
        String kelleleÜlekanne = sc.nextLine();

        if (!kelleleÜlekanne.isEmpty()) {
            System.out.println("Sisestage summa, mida soovite üle kanda: ");
            double ülekandeKogus = sc.nextDouble();

            if ((klient.getKontojääk() - ülekandeKogus) < 0) {
                System.out.println("Kontol pole piisavalt vahendeid, et summat üle kanda.");
                System.out.println("Hetkene kontojääk on: " + klient.getKontojääk() + " EUR.");
            } else {
                klient.setKontojääk(klient.getKontojääk() - ülekandeKogus);
                System.out.println(ülekandeKogus + " EUR üle kantud kasutajale: " + kelleleÜlekanne);
            }
        }
        }catch (Exception e){
            System.out.println("Midagi läks valesti! ");
        }
    }

    // [i] investeerimine (sisaldab math randomit)
    void investeerimine() {
        String[] fondid = {"Elukestev", "Kogumisfond 10", "Kogumisfond 30", "Kogumisfond 60", "Kogumisfond 100"};
        System.out.println("Valikus olevad fondid on: ");
        for (String fond : fondid) {
            System.out.print(fond + "; ");
        }
        System.out.println("\n");

        Scanner sc = new Scanner(System.in);
        System.out.println("Kas teate millisesse fondi soovite investeerida? ");
        String jahEi = sc.nextLine();

        if (jahEi.equalsIgnoreCase("Jah")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Millisesse fondi soovite investeerida? ");
            String fond = scanner.nextLine();

            Scanner scan = new Scanner(System.in);
            System.out.println("Kui palju soovite investeerida? ");
            double summa = scan.nextDouble();

            if ((klient.getKontojääk() - summa) < 0) {
                System.out.println("Pole piisvaid vahendeid, et investeerida.");
                System.out.println("Hetkene kontojääk on: " + klient.getKontojääk() + " EUR.");
            } else {
                klient.setKontojääk(klient.getKontojääk() - summa);
                fond = fond.substring(0, 1).toUpperCase() + fond.substring(1).toLowerCase();
                System.out.println(fond + " edukalt investeeritud " + summa + " EUR.");
            }

        }
        if (jahEi.equalsIgnoreCase("Ei")) {
            int indeks = (int) (Math.random() * ((fondid.length)));
            System.out.println("Suvaliselt valitud fond on " + fondid[indeks] + ".");
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Kui palju soovite investeerida?");
            double summa = scanner1.nextDouble();

            if ((klient.getKontojääk() - summa) < 0) {
                System.out.println("Pole piisvaid vahendeid, et investeerida.");
                System.out.println("Hetkene kontojääk on: " + klient.getKontojääk() + " EUR.");
            } else {
                klient.setKontojääk(klient.getKontojääk() - summa);
                System.out.println(fondid[indeks] + " edukalt investeeritud " + summa + " EUR.");
            }
        }
    }
}