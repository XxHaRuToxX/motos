package org.company.reports;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class ReportsProducts {
    public void exportExcel(JTable t)throws IOException{
        JFileChooser chooser=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Archivos de Excel","xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){

            String ruta =chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS=new File(ruta);
                if (archivoXLS.exists()){
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro= new HSSFWorkbook();
                FileOutputStream archivo=new FileOutputStream(archivoXLS);
                Sheet hoja=libro.createSheet("Datos");
                hoja.setDisplayGridlines(true);

                for (int f=0;f<t.getRowCount();f++){
                    Row fila=hoja.createRow(f);
                    for (int c=0;c<t.getColumnCount();c++){
                        Cell celda=fila.createCell(c);
                        if(f==0){
                            celda.setCellValue(t.getColumnName(c));
                        }
                    }
                }
                int filaInicio=1;
                for (int f=0;f<t.getRowCount();f++){
                    Row fila=hoja.createRow(filaInicio);
                    filaInicio++;
                    for (int c=0;c<t.getColumnCount();c++){
                        Cell celda=fila.createCell(c);
                        if(t.getValueAt(f,c) instanceof Double){
                            celda.setCellValue(Double.parseDouble(t.getValueAt(f,c).toString()));
                        }else if(t.getValueAt(f,c) instanceof Float){
                            celda.setCellValue(Float.parseFloat((String) t.getValueAt(f,c)));
                        }else{
                            celda.setCellValue(String.valueOf(t.getValueAt(f,c)));
                        }
                    }
                }
                libro.write(archivo);
                archivo.close();
                Desktop.getDesktop().open(archivoXLS);

            }catch (IOException | NumberFormatException e){
                throw e;
            }
        }
    }
}
