/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbm.data;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author root
 */
public class dbConnection {

    DataSource ds = null;
    
    public Boolean cargaUsuario(String usuario, String password) throws SQLException {
        List<String> valores = new ArrayList<>();
        try {
            InitialContext ctx = new InitialContext();
            this.ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DB2CONN");
            Connection con = this.ds.getConnection();
            System.out.println("**** Loaded the JDBC driver");
            con.setAutoCommit(false);
            System.out.println("**** Created a JDBC connection to the data source");
            Statement stmt = con.createStatement();
            System.out.println("**** Created JDBC Statement object");
            ResultSet rs = stmt.executeQuery("SELECT username FROM SAMPLE.USERS WHERE USERNAME='"+usuario+"' and PASSWORD='"+password+"'");
            System.out.println("**** Created JDBC ResultSet object");
            while (rs.next()) {
                valores.add(rs.getString(1));
                System.out.println(rs.getString(1));
                return true;
            }
            System.out.println("**** Fetched all rows from JDBC ResultSet");
            rs.close();
            System.out.println("**** Closed JDBC ResultSet");
            stmt.close();
            System.out.println("**** Closed JDBC Statement");
            con.commit();
            System.out.println("**** Transaction committed");
            con.close();
            System.out.println("**** Disconnected from data source");
            System.out.println("**** JDBC Exit from class EzJava - no errors");
            
        } catch (Exception e) {
            System.err.println("Could not load JDBC driver");
            System.out.println("Exception: " + e);
            Writer error = new StringWriter();
            e.printStackTrace(new PrintWriter(error));
            e.printStackTrace();
            valores.add(error.toString());
        }
        return false;
    }

    public List<String> cargaPaises() throws SQLException {
        List<String> valores = new ArrayList<>();
        String resultado = "";
        try {
            InitialContext ctx = new InitialContext();
            this.ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DB2CONN");
            Connection con = this.ds.getConnection();
            System.out.println("**** Loaded the JDBC driver");
            con.setAutoCommit(false);
            System.out.println("**** Created a JDBC connection to the data source");
            Statement stmt = con.createStatement();
            System.out.println("**** Created JDBC Statement object");
            ResultSet rs = stmt.executeQuery("SELECT NOMBRE_PAIS FROM SAMPLE.PAISES");
            System.out.println("**** Created JDBC ResultSet object");
            while (rs.next()) {
                valores.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }
            System.out.println("**** Fetched all rows from JDBC ResultSet");
            rs.close();
            System.out.println("**** Closed JDBC ResultSet");
            stmt.close();
            System.out.println("**** Closed JDBC Statement");
            con.commit();
            System.out.println("**** Transaction committed");
            con.close();
            System.out.println("**** Disconnected from data source");
            System.out.println("**** JDBC Exit from class EzJava - no errors");
        } catch (Exception e) {
            System.err.println("Could not load JDBC driver");
            System.out.println("Exception: " + e);
            Writer error = new StringWriter();
            e.printStackTrace(new PrintWriter(error));
            e.printStackTrace();
            valores.add(error.toString());
        }
        return valores;
    }
}
