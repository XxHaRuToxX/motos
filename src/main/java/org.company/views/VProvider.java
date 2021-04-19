package org.company.views;

import org.company.controllers.CProviders;
import org.company.models.Providers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.util.List;

public class VProvider {
    private JPanel panel1;
    private JTextField txtDniRucProvider;
    private JTextField txtNameProvider;
    private JTextField txtPhoneProvider;
    private JTextField txtAddressProvider;
    private JTextField txtNameCompanyProvider;
    private JButton btnSaveProvider;
    private JButton btnNewProvider;
    private JButton btnUpdateProvider;
    private JButton btnDeleteProvider;
    private JTable tableProvider;
    private JTextField txtIdProvider;
    private DefaultTableModel providerModel;
    private Providers provider;

    public VProvider(){
        String[] titles={"ID","RUC", "NOMBRE", "TELÉFONO", "DIRECCIÓN", "RAZÓN SOCIAL"};
        providerModel=new DefaultTableModel(null,titles);
        tableProvider.setModel(providerModel);
        columnsDimension();
        getProviders();
        txtIdProvider.setEnabled(false);

        txtIdProvider.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    provider = Providers.getProvider(Integer.parseInt(txtIdProvider.getText()));
                    if (provider == null) {
                        JOptionPane.showMessageDialog(null, "no existe Proveedor");
                        cleanInputs();
                    } else {
                        showData();
                    }
                }
            }
        });
        btnSaveProvider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateSpaces() && !(txtNameProvider.getText().isEmpty()||txtAddressProvider.getText().isEmpty() || txtNameCompanyProvider.getText().isEmpty())){
                    saveProviders();
                    Providers objClients=Providers.getLastProvider();
                    Object [] fila=new Object[6];
                    fila[0]=objClients.getId_provider();
                    fila[1]=objClients.getRuc_provider();
                    fila[2]=objClients.getName_provider();
                    fila[3]=objClients.getPhone_provider();
                    fila[4]=objClients.getAddress_provider();
                    fila[5]=objClients.getName_company_provider();
                    providerModel.addRow(fila);

                    tableProvider.setModel(providerModel);
                }else{
                    JOptionPane.showMessageDialog(null,"Complete los espacios requeridos o Ingrese correctamente el ruc y el teléfono por favor");
                }
            }
        });
        tableProvider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row=tableProvider.rowAtPoint(e.getPoint());
                txtIdProvider.setText(tableProvider.getValueAt(row,0).toString());
                txtDniRucProvider.setText(tableProvider.getValueAt(row,1).toString());
                txtNameProvider.setText(tableProvider.getValueAt(row,2).toString());
                txtPhoneProvider.setText(tableProvider.getValueAt(row,3).toString());
                txtAddressProvider.setText(tableProvider.getValueAt(row,4).toString());
                txtNameCompanyProvider.setText(tableProvider.getValueAt(row,5).toString());
            }
        });
        btnUpdateProvider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=String.valueOf(txtIdProvider.getText());
                if(id.length()>0){
                    updateProviderV();
                }else {
                    JOptionPane.showMessageDialog(null,"Ingrese un Id para Actualizar por favor");
                }
            }
        });
        btnDeleteProvider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=String.valueOf(txtIdProvider.getText());
                if(id.length()>0){
                    deleteProviderV();
                }else {
                    JOptionPane.showMessageDialog(null,"Ingrese un Id para eliminar por favor");
                }
            }
        });
        btnNewProvider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtIdProvider.setEnabled(true);
                cleanInputs();
            }
        });
    }

    public JPanel getPanel(){
        return panel1;
    }
    public void columnsDimension(){
        TableColumnModel modeloColumna=tableProvider.getColumnModel();
        modeloColumna.getColumn(0).setMaxWidth(40);
        modeloColumna.getColumn(1).setMaxWidth(40);
        modeloColumna.getColumn(2).setMaxWidth(200);
        modeloColumna.getColumn(3).setMaxWidth(100);
        modeloColumna.getColumn(4).setMaxWidth(150);
        modeloColumna.getColumn(5).setMaxWidth(150);
    }
    public void saveProviders(){
        long ruc_provider=Long.parseLong(txtDniRucProvider.getText());
        String name_provider=txtNameProvider.getText();
        int phone_provider=Integer.parseInt(txtPhoneProvider.getText());
        String address_provider=txtAddressProvider.getText();
        String name_company_provider=txtNameCompanyProvider.getText();

        provider= CProviders.createProvider(ruc_provider,name_provider,phone_provider,address_provider,name_company_provider);
        try {

            if (!provider.saveProvider()){
                JOptionPane.showMessageDialog(null,"Proveedor Guardado");
                cleanInputs();
            }else{
                JOptionPane.showMessageDialog(null,"No se guardo Proveedor");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    public void updateProviderV(){
        provider= CProviders.update(Long.parseLong(txtDniRucProvider.getText()),txtNameProvider.getText(),Integer.parseInt(txtPhoneProvider.getText()),txtAddressProvider.getText(),txtNameCompanyProvider.getText());
        provider.setId_provider(Integer.parseInt(txtIdProvider.getText()));
//        System.out.println(client);
        try{
            int question = JOptionPane.showConfirmDialog(null, "¿Estas seguro de actualizar?");
            if(question==0){
                if (provider != null) {
                    JOptionPane.showMessageDialog(null, "Actualizado correctamente");
                    provider.updateProvider();
                    cleanInputs();
                    clearTable();
                    txtIdProvider.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "No se actualizó");
                }
            }else{
                JOptionPane.showMessageDialog(null,"se cancelo la actualización");
                cleanInputs();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void deleteProviderV(){
        int id=Integer.parseInt(txtIdProvider.getText());
        provider=CProviders.dropProvider(id);
        provider.setId_provider(Integer.parseInt(txtIdProvider.getText()));
        try {
            int question = JOptionPane.showConfirmDialog(null, "¿Estas seguro de eliminarlo?");

            if (question == 0) {
                if (!provider.deleteProvider(id)){
                    JOptionPane.showMessageDialog(null, "eliminado");
                    cleanInputs();
                    clearTable();
                } else {
                    JOptionPane.showMessageDialog(null,"no se eliminó");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Se canceló la eliminación");
                cleanInputs();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void getProviders(){
        Providers listProvider=new Providers();
        List<Providers> getProvider=listProvider.listProviders();
        providerModel= (DefaultTableModel) tableProvider.getModel();

        Object[]obj=new Object[6];
        for(int i=0;i<getProvider.size();i++){
            obj[0]=getProvider.get(i).getId_provider();
            obj[1]=getProvider.get(i).getRuc_provider();
            obj[2]=getProvider.get(i).getName_provider();
            obj[3]=getProvider.get(i).getPhone_provider();
            obj[4]=getProvider.get(i).getAddress_provider();
            obj[5]=getProvider.get(i).getName_company_provider();
            providerModel.addRow(obj);
        }
        tableProvider.setModel(providerModel);
    }
    private void cleanInputs(){
        txtIdProvider.setText(null);
        txtDniRucProvider.setText(null);
        txtNameProvider.setText(null);
        txtPhoneProvider.setText(null);
        txtAddressProvider.setText(null);
        txtNameCompanyProvider.setText(null);
    }
    public void clearTable(){
        for (int i=0;i<providerModel.getRowCount();i++){
            providerModel.removeRow(i);
            i=i-1;
        }
        getProviders();
    }
    private boolean validateSpaces(){
        String ruc=String.valueOf(txtDniRucProvider.getText());
        String name=txtNameProvider.getText();
        String phone=String.valueOf(txtPhoneProvider.getText());
        String address=txtAddressProvider.getText();
        String company=txtNameCompanyProvider.getText();
        if(ruc.length()==11&&phone.length()==9){
            return true;
        }else{
            return false;
        }
    }
    private void showData(){
        txtDniRucProvider.setText(String.valueOf(provider.getRuc_provider()));
        txtNameProvider.setText(provider.getName_provider());
        txtPhoneProvider.setText(String.valueOf(provider.getPhone_provider()));
        txtAddressProvider.setText(provider.getAddress_provider());
        txtNameCompanyProvider.setText(provider.getName_company_provider());

    }
}
