/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instantmessenger;

import java.net.UnknownHostException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Saketh
 */
public class InstantMessenger {

    /**
     */
    
    public static String user;//Username
    public static String remUser;
    public static dataBase database;
    public static FLogin loginPage = new FLogin();
    public static FTablePrint Frn = new FTablePrint();
    public static FChat chatPage = new FChat();
    public static Serv server;
    public static void main(String[] args) {
        try {
            database = new dataBase();
            //System.out.println(database.dbIP);
            server = new Serv(6789, database);
            server.start();
            System.out.println("DEBUG");
            
            loginPage.getFrames(Frn, chatPage, database);
            Frn.getFrames(loginPage, chatPage);
            chatPage.getFrames(Frn, loginPage, database);
        } catch (UnknownHostException | SQLException ex) {
            JOptionPane.showMessageDialog(loginPage, "Either Database is not connected or you're IP's not found");
            System.exit(0);
        }
        loginPage.setVisible(true);
        checkClose close = new checkClose();
        checkConn conn = new checkConn();
        close.start();
        conn.start();
    }
    
    public static void exitApp(){
            database.die();
    }
}

class checkClose extends Thread{
    @Override
    public void run(){
        while((InstantMessenger.Frn.isVisible() || InstantMessenger.loginPage.isVisible() || InstantMessenger.chatPage.isVisible())){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                
            }
        }
        InstantMessenger.exitApp();
    }
}

class checkConn extends Thread{
    @Override
    public void run(){
        while(InstantMessenger.chatPage.sock == null){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                
            }
        }
        while(InstantMessenger.chatPage.sock != null){
        }
        JOptionPane.showMessageDialog(null, "Chat disconnected!");
        //InstantMessenger.chatPage.setVisible(false);
        InstantMessenger.Frn.setVisible(true);
        InstantMessenger.server.start();
    }
}