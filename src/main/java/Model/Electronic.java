/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ASUS
 */
public class Electronic extends Product {
    private String jenis;

    public Electronic(int id, String nama, double harga, String jenis) {
        super(id, nama, harga);
        this.jenis = jenis;
    }

    @Override
    public void displayInfo() {
        System.out.println("ID: " + getId() +"\n"+ "Nama: " + getNama() +"\n"+ "Harga: Rp" + getHarga() +"\n"+ "Jenis: " + jenis);
    }
}
