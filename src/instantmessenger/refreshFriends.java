/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instantmessenger;

import javax.swing.JTextArea;

/**
 *
 * @author Saketh
 */
public class refreshFriends extends Thread{
    @Override
    public void run(){
        while(true){
            try {
                InstantMessenger.Frn.displayFrnds();
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                //Logger.getLogger(refreshFriends.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
