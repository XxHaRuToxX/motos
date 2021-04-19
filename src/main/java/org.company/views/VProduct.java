package org.company.views;

import org.company.controllers.CProducts;
import org.company.models.Products;
import org.company.reports.ReportsProducts;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.util.List;

public class VProduct {

    private JPanel panel1;
    private JTextField txtCodeProduct;
    private JTextField txtDescriptionProduct;
    private JTextField txtQuantityProduct;
    private JTextField txtPriceProduct;
    private JButton btnSaveProduct;
    private JButton btnNewProduct;
    private JButton btnUpdateProduct;
    private JButton btnDeleteProduct;
    private JTable tableProduct;
    private JButton btnExportToExcelProduct;
    private JTextField txtIdProduct;
    private JComboBox boxProviderProduct;
    private JButton btnGetBarCodeProduct;
    private JComboBox boxBarProduct;
    private JButton btnGetAllBarsProduct;
    private DefaultTableModel productModel;
    private Products product;
    private ReportsProducts report;

    public VProduct(){
        String[] titles={"ID","CÓDIGO", "DESCRIPCIÓN", "PROVEEDOR", "CANTIDAD", "PRECIO"};
        productModel=new DefaultTableModel(null,titles);
        tableProduct.setModel(productModel);
        columnsDimension();
//        Products productsProvider=new Products();
        AutoCompleteDecorator.decorate(boxProviderProduct);
        product.showProviders(boxProviderProduct);
        product.showProducts(boxBarProduct);
        getProducts();
        txtIdProduct.setEnabled(false);

        txtIdProduct.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    product = Products.getProduct(Integer.parseInt(txtIdProduct.getText()));
                    if (product == null) {
                        JOptionPane.showMessageDialog(null, "no existe Producto");
                        cleanInputs();
                    } else {
                        showData();
                    }
                }
            }
        });

        btnSaveProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateSpaces() && !(txtCodeProduct.getText().isEmpty() || txtDescriptionProduct.getText().isEmpty() || txtQuantityProduct.getText().isEmpty() || txtPriceProduct.getText().isEmpty())){
                    saveProducts();
                    Products objProducts=Products.getLastProduct();
                    Object [] fila=new Object[6];
                    fila[0]=objProducts.getId_product();
                    fila[1]=objProducts.getCode_product();
                    fila[2]=objProducts.getName_product();
                    fila[3]=objProducts.getProvider_product();
                    fila[4]=objProducts.getStock_product();
                    fila[5]=objProducts.getPrice_product();
                    productModel.addRow(fila);

                    tableProduct.setModel(productModel);
                }else{
                    JOptionPane.showMessageDialog(null,"Complete los espacios requeridos y el código correctamente por favor");
                }
            }
        });
        tableProduct.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row=tableProduct.rowAtPoint(e.getPoint());
                txtIdProduct.setText(tableProduct.getValueAt(row,0).toString());
                txtCodeProduct.setText(tableProduct.getValueAt(row,1).toString());
                txtDescriptionProduct.setText(tableProduct.getValueAt(row,2).toString());
                boxProviderProduct.setSelectedItem(tableProduct.getValueAt(row,3));
                txtQuantityProduct.setText(tableProduct.getValueAt(row,4).toString());
                txtPriceProduct.setText(tableProduct.getValueAt(row,5).toString());
            }
        });
        btnUpdateProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=String.valueOf(txtIdProduct.getText());
                if(id.length()>0){
                    updateProductV();
                }else {
                    JOptionPane.showMessageDialog(null,"Ingrese un Id para Actualizar por favor");
                }
            }
        });
        btnDeleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=String.valueOf(txtIdProduct.getText());
                if(id.length()>0){
                    deleteProductV();
                }else {
                    JOptionPane.showMessageDialog(null,"Ingrese un Id para Eliminar por favor");
                }
            }
        });
        btnExportToExcelProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    report=new ReportsProducts();
                    report.exportExcel(tableProduct);
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });
        btnGetBarCodeProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String boxProduct=boxBarProduct.getSelectedItem().toString();

                if(!(boxProduct.equals("Seleccione una Opción"))){
                    confirmGetBarProduct();

                }else{
                    JOptionPane.showMessageDialog(null,"Seleccione un codigo correcto para generar un codigo individual");
                }

            }
        });
        btnGetAllBarsProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int question = JOptionPane.showConfirmDialog(null, "¿Generar Todas los codigos de barra de los productos?");
                if(question==0){
                    Products.generateBarsProduct();
                }else{
                    JOptionPane.showMessageDialog(null,"Se canceló la generación de codigos.");
                }
            }
        });
    }

    public JPanel getPanel(){
        return panel1;
    }
    public void columnsDimension(){
        TableColumnModel modeloColumna=tableProduct.getColumnModel();
        modeloColumna.getColumn(0).setMaxWidth(30);
        modeloColumna.getColumn(1).setMaxWidth(50);
        modeloColumna.getColumn(2).setMaxWidth(200);
        modeloColumna.getColumn(3).setMaxWidth(100);
        modeloColumna.getColumn(4).setMaxWidth(150);
        modeloColumna.getColumn(5).setMaxWidth(150);
    }

    public void saveProducts(){
        String code_product=txtCodeProduct.getText();
        String name_product=txtDescriptionProduct.getText();
        String provider_product=boxProviderProduct.getSelectedItem().toString();
        int stock_product=Integer.parseInt(txtQuantityProduct.getText());
        double price_product=Double.parseDouble(txtPriceProduct.getText());

        product= CProducts.createProducts(code_product,name_product,provider_product,stock_product,price_product);
        try {

            if (!product.saveProduct()){
                JOptionPane.showMessageDialog(null,"Producto Guardado");
                cleanInputs();
            }else{
                JOptionPane.showMessageDialog(null,"No se guardo Proveedor");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    public void updateProductV(){
        product= CProducts.update(txtCodeProduct.getText(),txtDescriptionProduct.getText(),(String)boxProviderProduct.getSelectedItem(),Integer.parseInt(txtQuantityProduct.getText()),Double.parseDouble(txtPriceProduct.getText()));
        product.setId_product(Integer.parseInt(txtIdProduct.getText()));
//        System.out.println(client);
        try{
            int question = JOptionPane.showConfirmDialog(null, "¿Estas seguro de actualizar?");
            if(question==0){
                if (product != null) {
                    JOptionPane.showMessageDialog(null, "Actualizado correctamente");
                    product.updateProduct();
                    cleanInputs();
                    clearTable();
                    txtIdProduct.setEnabled(false);
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
    public void deleteProductV(){
        int id=Integer.parseInt(txtIdProduct.getText());
        product=CProducts.dropProduct(id);
        product.setId_product(Integer.parseInt(txtIdProduct.getText()));
        try {
            int question = JOptionPane.showConfirmDialog(null, "¿Estas seguro de eliminarlo?");

            if (question == 0) {
                if (!product.deleteProduct(id)){
                    JOptionPane.showMessageDialog(null, "eliminado");
                    cleanInputs();
                    clearTable();
                    txtIdProduct.setEnabled(false);
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
        txtIdProduct.setText(null);
        txtCodeProduct.setText(null);
        txtDescriptionProduct.setText(null);
        boxProviderProduct.setSelectedItem("Seleccione una Opción");
        txtQuantityProduct.setText(null);
        txtPriceProduct.setText(null);
    }
    public void getProducts(){
        Products listProduct=new Products();
        List<Products> getProduct=listProduct.listProducts();
        productModel= (DefaultTableModel) tableProduct.getModel();

        Object[]obj=new Object[6];
        for(int i=0;i<getProduct.size();i++){
            obj[0]=getProduct.get(i).getId_product();
            obj[1]=getProduct.get(i).getCode_product();
            obj[2]=getProduct.get(i).getName_product();
            obj[3]=getProduct.get(i).getProvider_product();
            obj[4]=getProduct.get(i).getStock_product();
            obj[5]=getProduct.get(i).getPrice_product();
            productModel.addRow(obj);
        }
        tableProduct.setModel(productModel);
    }
    public void clearTable(){
        for (int i=0;i<productModel.getRowCount();i++){
            productModel.removeRow(i);
            i=i-1;
        }
        getProducts();
    }
    private boolean validateSpaces(){
        String code=String.valueOf(txtCodeProduct.getText());
        String name=txtDescriptionProduct.getText();
        String stock=String.valueOf(txtQuantityProduct.getText());
        String price=String.valueOf(txtPriceProduct.getText());
        if(code.length()==5){
            return true;
        }else{
            return false;
        }
    }
    private void showData(){
        txtCodeProduct.setText(product.getCode_product());
        txtDescriptionProduct.setText(product.getName_product());
        txtQuantityProduct.setText(String.valueOf(product.getStock_product()));
        boxProviderProduct.setSelectedItem(product.getProvider_product());
        txtPriceProduct.setText(String.valueOf(product.getPrice_product()));

    }
    private void confirmGetBarProduct(){
        int questionBox=JOptionPane.showConfirmDialog(null,"¿Desea generar el codigo de barra de este producto");
        String boxBar=boxBarProduct.getSelectedItem().toString();
        if(questionBox==0){
            Products.getBarsProduct(boxBar);
        }else{
            JOptionPane.showMessageDialog(null,"Se canceló la generación de codigo de barras");

        }
    }
}
