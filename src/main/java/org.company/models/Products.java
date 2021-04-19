package org.company.models;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
import org.company.conexion.Conexion;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Products extends Conexion {
    private int id_product;
    private String code_product;
    private String name_product;
    private String provider_product;
    private int stock_product;
    private double price_product;

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getCode_product() {
        return code_product;
    }

    public void setCode_product(String code_product) {
        this.code_product = code_product;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getProvider_product() {
        return provider_product;
    }

    public void setProvider_product(String provider_product) {
        this.provider_product = provider_product;
    }

    public int getStock_product() {
        return stock_product;
    }

    public void setStock_product(int stock_product) {
        this.stock_product = stock_product;
    }

    public double getPrice_product() {
        return price_product;
    }

    public void setPrice_product(double price_product) {
        this.price_product = price_product;
    }

    public Products() {
    }

    public Products(int id_product, String code_product, String name_product, String provider_product, int stock_product, double price_product) {
        this.id_product = id_product;
        this.code_product = code_product;
        this.name_product = name_product;
        this.provider_product = provider_product;
        this.stock_product = stock_product;
        this.price_product = price_product;
    }
    public boolean saveProduct(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("insert into products (code_product, name_product, provider_product, stock_product, price_product) values (?,?,?,?,?)");
            consulta.setString(1,getCode_product());
            consulta.setString(2,getName_product());
            consulta.setString(3,getProvider_product());
            consulta.setInt(4,getStock_product());
            consulta.setDouble(5,getPrice_product());
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }
    public boolean updateProduct(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("update products set code_product=?, name_product=?, provider_product=?, stock_product=?, price_product=? where id_product=?");
            consulta.setString(1, getCode_product());
            consulta.setString(2, getName_product());
            consulta.setString(3, getProvider_product());
            consulta.setInt(4, getStock_product());
            consulta.setDouble(5, getPrice_product());
            consulta.setInt(6, getId_product());
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }

    public boolean deleteProduct(int id){
        try {
            openConexion();
            consulta=conexion.prepareStatement("delete from products where id_product=?");
            consulta.setInt(1,id);
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }
    public static void showProviders(JComboBox provider){
        try {
            openConexion();
            consulta=conexion.prepareStatement("select name_provider from providers");
            resultado=consulta.executeQuery();
            provider.addItem("Seleccione una Opción");
            while (resultado.next()){
                provider.addItem(resultado.getString("name_provider"));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
    }
    public List<Products> listProducts(){
        List<Products> ListPro=new ArrayList<>();
        try {
            openConexion();
            consulta=conexion.prepareStatement("select*from products");
            resultado=consulta.executeQuery();
            while (resultado.next()){
                Products produ=new Products();
                produ.setId_product(resultado.getInt(1));
                produ.setCode_product(resultado.getString(2));
                produ.setName_product(resultado.getString(3));
                produ.setProvider_product(resultado.getString(4));
                produ.setStock_product(resultado.getInt(5));
                produ.setPrice_product(resultado.getDouble(6));
                ListPro.add(produ);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return ListPro;
    }
    public static Products getLastProduct() {
        try {
            openConexion();
            consulta = conexion.prepareStatement("select id_product,code_product, name_product, provider_product, stock_product, price_product from products WHERE id_product = (select max(id_product) from products)");
            resultado = consulta.executeQuery();
            if (resultado.next()) {
                Products p = new Products();
                p.setId_product(resultado.getInt(1));
                p.setCode_product(resultado.getString(2));
                p.setName_product(resultado.getString(3));
                p.setProvider_product(resultado.getString(4));
                p.setStock_product(resultado.getInt(5));
                p.setPrice_product(resultado.getDouble(6));
                return p;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConexion();
        }
        return null;
    }
    public static Products getProduct(int id_product){
        try {
            openConexion();
            consulta=conexion.prepareStatement("select id_product,code_product, name_product, provider_product, stock_product, price_product from products where id_product=?");
            consulta.setInt(1,id_product);
            resultado=consulta.executeQuery();
            if(resultado.next()){
                Products pro=new Products();
                pro.setId_product(resultado.getInt(1));
                pro.setCode_product(resultado.getString(2));
                pro.setName_product(resultado.getString(3));
                pro.setProvider_product(resultado.getString(4));
                pro.setStock_product(resultado.getInt(5));
                pro.setPrice_product(resultado.getDouble(6));
                return pro;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return  null;
    }
    public static Products searchProduct(String code_product){
        try{
            openConexion();
            consulta=conexion.prepareStatement("select*from products where code_product=?");
            consulta.setString(1,code_product);
            resultado=consulta.executeQuery();
            if(resultado.next()){
                Products pro=new Products();
                pro.setName_product(resultado.getString(3));
                pro.setStock_product(resultado.getInt(5));
                pro.setPrice_product(resultado.getDouble(6));
                return pro;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return null;
    }
    public static void generateBarsProduct(){
        Image img;
        JFileChooser chooser=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Archivos de PDF","pdf");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
            String ruta =chooser.getSelectedFile().toString().concat(".pdf");
            try {
                openConexion();
                consulta = conexion.prepareStatement("select*from products");
                resultado = consulta.executeQuery();
                Document doc = new Document();
                PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(ruta));
                doc.open();
                Barcode39 code = new Barcode39();
                while (resultado.next()) {
                    code.setCode(resultado.getString("code_product"));
                    img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                    doc.add(img);
                    doc.add(new Paragraph(""));
                }
                doc.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                closeConexion();
            }
        }
    }
    public static void getBarsProduct(String codeProduct){
        Image img;
        JFileChooser chooser=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Archivos de PDF","pdf");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
            String ruta =chooser.getSelectedFile().toString().concat(".pdf");
            try {
                openConexion();
                consulta = conexion.prepareStatement("select code_product from products where code_product=?");
                consulta.setString(1,codeProduct);
                resultado = consulta.executeQuery();
                Document doc = new Document();
                PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(ruta));
                doc.open();
                Barcode39 code = new Barcode39();
                while (resultado.next()) {
                    code.setCode(resultado.getString("code_product"));
                    img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                    doc.add(img);
                    doc.add(new Paragraph(""));
                }
                doc.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                closeConexion();
            }
        }
    }
    public static void showProducts(JComboBox product){
        try {
            openConexion();
            consulta=conexion.prepareStatement("select code_product from products");
            resultado=consulta.executeQuery();
            product.addItem("Seleccione una Opción");
            while (resultado.next()){
                product.addItem(resultado.getString("code_product"));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
    }

}
