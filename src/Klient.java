public class Klient {
    //private int kasutajaTunnus; //nagu tavapangas see numbrite kombo (hetkel veel puudub aga tuleb 2. rühmatöös)
    private String kliendiNimi;
    private double kontojääk;
    private String parool;

    public Klient(String kliendiNimi, double kontojääk, String parool) {
        //this.kasutajaTunnus = kasutajaTunnus;
        this.kliendiNimi = kliendiNimi;
        this.kontojääk = kontojääk;
        this.parool = parool;
    }

    public String getKliendiNimi() {
        return kliendiNimi;
    }

    public double getKontojääk() {
        return kontojääk;
    }

    public String getParool() {
        return parool;
    }

    public void setParool(String parool) {
        this.parool = parool;
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
                ", tase: " + tase();
    }
}