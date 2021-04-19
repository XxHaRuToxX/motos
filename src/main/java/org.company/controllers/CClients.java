package org.company.controllers;

import org.company.models.Clients;

public class CClients {
    public static Clients createClient(int dni, String name, int phone, String address, String company){
        Clients save=new Clients();
        save.setDni_client(dni);
        save.setName_client(name);
        save.setPhone_client(phone);
        save.setAddress_client(address);
        save.setName_company_client(company);
        return save;
    }
    public static Clients dropClient(int id_client){
        Clients delete=new Clients();
        delete.setId_client(id_client);
        return delete;
    }
    public static Clients update(int dni, String name, int phone, String address, String company){
        Clients actualizar=new Clients();
        actualizar.setDni_client(dni);
        actualizar.setName_client(name);
        actualizar.setPhone_client(phone);
        actualizar.setAddress_client(address);
        actualizar.setName_company_client(company);
        return actualizar;
    }
}
