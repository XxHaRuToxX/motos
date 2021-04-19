package org.company.views;

import org.company.models.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VPrincipal extends JFrame {
    private JPanel panel1;
    private JButton btnNewSell;
    private JButton btnClient;
    private JButton btnProvider;
    private JButton btnProduct;
    private JButton btnSell;
    private JButton btnConfig;
    private JPanel ViewPanel;
    private JLabel labelSellerPrincipal;
    private JButton btnUserPrincipal;

    public VPrincipal(Users priv){
        btnClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNewSell.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProvider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSell.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfig.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUserPrincipal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        initComponent();
        if(priv.getRol_user().equals("Asistente")){
            btnProvider.setVisible(false);
            btnUserPrincipal.setVisible(false);
            btnConfig.setVisible(false);
            labelSellerPrincipal.setText(priv.getName_user());
        }else{
            btnProvider.setEnabled(true);
            btnClient.setEnabled(true);
            labelSellerPrincipal.setText(priv.getName_user());
        }
        btnNewSell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNewSalePanel();
            }
        });
        btnProvider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProviderPanel();
            }
        });
        btnClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadClientPanel();
            }
        });
        btnProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProductPanel();
            }
        });
        btnSell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSalesPanel();
            }
        });
        btnConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadConfigPanel();
            }
        });
        btnUserPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadUserPanel();
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent){
                Login login = new Login();
                login.setVisible(true);
            }
        });
    }

    private void initComponent(){
        setContentPane(panel1);
        setSize(1300,600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    public void loadNewSalePanel(){
        VNewSale newSale=new VNewSale();
        nonDuplicatePanel();
        ViewPanel.add(newSale.getPanel());
        setVisible(true);
    }
    public void loadProviderPanel(){
        VProvider provider=new VProvider();
        nonDuplicatePanel();
        ViewPanel.add(provider.getPanel());
        setVisible(true);
    }
    public void loadClientPanel(){
        VClient client=new VClient();
        nonDuplicatePanel();
        ViewPanel.add(client.getPanel());
        setVisible(true);
    }
    public void loadProductPanel(){
        VProduct product=new VProduct();
        nonDuplicatePanel();
        ViewPanel.add(product.getPanel());
        setVisible(true);
    }
    public void loadSalesPanel(){
        VSales sales=new VSales();
        nonDuplicatePanel();
        ViewPanel.add(sales.getPanel());
        setVisible(true);
    }
    public void loadConfigPanel(){
        VConfig config=new VConfig();
        nonDuplicatePanel();
        ViewPanel.add(config.getPanel());
        setVisible(true);
    }
    public void loadUserPanel(){
        VUser config=new VUser();
        nonDuplicatePanel();
        ViewPanel.add(config.getPanel());
        setVisible(true);
    }
    public void nonDuplicatePanel(){
        if (ViewPanel.getComponentCount()==1){
            ViewPanel.remove(0);
        }
    }

}
