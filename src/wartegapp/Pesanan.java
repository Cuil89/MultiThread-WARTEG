/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wartegapp;

/**
 *
 * @author izidn
 */
import javax.swing.table.DefaultTableModel;

public class Pesanan extends Thread {
    private String pelanggan;
    private String makanan;
    private DefaultTableModel modelTabel;
    private int rowIndex;

    public Pesanan(String pelanggan, String makanan, DefaultTableModel modelTabel, int rowIndex) {
        this.pelanggan = pelanggan;
        this.makanan = makanan;
        this.modelTabel = modelTabel;
        this.rowIndex = rowIndex;
    }

    @Override
    public void run() {
        // Proses pesanan di dapur
        Dapur.memasak(pelanggan, makanan);

        // Setelah selesai masak, update status di tabel
        synchronized (modelTabel) {
            modelTabel.setValueAt("Selesai", rowIndex, 3);
        }
    }
}