package org.company.views;

import org.company.models.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VUser extends JFrame {
    private JPanel panel1;
    private JButton btnSearchUser;
    private JTextField txtSearchUser;
    public JTable tableUsers;
    private JButton btnAddUser;
    private JButton btnUpdateUser;
    private JButton btnDeleteUser;
    public DefaultTableModel userModel;

    public VUser() {
        String[] titles={"ID","NOMBRE","DNI", "CORREO", "CONTRASEÑA", "ROL"};
        userModel=new DefaultTableModel(null,titles);
        tableUsers.setModel(userModel);
        getUsers();
        btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                VUserRegister register=new VUserRegister();
//                register.setVisible(true);
//                register.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                VUsersDialog prueba=new VUsersDialog();
                prueba.setVisible(true);
            }
        });
    }

    public JPanel getPanel(){
        return panel1;
    }
    public void getUsers(){
        Users listUser=new Users();
        List<Users> getUser=listUser.listUsers();
        userModel= (DefaultTableModel) tableUsers.getModel();

        Object[]obj=new Object[6];
        for(int i=0;i<getUser.size();i++){
            obj[0]=getUser.get(i).getId_user();
            obj[1]=getUser.get(i).getName_user();
            obj[2]=getUser.get(i).getDni_user();
            obj[3]=getUser.get(i).getEmail_user();
            obj[4]=getUser.get(i).getPassword_user();
            obj[5]=getUser.get(i).getRol_user();
            userModel.addRow(obj);
        }
        tableUsers.setModel(userModel);
    }
}
