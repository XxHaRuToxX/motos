package org.company.controllers;

import org.company.models.Users;

public class CUsers {
    public static Users createUsers(String name,int dni, String email, String password, String rol){
        Users save=new Users();
        save.setName_user(name);
        save.setDni_user(dni);
        save.setEmail_user(email);
        save.setPassword_user(password);
        save.setRol_user(rol);
        return save;
    }
}
