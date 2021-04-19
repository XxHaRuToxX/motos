package org.company.reports;

import org.company.conexion.Conexion;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Graphics extends Conexion {
    public static void graphicCake(String date){
        try {
            openConexion();
            consulta=conexion.prepareStatement("select total_sale from sales where date_sale=?");
            consulta.setString(1,date);
            resultado=consulta.executeQuery();
            DefaultPieDataset setDate=new DefaultPieDataset();
            while (resultado.next()){
                setDate.setValue(resultado.getString("total_sale"),resultado.getDouble("total_sale"));
            }
            JFreeChart jFree= ChartFactory.createPieChart("Reporte de Venta",setDate);
            ChartFrame chartFrame=new ChartFrame("Total de ventas por dia",jFree);
            chartFrame.setSize(1000,500);
            chartFrame.setLocationRelativeTo(null);
            chartFrame.setVisible(true);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
