package com;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager
{
    Connection connection = null;
    String host = null;
    String db = null;
    String userName = null;
    String password = null;
    int port = 5432;

    public ConnectionManager (String host, int port,
            String dbname,
            String username,
            String password)
    {
        //Creation connection
        createConnection(host, port, dbname, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

    /*
     * 1. Get PostgreSQL
     * 2. Set Driver class
     * 3. Connect DB
     * 4. Check connection
     */
    public Connection createConnection (
        String host, int port,
        String dbname,
        String username,
        String password )
    {
        if (connection != null) {
            return connection;
        }

        try
        {
            //Driver class for the Postgres JDBC driver
            Class.forName("org.postgresql.Driver").newInstance();
            //Connect DB
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://"+host+":"+port+"/"+dbname,
                    username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (connection != null)
            System.out.println("Connection with database established successfully!");
        else
            System.out.println("Connection failed!");

        return connection;
    }//createConnection

    public void close()
    {
        try
        {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String args[])
    {
        ConnectionManager db = new ConnectionManager("localhost", 5432, "postgres", "postgres", "root");
    }
}//ConnectionManager

