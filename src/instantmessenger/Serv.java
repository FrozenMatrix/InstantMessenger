/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instantmessenger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_NO_OPTION;

/**
 *
 * @author FrozenFractal
 */

public class Serv extends Thread{
    public ServerSocket server;
    Socket sock;
    dataBase db;
    
    public Serv(int port, dataBase db){
        try {
            this.db = db;
            server = new ServerSocket(port);
        } catch (IOException ex) {
            int ans = JOptionPane.showConfirmDialog(null,"Server couldn't start, try again?", null, YES_NO_OPTION);
            if(ans == 1)
                InstantMessenger.exitApp();
        }
    }
    
    
    @Override
    public void run(){
        while(true){
            try {
                sock = server.accept();
                String IP = returnIP(sock.getRemoteSocketAddress().toString());
                InstantMessenger.remUser = db.getName(IP);
                System.out.println(InstantMessenger.remUser);
                int ans = JOptionPane.showConfirmDialog(null,InstantMessenger.remUser+" is trying to connect. Accept?", null, YES_NO_OPTION);
                DataOutputStream s = new DataOutputStream(sock.getOutputStream());
                System.out.println(ans);
                if(ans == 1){
                    s.writeUTF("no");
                    sock.close();
                    System.out.println("kjsdfjkasjdkf");
                }
                else if(ans == 0){
                    s.writeUTF("yes");
                    InstantMessenger.Frn.setVisible(false);
                    InstantMessenger.chatPage.setSock(sock);
                    InstantMessenger.chatPage.receiveFriend();
                    InstantMessenger.chatPage.setVisible(true);
                }
            } catch (IOException ex) {}
        }
    }
    
    public static String returnIP(String IP){
        char ar[] = IP.toCharArray();
        int i;
        for(i=0;i<IP.length();i++){
            if(ar[i] == ':')
                break;
        }
        IP = IP.substring(1, i);
        return IP;
    }
}
