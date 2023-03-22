public class Kuldklient extends Klient {
    public Kuldklient(int kasutajaTunnus, String kliendiNimi, double kontojääk) {
        super(kasutajaTunnus, kliendiNimi, kontojääk);
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