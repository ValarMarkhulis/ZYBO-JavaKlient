package javaclient_zybo;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class Javaclient_GUI {

    static SerialPort valgteport;
    static OutputStream out = null;
    static InputStream in = null;
    static String ModtagetBesked = "";
    static boolean besked_to_long_flag = false;
    static int max_antal_char_modtager = 21;

    public static void main(String[] args) {
        Scanner tastatur = new Scanner(System.in);

        //<editor-fold defaultstate="collapsed" desc="SWING komponenter opsætning">
        JFrame vindue = new JFrame();
        vindue.setTitle("Send data ud på COM port");
        vindue.setSize(600, 500);
        vindue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // reagér på luk

        // Drop-down box og tilslut knap
        JComboBox<String> portlist = new JComboBox<String>();
        JButton tilslutknap = new JButton("Tilslut");

        JTextField beskeder_out = new JTextField();
        JButton sendknap = new JButton("Send");

        beskeder_out.setColumns(20);
        beskeder_out.setAutoscrolls(true);

        JButton kanal2 = new JButton();
        JFrame vindue2 = new JFrame();
        vindue2.add(kanal2);
        vindue2.setVisible(true);
        vindue2.setSize(100, 100);

        JTextArea beskeder_in = new JTextArea();
        beskeder_in.setEditable(false);
        beskeder_in.setRows(5);
        beskeder_in.setColumns(20);
        beskeder_in.setLineWrap(true);
        beskeder_in.setWrapStyleWord(true);

        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        //Sæt ScrollPanel sammen med beskeder_in
        jScrollPane1.setViewportView(beskeder_in);

        DefaultCaret caret = (DefaultCaret) beskeder_in.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(vindue.getContentPane());
        vindue.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jScrollPane1)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(beskeder_out, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(sendknap)))
                                                .addGap(25, 25, 25))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(portlist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tilslutknap)
                                                .addGap(113, 113, 113))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(portlist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tilslutknap))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(beskeder_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sendknap))
                                .addGap(14, 14, 14))
        );

        vindue.pack();

        //Sæt send knappen som knappen der bliver trykket på når man trykker "Enter"
        vindue.getRootPane().setDefaultButton(sendknap);

        // Læg noget i drop-down boxen
        SerialPort ports[] = SerialPort.getCommPorts();

//</editor-fold>
// Find alle COM portene der er tilsluttede
        for (int i = 0; i < ports.length; i++) {
            portlist.addItem(ports[i].getSystemPortName());
        }

        /* Opsæt tilslut knappen og brug en anden proces til at lytte efter data */
        tilslutknap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (tilslutknap.getText().equals("Tilslut")) {
                    //Tilslut
                    valgteport = SerialPort.getCommPort(portlist.getSelectedItem().toString());
                    // Opsætter port
                    valgteport.setComPortParameters(115200, 8, 1, 0);
                    if (valgteport.openPort()) {
                        System.out.println("\"Porten \"" + valgteport.getSystemPortName() + "\" blev opsat korrekt\"");
                        tilslutknap.setText("Afbryd");
                        portlist.setEnabled(false);
                        valgteport.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
                        in = valgteport.getInputStream();


                        /* Thread der står og modtager karaktere og indsætter dem i textboksen. Den kører sålænge, at knappen
                        viser afbryd*/
                        Thread thread_modtag = new Thread() {
                            @Override
                            public void run() {

                                //Aflæs fra Inputstream'en så længe, at brugeren har tilsluttet sig en port
                                while (tilslutknap.getText().equals("Afbryd")) {
                                    try {
                                        Thread.sleep(50);

                                        //Læs så længe at der er data at læse
                                        while (in.available() != 0) {
                                            char karakter = (char) in.read();
                                            //System.out.print(karakter); 
                                            if (karakter < 32) {
                                                if (karakter == 13) {
                                                    System.out.println("modB indeholder: \"" + ModtagetBesked + "\"");

                                                    /**
                                                     * **************************************************
                                                     * Man kan ikke få '\n' med når man arbejder og kører det fra NETBEANS!!!!! Så her er den linje til at få mellemrum med: ModtagetBesked = ModtagetBesked.concat("\n");
                                                     *
                                                     * TIL TEST AF SENDE på fpga: while true; do sleep 1; echo 'test';done
                                                     *
                                                     *
                                                     *
                                                     *
                                                     *
                                                     *
                                                     *
                                                     ***********************************************
                                                     */
                                                    ModtagetBesked = ModtagetBesked.concat("\n");
                                                    beskeder_in.append(ModtagetBesked);

                                                    beskeder_in.setCaretPosition(beskeder_in.getDocument().getLength());
                                                    ModtagetBesked = "";
                                                }

                                            } else {
                                                ModtagetBesked += karakter;
                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                        thread_modtag.start();

                    } else { // Hvis porten ikke kan åbnes
                        try {
                            throw new Exception("Porten kunne ikke åbnes korrekt, prøv igen!");
                        } catch (Exception ex) {
                            System.exit(0);
                        }
                    }
                } else {
                    //Afbryd forbindelsen med COM porten
                    System.out.println("\nPorten blev lukket!");
                    valgteport.closePort();
                    tilslutknap.setText("Tilslut");
                    portlist.setEnabled(true);
                }
            }
        });

        /*Opsæt send knappen og opret en proces der køre når knappen viser "Afbryd" og man trykker send. 
        Processen står for, at sende data på OutputStream'en udfra teksten brugeren kan
        skrive ind og sende via GUI.*/
        sendknap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (tilslutknap.getText().equals("Afbryd")) {
                    // Lav en ny thread og læg data til output Streamen
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            out = valgteport.getOutputStream();
                            besked_to_long_flag = false;

                            try {
                                Thread.sleep(50);
                                String besked = beskeder_out.getText() + "\n";
                                beskeder_out.setText("");
                                String besked2 = null;

                                if (besked.length() > 16 && besked.length() < max_antal_char_modtager) {
                                    besked2 = besked.substring(16, besked.length());
                                    besked = besked.substring(0, 16);
                                    besked_to_long_flag = true;
                                } else if (besked.length() > max_antal_char_modtager) {
                                    //System.out.println();
                                    sendknap.setForeground(Color.RED);
                                    Thread.sleep(1000);
                                    sendknap.setForeground(Color.BLACK);
                                    throw new Exception("Hele beskeden kommer måske ikke frem, da strengen er " + (besked.length() - max_antal_char_modtager) + " karaktere for lang (" + max_antal_char_modtager + " max).");
                                }

                                byte[] buffer = besked.getBytes("ISO-8859-1");
                                valgteport.writeBytes(buffer, besked.length());
                                out.flush();

                                if (besked_to_long_flag) {
                                    byte[] buffer2 = besked2.getBytes("ISO-8859-1");
                                    valgteport.writeBytes(buffer2, besked2.length());
                                    out.flush();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                } else {
                    // Do nothing
                }
            }
        });

        kanal2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                out = valgteport.getOutputStream();
                try {
                    String besked = "c\n";
                    byte[] buffer = besked.getBytes("ISO-8859-1");
                    valgteport.writeBytes(buffer, besked.length());
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        vindue.setVisible(true);

        /*
        while (true) {
            boolean besked_to_long_flag = false;
            try {
                //Thread.sleep(100);
                String besked = tastatur.nextLine() + "\n";
                String besked2 = null;
                if (besked.length() > 16 && besked.length() < 35) {
                    besked2 = besked.substring(16, besked.length());
                    besked = besked.substring(0, 16);
                    besked_to_long_flag = true;
                } else if (besked.length() > 35) {
                    //System.out.println();
                    throw new Exception("Hele beskeden kommer måske ikke frem, da strengen er " + (besked.length() - 35) + " karaktere for lang (34 max).");
                }

                byte[] buffer = besked.getBytes("ISO-8859-1");
                valgteport.writeBytes(buffer, besked.length());
                //Thread.sleep(100);
                out = valgteport.getOutputStream();
                out.flush();
                if (besked_to_long_flag) {
                    byte[] buffer2 = besked2.getBytes("ISO-8859-1");
                    valgteport.writeBytes(buffer2, besked2.length());
                    //Thread.sleep(100);      
                    out.flush();
                }
                while (in.available() != 0) {
                    System.out.print((char) in.read());
                    //Thread.sleep(100);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         */
    }
}
