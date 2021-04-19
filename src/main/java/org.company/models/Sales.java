package org.company.models;

import org.company.conexion.Conexion;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Sales extends Conexion {
    private int id_sale;
    private String client_sale;
    private String seller_sale;
    private double total_sale;
    private String date_sale;
    private int r;

    public int getId_sale() {
        return id_sale;
    }

    public void setId_sale(int id_sale) {
        this.id_sale = id_sale;
    }

    public String getClient_sale() {
        return client_sale;
    }

    public void setClient_sale(String client_sale) {
        this.client_sale = client_sale;
    }

    public String getSeller_sale() {
        return seller_sale;
    }

    public void setSeller_sale(String seller_sale) {
        this.seller_sale = seller_sale;
    }

    public double getTotal_sale() {
        return total_sale;
    }

    public void setTotal_sale(double total_sale) {
        this.total_sale = total_sale;
    }

    public String getDate_sale() {
        return date_sale;
    }

    public void setDate_sale(String date_sale) {
        this.date_sale = date_sale;
    }

    public Sales() {
    }

    public Sales(int id_sale, String client_sale, String seller_sale, double total_sale, String date_sale) {
        this.id_sale = id_sale;
        this.client_sale = client_sale;
        this.seller_sale = seller_sale;
        this.total_sale = total_sale;
        this.date_sale=date_sale;
    }

    public int saveSale(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("insert into sales (client_sale,seller_sale,total_sale,date_sale) values (?,?,?,?)");
            consulta.setString(1,getClient_sale());
            consulta.setString(2,getSeller_sale());
            consulta.setDouble(3,getTotal_sale());
            consulta.setString(4,getDate_sale());
            consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return r;
    }
    public int idSale(){
        int id=0;
        try{
            openConexion();
            consulta=conexion.prepareStatement("select max(id_sale) from sales");
            resultado=consulta.executeQuery();
            if(resultado.next()){
                id=resultado.getInt(1);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return id;
    }

    public boolean updateStock(int quantity, String code){
        try {
            openConexion();
            consulta=conexion.prepareStatement("update products set stock_product=? where code_product=?");
            consulta.setInt(1,quantity);
            consulta.setString(2,code);
            consulta.execute();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return false;
    }
    public List<Sales> listSales(){
        List<Sales> listSale=new ArrayList<>();
        try {
            openConexion();
            consulta=conexion.prepareStatement("select*from sales");
            resultado=consulta.executeQuery();
            while (resultado.next()){
                Sales sale=new Sales();
                sale.setId_sale(resultado.getInt(1));
                sale.setClient_sale(resultado.getString(2));
                sale.setSeller_sale(resultado.getString(3));
                sale.setTotal_sale(resultado.getDouble(4));
                listSale.add(sale);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return listSale;
    }
    public static void showSellers(JComboBox seller){
        try {
            openConexion();
            consulta=conexion.prepareStatement("select name_user from users");
            resultado=consulta.executeQuery();
            seller.addItem("Seleccione una Opción");
            while (resultado.next()){
                seller.addItem(resultado.getString("name_user"));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
    }
}
