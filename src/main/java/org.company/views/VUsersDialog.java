package org.company.views;

import org.company.controllers.CUsers;
import org.company.models.Users;

import javax.swing.*;
import java.awt.event.*;

public class VUsersDialog extends JDialog {
    private JPanel contentPane;
    private JPanel panelUserRegister;
    private JButton btnSignUpRegister;
    private JTextField txtNameRegister;
    private JComboBox boxRolRegister;
    private JPasswordField txtPasswordRegister;
    private JTextField txtEmailRegister;
    private JTextField txtDniRegister;
    private JButton buttonOK;
    private JButton buttonCancel;
    private Users signup;
    private VUser vUser=new VUser();

    public VUsersDialog() {
        setContentPane(contentPane);
        setModal(true);
        setSize(300,495);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);

        btnSignUpRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(validateSpaces()){
                    int question=JOptionPane.showConfirmDialog(null,"¿Está seguro de Agregar?");
                    if(question==0){
                        signUpUsersV();
                        onOK();
                    }else{
                        JOptionPane.showMessageDialog(null,"se cancelo el registro");
                        cleanControls();
                    }

                }else {
                    JOptionPane.showMessageDialog(null,"Complete los espacios requeridos");
                }
//                if(validateSpaces()){
//                    signUpUsersV();
//                    Users objProducts=Users.getLastUser();
//                    Object [] fila=new Object[6];
//                    fila[0]=objProducts.getId_user();
//                    fila[1]=objProducts.getName_user();
//                    fila[2]=objProducts.getDni_user();
//                    fila[3]=objProducts.getEmail_user();
//                    fila[4]=objProducts.getPassword_user();
//                    fila[5]=objProducts.getRol_user();
//                    vUser.userModel.addRow(fila);
//
//                    vUser.tableUsers.setModel(vUser.userModel);
//                }else{
//                    JOptionPane.showMessageDialog(null,"Complete los espacios requeridos y el código correctamente por favor");
//                }
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
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

//    public static void main(String[] args) {
//
//        VPrueba1 dialog = new VPrueba1();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
