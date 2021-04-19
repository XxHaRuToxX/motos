package org.company.views;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import org.company.events.Events;
import org.company.models.*;
import org.company.reports.Graphics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VNewSale extends JFrame {
    private JPanel panel1;
    private JButton btnDeleteNewSale;
    private JTextField txtCodeNewSale;
    private JTextField txtDescriptionNewSale;
    private JTextField txtQuantityNewSale;
    private JTextField txtPriceNewSale;
    private JTextField txtStockAvailableNewSale;
    private JTable tableNewSale;
    private JTextField txtDniRucNewClientSale;
    private JTextField txtNameClientNewSale;
    private JButton btnPrint;
    private JTextField txtPhoneClientNewSale;
    private JTextField txtAddressClientNewSale;
    private JTextField txtNameCompanyClientNewSale;
    private JTextField textField1;
    private JLabel labelTotalToPayNewSale;
    private JPanel panelCalendarNewSale;
    private JButton btnCakeNewSale;
    private JComboBox boxSellerNewSale;
    private DefaultTableModel newSalesModel;
    private Products searchProduct;
    private Clients getDniClient;
    private Sales sale;
    private Details saveDetails;
    private VConfig vConfig;
    private Events events=new Events();
    private Calendar calendar=Calendar.getInstance();
    private JDateChooser jDateChooser=new JDateChooser();
    private int item;
    private double totalToPay=0.00;
    private Date dateSale= new Date();
    private String actualDate=new SimpleDateFormat("dd/MM/yyyy").format(dateSale);

    public VNewSale(){

        String[]titles={"CÓDIGO","DESCRIPCIÓN","CANTIDAD","PRECIO","TOTAL"};
        newSalesModel=new DefaultTableModel(null,titles);
        tableNewSale.setModel(newSalesModel);
        sale.showSellers(boxSellerNewSale);
        dimensionColumnas();
        txtStockAvailableNewSale.setEnabled(false);
//        jDateChooser.setDateFormatString("dd/MM/yyyy");
//        jDateChooser.setDateFormatString(actualDate);
        jDateChooser.setDate(dateSale);
        panelCalendarNewSale.add(jDateChooser);
        txtCodeNewSale.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(!(txtCodeNewSale.getText().isEmpty())){
                        searchProduct = Products.searchProduct(txtCodeNewSale.getText());
                        if (searchProduct == null) {
                            JOptionPane.showMessageDialog(null, "no existe Producto");
                            cleanInputsProduct();
                            txtCodeNewSale.requestFocus();
                        } else {
                            showDataProduct();
                            txtQuantityNewSale.requestFocus();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Ingrese un Codigo de Producto por favor");
                    }

                }
            }
        });
        txtQuantityNewSale.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(!(txtQuantityNewSale.getText().isEmpty())){
                        String code=txtCodeNewSale.getText();
                        String description=txtDescriptionNewSale.getText();
                        int quantity=Integer.parseInt(txtQuantityNewSale.getText());
                        double price=Double.parseDouble(txtPriceNewSale.getText());
                        double total=quantity*price;
                        int stock=Integer.parseInt(txtStockAvailableNewSale.getText());
                        if(stock>=quantity){
                            item=item+1;
                            newSalesModel= (DefaultTableModel) tableNewSale.getModel();
                            for (int i=0;i<tableNewSale.getRowCount();i++){
                                if(tableNewSale.getValueAt(i,1).equals(txtDescriptionNewSale.getText())){
                                    JOptionPane.showMessageDialog(null,"El producto ya esta agregado");
                                    return;
                                }
                            }
                            ArrayList list=new ArrayList();
                            list.add(item);
                            list.add(code);
                            list.add(description);
                            list.add(quantity);
                            list.add(price);
                            list.add(total);
                            Object[] objProduct=new Object[5];
                            objProduct[0]=list.get(1);
                            objProduct[1]=list.get(2);
                            objProduct[2]=list.get(3);
                            objProduct[3]=list.get(4);
                            objProduct[4]=list.get(5);
                            newSalesModel.addRow(objProduct);
                            tableNewSale.setModel(newSalesModel);
                            amountToPay();
                            cleanInputsProduct();
                            txtCodeNewSale.requestFocus();
                        }else{
                            JOptionPane.showMessageDialog(null,"Stock no disponible");
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,"Ingrese cantidad por favor");
                    }
                }
            }
        });
        btnDeleteNewSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newSalesModel= (DefaultTableModel) tableNewSale.getModel();
                newSalesModel.removeRow(tableNewSale.getSelectedRow());
                amountToPay();
                txtCodeNewSale.requestFocus();
            }
        });
        txtDniRucNewClientSale.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(!"".equals(txtDniRucNewClientSale.getText())){
                        getDniClient = Clients.getClientDni(Integer.parseInt(txtDniRucNewClientSale.getText()));
                        if (getDniClient == null) {
                            JOptionPane.showMessageDialog(null, "no existe Cliente");
                            cleanInputsClient();
                            txtDniRucNewClientSale.requestFocus();
                        } else {
                            showDataClient();
                        }
                    }else{
                        cleanInputsClient();
                        JOptionPane.showMessageDialog(null,"Ingrese un Dni de cliente por favor");
                    }

                }
            }
        });
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tableNewSale.getRowCount()>0){
                    if(!(txtDniRucNewClientSale.getText().isEmpty() && txtCodeNewSale.getText().isEmpty()) && !(boxSellerNewSale.getSelectedItem().equals("Seleccione una Opción"))){
                        saveSalesV();
                        saveDetailV();
                        updateStockV();
                        pdf();
                        cleanTableNewSale();
                        cleanInputsClient();
                    }else{
                        JOptionPane.showMessageDialog(null,"Por favor Ingrese un codigo de producto, dni cliente y selecione un Vendedor");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"No hay Productos en la tabla para imprimir");
                }

            }
        });
        txtCodeNewSale.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                events.numberKeyPress(e);
            }
        });

        txtDescriptionNewSale.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                events.textKeyPress(e);
            }
        });

        txtQuantityNewSale.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                events.numberKeyPress(e);
            }
        });

        txtPriceNewSale.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                events.numberDecimalKeyPress(e,txtPriceNewSale);
            }
        });
        btnCakeNewSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jDateChooser.getDate()==null){
                    JOptionPane.showMessageDialog(null,"Ingrese una fecha");
                    return;
                }
                String reportDate=new SimpleDateFormat("dd/MM/yyyy").format(jDateChooser.getDate());
                Graphics.graphicCake(reportDate);
//                String reportDate=new SimpleDateFormat("dd/MM/yyyy").format(jDateChooser.getDate());
//                Graphics.graphicCake(actualDate);
//                Graphics.graphicCake(reportDate);
            }
        });
    }

    public JPanel getPanel(){
        return panel1;
    }
    public void dimensionColumnas(){
        TableColumnModel modeloColumna=tableNewSale.getColumnModel();
        modeloColumna.getColumn(0).setMaxWidth(80);
        modeloColumna.getColumn(1).setMaxWidth(180);
        modeloColumna.getColumn(2).setMaxWidth(70);
        modeloColumna.getColumn(3).setMaxWidth(50);
        modeloColumna.getColumn(4).setMaxWidth(50);
    }
    private void cleanInputsProduct(){
        txtCodeNewSale.setText(null);
        txtDescriptionNewSale.setText(null);
        txtQuantityNewSale.setText(null);
        txtPriceNewSale.setText(null);
        txtStockAvailableNewSale.setText(null);
    }
    private void showDataProduct(){
        txtDescriptionNewSale.setText(searchProduct.getName_product());
        txtStockAvailableNewSale.setText(String.valueOf(searchProduct.getStock_product()));
        txtPriceNewSale.setText(String.valueOf(searchProduct.getPrice_product()));

    }
    private void cleanInputsClient(){
        txtDniRucNewClientSale.setText(null);
        txtNameClientNewSale.setText(null);
        txtPhoneClientNewSale.setText(null);
        txtAddressClientNewSale.setText(null);
        txtNameCompanyClientNewSale.setText(null);
    }
    private void showDataClient(){
        txtNameClientNewSale.setText(getDniClient.getName_client());
        txtPhoneClientNewSale.setText(String.valueOf(getDniClient.getPhone_client()));
        txtAddressClientNewSale.setText(getDniClient.getAddress_client());
        txtNameCompanyClientNewSale.setText(getDniClient.getName_company_client());

    }
    private void amountToPay(){
        totalToPay=0.00;
        int numberRow=tableNewSale.getRowCount();
        for (int i=0; i<numberRow;i++){
            double calculate=Double.parseDouble(String.valueOf(tableNewSale.getModel().getValueAt(i,4)));
            totalToPay=totalToPay+calculate;
        }
        labelTotalToPayNewSale.setText(String.format("%.2f",totalToPay));
    }
    private void saveSalesV(){
        sale=new Sales();
        String client=txtNameClientNewSale.getText();
        String seller=boxSellerNewSale.getSelectedItem().toString();
        double total_amount=totalToPay;
        sale.setClient_sale(client);
        sale.setSeller_sale(seller);
        sale.setTotal_sale(total_amount);
        sale.setDate_sale(actualDate);
        sale.saveSale();
    }
    private void saveDetailV(){
        sale=new Sales();
        saveDetails=new Details();
        for (int i=0; i<tableNewSale.getRowCount();i++){
            String code=tableNewSale.getValueAt(i,0).toString();
            int quantity=Integer.parseInt(tableNewSale.getValueAt(i,2).toString());
            double price=Double.parseDouble(tableNewSale.getValueAt(i,3).toString());
            int id=sale.idSale();
            saveDetails.setCode_product_detail(code);
            saveDetails.setQuantity_detail(quantity);
            saveDetails.setPrice_detail(price);
            saveDetails.setId_sale_detail(id);
            saveDetails.saveDetail();
        }
    }
    private void updateStockV(){
        for (int i=0;i<tableNewSale.getRowCount();i++){
            String code=tableNewSale.getValueAt(i,0).toString();
            int quantity=Integer.parseInt(tableNewSale.getValueAt(i,2).toString());
            searchProduct=Products.searchProduct(code);
            int stock_actual=searchProduct.getStock_product() - quantity;
            sale.updateStock(stock_actual,code);
        }
    }
    public void cleanTableNewSale(){
        newSalesModel= (DefaultTableModel) tableNewSale.getModel();
        int fila=tableNewSale.getRowCount();
        for(int i=0;i<fila;i++){
            newSalesModel.removeRow(0);
        }
    }
    private void pdf() {
        try {
            sale=new Sales();
            int id=sale.idSale();
            FileOutputStream archivo;
            File file = new File("src/org/company/PDF/venta"+id+".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance("src/org/company/img/printer.png");

            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
//            Date date = new Date();
//            fecha.add("Factura:1"+id+"\n"+"Fecha: " + new SimpleDateFormat("DD-MM-YYYY").format(date) + "\n\n");
            Calendar dateCalendar=Calendar.getInstance();
            int year=dateCalendar.get(Calendar.YEAR);
            int month=dateCalendar.get(Calendar.MONTH);
            int day=dateCalendar.get(Calendar.DAY_OF_MONTH);
            fecha.add("Factura: "+id+"\n"+"Fecha: " + day+"/"+(month+1)+"/"+year+ "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(img);
            vConfig=new VConfig();
            String ruc =vConfig.getTxtRucConfig().getText();
            String nom = vConfig.getTxtNameConfig().getText();
            String tel = vConfig.getTxtPhoneConfig().getText();
            String dir = vConfig.getTxtAddressConfig().getText();
            String ra = vConfig.getTxtNameCompanyConfig().getText();

            Encabezado.addCell("");
            Encabezado.addCell("Ruc: " + ruc + "\nNombre: " + nom + "\nTelefono: " + tel + "\nDireccion: " + dir + "\nRazon: " + ra);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("Datos de los clientes" + "\n\n");
            doc.add(cli);

            PdfPTable tablacli = new PdfPTable(4);
            tablacli.setWidthPercentage(100);
            tablacli.getDefaultCell().setBorder(0);
            float[] Columnacli = new float[]{20f, 50f, 30f, 40f};
            tablacli.setWidths(Columnacli);
            tablacli.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cl1 = new PdfPCell(new Phrase("Dni/Ruc", negrita));
            PdfPCell cl2 = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cl3 = new PdfPCell(new Phrase("Teléfono", negrita));
            PdfPCell cl4 = new PdfPCell(new Phrase("Dirección", negrita));
            cl1.setBorder(0);
            cl2.setBorder(0);
            cl3.setBorder(0);
            cl4.setBorder(0);
            tablacli.addCell(cl1);
            tablacli.addCell(cl2);
            tablacli.addCell(cl3);
            tablacli.addCell(cl4);
            tablacli.addCell(txtDniRucNewClientSale.getText());
            tablacli.addCell(txtNameClientNewSale.getText());
            tablacli.addCell(txtPhoneClientNewSale.getText());
            tablacli.addCell(txtAddressClientNewSale.getText());

            doc.add(tablacli);

//            productos
            PdfPTable tablapro = new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            float[] Columnapro = new float[]{10f, 50f, 15f, 20f};
            tablapro.setWidths(Columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pro1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell pro2 = new PdfPCell(new Phrase("Descripción", negrita));
            PdfPCell pro3 = new PdfPCell(new Phrase("Precio U.", negrita));
            PdfPCell pro4 = new PdfPCell(new Phrase("Precio T.", negrita));
            pro1.setBorder(0);
            pro2.setBorder(0);
            pro3.setBorder(0);
            pro4.setBorder(0);
            pro1.setBackgroundColor(BaseColor.CYAN);
            pro2.setBackgroundColor(BaseColor.CYAN);
            pro3.setBackgroundColor(BaseColor.CYAN);
            pro4.setBackgroundColor(BaseColor.CYAN);
            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            for (int i = 0; i < tableNewSale.getRowCount(); i++) {
                String producto = tableNewSale.getValueAt(i, 1).toString();
                String cantidad = tableNewSale.getValueAt(i, 2).toString();
                String precio = tableNewSale.getValueAt(i, 3).toString();
                String total = tableNewSale.getValueAt(i, 4).toString();
                tablapro.addCell(cantidad);
                tablapro.addCell(producto);
                tablapro.addCell(precio);
                tablapro.addCell(total);
            }
            doc.add(tablapro);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a Pagar: " + totalToPay);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);
//
            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelación y Firma\n\n");
            firma.add("------------------------");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);
//
            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por su Compra");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
