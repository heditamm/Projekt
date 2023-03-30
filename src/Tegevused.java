import java.util.Objects;
import java.util.Scanner;

public class Tegevused {
    private Klient klient;
    private int parool;

    public Tegevused(Klient klient, int parool) {
        this.klient = klient;
        this.parool = parool;
    }

    public String getTase() {
        return klient.tase();
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
            double kogus = sc.nextDouble();
            klient.setKontojääk(klient.getKontojääk() + kogus);
            System.out.println(kogus + " EUR sissemakse teostatud ");
        } catch (Exception e) {//vigane summa kui on a la string, double'i asemel
            System.out.println("Vigane summa!");
        }
    }

    // [v] sularaha väljavõte
    void väljavõtt() {//sularaha väljavõtt pangaautomaadist
        Scanner sc = new Scanner(System.in);
        System.out.println("Sisestage summa, mida soovite välja võtta: ");
        try {
            double kogus = sc.nextDouble();
            if (piisavSumma(kogus)) {
                klient.setKontojääk(klient.getKontojääk() - kogus);
                System.out.println("Sularaha väljavõtt " + kogus + " EUR.");
            } else {
                System.out.println("Kontol pole piisavalt vahendeid, et summat välja võtta.");
            }
        } catch (Exception e) {
            System.out.println("Vigane summa!");
        }
    }

    // [ü] ülekanne
    void ülekanne() { //ülekanne kellegi teisele kontole
        Scanner sc = new Scanner(System.in);
        System.out.println("Sisestage kasutajanimi, kellele soovite ülekannet teha: ");
        try {
            String kelleleÜlekanne = sc.nextLine();

            if (!kelleleÜlekanne.isEmpty()) {
                System.out.println("Sisestage summa, mida soovite üle kanda: ");
                double ülekandeKogus = sc.nextDouble();

                if ((klient.getKontojääk() - ülekandeKogus) < 0) {//kontrollib kas kontol on piisavalt raha, et teha sellises summas ülekannet
                    //kui ei ole siis ütleb nii
                    System.out.println("Kontol pole piisavalt vahendeid, et summat üle kanda.");
                    System.out.println("Hetkene kontojääk on: " + klient.getKontojääk() + " EUR.");
                } else {//kui on siis kannab
                    klient.setKontojääk(klient.getKontojääk() - ülekandeKogus);
                    System.out.println(ülekandeKogus + " EUR üle kantud kasutajale: " + kelleleÜlekanne);
                }
            }
        } catch (Exception e) {//kui sisestatakse a la String double'i asemel
            System.out.println("Midagi läks valesti! Kontrollige üle kasutajanimi ja summa! ");
        }
    }

    // [i] investeerimine (sisaldab math randomit)
    void investeerimine() {
        String[] fondid = {"Elukestev", "Kogumisfond 10", "Kogumisfond 30", "Kogumisfond 60", "Kogumisfond 100"}; //praegu need ei tee midagi, tulevikus võiks saada suvaliselt tasemele vastavalt investeerida
        String[] fondidKuldklient = {"Rikkur", "Kogumisfond 5000", "Kogumisfond Lill"};

        Scanner sc = new Scanner(System.in);
        System.out.println("\n" + "Kas teate millisesse fondi soovite investeerida? [Jah/Ei]");
        String jahEi = sc.nextLine();

        if (jahEi.equalsIgnoreCase("Jah") || jahEi.equalsIgnoreCase("J")) {//juhul kui tead mis fondi investeerida siis küsib täpsustavaid küsimusi
            Scanner scanner = new Scanner(System.in);
            System.out.println("Millisesse fondi soovite investeerida? ");
            String fond = scanner.nextLine();

            Scanner scan = new Scanner(System.in);
            System.out.println("Kui palju soovite investeerida? ");
            try {
                double summa = scan.nextDouble();

                if ((klient.getKontojääk() - summa) < 0) {//kontrollib kas on piisavalt vahendeid, et investeerida antud summat
                    System.out.println("Pole piisvaid vahendeid, et investeerida.");
                    System.out.println("Hetkene kontojääk on: " + klient.getKontojääk() + " EUR.");
                } else {
                    klient.setKontojääk(klient.getKontojääk() - summa);
                    fond = fond.substring(0, 1).toUpperCase() + fond.substring(1).toLowerCase();
                    System.out.println(fond + " edukalt investeeritud " + summa + " EUR.");
                }
            } catch (Exception e) {
                System.out.println("Vigane summa palun proovige uuesti!");
            }

        }
        if (jahEi.equalsIgnoreCase("Ei") || jahEi.equalsIgnoreCase("E")) {//siin ei ole vahet kas oled tava või kuldklient, valib tavafondide seast kõigile.
            int indeks = (int) (Math.random() * ((fondid.length)));//valib suvalise fondi listist
            System.out.println("Suvaliselt valitud fond on " + fondid[indeks] + ".");
            try {
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
            } catch (Exception e) {
                System.out.println("Vigane summa palun proovige uuesti!");
            }
        }
    }

    void parooliTegevused() {
        //saad vaadata oma parooli pärast seda, kui oled sisse loginud. Soovi korral ka parooli muuta. Aga ei muuda tekstifailis parooli.
        System.out.println("Teie parool on: " + klient.getParool());
        Scanner parooliga = new Scanner(System.in);
        System.out.println("Kas soovite parooli muuta? [Jah/Ei]");

        String jahEi = parooliga.nextLine();
        //kui jah siis setParooliga muudab sisselogitud kasutaja parooli
        if (jahEi.equalsIgnoreCase("Jah") || jahEi.equalsIgnoreCase("J")) {
            Scanner uus = new Scanner(System.in);
            System.out.println("Sisestage uus parool: ");
            try {
                klient.setParool(uus.nextInt());
                System.out.println("Teie uus parool on: " + klient.getParool());
            } catch (Exception e) {
                System.out.println("Parool peab koosnema täisarvudest!");
            }
        }
    }
}