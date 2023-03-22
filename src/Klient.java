public class Klient {
    private int kasutajaTunnus; //nagu tavapangas see numbrite kombo
    private String kliendiNimi;
    private double kontojääk;

    public Klient(int kasutajaTunnus, String kliendiNimi, double kontojääk) {
        this.kasutajaTunnus = kasutajaTunnus;
        this.kliendiNimi = kliendiNimi;
        this.kontojääk = kontojääk;
    }

    public int getKasutajaTunnus() {
        return kasutajaTunnus;
    }

    public String getKliendiNimi() {
        return kliendiNimi;
    }

    public double getKontojääk() {
        return kontojääk;
    }

    public void setKontojääk(double kontojääk) {
        this.kontojääk = kontojääk;
    }

    String tase() {
        return "Tavakasutaja";
    }

    @Override
    public String toString() {
        return "Klient: " + kliendiNimi +
                ", tase: " + tase(); //alamklassides saaks juurde lisada +", hõbeklient" jne
    }
}