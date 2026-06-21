/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;

import Model.Product;

/**
 *
 * @author ASUS VIVOBOOK
 */
public interface IHistory {
    public void addHistory(String action, Product item);
    public void showHistory();
}
