/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.Electronic;
import Model.Fashion;
import Model.Living;
import Model.Product;
import Model.User;
import Utils.AppUtils;
/**
 *
 * @author ASUS VIVOBOOK
 */
public class TransactionService implements ITransaction{
    ProductService productService;
    HistoryService historyService;
    public TransactionService(ProductService productService, HistoryService historyService ) {
        this.productService = productService;
        this.historyService = historyService;
    }
    
    @Override
    public void buyItem(Product item, User user) {
        if (item != null) {
            double harga = user.isMember() ? item.getHarga() * 0.9 : item.getHarga(); // Diskon 10% jika member
            if (user.getSaldo() >= harga) {
                user.kurangiSaldo(harga);
                productService.deleteProduct(item);
                historyService.addHistory("Membeli", item);
                System.out.printf("Berhasil membeli produk: " + item.getNama() + "total Harga: Rp %.0f\n", harga);
            } else {
                System.out.println("Saldo tidak mencukupi.");
            }
        } else {
            System.out.println("Produk tidak ditemukan.");
        }
    }

    @Override
    public void sellItem() {
        System.out.println("\n----------Pilih Kategori----------");
        System.out.println("1. Fashion");
        System.out.println("2. Electronic");
        System.out.println("3. Living");
        System.out.println("----------------------------------");
        int choice = AppUtils.inputInt("Pilih Kategori: ");
        if (choice == 1) {
            System.out.print("Masukkan ID Produk: ");
            int idItem = AppUtils.getIntInput();
            System.out.println();
            System.out.print("Masukkan Nama Produk: ");
            String namaItem = AppUtils.getStringInput();
            System.out.println();
            System.out.print("Masukkan Harga Produk: ");
            double hargaItem = AppUtils.getDoubleInput();
            System.out.println();
            System.out.print("Masukkan Warna Produk: ");
            String warna = AppUtils.getStringInput();
            System.out.println();
            System.out.print("Masukkan Ukuran Produk: ");
            String ukuran = AppUtils.getStringInput();
            System.out.println();
            System.out.print("Masukkan Jenis Produk: ");
            String jenis = AppUtils.getStringInput();
   
            productService.addProduct(new Fashion(idItem, namaItem, hargaItem, warna, ukuran, jenis));
            System.out.println("Produk berhasil ditambahkan.");
        } else if (choice == 2) {
            System.out.print("Masukkan ID Produk: ");
            int idItem = AppUtils.getIntInput();
            System.out.println();
            System.out.print("Masukkan Nama Produk: ");
            String namaItem = AppUtils.getStringInput();
            System.out.println();
            System.out.print("Masukkan Harga Produk: ");
            double hargaItem = AppUtils.getDoubleInput();
            System.out.println();
            System.out.print("Masukkan Jenis Produk: ");
            String jenis = AppUtils.getStringInput();
   
            productService.addProduct(new Electronic(idItem, namaItem, hargaItem,jenis));
            System.out.println("Produk berhasil ditambahkan.");
        } else if(choice == 3) {
            System.out.print("Masukkan ID Produk: ");
            int idItem = AppUtils.getIntInput();
            System.out.println();
            System.out.print("Masukkan Nama Produk: ");
            String namaItem = AppUtils.getStringInput();
            System.out.println();
            System.out.print("Masukkan Harga Produk: ");
            double hargaItem = AppUtils.getDoubleInput();
            System.out.println();
            System.out.print("Masukkan Jenis Produk: ");
            String jenis = AppUtils.getStringInput();
   
            productService.addProduct(new Living(idItem, namaItem, hargaItem,jenis));
            System.out.println("Produk berhasil ditambahkan.");
        }
    }
    
}
