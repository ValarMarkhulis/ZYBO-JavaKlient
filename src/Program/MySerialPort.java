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
                System.out.println("Beskeden var \"" + modtaget_besked.trim() + "\".");

                if (modtaget_besked.trim().equals("hello from Zybo")) {
                    //We have found the correct port with a ZYBO board connected
                    ZYBO_port = port;
                    connected = true;
                    System.out.println("The port is found!");
                } else {
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

    public static void start_recieverThread(Program_page program_page) {
        Thread thread;
        thread = new Thread() {
            long lStartTime = 0;
            long lEndTime = 0;

            @Override
            public void run() {
                try {
                    while (true) {
                        while (ZYBO_port.bytesAvailable() == 0) {
                            Thread.sleep(20);
                        }

                        byte[] readBuffer = new byte[ZYBO_port.bytesAvailable()];
                        int numRead = ZYBO_port.readBytes(readBuffer, readBuffer.length);
                        System.out.println("Read " + numRead + " bytes.");
                        String temp_string = new String(readBuffer);
                        System.out.println("Modtaget: \"" + temp_string.trim() + "\"");

                        //Process recieved message
                        processRecievedStringFromZYBO(temp_string.trim());

                        program_page.updateText(new String(readBuffer));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } // Run end

            private void processRecievedStringFromZYBO(String temp_string) {
                
                if (temp_string.contains("Random tal er: ")) {
                    int index = temp_string.lastIndexOf("Random tal er: ") + ("Random tal er: ").length();
                    //System.err.println("Jeg er kaldt! med index "+index);
                    //System.err.println(temp_string.substring(index, temp_string.length()));
                    String returnString = temp_string.substring(index, temp_string.length());
                    program_page.setReturnText(1, returnString);

                    //lStartTime = System.currentTimeMillis();
                } else if (temp_string.contains("Krypt folgende: \"")) {
                    int index = temp_string.lastIndexOf("Krypt folgende: \"") + ("Krypt folgende: \"").length();
                    //System.err.println("Jeg er kaldt! med index "+index);
                    //System.err.println(temp_string.substring(index, temp_string.length() - 1));
                    String returnString = temp_string.substring(index, temp_string.length() - 1);
                    program_page.setReturnText(2, returnString);

                    //lStartTime = System.currentTimeMillis();
                } else if (temp_string.contains("Dekrypt folgende: \"")) {
                    int index = temp_string.lastIndexOf("Dekrypt folgende: \"") + ("Dekrypt folgende: \"").length();
                    //System.err.println("Jeg er kaldt! med index " + index);
                    //System.err.println(temp_string.substring(index, temp_string.length() - 1));
                    String returnString = temp_string.substring(index, temp_string.length() - 1);
                    program_page.setReturnText(3, returnString);

                    //lStartTime = System.currentTimeMillis();
                } else if (temp_string.contains("benchmark started")) {
                    //System.err.println("Jeg er kaldt!");
                    lStartTime = System.currentTimeMillis();
                } else if (temp_string.contains("benchmark ended")) {
                    //System.err.println("Jeg er kaldt2!");
                    lEndTime = System.currentTimeMillis();
                    long output = lEndTime - lStartTime;
                    if (temp_string.contains("CPU")) {
                        System.out.println("It took the CPU: " + output + " in milliseconds");
                        program_page.addDataToChart(output, 1);
                    } else if (temp_string.contains("HW")) {
                        System.out.println("It took the Hardware: " + output + " in milliseconds");
                        program_page.enableButtons("jButton_benchmark");
                        program_page.addDataToChart(output, 2);
                    } else {
                        System.err.println("Fandt hverken CPU eller HW i strengen :(");
                    }
                }else{
                    System.err.println("ingen streng matched");
                }
            }
        };
        thread.start();
    }

}
