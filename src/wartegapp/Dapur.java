package wartegapp;

public class Dapur {
    public static void memasak(String pelanggan, String makanan) {
        System.out.println(pelanggan + " sedang memasak " + makanan);
        try {
            Thread.sleep(3000); // Simulasi memasak 3 detik
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(pelanggan + " selesai masak " + makanan);
    }
}
