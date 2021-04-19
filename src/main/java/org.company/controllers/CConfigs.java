package org.company.controllers;

import org.company.models.Configs;

public class CConfigs {
    public static Configs update(long ruc, String name, int phone, String address, String company){
        Configs actualizar=new Configs();
        actualizar.setRuc_config(ruc);
        actualizar.setName_config(name);
        actualizar.setPhone_config(phone);
        actualizar.setAddress_config(address);
        actualizar.setName_company_config(company);
        return actualizar;
    }
}
