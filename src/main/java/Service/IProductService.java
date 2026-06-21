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
public interface IProductService {
    public void addProduct(Product item);
    public void showAllProduct();
    public void showFashionProduct();
    public void showLivingProduct();
    public void showElectronicProduct();
    public Product findProductById(int id);
    public void deleteProduct(Product item);
    public void showMenuProduct();
}
