package wartegapp;

import java.io.Serializable;

public class PesananData implements Serializable {
    private int nomor;
    private String pelanggan;
    private String makanan;
    private int jumlah;
    private int totalHarga;
    private String status;

    public PesananData(int nomor, String pelanggan, String makanan, int jumlah, int totalHarga, String status) {
        this.nomor = nomor;
        this.pelanggan = pelanggan;
        this.makanan = makanan;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.status = status;
    }

    public int getNomor() {
        return nomor;
    }

    public String getPelanggan() {
        return pelanggan;
    }

    public String getMakanan() {
        return makanan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public String getStatus() {
        return status;
    }
}
