package org.company.models;

import org.company.conexion.Conexion;

public class Details extends Conexion {
    private int id_detail;
    private String code_product_detail;
    private int quantity_detail;
    private double price_detail;
    private int id_sale_detail;
    private int r;

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public String getCode_product_detail() {
        return code_product_detail;
    }

    public void setCode_product_detail(String code_product_detail) {
        this.code_product_detail = code_product_detail;
    }

    public int getQuantity_detail() {
        return quantity_detail;
    }

    public void setQuantity_detail(int quantity_detail) {
        this.quantity_detail = quantity_detail;
    }

    public double getPrice_detail() {
        return price_detail;
    }

    public void setPrice_detail(double price_detail) {
        this.price_detail = price_detail;
    }

    public int getId_sale_detail() {
        return id_sale_detail;
    }

    public void setId_sale_detail(int id_sale_detail) {
        this.id_sale_detail = id_sale_detail;
    }

    public Details() {
    }

    public Details(int id_detail, String code_product_detail, int quantity_detail, double price_detail, int id_sale_detail) {
        this.id_detail = id_detail;
        this.code_product_detail = code_product_detail;
        this.quantity_detail = quantity_detail;
        this.price_detail = price_detail;
        this.id_sale_detail = id_sale_detail;
    }
    public int saveDetail(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("insert into details (code_product_detail,quantity_detail,price_detail,id_sale_detail) values (?,?,?,?)");
            consulta.setString(1,getCode_product_detail());
            consulta.setInt(2,getQuantity_detail());
            consulta.setDouble(3,getPrice_detail());
            consulta.setInt(4,getId_sale_detail());
            consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return r;
    }
}
