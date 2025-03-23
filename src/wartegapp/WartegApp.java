/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package wartegapp;

/**
 *
 * @author izidn
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WartegApp extends JFrame {
    private JTextArea textArea;
    private JButton btnPesan;
    private JComboBox<String> menuMakanan;
    private JTable tabelPesanan;
    private DefaultTableModel modelTabel;
    private int nomorPesanan = 1; // Nomor pesanan dimulai dari 1

    public WartegApp() {
        setTitle("Warteg Sederhana - MultiThread");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel atas buat milih menu makanan
        JPanel panelAtas = new JPanel();
        panelAtas.add(new JLabel("Pilih Makanan:"));

        // Dropdown menu buat pilih makanan
        menuMakanan = new JComboBox<>(new String[]{"Nasi Lengko", "Ayam Goreng", "Soto", "Rames"});
        panelAtas.add(menuMakanan);
        add(panelAtas, BorderLayout.NORTH);

        // Bagian tengah: tabel buat nampilin daftar pesanan
        String[] kolom = {"No", "Pelanggan", "Makanan", "Status"};
        modelTabel = new DefaultTableModel(kolom, 0);
        tabelPesanan = new JTable(modelTabel);
        add(new JScrollPane(tabelPesanan), BorderLayout.CENTER);

        // Tombol buat mesan makanan
        btnPesan = new JButton("Pesan Makanan");
        add(btnPesan, BorderLayout.SOUTH);

        // Action listener buat tombol "Pesan Makanan"
        btnPesan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String namaPelanggan = "Pelanggan " + nomorPesanan;
                String makanan = (String) menuMakanan.getSelectedItem();

                // Tambahin pesanan ke tabel
                modelTabel.addRow(new Object[]{nomorPesanan, namaPelanggan, makanan, "Dimasak"});

                // Bikin thread baru buat proses pesanan
                Pesanan pesanan = new Pesanan(namaPelanggan, makanan, modelTabel, tabelPesanan.getRowCount() - 1);
                pesanan.start(); // Mulai thread

                nomorPesanan++; // Biar nomor pesanan naik terus
            }
        });
    }

    public static void main(String[] args) {
        // Supaya tampilan UI jalan di thread khusus
        SwingUtilities.invokeLater(() -> {
            WartegApp app = new WartegApp();
            app.setVisible(true);
        });
    }
}