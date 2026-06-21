/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.Electronic;
import Model.Fashion;
import Model.Living;
import Model.Product;
import Utils.AppUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ProductService implements IProductService{
    private List<Product> productList = new ArrayList<>();

    public List<Product> getProdukByKategori(Class<?> kategori) {
        List<Product> hasil = new ArrayList<>();
        for (Product item : productList) {
            if (kategori.isInstance(item)) {
                hasil.add(item);
            }
        }
        return hasil;
    }
    public void initializeProduct(){
        addProduct(new Electronic(1, "Laptop Lenovo", 5000000, "Laptop"));
        addProduct(new Fashion(2, "T-shirt Band", 150000, "Hitam", "M", "Pakaian"));
    }
    @Override
    public void addProduct(Product item) {
        productList.add(item);
    }

    @Override
    public void showAllProduct() {
        if (productList.isEmpty()) {
            System.out.println("Tidak ada produk yang tersedia.");
        } else {
            for (Product item : productList) {
                item.displayInfo();
                System.out.println("-------------------------");
            }
        }
    }

    @Override
    public void showFashionProduct() {
        if (productList.isEmpty()) {
            System.out.println("Tidak ada produk yang tersedia.");
        } else {
            for (Product item : productList) {
                if (item instanceof Fashion){
                    item.displayInfo();
                    System.out.println("------------------------");
                }
            }
        }
    }

    @Override
    public void showLivingProduct() {
        if (productList.isEmpty()) {
            System.out.println("Tidak ada produk yang tersedia.");
        } else {
            for (Product item : productList) {
                if (item instanceof Living){
                    item.displayInfo();
                    System.out.println("------------------------");
                }
            }
        }
    }

    @Override
    public void showElectronicProduct() {
        if (productList.isEmpty()) {
            System.out.println("Tidak ada produk yang tersedia.");
        } else {
            for (Product item : productList) {
                if (item instanceof Electronic){
                    item.displayInfo();
                    System.out.println("------------------------");
                }
            }
        }
    }

    @Override
    public Product findProductById(int id) {
        for (Product item : productList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void deleteProduct(Product item) {
        productList.remove(item);
    }

    @Override
    public void showMenuProduct() {
        System.out.println("\n----------Pilih Menu----------");
        System.out.println("1. Lihat Semua");
        System.out.println("2. Produk Fashion");
        System.out.println("3. Produk Electronic");
        System.out.println("4. Produk Living");
        System.out.println("------------------------------");
        int choice = AppUtils.inputInt("Pilih Menu: ");
        switch(choice){
            case 1:
                showAllProduct();
                break;
            case 2:
                showFashionProduct();
                break;
            case 3:
                showElectronicProduct();
                break;
            case 4:
                showLivingProduct();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                break;
        }
    }
}
