package org.company.controllers;

import org.company.models.Products;

public class CProducts {
    public static Products createProducts(String code, String name, String provider, int stock, double price){
        Products save=new Products();
        save.setCode_product(code);
        save.setName_product(name);
        save.setProvider_product(provider);
        save.setStock_product(stock);
        save.setPrice_product(price);
        return save;
    }
    public static Products dropProduct(int id_product){
        Products delete=new Products();
        delete.setId_product(id_product);
        return delete;
    }
    public static Products update(String code, String name, String provider, int stock, double price){
        Products actualizar=new Products();
        actualizar.setCode_product(code);
        actualizar.setName_product(name);
        actualizar.setProvider_product(provider);
        actualizar.setStock_product(stock);
        actualizar.setPrice_product(price);
        return actualizar;
    }
}
