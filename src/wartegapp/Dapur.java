/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wartegapp;

/**
 *
 * @author izidn
 */
public class Dapur {
    public static void memasak(String pelanggan, String makanan) {
        System.out.println(pelanggan + " memesan " + makanan + "...");

        try {
            Thread.sleep(3000); // Simulasi memasak selama 3 detik
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(pelanggan + " pesanan " + makanan + " selesai!");
    }
}
