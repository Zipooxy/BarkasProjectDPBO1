/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ASUS
 */

public class User{
    private int id;
    private String username;
    private String password;
    private boolean isMember; // Apakah user menjadi member
    private double saldo;

    public User(int id, String username, String password, double saldo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.saldo = saldo;
        this.isMember = false; // Default bukan member
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public double getSaldo() {
        return saldo;
    }

    public boolean isMember() {
        return isMember;
    }

    public void menjadiMember() {
        isMember = true;
        System.out.println("Selamat, Anda menjadi member!");
    }

    public void kurangiSaldo(double jumlah) {
        saldo -= jumlah;
    }

    public void tambahSaldo(double jumlah) {
        saldo += jumlah;
    }
}

