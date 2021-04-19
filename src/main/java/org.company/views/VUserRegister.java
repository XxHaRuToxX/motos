package org.company.views;

import org.company.controllers.CUsers;
import org.company.models.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VUserRegister extends JFrame {
    private JButton btnSignUpRegister;
    private JTextField txtNameRegister;
    private JComboBox boxRolRegister;
    private JPasswordField txtPasswordRegister;
    private JTextField txtEmailRegister;
    private JPanel panelUserRegister;
    private JTextField txtDniRegister;
    private Users signup;
    private VUser vUser=new VUser();

    public VUserRegister(){
        initComponent();
        btnSignUpRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if(validateSpaces()){
//                    signUpUsersV();
//                }else {
//                    JOptionPane.showMessageDialog(null,"Complete los espacios requeridos");
//                }
//                 && !(txtNameRegister.getText().isEmpty() || txtDniRegister.getText().isEmpty() || txtEmailRegister.getText().isEmpty() || txtPasswordRegister.getText().isEmpty()) && boxRolRegister.getSelectedItem().equals("Seleccione una Opción")
                if(validateSpaces()){
                    signUpUsersV();
                    Users objProducts=Users.getLastUser();
                    Object [] fila=new Object[6];
                    fila[0]=objProducts.getId_user();
                    fila[1]=objProducts.getName_user();
                    fila[2]=objProducts.getDni_user();
                    fila[3]=objProducts.getEmail_user();
                    fila[4]=objProducts.getPassword_user();
                    fila[5]=objProducts.getRol_user();
                    vUser.userModel.addRow(fila);

                    vUser.tableUsers.setModel(vUser.userModel);
                }else{
                    JOptionPane.showMessageDialog(null,"Complete los espacios requeridos y el código correctamente por favor");
                }
            }
        });
    }
    private void initComponent(){
        setContentPane(panelUserRegister);
        setSize(400,400);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
    }
    public void signUpUsersV(){
        String name_user=txtNameRegister.getText();
        int dni_user=Integer.parseInt(txtDniRegister.getText());
        String email_user=txtEmailRegister.getText();
        String password_user=txtPasswordRegister.getText();
        String rol_user= boxRolRegister.getSelectedItem().toString();
        signup= CUsers.createUsers(name_user,dni_user,email_user,password_user,rol_user);
        try{
            if(!signup.userSignup()){
                JOptionPane.showMessageDialog(null,"Registro realizado");
                cleanControls();
            }else{
                JOptionPane.showMessageDialog(null,"no se REgistro usuario");
                cleanControls();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private boolean validateSpaces(){
        String name=txtNameRegister.getText();
        String dni=String.valueOf(txtDniRegister.getText());
        String email=txtEmailRegister.getText();
        String password=txtPasswordRegister.getText();
        String rol=boxRolRegister.getSelectedItem().toString();
        if(name.length()>0 &&dni.length()>0&&email.length()>0&&password.length()>0 && rol.length()>0){
            return true;
        }else{
            return false;
        }
    }
    private void cleanControls(){
        txtNameRegister.setText(null);
        txtDniRegister.setText(null);
        txtEmailRegister.setText(null);
        txtPasswordRegister.setText(null);

    }
}
