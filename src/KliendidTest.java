public class KliendidTest {
    public static void main(String[] args) {
        Klient klient1=new Klient(1234567, "Toomas Põder", 679.8);
        System.out.println(klient1.toString());

        Kuldklient kuldklient1= new Kuldklient(98765, "Natalia Uus", 87665.32);
        System.out.println(kuldklient1.toString());
    }
}
