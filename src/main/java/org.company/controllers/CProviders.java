package org.company.controllers;

import org.company.models.Providers;

public class CProviders {
    public static Providers createProvider(long ruc, String name, int phone, String address, String company){
        Providers save=new Providers();
        save.setRuc_provider(ruc);
        save.setName_provider(name);
        save.setPhone_provider(phone);
        save.setAddress_provider(address);
        save.setName_company_provider(company);
        return save;
    }
    public static Providers dropProvider(int id_provider){
        Providers delete=new Providers();
        delete.setId_provider(id_provider);
        return delete;
    }
    public static Providers update(long ruc, String name, int phone, String address, String company){
        Providers actualizar=new Providers();
        actualizar.setRuc_provider(ruc);
        actualizar.setName_provider(name);
        actualizar.setPhone_provider(phone);
        actualizar.setAddress_provider(address);
        actualizar.setName_company_provider(company);
        return actualizar;
    }
}
