package com;

import java.io.File;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class JasperReportsWrapper
{
    private static File file = new File("MonthlyCustomerInvoices.jrxml");
    public static String path2JRXMLFile = file.getAbsolutePath();
    public static String pdfFileName = "MonthlyCustomerInvoices.pdf";
    public static String dbServerAdd = "localhost";
    public static int dbServerPort = 5432;
    public static String dbName = "postgres";
    public static String dbUser = "postgres";
    public static String dbPass = "root";


    private Connection connection = null;
    private ConnectionManager conManager = null;

    public JasperReportsWrapper ()   {
    }//JasperReportsWrapper

    //Database Connection
    public Connection connect2DB (
        String host,
        int port,
        String dbname, 
        String username,
        String password)
    {
        conManager = new ConnectionManager(host, port, dbname, username, password);
        connection = conManager.getConnection();
        return connection;
    }//connect2DB

    public Connection getConnection() {
        return connection;
    }

    public JasperReport compileJRXMLFile(String jasperXMLFileName)
    {
        try
        {
            JasperReport jr = JasperCompileManager.compileReport(jasperXMLFileName);
            if (jr != null)
                System.out.println ("Your report compiled successfully");
            return jr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }//compileJRXMLFile

    public JasperPrint fillReport(
            JasperReport jasperReport,
            Map params,
            Connection con)
    {
        try
        {
            JasperPrint jp =
                JasperFillManager.fillReport(jasperReport, params, con);
            if (jp != null)
                System.out.println ("Your report filled with application data");

            return jp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }//fillReport

    public void saveReportInPDF (JasperPrint jPrint, String pdfFileName)
    {
        try
        {
            JasperExportManager.exportReportToPdfFile(jPrint, pdfFileName);
            System.out.println ("Your report successfully saved in PDF");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//savePDFReport

    public static void main(String[] args)
    {
        JasperReportsWrapper wrapper = new JasperReportsWrapper();
        Connection connection = wrapper.connect2DB(dbServerAdd, dbServerPort, dbName, dbUser, dbPass);
        JasperReport jasperReport = wrapper.compileJRXMLFile (path2JRXMLFile);
        JasperPrint jasperPrint = wrapper.fillReport(jasperReport, null, connection);
        wrapper.saveReportInPDF(jasperPrint, pdfFileName);
    }//main

}
