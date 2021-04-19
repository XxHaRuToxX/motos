package org.company.models;

import org.company.conexion.Conexion;

public class Configs extends Conexion {
    private int id_config;
    private long ruc_config;
    private String name_config;
    private int phone_config;
    private String address_config;
    private String name_company_config;

    public int getId_config() {
        return id_config;
    }

    public void setId_config(int id_config) {
        this.id_config = id_config;
    }

    public long getRuc_config() {
        return ruc_config;
    }

    public void setRuc_config(long ruc_config) {
        this.ruc_config = ruc_config;
    }

    public String getName_config() {
        return name_config;
    }

    public void setName_config(String name_config) {
        this.name_config = name_config;
    }

    public int getPhone_config() {
        return phone_config;
    }

    public void setPhone_config(int phone_config) {
        this.phone_config = phone_config;
    }

    public String getAddress_config() {
        return address_config;
    }

    public void setAddress_config(String address_config) {
        this.address_config = address_config;
    }

    public String getName_company_config() {
        return name_company_config;
    }

    public void setName_company_config(String name_company_config) {
        this.name_company_config = name_company_config;
    }

    public Configs() {
    }

    public Configs(int id_config, long ruc_config, String name_config, int phone_config, String address_config, String name_company_config) {
        this.id_config = id_config;
        this.ruc_config = ruc_config;
        this.name_config = name_config;
        this.phone_config = phone_config;
        this.address_config = address_config;
        this.name_company_config = name_company_config;
    }
    public static Configs searchData(){
        try{
            openConexion();
            consulta=conexion.prepareStatement("select*from configs");
            resultado=consulta.executeQuery();
            if(resultado.next()){
                Configs data=new Configs();
                data.setId_config(resultado.getInt(1));
                data.setRuc_config(resultado.getLong(2));
                data.setName_config(resultado.getString(3));
                data.setPhone_config(resultado.getInt(4));
                data.setAddress_config(resultado.getString(5));
                data.setName_company_config(resultado.getString(6));
                return data;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return null;
    }
    public boolean updateCompanyConfig(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("update configs set ruc_config=?, name_config=?, phone_config=?, address_config=?, name_company_config=? where id_config=?");
            consulta.setLong(1, getRuc_config());
            consulta.setString(2, getName_config());
            consulta.setInt(3, getPhone_config());
            consulta.setString(4, getAddress_config());
            consulta.setString(5, getName_company_config());
            consulta.setInt(6, getId_config());
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }
    public static Configs getConfigData(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("select id_config,ruc_config, name_config, phone_config, address_config, name_company_config from configs where id_config=(select max(id_config) from configs)");
            resultado=consulta.executeQuery();
            if(resultado.next()){
                Configs Conf=new Configs();
                Conf.setId_config(resultado.getInt(1));
                Conf.setRuc_config(resultado.getLong(2));
                Conf.setName_config(resultado.getString(3));
                Conf.setPhone_config(resultado.getInt(4));
                Conf.setAddress_config(resultado.getString(5));
                Conf.setName_company_config(resultado.getString(6));
                return Conf;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return  null;
    }
}
