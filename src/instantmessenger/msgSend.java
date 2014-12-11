/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instantmessenger;

/**
 *
 * @author Saketh
 */

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class msgSend {
    Socket sock;
    DataOutputStream output;
    public msgSend(Socket socket){
        try {
            sock = socket;
            output = new DataOutputStream(sock.getOutputStream());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fatal Error! didn't get DataOutputStream");
        }
    }
    public void send(String msg){
        try{
            System.out.println("Sending "+ msg);
            output.writeUTF(msg);
            System.out.println("Sent");
        }catch(IOException e){}
    }
}

