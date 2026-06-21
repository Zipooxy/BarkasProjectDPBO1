/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ASUS
 */

public class Fashion extends Product {
    private String warna;
    private String ukuran;
    private String jenis;

    public Fashion(int id, String nama, double harga, String warna, String ukuran, String jenis) {
        super(id, nama, harga);
        this.warna = warna;
        this.ukuran = ukuran;
        this.jenis = jenis;
    }

    @Override
    public void displayInfo() {
        System.out.println("ID: " + getId() + "\nNama: " + getNama() + "\nHarga: Rp" + getHarga() + "\nWarna: " + warna +"\nUkuran: " + ukuran + "\nJenis: " + jenis);
    }
}

