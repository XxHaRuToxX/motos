package org.company.views;

import org.company.controllers.CClients;
import org.company.models.Clients;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VClient {
    private JPanel panel1;
    private JButton btnSaveClient;
    private JButton btnNewClient;
    private JButton btnUpdateClient;
    private JButton btnDeleteClient;
    private JTextField txtDniRucClient;
    private JTextField txtNameClient;
    private JTextField txtPhoneNumber;
    private JTextField txtAddressClient;
    private JTextField txtNameCompanyClient;
    private JTable tableClient;
    private JTextField txtIdClient;
    private DefaultTableModel clientModel;
    private Clients client;

    public VClient(){
        String[] titles={"ID","DNI / RUC", "NOMBRE", "TELÉFONO", "DIRECCIÓN", "RAZÓN SOCIAL"};
        clientModel=new DefaultTableModel(null,titles);
        tableClient.setModel(clientModel);
        getClients();
        dimensionColumnas();
        txtIdClient.setEnabled(false);
        btnSaveClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdateClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNewClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDeleteClient.setCursor(new Cursor(Cursor.HAND_CURSOR));

        txtIdClient.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    client = Clients.getClients(Integer.parseInt(txtIdClient.getText()));
                    if (client == null) {
                        JOptionPane.showMessageDialog(null, "no existe Proveedor");
                        cleanInputs();
                    } else {
                        showData();
                    }
                }
            }
        });
        btnSaveClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateSpaces() && !(txtNameClient.getText().isEmpty()||txtAddressClient.getText().isEmpty() || txtNameCompanyClient.getText().isEmpty())){
                    saveClients();
                    Clients objClients=Clients.getLastClients();
                    Object [] fila=new Object[6];
                    fila[0]=objClients.getId_client();
                    fila[1]=objClients.getDni_client();
                    fila[2]=objClients.getName_client();
                    fila[3]=objClients.getPhone_client();
                    fila[4]=objClients.getAddress_client();
                    fila[5]=objClients.getName_company_client();
                    clientModel.addRow(fila);

                    tableClient.setModel(clientModel);
                }else{
                    JOptionPane.showMessageDialog(null,"Complete los espacios requeridos o Ingrese correctamente el dni y el teléfono por favor");
                }
            }
        });
        tableClient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row=tableClient.rowAtPoint(e.getPoint());
                txtIdClient.setText(tableClient.getValueAt(row,0).toString());
                txtDniRucClient.setText(tableClient.getValueAt(row,1).toString());
                txtNameClient.setText(tableClient.getValueAt(row,2).toString());
                txtPhoneNumber.setText(tableClient.getValueAt(row,3).toString());
                txtAddressClient.setText(tableClient.getValueAt(row,4).toString());
                txtNameCompanyClient.setText(tableClient.getValueAt(row,5).toString());
            }
        });
        btnDeleteClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=String.valueOf(txtIdClient.getText());
                if(id.length()>0){
                    deleteClientsV();
                }else {
                    JOptionPane.showMessageDialog(null,"Ingrese un Id para eliminar por favor");
                }

            }
        });
        btnUpdateClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=String.valueOf(txtIdClient.getText());
                if(id.length()>0){
                    updateClientsV();
                }else {
                    JOptionPane.showMessageDialog(null,"Ingrese un Id para Actualizar por favor");
                }
            }
        });
        btnNewClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtIdClient.setEnabled(true);
                cleanInputs();
            }
        });
    }

    public JPanel getPanel(){
        return panel1;
    }
    public void dimensionColumnas(){
        TableColumnModel modeloColumna=tableClient.getColumnModel();
        modeloColumna.getColumn(0).setMaxWidth(50);
        modeloColumna.getColumn(1).setMaxWidth(100);
        modeloColumna.getColumn(2).setMaxWidth(200);
        modeloColumna.getColumn(3).setMaxWidth(100);
        modeloColumna.getColumn(4).setMaxWidth(150);
        modeloColumna.getColumn(5).setMaxWidth(180);
    }
    public void saveClients(){
        int dni_client=Integer.parseInt(txtDniRucClient.getText());
        String name_client=txtNameClient.getText();
        int phone_client=Integer.parseInt(txtPhoneNumber.getText());
        String address_client=txtAddressClient.getText();
        String name_company_client=txtNameCompanyClient.getText();

        client= CClients.createClient(dni_client,name_client,phone_client,address_client,name_company_client);
        try {

            if (!client.saveClient()){
                JOptionPane.showMessageDialog(null,"Cliente Guardado");
                cleanInputs();
            }else{
                JOptionPane.showMessageDialog(null,"No se guardo Cliente");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    public void updateClientsV(){
        client= CClients.update(Integer.parseInt(txtDniRucClient.getText()),txtNameClient.getText(),Integer.parseInt(txtPhoneNumber.getText()),txtAddressClient.getText(),txtNameCompanyClient.getText());
        client.setId_client(Integer.parseInt(txtIdClient.getText()));
//        System.out.println(client);
        try{
            int question = JOptionPane.showConfirmDialog(null, "¿Estas seguro de actualizar?");
            if(question==0){
                if (client != null) {
                    JOptionPane.showMessageDialog(null, "Actualizado correctamente");
                    client.updateClient();
                    cleanInputs();
                    clearTable();
                    txtIdClient.setEnabled(false);
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
    public void deleteClientsV(){
        int id=Integer.parseInt(txtIdClient.getText());
        client=CClients.dropClient(id);
        client.setId_client(Integer.parseInt(txtIdClient.getText()));
        try {
            int question = JOptionPane.showConfirmDialog(null, "¿Estas seguro de eliminarlo?");

            if (question == 0) {
                if (!client.deleteClient(id)){
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
    private void cleanInputs(){
        txtIdClient.setText(null);
        txtDniRucClient.setText(null);
        txtNameClient.setText(null);
        txtPhoneNumber.setText(null);
        txtAddressClient.setText(null);
        txtNameCompanyClient.setText(null);
    }
    public void getClients(){
        Clients listClients=new Clients();
        List<Clients> getClient=listClients.listClients();
        clientModel= (DefaultTableModel) tableClient.getModel();

        Object[]obj=new Object[6];
        for(int i=0;i<getClient.size();i++){
            obj[0]=getClient.get(i).getId_client();
            obj[1]=getClient.get(i).getDni_client();
            obj[2]=getClient.get(i).getName_client();
            obj[3]=getClient.get(i).getPhone_client();
            obj[4]=getClient.get(i).getAddress_client();
            obj[5]=getClient.get(i).getName_company_client();
            clientModel.addRow(obj);
        }
        tableClient.setModel(clientModel);
    }
    public void clearTable(){
        for (int i=0;i<clientModel.getRowCount();i++){
            clientModel.removeRow(i);
            i=i-1;
        }
        getClients();
    }
    private void showData(){
        txtDniRucClient.setText(String.valueOf(client.getDni_client()));
        txtNameClient.setText(client.getName_client());
        txtPhoneNumber.setText(String.valueOf(client.getPhone_client()));
        txtAddressClient.setText(client.getAddress_client());
        txtNameCompanyClient.setText(client.getName_company_client());

    }
    private boolean validateSpaces(){
        String dni=String.valueOf(txtDniRucClient.getText());
        String name=txtNameClient.getText();
        String phone=String.valueOf(txtPhoneNumber.getText());
        String address=txtAddressClient.getText();
        String company=txtNameCompanyClient.getText();
        if(dni.length()==8&&phone.length()==9){
            return true;
        }else{
            return false;
        }
    }

}
