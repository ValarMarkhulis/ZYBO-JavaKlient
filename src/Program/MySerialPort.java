/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author chris
 */
class MySerialPort {

    public static SerialPort ZYBO_port;
    public static boolean connected = false;
    public static SerialPort ports[];

    public MySerialPort(SerialPort[] ports) {

        System.out.println("Vælg en COM port: ");
        int i = 1;
        for (SerialPort port : ports) {
            System.out.println(i++ + ". " + port.getSystemPortName());
            try {
                port.setComPortParameters(115200, 8, 1, 0);
                if (port.openPort()) {
                    port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
                    System.out.println("Porten \"" + port.getSystemPortName() + "\" blev opsat korrekt");
                    if (port.getSystemPortName().equals("COM3")) {
                        throw new Exception("Springer denne over");
                    }

                } else {
                    System.out.println("Porten kunne ikke åbnes korrekt, prøv igen!");
                    break;
                }

                OutputStream out = port.getOutputStream();
                InputStream in = port.getInputStream();
                String besked = "hej_ZYBO\n";
                byte[] buffer = besked.getBytes("ISO-8859-1");
                port.writeBytes(buffer, besked.length());
                out.flush();
                Thread.sleep(1000);
                /*
                while (in.available() != 0) {
                    char karakter = (char) in.read();
                    if (karakter == 13) {
                        in.read();
                        break;
                    }
                }
*/

                String modtaget_besked = "";
                while (in.available() != 0) {
                    char karakter = (char) in.read();
                    if (karakter == 13) {
                        break;
                    }
                    modtaget_besked = modtaget_besked.concat(karakter + "");
                }
                System.out.println("Beskeden var \"" + modtaget_besked.trim()+ "\".");

                if (modtaget_besked.trim().equals("hello from Zybo")) {
                    //We have found the correct port with a ZYBO board connected
                    ZYBO_port = port;
                    connected = true;
                    System.out.println("The port is found!");
                }else{
                    System.out.println("The port was found, but the wrong message was recieved");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Porten kunne ikke åbnes korrekt, prøv igen!");
            }

        }

    }

    void forbindTilPort(int portNR) {
        SerialPort ports[] = SerialPort.getCommPorts();
        SerialPort test_port = ports[portNR];
        try {
            test_port.setComPortParameters(115200, 8, 1, 0);
            if (test_port.openPort()) {
                test_port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
                System.out.println("Porten \"" + test_port.getSystemPortName() + "\" blev opsat korrekt");
                if (test_port.getSystemPortName().equals("COM3")) {
                    throw new Exception("Springer denne over");
                }

            } else {
                System.out.println("Porten kunne ikke åbnes korrekt, prøv igen!");
                return;
            }

            OutputStream out = test_port.getOutputStream();
            InputStream in = test_port.getInputStream();
            String besked = "hej_ZYBO\n";
            byte[] buffer = besked.getBytes("ISO-8859-1");
            test_port.writeBytes(buffer, besked.length());
            out.flush();
            Thread.sleep(1000);
            /*
            while (in.available() != 0) {
                char karakter = (char) in.read();
                if (karakter == 13) {
                    in.read();
                    break;
                }
            }
            */

            String modtaget_besked = "";
            while (in.available() != 0) {
                char karakter = (char) in.read();
                if (karakter == 13) {
                    break;
                }
                modtaget_besked = modtaget_besked.concat(karakter + "");
            }
            System.out.println("Beskeden var \"" + modtaget_besked.trim() + "\".");

            if (modtaget_besked.trim().equals("hello from Zybo")) {
                //We have found the correct port with a ZYBO board connected
                ZYBO_port = test_port;
                connected = true;
                synchronized (this) {
                    this.notify();
                }
                System.out.println("The port is found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Porten kunne ikke åbnes korrekt, prøv igen!");
        }

    }

    void updatePorts() {
        ports = SerialPort.getCommPorts();
    }

}
