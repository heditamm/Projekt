public class Kuldklient extends Klient {
    public Kuldklient(String kliendiNimi, double kontojääk, String parool) {
        super(kliendiNimi, kontojääk, parool);
    }

    @Override
    String tase() {
        return "Kuldklient";
    }
}