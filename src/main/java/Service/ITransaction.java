/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;

import Model.Product;
import Model.User;

/**
 *
 * @author ASUS VIVOBOOK
 */
public interface ITransaction {
    public void buyItem(Product Item, User user);
    public void sellItem();
}
