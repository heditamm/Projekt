public class Kuldklient extends Klient{


    public Kuldklient(int kasutajaTunnus, String kliendiNimi, double kontojääk) {
        super(kasutajaTunnus, kliendiNimi, kontojääk);
    }
        @Override
        public int määrabtaseme(double kontojääk){
            if (kontojääk <5000){
                return 1;
            } else if (kontojääk <50000) {
                return 2;
            } else if (kontojääk <100000) {
                return 3;
            }
            return 0;
        }

    @Override
    public String toString() {
        return super.toString() + " Kuldklient";
    }
}

