public class Kuldklient extends Klient {
    public Kuldklient(String kliendiNimi, double kontojääk, int parool) {
        super(kliendiNimi, kontojääk, parool);
    }

    @Override
    String tase() {
        return "Kuldklient";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}