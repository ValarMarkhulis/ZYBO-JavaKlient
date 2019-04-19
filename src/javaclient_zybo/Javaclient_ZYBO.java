/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclient_zybo;

import com.fazecast.jSerialComm.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 *
 * @author root
 */
public class Javaclient_ZYBO {

    private static SerialPort port;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        // VÆLGER PORT.
        SerialPort ports[] = SerialPort.getCommPorts();
        
        
        System.out.println("Vælg en COM port: ");
        int i = 1;
        for(SerialPort port : ports){
            System.out.println(i++ + ". "+ port.getSystemPortName());
        }
       
        Scanner tastatur = new Scanner(System.in);
        int valg= 0;
        valg = tastatur.nextInt();
        port = ports[valg - 1];
        
        
        //Scanner tastatur = new Scanner(System.in);
        port = ports[1];
        
        // Opsætter port
        port.setComPortParameters(115200, 8, 1, 0);
        
        if(port.openPort()){
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
            System.out.println("Porten \""+port.getSystemPortName()+"\" blev opsat korrekt");
        }else{
            System.out.println("Porten kunne ikke åbnes korrekt, prøv igen!");
            System.exit(3);
        }
        
        OutputStream out = port.getOutputStream();
        InputStream in = port.getInputStream();
        while(true) {
            boolean besked_to_long_flag = false;
            try{
                //Thread.sleep(100);
                String besked = tastatur.nextLine()+"\n";
                String besked2 = null;
                if(besked.length() > 16 && besked.length() < 35){
                    besked2 = besked.substring(16, besked.length());
                    besked = besked.substring(0,16);
                    besked_to_long_flag = true;
                }else if(besked.length() > 35){
                    //System.out.println();
                    throw new Exception("Hele beskeden kommer måske ikke frem, da strengen er "+(besked.length()-35)+" karaktere for lang (34 max).");
                }
                
                byte[] buffer = besked.getBytes("ISO-8859-1");
                port.writeBytes(buffer, besked.length());
                //Thread.sleep(100);
                out.flush();
                if(besked_to_long_flag){
                    byte[] buffer2 = besked2.getBytes("ISO-8859-1");
                    port.writeBytes(buffer2, besked2.length());
                    //Thread.sleep(100);      
                    out.flush();
                }
                while(in.available() != 0) {
                    System.out.print((char)in.read());
                    //Thread.sleep(100);
                }
            } catch(Exception e) {
               e.printStackTrace();
            }
        }
    }
    
}
