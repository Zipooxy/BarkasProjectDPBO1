/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class HistoryService implements IHistory{
    private List<String> historyList = new ArrayList<>();

    public void tambahHistory(String aksi, Product produk) {
        String entry = aksi + ": " + produk.getNama();
        historyList.add(entry);
    }

    public void lihatHistory() {
        if (historyList.isEmpty()) {
            System.out.println("Belum ada history.");
        } else {
            for (String entry : historyList) {
                System.out.println(entry);
            }
        }
    }

    @Override
    public void addHistory(String action, Product item) {
        String entry = action + ": " + item.getNama();
        historyList.add(entry);       
    }

    @Override
    public void showHistory() {
        if (historyList.isEmpty()) {
            System.out.println("Belum ada history.");
        } else {
            for (String entry : historyList) {
                System.out.println(entry);
            }
        }        
    }
}
