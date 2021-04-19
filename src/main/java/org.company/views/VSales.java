package org.company.views;

import org.company.models.Sales;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class VSales {
    private JPanel panel1;
    private JButton exportarPDFButton;
    private JTable tableSales;
    private JTextField txtIdSales;
    private  DefaultTableModel salesModel;

    public VSales(){
        String[] titles={"ID", "CLIENTE", "VENDEDOR", "TOTAL"};
        salesModel=new DefaultTableModel(null,titles);
        tableSales.setModel(salesModel);
        columnsDimension();
        getSalesV();
        tableSales.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila=tableSales.rowAtPoint(e.getPoint());
                txtIdSales.setText(tableSales.getValueAt(fila,0).toString());
            }
        });
        exportarPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id=Integer.parseInt(txtIdSales.getText());
                    File file=new File("src/org/company/PDF/venta"+id+".pdf");
                    Desktop.getDesktop().open(file);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public JPanel getPanel(){
        return panel1;
    }
    public void columnsDimension(){
        TableColumnModel modeloColumna=tableSales.getColumnModel();
        modeloColumna.getColumn(0).setMaxWidth(80);
        modeloColumna.getColumn(1).setMaxWidth(200);
        modeloColumna.getColumn(2).setMaxWidth(100);
        modeloColumna.getColumn(3).setMaxWidth(150);
    }
    public void getSalesV(){
        Sales listSale=new Sales();
        List<Sales> getSale=listSale.listSales();
        salesModel= (DefaultTableModel) tableSales.getModel();

        Object[]obj=new Object[6];
        for(int i=0;i<getSale.size();i++){
            obj[0]=getSale.get(i).getId_sale();
            obj[1]=getSale.get(i).getClient_sale();
            obj[2]=getSale.get(i).getSeller_sale();
            obj[3]=getSale.get(i).getTotal_sale();
            salesModel.addRow(obj);
        }
        tableSales.setModel(salesModel);
    }

}
