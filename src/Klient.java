public abstract class Klient {
    private int kasutajaTunnus; //nagu tavapangas see numbrite kombo
    private String kliendiNimi;
    private double kontojääk;

    public Klient(int kasutajaTunnus, String kliendiNimi, double kontojääk) {
        this.kasutajaTunnus = kasutajaTunnus;
        this.kliendiNimi = kliendiNimi;
        this.kontojääk = kontojääk;
    }

    public int määrabTaseme(double kontojääk){
            if (this.kontojääk <1000){
                return 1;
            } else if (this.kontojääk <5000) {
                return 2;
            } else if (this.kontojääk <9999) {
                return 3;
            }
        return 0;
    }

    public abstract int määrabtaseme(double kontojääk);

    @Override
    public String toString() {
        return "Klient: " + kliendiNimi+
                ", tase: " + määrabTaseme(kontojääk); //alamklassides saaks juurde lisada +", hõbeklient" jne
    }
}
