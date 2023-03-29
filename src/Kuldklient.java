public class Kuldklient extends Klient {
    public Kuldklient(int kasutajaTunnus, String kliendiNimi, double kontojääk, int parool) {
        super(kasutajaTunnus, kliendiNimi, kontojääk, parool);
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