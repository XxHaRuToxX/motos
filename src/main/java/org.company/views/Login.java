package org.company.views;

import org.company.models.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel panel1;
    private JButton btnLogin;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    public static Users users;

    public Login(){

        initComponent();

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loginUsers();
            }
        });
    }
    private void initComponent(){
        setContentPane(panel1);
        setSize(600,400);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
    }
    public void loginUsers(){
        String email_user=txtEmail.getText();
        String password_user=txtPassword.getText();
        users=Users.login(email_user,password_user);
        try{
            if(validateSpaces()){
                if(users!=null){
                    VPrincipal viewPrincipal = new VPrincipal(users);
                    viewPrincipal.setVisible(true);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Contraseña o Usuario son incorrectos");
                    cleanControls();
                }

            }else{
                JOptionPane.showMessageDialog(null,"Favor Complete los espacios");
                cleanControls();

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private boolean validateSpaces(){
        String email=txtEmail.getText();
        String password=txtPassword.getText();
        if(email.length()>0&&password.length()>0){
            return true;
        }else{
            return false;
        }
    }
    private void cleanControls(){
        txtEmail.setText(null);
        txtPassword.setText(null);

    }
}
