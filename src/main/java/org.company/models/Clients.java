package org.company.models;

import org.company.conexion.Conexion;

import java.util.ArrayList;
import java.util.List;

public class Clients extends Conexion {
    private int id_client;
    private int dni_client;
    private String name_client;
    private int phone_client;
    private String address_client;
    private String name_company_client;

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getDni_client() {
        return dni_client;
    }

    public void setDni_client(int dni_client) {
        this.dni_client = dni_client;
    }

    public String getName_client() {
        return name_client;
    }

    public void setName_client(String name_client) {
        this.name_client = name_client;
    }

    public int getPhone_client() {
        return phone_client;
    }

    public void setPhone_client(int phone_client) {
        this.phone_client = phone_client;
    }

    public String getAddress_client() {
        return address_client;
    }

    public void setAddress_client(String address_client) {
        this.address_client = address_client;
    }

    public String getName_company_client() {
        return name_company_client;
    }

    public void setName_company_client(String name_company_client) {
        this.name_company_client = name_company_client;
    }

    public Clients() {
    }

    public Clients(int id_client, int dni_client, String name_client, int phone_client, String address_client, String name_company_client) {
        this.id_client = id_client;
        this.dni_client = dni_client;
        this.name_client = name_client;
        this.phone_client = phone_client;
        this.address_client = address_client;
        this.name_company_client = name_company_client;
    }

    public boolean saveClient(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("insert into clients (dni_client, name_client, phone_client, address_client, name_company_client) values (?,?,?,?,?)");
            consulta.setInt(1,getDni_client());
            consulta.setString(2,getName_client());
            consulta.setInt(3,getPhone_client());
            consulta.setString(4,getAddress_client());
            consulta.setString(5,getName_company_client());
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }
    public boolean updateClient(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("update clients set dni_client=?, name_client=?, phone_client=?, address_client=?, name_company_client=? where id_client=?");
            consulta.setInt(1, getDni_client());
            consulta.setString(2, getName_client());
            consulta.setInt(3, getPhone_client());
            consulta.setString(4, getAddress_client());
            consulta.setString(5, getName_company_client());
            consulta.setInt(6, getId_client());
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }

    public boolean deleteClient(int id){
        try {
            openConexion();
            consulta=conexion.prepareStatement("delete from clients where id_client=?");
            consulta.setInt(1,id);
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }

    public List<Clients> listClients(){
        List<Clients> ListCli=new ArrayList<>();
        try {
            openConexion();
            consulta=conexion.prepareStatement("select*from clients");
            resultado=consulta.executeQuery();
            while (resultado.next()){
                Clients client=new Clients();
                client.setId_client(resultado.getInt(1));
                client.setDni_client(resultado.getInt(2));
                client.setName_client(resultado.getString(3));
                client.setPhone_client(resultado.getInt(4));
                client.setAddress_client(resultado.getString(5));
                client.setName_company_client(resultado.getString(6));
                ListCli.add(client);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return ListCli;
    }
    public static Clients getLastClients() {
        try {
            openConexion();
            consulta = conexion.prepareStatement("select id_client,dni_client,name_client,phone_client,address_client, name_company_client from clients WHERE id_client = (select max(id_client) from clients)");
            resultado = consulta.executeQuery();
            if (resultado.next()) {
                Clients p = new Clients();
                p.setId_client(resultado.getInt(1));
                p.setDni_client(resultado.getInt(2));
                p.setName_client(resultado.getString(3));
                p.setPhone_client(resultado.getInt(4));
                p.setAddress_client(resultado.getString(5));
                p.setName_company_client(resultado.getString(6));
                return p;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConexion();
        }
        return null;
    }
    public static Clients getClients(int id_client){
        try {
            openConexion();
            consulta=conexion.prepareStatement("select id_client,dni_client,name_client,phone_client,address_client, name_company_client from clients where id_client=?");
            consulta.setInt(1,id_client);
            resultado=consulta.executeQuery();
            if(resultado.next()){
                Clients pro=new Clients();
                pro.setId_client(resultado.getInt(1));
                pro.setDni_client(resultado.getInt(2));
                pro.setName_client(resultado.getString(3));
                pro.setPhone_client(resultado.getInt(4));
                pro.setAddress_client(resultado.getString(5));
                pro.setName_company_client(resultado.getString(6));
                return pro;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return  null;
    }
    public static Clients getClientDni(int dni_client){
        try {
            openConexion();
            consulta=conexion.prepareStatement("select*from clients where dni_client=?");
            consulta.setInt(1,dni_client);
            resultado=consulta.executeQuery();
            if(resultado.next()){
                Clients pro=new Clients();
                pro.setName_client(resultado.getString(3));
                pro.setPhone_client(resultado.getInt(4));
                pro.setAddress_client(resultado.getString(5));
                pro.setName_company_client(resultado.getString(6));
                return pro;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return  null;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "id_client=" + id_client +
                ", dni_client=" + dni_client +
                ", name_client='" + name_client + '\'' +
                ", phone_client=" + phone_client +
                ", address_client='" + address_client + '\'' +
                ", name_company_client='" + name_company_client + '\'' +
                '}';
    }
}
