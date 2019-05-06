/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import com.fazecast.jSerialComm.SerialPort;
import static Program.MySerialPort.ZYBO_port;
import javax.swing.JFrame;

/**
 *
 * @author chris
 */
public class Program {

    public static void main(String[] args) throws InterruptedException {
        JFrame vindue = new JFrame("Program");
        Login_page login_page = new Login_page();
        vindue.add(login_page);
        vindue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vindue.setSize(500, 300);
        vindue.setVisible(true);
        // VÆLGER PORT.
        SerialPort ports[] = SerialPort.getCommPorts();
        login_page.populateList(ports);

        MySerialPort mySerialPort = new MySerialPort(ports);
        if (ZYBO_port == null) {
            //if the port was not setup
            System.out.println("Port blev ikke oprettet");
            login_page.ZYBONOTConnected();
            login_page.getmySerialPort(mySerialPort);

            //Main thread waits for the user to connect sucessufully to a port though the GUI
            //The MySerialPort will call notify on it self, when this has happend
            //This is instead of doing a while loop looking at the "connected" boolean 
            // member in mySerialPort
            synchronized (mySerialPort) {
                mySerialPort.wait();
            }
        }
        
        login_page.ZYBOConnected();
        
        System.out.println("Jeg er her og klar til, at teste det nye :) !");
        //Wait for the login button to be pressed! This happens in the Login_page, when the "login" button is pressed and the
        //lock on mySerialPort is released.
        synchronized (mySerialPort) {
            mySerialPort.wait();
        }
        
        Program_page program_page = new Program_page(mySerialPort);

        Thread thread;
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        while (mySerialPort.ZYBO_port.bytesAvailable() == 0) {
                            Thread.sleep(20);
                        }

                        byte[] readBuffer = new byte[mySerialPort.ZYBO_port.bytesAvailable()];
                        int numRead = mySerialPort.ZYBO_port.readBytes(readBuffer, readBuffer.length);
                        System.out.println("Read " + numRead + " bytes.");
                        String temp_string = new String(readBuffer);
                        System.out.println("Modtaget: \"" + temp_string + "\"");
                        program_page.updateText(new String(readBuffer));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();

        //Do login
        System.out.println("Skifter vindue");

        vindue.getContentPane().removeAll();
        vindue.getContentPane().add(program_page);
        vindue.pack();
    }

}

/*
Test script for ZYBO LINUX, det skal køres med "sh ./test.sh". Husk at chmod +x den først
#!/bin/bash

while true
do
        read test
        if [[ -z $test ]]
        then
                : ##Do nothing
        elif [[ $test == 'hello' ]]
        then
                printf "hello from Zybo"
        fi



done
 */
