package wartegapp;

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
        Dapur.memasak(pelanggan, makanan);
        synchronized (modelTabel) {
            modelTabel.setValueAt("Selesai", rowIndex, 5); // Kolom ke-5 = Status
        }
    }
}
