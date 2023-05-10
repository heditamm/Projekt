public class Kuldklient extends Klient {
    public Kuldklient(String kliendiNimi, double kontojääk, String parool, String parooliHash) {
        super(kliendiNimi, kontojääk, parool, parooliHash);
    }

    @Override
    String tase() {
        return "Kuldklient";
    }
}