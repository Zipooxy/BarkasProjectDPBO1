/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Exception.InvalidRegistrationException;
import Exception.LoginException;
import Model.Electronic;
import Model.Fashion;
//import Model.Living;
import Model.Product;
import Model.User;
import Service.HistoryService;
import Service.ProductService;
import Service.TransactionService;
import Service.UserService;
import Utils.AppUtils;

/**
 *
 * @author ASUS
 */
public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        UserService userService = new UserService();
        HistoryService historyService = new HistoryService();
        TransactionService transaction = new TransactionService(productService, historyService);
        
        productService.initializeProduct();

        while (true) {
            System.out.println("\n===== MENU MASUK =====");
            System.out.println("1. Registrasi User Baru");
            System.out.println("2. Login");
            System.out.println("3. Keluar Aplikasi");
            
            try {
                int choice = AppUtils.inputInt("Pilih menu (1-3): ");

                switch (choice) {
                    case 1:
                        try {
                            User newUser = userService.inputDataRegistrasi();
                            userService.registerUser(newUser);
                            System.out.println("Registrasi berhasil!");
                        } catch (InvalidRegistrationException e) {
                            System.out.println("Registrasi gagal: " + e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            User userLogin = userService.inputLogin();
                            System.out.println("Login berhasil!");
                            int pilihan = 0;
                            while (pilihan != 6){
                                System.out.println("\n----------Menu----------");
                                System.out.println("Selamat datang, " + userLogin.getUsername());
                                System.out.printf("Saldo Anda: Rp %.0f \n", userLogin.getSaldo());
                                System.out.println("Status Member: " + (userLogin.isMember() ? "Ya" : "Tidak"));
                                System.out.println("1. Lihat Produk");
                                System.out.println("2. Beli Produk");
                                System.out.println("3. Jual Produk");
                                System.out.println("4. Menu User");
                                System.out.println("5. History");
                                System.out.println("6. Logout");
                                System.out.println("--------------------------");
                                pilihan = AppUtils.inputInt("Pilih menu: ");

                                switch (pilihan) {
                                    case 1:
                                        productService.showMenuProduct();
                                        break;
                                    case 2:
                                        int idProduk = AppUtils.inputInt("Masukkan ID produk yang ingin dibeli: ");
                                        Product produk = productService.findProductById(idProduk);
                                        transaction.buyItem(produk, userLogin);
                                        break;
                                    case 3:
                                        transaction.sellItem();
                                        break;
                                    case 4:
                                        userService.displayUserMenu();
                                        break;
                                    case 5:
                                        historyService.lihatHistory();
                                        break;
                                    case 6:
                                        System.out.println("Terima kasih!");
                                        break;
                                    default:
                                        System.out.println("Pilihan tidak valid.");
                                }
                            }

                        } catch (LoginException e) {
                            System.out.println("Login gagal: " + e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Terima kasih. Sampai jumpa!");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
    }
}
