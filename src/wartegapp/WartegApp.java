package wartegapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class WartegApp extends JFrame {
    private JTable tabelPesanan;
    private DefaultTableModel modelTabel;
    private JComboBox<String> menuMakanan;
    private JSpinner spinnerJumlah;
    private JButton btnPesan, btnCetakStruk, btnUpdate, btnDelete;
    private int nomorPesanan = 1;
    private final String FILE_NAME = "pesanan.ser";
    private final Map<String, Integer> hargaMakanan;

    public WartegApp() {
        setTitle("Warteg Sederhana - Multithread + Struk");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Harga makanan
        hargaMakanan = new HashMap<>();
        hargaMakanan.put("Nasi Lengko", 10000);
        hargaMakanan.put("Ayam Goreng", 12000);
        hargaMakanan.put("Soto", 15000);
        hargaMakanan.put("Rames", 9000);

        // Panel atas
        JPanel panelAtas = new JPanel();
        panelAtas.add(new JLabel("Pilih Makanan:"));
        menuMakanan = new JComboBox<>(hargaMakanan.keySet().toArray(new String[0]));
        panelAtas.add(menuMakanan);

        panelAtas.add(new JLabel("Jumlah:"));
        spinnerJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        panelAtas.add(spinnerJumlah);
        add(panelAtas, BorderLayout.NORTH);

        // Tabel
        String[] kolom = {"No", "Pelanggan", "Makanan", "Jumlah", "Total Harga", "Status"};
        modelTabel = new DefaultTableModel(kolom, 0);
        tabelPesanan = new JTable(modelTabel);
        add(new JScrollPane(tabelPesanan), BorderLayout.CENTER);

        // Panel bawah
        JPanel panelBawah = new JPanel();
        btnPesan = new JButton("Pesan Makanan");
        btnCetakStruk = new JButton("Cetak Struk");
        btnUpdate = new JButton("Update Pesanan");
        btnDelete = new JButton("Hapus Pesanan");

        panelBawah.add(btnPesan);
        panelBawah.add(btnCetakStruk);
        panelBawah.add(btnUpdate);
        panelBawah.add(btnDelete);
        add(panelBawah, BorderLayout.SOUTH);

        // Tombol pesan
        btnPesan.addActionListener(e -> {
            String namaPelanggan = "Pelanggan " + nomorPesanan;
            String makanan = (String) menuMakanan.getSelectedItem();
            int jumlah = (int) spinnerJumlah.getValue();
            int totalHarga = hargaMakanan.get(makanan) * jumlah;

            modelTabel.addRow(new Object[]{nomorPesanan, namaPelanggan, makanan, jumlah, totalHarga, "Dimasak"});
            Pesanan pesanan = new Pesanan(namaPelanggan, makanan, modelTabel, modelTabel.getRowCount() - 1);
            pesanan.start();

            nomorPesanan++;
            simpanData();
        });

        // Tombol cetak struk
            // Tombol cetak struk semua pesanan
// Tombol cetak struk semua pesanan
btnCetakStruk.addActionListener(e -> {
    if (modelTabel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Belum ada pesanan untuk dicetak.");
        return;
    }

    StringBuilder struk = new StringBuilder();
    struk.append("======= STRUK PEMBAYARAN WARTEG =======\n");

    int totalSemuaPesanan = 0; // Variabel untuk menghitung total semua pesanan

    for (int i = 0; i < modelTabel.getRowCount(); i++) {
        struk.append("\nNo Pesanan: ").append(modelTabel.getValueAt(i, 0)).append("\n");
        struk.append("Pelanggan : ").append(modelTabel.getValueAt(i, 1)).append("\n");
        struk.append("Makanan   : ").append(modelTabel.getValueAt(i, 2)).append("\n");
        struk.append("Jumlah    : ").append(modelTabel.getValueAt(i, 3)).append("\n");
        struk.append("Total     : Rp").append(modelTabel.getValueAt(i, 4)).append("\n");

        // Menambahkan total harga pesanan ke totalSemuaPesanan
        totalSemuaPesanan += Integer.parseInt(modelTabel.getValueAt(i, 4).toString());

        struk.append("----------------------------------------");
    }

    struk.append("\n----------------------------------------");
    struk.append("\nTotal Semua Pesanan : Rp").append(totalSemuaPesanan);
    struk.append("\n----------------------------------------");
    struk.append("\nTerima Kasih Telah Berkunjung!\n");

    // Tampilkan di layar
    JOptionPane.showMessageDialog(this, struk.toString(), "Struk Semua Pesanan", JOptionPane.INFORMATION_MESSAGE);

    // Simpan ke G:\setrukk jarkom lat
    try {
        File file = new File("G:/setrukk jarkom lat/struk_pesanan.txt");
        file.getParentFile().mkdirs(); // Bikin folder kalau belum ada

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(struk.toString());
        }

        JOptionPane.showMessageDialog(this, "Struk berhasil disimpan di:\n" + file.getAbsolutePath());
    } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal menyimpan struk!");
    }
});




        // Tombol update
        btnUpdate.addActionListener(e -> {
            int row = tabelPesanan.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih pesanan yang ingin diupdate!");
                return;
            }

            String makanan = (String) menuMakanan.getSelectedItem();
            int jumlah = (int) spinnerJumlah.getValue();
            int totalHarga = hargaMakanan.get(makanan) * jumlah;

            modelTabel.setValueAt(makanan, row, 2);
            modelTabel.setValueAt(jumlah, row, 3);
            modelTabel.setValueAt(totalHarga, row, 4);
            modelTabel.setValueAt("Dimasak", row, 5);

            simpanData();
        });

        // Tombol delete
        btnDelete.addActionListener(e -> {
            int row = tabelPesanan.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih pesanan yang ingin dihapus!");
                return;
            }
            modelTabel.removeRow(row);
            simpanData();
        });

        muatData();
    }

    private void simpanData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            List<PesananData> dataList = new ArrayList<>();

            for (int i = 0; i < modelTabel.getRowCount(); i++) {
                int no = Integer.parseInt(modelTabel.getValueAt(i, 0).toString());
                String pelanggan = modelTabel.getValueAt(i, 1).toString();
                String makanan = modelTabel.getValueAt(i, 2).toString();
                int jumlah = Integer.parseInt(modelTabel.getValueAt(i, 3).toString());
                int total = Integer.parseInt(modelTabel.getValueAt(i, 4).toString());
                String status = modelTabel.getValueAt(i, 5).toString();

                dataList.add(new PesananData(no, pelanggan, makanan, jumlah, total, status));
            }

            out.writeObject(dataList);
            System.out.println("Data berhasil disimpan.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void muatData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            List<PesananData> dataList = (List<PesananData>) in.readObject();

            for (PesananData p : dataList) {
                modelTabel.addRow(new Object[]{
                    p.getNomor(), p.getPelanggan(), p.getMakanan(),
                    p.getJumlah(), p.getTotalHarga(), p.getStatus()
                });

                nomorPesanan = Math.max(nomorPesanan, p.getNomor() + 1);
            }

            System.out.println("Data berhasil dimuat.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Main method untuk menjalankan aplikasi
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WartegApp().setVisible(true);
        });
    }
}
