<%@ page language="java" session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="com.JasperReportsWrapper" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title> JasperReports Web SAMPLE</title>
    </head>
    <body>
        <%
         try
         {
		String path2JRXMLFile = getServletContext().getRealPath("vehicle.jrxml");

 		//Connect DB
            JasperReportsWrapper wrapper = new JasperReportsWrapper();
	 	wrapper.connect2DB(wrapper.dbServerAdd, wrapper.dbServerPort, wrapper.dbName, wrapper.dbUser, wrapper.dbPass);

 		//Comiple JRXML file
            JasperReport jasperReport = wrapper.compileJRXMLFile(path2JRXMLFile);

 		//Fill compiled JRXML file with data
            JasperPrint jasperPrint = wrapper.fillReport(jasperReport, null, wrapper.getConnection()); 

 		//Set reponse content type
            response.setContentType("application/pdf");

 		//Export PDF file to browser window
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            exporter.exportReport();
            out.clear(); // where out is a JspWriter
            out = pageContext.pushBody();
         }
         catch (Exception e)
         { 
           e.printStackTrace();
           out.clear(); // where out is a JspWriter
           out = pageContext.pushBody();
         }
        %>
    </body>
</html>