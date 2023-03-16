public class KliendidTest {
    public static void main(String[] args) {
        Klient klient1= new Klient(999991, "Toomas Mets", 3222) {
            @Override
            public int määrabtaseme(double kontojääk) {
                return 0;
            }
        };
        System.out.println(klient1.toString());

        Kuldklient kuldklient1= new Kuldklient(98765, "Natalia Uus", 87665.32);
        System.out.println(kuldklient1.toString());

    }
}
