package com.crm4telecom.greeter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nastya
 */
public class QueryExecutor {

    private Connection connection;

    private Connection getConnection() {
        Connection connection = null;
        try {
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource) context
                    .lookup("jdbc/c4tRes");
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public QueryExecutor() {
        connection = getConnection();
    }

    public String execute(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            StringBuilder table = new StringBuilder();
            table.append("<table border=1><thead><tr>");
            for (int col = 1; col <= resultSetMetaData.getColumnCount(); col++) {
                table.append("<th>");
                table.append(resultSetMetaData.getColumnName(col));
                table.append("</th>");

            }
            table.append("</tr></thead><tbody>");
            while (resultSet.next()) {
                table.append("<tr>");
                for (int col = 1; col <= resultSetMetaData.getColumnCount(); col++) {
                    table.append("<th>");
                    table.append(resultSet.getString(col));
                    table.append("</th>");
                }
                table.append("</tr>");

            }
            table.append("</tbody>");
            return table.toString();

        } catch (SQLException ex) {
            Logger.getLogger(QueryExecutor.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
