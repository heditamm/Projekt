public class Klient {
    private String kliendiNimi;
    private double kontojääk;
    private String parool;
    private String parooliHash;

    public Klient(String kliendiNimi, double kontojääk, String parool, String parooliHash) {
        this.kliendiNimi = kliendiNimi;
        this.kontojääk = kontojääk;
        this.parool = parool;
        this.parooliHash=parooliHash;
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

    public String getParooliHash() {
        return parooliHash;
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