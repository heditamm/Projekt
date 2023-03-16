public class Klient {
    private int kasutajaTunnus; //nagu tavapangas see numbrite kombo
    private String kliendiNimi;
    private int punktiSumma; //määrab taseme
    private int kontojääk;

    public Klient(int kasutajaTunnus, String kliendiNimi, int punktiSumma, int kontojääk) {
        this.kasutajaTunnus = kasutajaTunnus;
        this.kliendiNimi = kliendiNimi;
        this.punktiSumma = punktiSumma;
        this.kontojääk = kontojääk;
    }

    @Override
    public String toString() {
        return "Klient: " + kliendiNimi+
                ", punktisumma: " + punktiSumma; //alamklassides saaks juurde lisada +", hõbeklient" jne
    }
}
