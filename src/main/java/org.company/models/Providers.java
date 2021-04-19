package org.company.models;

import org.company.conexion.Conexion;

import java.util.ArrayList;
import java.util.List;

public class Providers extends Conexion {
    private int id_provider;
    private long ruc_provider;
    private String name_provider;
    private int phone_provider;
    private String address_provider;
    private String name_company_provider;

    public int getId_provider() {
        return id_provider;
    }

    public void setId_provider(int id_provider) {
        this.id_provider = id_provider;
    }

    public long getRuc_provider() {
        return ruc_provider;
    }

    public void setRuc_provider(long ruc_provider) {
        this.ruc_provider = ruc_provider;
    }

    public String getName_provider() {
        return name_provider;
    }

    public void setName_provider(String name_provider) {
        this.name_provider = name_provider;
    }

    public int getPhone_provider() {
        return phone_provider;
    }

    public void setPhone_provider(int phone_provider) {
        this.phone_provider = phone_provider;
    }

    public String getAddress_provider() {
        return address_provider;
    }

    public void setAddress_provider(String address_provider) {
        this.address_provider = address_provider;
    }

    public String getName_company_provider() {
        return name_company_provider;
    }

    public void setName_company_provider(String name_company_provider) {
        this.name_company_provider = name_company_provider;
    }

    public Providers() {
    }

    public Providers(int id_provider, int ruc_provider, String name_provider, int phone_provider, String address_provider, String name_company_provider) {
        this.id_provider = id_provider;
        this.ruc_provider = ruc_provider;
        this.name_provider = name_provider;
        this.phone_provider = phone_provider;
        this.address_provider = address_provider;
        this.name_company_provider = name_company_provider;
    }
    public boolean saveProvider(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("insert into providers (ruc_provider, name_provider, phone_provider, address_provider, name_company_provider) values (?,?,?,?,?)");
            consulta.setLong(1,getRuc_provider());
            consulta.setString(2,getName_provider());
            consulta.setInt(3,getPhone_provider());
            consulta.setString(4,getAddress_provider());
            consulta.setString(5,getName_company_provider());
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }
    public boolean updateProvider(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("update providers set ruc_provider=?, name_provider=?, phone_provider=?, address_provider=?, name_company_provider=? where id_provider=?");
            consulta.setLong(1, getRuc_provider());
            consulta.setString(2, getName_provider());
            consulta.setInt(3, getPhone_provider());
            consulta.setString(4, getAddress_provider());
            consulta.setString(5, getName_company_provider());
            consulta.setInt(6, getId_provider());
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }

    public boolean deleteProvider(int id){
        try {
            openConexion();
            consulta=conexion.prepareStatement("delete from providers where id_provider=?");
            consulta.setInt(1,id);
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }
    public List<Providers> listProviders(){
        List<Providers> ListPro=new ArrayList<>();
        try {
            openConexion();
            consulta=conexion.prepareStatement("select*from providers");
            resultado=consulta.executeQuery();
            while (resultado.next()){
                Providers provi=new Providers();
                provi.setId_provider(resultado.getInt(1));
                provi.setRuc_provider(resultado.getLong(2));
                provi.setName_provider(resultado.getString(3));
                provi.setPhone_provider(resultado.getInt(4));
                provi.setAddress_provider(resultado.getString(5));
                provi.setName_company_provider(resultado.getString(6));
                ListPro.add(provi);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return ListPro;
    }
    public static Providers getLastProvider() {
        try {
            openConexion();
            consulta = conexion.prepareStatement("select id_provider,ruc_provider, name_provider, phone_provider, address_provider, name_company_provider from providers WHERE id_provider = (select max(id_provider) from providers)");
            resultado = consulta.executeQuery();
            if (resultado.next()) {
                Providers p = new Providers();
                p.setId_provider(resultado.getInt(1));
                p.setRuc_provider(resultado.getLong(2));
                p.setName_provider(resultado.getString(3));
                p.setPhone_provider(resultado.getInt(4));
                p.setAddress_provider(resultado.getString(5));
                p.setName_company_provider(resultado.getString(6));
                return p;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConexion();
        }
        return null;
    }
    public static Providers getProvider(int id_provider){
        try {
            openConexion();
            consulta=conexion.prepareStatement("select id_provider,ruc_provider, name_provider, phone_provider, address_provider, name_company_provider from providers where id_provider=?");
            consulta.setInt(1,id_provider);
            resultado=consulta.executeQuery();
            if(resultado.next()){
                Providers pro=new Providers();
                pro.setId_provider(resultado.getInt(1));
                pro.setRuc_provider(resultado.getLong(2));
                pro.setName_provider(resultado.getString(3));
                pro.setPhone_provider(resultado.getInt(4));
                pro.setAddress_provider(resultado.getString(5));
                pro.setName_company_provider(resultado.getString(6));
                return pro;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return  null;
    }
}
