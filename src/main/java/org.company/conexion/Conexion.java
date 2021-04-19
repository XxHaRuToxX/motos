package org.company.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Conexion {
    protected static Connection conexion;
    protected static PreparedStatement consulta;
    protected static ResultSet resultado;

    protected static final String DRIVER="org.mariadb.jdbc.Driver";
    protected static final String DB_URL="jdbc:mariadb://localhost:3306/motosystem";
    protected static final String USER="xxharutoxx";
    protected static final String PASSWORD="dell190";

    protected static boolean openConexion(){
        try{
            Class.forName(DRIVER);
            conexion= DriverManager.getConnection(DB_URL, USER,PASSWORD);
            return false;

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
    protected static boolean closeConexion(){
        try{
            if(!conexion.isClosed()){
                consulta.close();
                conexion.close();
            }
            return false;

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
}
