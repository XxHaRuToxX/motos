package org.company.models;

import org.company.conexion.Conexion;

import java.util.ArrayList;
import java.util.List;

public class Users extends Conexion {
    private int id_user;
    private String name_user;
    private int dni_user;
    private String email_user;
    private String password_user;
    private String rol_user;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public int getDni_user() {
        return dni_user;
    }

    public void setDni_user(int dni_user) {
        this.dni_user = dni_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    public String getRol_user() {
        return rol_user;
    }

    public void setRol_user(String rol_user) {
        this.rol_user = rol_user;
    }

    public Users() {
    }

    public Users(int id_user, String name_user,int dni_user, String email_user, String password_user, String rol_user) {
        this.id_user = id_user;
        this.name_user = name_user;
        this.dni_user=dni_user;
        this.email_user = email_user;
        this.password_user = password_user;
        this.rol_user=rol_user;
    }

    public static Users login(String email,String password){
        try {
            openConexion();
            consulta=conexion.prepareStatement("select*from users where email_user=? and password_user=?");
            consulta.setString(1,email);
            consulta.setString(2,password);
            resultado=consulta.executeQuery();
            if(resultado.next()){
                Users users=new Users();
                users.setId_user(resultado.getInt(1));
                users.setName_user(resultado.getString(2));
                users.setDni_user(resultado.getInt(3));
                users.setEmail_user(resultado.getString(4));
                users.setPassword_user(resultado.getString(5));
                users.setRol_user(resultado.getString(6));
                return users;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return  null;
    }
    public boolean userSignup(){
        try {
            openConexion();
            consulta=conexion.prepareStatement("insert into users (name_user,dni_user,email_user,password_user,rol_user) values (?,?,?,?,?)");
            consulta.setString(1,getName_user());
            consulta.setInt(2,getDni_user());
            consulta.setString(3,getEmail_user());
            consulta.setString(4,getPassword_user());
            consulta.setString(5,getRol_user());
            return consulta.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return true;
    }
    public List<Users> listUsers(){
        List<Users> ListUser=new ArrayList<>();
        try {
            openConexion();
            consulta=conexion.prepareStatement("select*from users");
            resultado=consulta.executeQuery();
            while (resultado.next()){
                Users user=new Users();
                user.setId_user(resultado.getInt(1));
                user.setName_user(resultado.getString(2));
                user.setDni_user(resultado.getInt(3));
                user.setEmail_user(resultado.getString(4));
                user.setPassword_user(resultado.getString(5));
                user.setRol_user(resultado.getString(6));
                ListUser.add(user);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            closeConexion();
        }
        return ListUser;
    }
    public static Users getLastUser() {
        try {
            openConexion();
            consulta = conexion.prepareStatement("select id_user,name_user, dni_user, email_user, password_user, rol_user from users WHERE id_user = (select max(id_user) from users)");
            resultado = consulta.executeQuery();
            if (resultado.next()) {
                Users p = new Users();
                p.setId_user(resultado.getInt(1));
                p.setName_user(resultado.getString(2));
                p.setDni_user(resultado.getInt(3));
                p.setEmail_user(resultado.getString(4));
                p.setPassword_user(resultado.getString(5));
                p.setRol_user(resultado.getString(6));
                return p;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConexion();
        }
        return null;
    }
}
