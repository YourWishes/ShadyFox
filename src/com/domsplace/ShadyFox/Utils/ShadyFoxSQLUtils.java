package com.domsplace.ShadyFox.Utils;

import com.domsplace.ShadyFox.ShadyFoxBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShadyFoxSQLUtils extends ShadyFoxBase {
    public static final String TABLE_NAME = "Players";
    
    public static String host;
    public static String port;
    public static String username;
    public static String password;
    public static String database;
    public static String prefix;
    
    public static Connection sql;
    
    public static String formatQuery(String query) {
        return query.replaceAll("%p%", "`" + database + "`.`" + prefix + TABLE_NAME + "` ");
    }
    
    public static boolean sqlConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://"+host+":"+port+"/" + database;
            msgConsole("Opening SQL connection to " + url);
            sql = DriverManager.getConnection(url,username,password);
            return true;
        } catch (Exception ex) {
            msgConsole(ChatError + "Failed to Connect to SQL. Error: " + ex.getLocalizedMessage());
            return false;
        }
    }
    
    public static boolean sqlQuery(String query) {
        query = formatQuery(query);
        
        try {
            PreparedStatement sqlStmt = sql.prepareStatement(query);
            boolean result = sqlStmt.execute(query);
            return result;
        } catch (SQLException ex) {
            msgConsole(ChatError + "Failed to execute SQL query. Error: " + ex.getLocalizedMessage());
        }
        return false;
    }
    
    public static boolean sqlQuery(String query, boolean SUPPRESS) {
        query = formatQuery(query);
        
        try {
            PreparedStatement sqlStmt = sql.prepareStatement(query);
            boolean result = sqlStmt.execute(query);
            return result;
        } catch (SQLException ex) {
        }
        return false;
    }
    
    public static int sqlQueryID(String query) {
        query = formatQuery(query);
        
        try {
            PreparedStatement sqlStmt = sql.prepareStatement(query);
            int result = sqlStmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            return result;
        } catch (SQLException ex) {
            msgConsole(ChatError + "Failed to execute SQL (Return ID) query. Error: " + ex.getLocalizedMessage());
        }
        return -1;
    }
    
    public static List<Map<String, String>> sqlFetch(String query) {
        query = formatQuery(query);
        
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        try {
            Statement myStmt = sql.createStatement();
            ResultSet result = myStmt.executeQuery(query);
            while (result.next()){
                Map<String, String> data = new HashMap<String, String>();
                for(int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    data.put(result.getMetaData().getColumnName(i), result.getString(result.getMetaData().getColumnName(i)));
                }
                results.add(data);
            }
        }
        catch (Exception sqlEx) {
            msgConsole(ChatError + "Failed to result SQL query. Error: " + sqlEx.getLocalizedMessage());
        }
        
        if(results.size() < 1) {
            return null;
        }
        
        return results;
    }
    
    public static void sqlClose() {
        try {
            msgConsole("Closing SQL connection...");
            sql.close();
            sql = null;
        } catch (Exception ex) {
            msgConsole(ChatError + "Failed to Close SQL connection. Error: " + ex.getLocalizedMessage());
        }
    }
}
