/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instantmessenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Saketh
 */

public class dataBase {
    static final String dbIP = "192.168.2.14";//retrieveIP();
    static final String DATABASE_URL = "jdbc:mysql://"+dbIP+":3306/test";
    static final String UserName = "user";
    static final String Password = "beckett";
    public static final String Table = "users";
    private Connection connection = null;
    String IP;
    static Statement statement = null;
    public ResultSet resultSet = null;

    public dataBase() throws UnknownHostException, SQLException {
            this.IP = Inet4Address.getLocalHost().getHostAddress();
            connection = DriverManager.getConnection(DATABASE_URL, UserName, Password);
            statement = connection.createStatement();
    }
    
    public void createUser(String userName, String password) throws SQLException{
        statement.executeUpdate("INSERT INTO `users`(`Name`, `Password`, `IP`, `Status`) VALUES ('"+userName+"','"+password+"','"+IP+"','alive')");
        InstantMessenger.user = userName;
        
    }
    
    public void updateIP() throws SQLException{
        statement.executeUpdate("UPDATE "+Table+" SET IP = '"+IP+"', status = 'alive' WHERE name='"+InstantMessenger.user+"'");
    }
    
    public void getPassword(String username) throws SQLException{
        resultSet = statement.executeQuery("SELECT password FROM "+Table+" WHERE name = '"+username+"'");
        resultSet.next();
    }
    public static String retrieveIP(){
        try {
            URL url = new URL("http://messenger55.site88.net/index.php");
            URLConnection con = url.openConnection();
            InputStream inp = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inp));
            return br.readLine();
        } catch (MalformedURLException ex) {
            //Logger.getLogger(TerminalChat.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            //Logger.getLogger(TerminalChat.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String getFrndIp(String name) throws SQLException{
        resultSet = statement.executeQuery("SELECT IP FROM "+Table+" WHERE name ='"+name+"'");
        resultSet.next();
        return resultSet.getString("IP");
    }
    
    public void getFriendsList() throws SQLException{
        resultSet = statement.executeQuery("SELECT NAME,STATUS FROM "+Table+" WHERE 1");
    }
    
    public String getName(String IP){
        try {
            resultSet = statement.executeQuery("SELECT name, status FROM "+Table+" WHERE IP ='"+IP+"'");
            while(resultSet.next()){
                System.out.println(resultSet.getString("name"));
                if(resultSet.getString("status").equals("alive"))
                    break;
            }
            return resultSet.getString("name");
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public void die(){
        try {
            statement.executeUpdate("UPDATE "+Table+" SET status ='dead', IP = '0.0.0.0' WHERE name = '"+InstantMessenger.user+"'");
            connection.close();
            } catch (SQLException ex) {}
            
        finally{
            System.exit(0);
        }
    }
}
