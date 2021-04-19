package org.company.views;

import org.company.controllers.CConfigs;
import org.company.models.Configs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VConfig {
    private JPanel panel1;
    private JTextField txtRucConfig;
    private JTextField txtNameConfig;
    private JTextField txtPhoneConfig;
    private JTextField txtAddressConfig;
    private JTextField txtNameCompanyConfig;
    private JButton btnUpdataConfig;
    private JTextField txtIDConfig;
    private JButton btnWantToUpdate;
    private Configs config;

    public JTextField getTxtRucConfig() {
        return txtRucConfig;
    }

    public void setTxtRucConfig(JTextField txtRucConfig) {
        this.txtRucConfig = txtRucConfig;
    }

    public JTextField getTxtNameConfig() {
        return txtNameConfig;
    }

    public void setTxtNameConfig(JTextField txtNameConfig) {
        this.txtNameConfig = txtNameConfig;
    }

    public JTextField getTxtPhoneConfig() {
        return txtPhoneConfig;
    }

    public void setTxtPhoneConfig(JTextField txtPhoneConfig) {
        this.txtPhoneConfig = txtPhoneConfig;
    }

    public JTextField getTxtAddressConfig() {
        return txtAddressConfig;
    }

    public void setTxtAddressConfig(JTextField txtAddressConfig) {
        this.txtAddressConfig = txtAddressConfig;
    }

    public JTextField getTxtNameCompanyConfig() {
        return txtNameCompanyConfig;
    }

    public void setTxtNameCompanyConfig(JTextField txtNameCompanyConfig) {
        this.txtNameCompanyConfig = txtNameCompanyConfig;
    }

    public JTextField getTxtIDConfig() {
        return txtIDConfig;
    }

    public void setTxtIDConfig(JTextField txtIDConfig) {
        this.txtIDConfig = txtIDConfig;
    }

    public VConfig(){
        showDataCompanyConfig();
        btnAndTxtEnableConfig();
        btnUpdataConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCompanyConfigV();
            }
        });
        btnWantToUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int question = JOptionPane.showConfirmDialog(null, "¿Habilitar botón de actualizar?");
                    if(question==0){
                        txtIDConfig.setEnabled(false);
                        txtRucConfig.setEnabled(true);
                        txtNameConfig.setEnabled(true);
                        txtPhoneConfig.setEnabled(true);
                        txtAddressConfig.setEnabled(true);
                        txtNameCompanyConfig.setEnabled(true);
                        btnUpdataConfig.setEnabled(true);
                    }else{
                        JOptionPane.showMessageDialog(null,"se canceló habilitar el boton Actualizar");
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public JPanel getPanel(){
        return panel1;
    }
    public void showDataCompanyConfig(){
        config=Configs.searchData();
        txtIDConfig.setText(String.valueOf(config.getId_config()));
        txtRucConfig.setText(String.valueOf(config.getRuc_config()));
        txtNameConfig.setText(config.getName_config());
        txtPhoneConfig.setText(String.valueOf(config.getPhone_config()));
        txtAddressConfig.setText(config.getAddress_config());
        txtNameCompanyConfig.setText(config.getName_company_config());
    }
    public void updateCompanyConfigV(){
        config= CConfigs.update(Long.parseLong(txtRucConfig.getText()),txtNameConfig.getText(),Integer.parseInt(txtPhoneConfig.getText()),txtAddressConfig.getText(),txtNameCompanyConfig.getText());
        config.setId_config(Integer.parseInt(txtIDConfig.getText()));
//        System.out.println(client);
        try{
            int question = JOptionPane.showConfirmDialog(null, "¿Estas seguro de actualizar?");
            if(question==0){
                if (config != null) {
                    JOptionPane.showMessageDialog(null, "Actualizado correctamente");
                    config.updateCompanyConfig();
                    showNewDataCompanyConfig();
                    btnAndTxtEnableConfig();
                } else {
                    JOptionPane.showMessageDialog(null, "No se actualizó");
                }
            }else{
                JOptionPane.showMessageDialog(null,"se cancelo la actualización");
                btnAndTxtEnableConfig();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void showNewDataCompanyConfig(){
        config = Configs.getConfigData();
        if (config == null) {
            JOptionPane.showMessageDialog(null, "no se pudo actualizar");
        } else {
            showData();
        }
    }
    public void showData(){
        txtIDConfig.setText(String.valueOf(config.getId_config()));
        txtRucConfig.setText(String.valueOf(config.getRuc_config()));
        txtNameConfig.setText(config.getName_config());
        txtPhoneConfig.setText(String.valueOf(config.getPhone_config()));
        txtAddressConfig.setText(config.getAddress_config());
        txtNameCompanyConfig.setText(config.getName_company_config());
    }
    public void btnAndTxtEnableConfig(){
        txtIDConfig.setEnabled(false);
        txtRucConfig.setEnabled(false);
        txtNameConfig.setEnabled(false);
        txtPhoneConfig.setEnabled(false);
        txtAddressConfig.setEnabled(false);
        txtNameCompanyConfig.setEnabled(false);
        btnUpdataConfig.setEnabled(false);
    }
}
