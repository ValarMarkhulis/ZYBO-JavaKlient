/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.Color;

/**
 *
 * @author chris
 */
public class Login_page extends javax.swing.JPanel {

    private MySerialPort mySerialPort;

    /**
     * Creates new form Login_page
     */
    public Login_page() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton_ConnectZybo = new javax.swing.JButton();
        ZYBO_connected = new javax.swing.JLabel();
        PortList = new javax.swing.JComboBox<>();
        jButton_login = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 300));
        setMinimumSize(new java.awt.Dimension(500, 300));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(500, 300));

        jLabel1.setText("Login side");

        jTextField1.setText("casam");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Brugernavn");

        jLabel3.setText("Password");

        jTextField2.setText("tekst");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton_ConnectZybo.setForeground(new java.awt.Color(255, 255, 255));
        jButton_ConnectZybo.setText("Forbind til ZYBO");
        jButton_ConnectZybo.setEnabled(false);
        jButton_ConnectZybo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ConnectZyboActionPerformed(evt);
            }
        });

        ZYBO_connected.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        ZYBO_connected.setForeground(new java.awt.Color(255, 255, 255));
        ZYBO_connected.setText("Status:forsøger at forbinde..");

        PortList.setEnabled(false);
        PortList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PortListActionPerformed(evt);
            }
        });

        jButton_login.setText("Login");
        jButton_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_ConnectZybo)
                                .addGap(18, 18, 18)
                                .addComponent(ZYBO_connected)
                                .addGap(37, 37, 37)
                                .addComponent(PortList, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(jTextField2))
                                .addGap(86, 86, 86)
                                .addComponent(jButton_login)))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton_login))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_ConnectZybo)
                    .addComponent(ZYBO_connected)
                    .addComponent(PortList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton_ConnectZyboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ConnectZyboActionPerformed
        if (mySerialPort == null) {
            System.out.println("mySerialPort er ikke defineret! Den giver null!");
            return;
        } else {
            jButton_ConnectZybo.setEnabled(false);
            int portNR = PortList.getSelectedIndex();
            Thread thread;
            thread = new Thread() {
                @Override
                public void run() {
                    mySerialPort.forbindTilPort(portNR);
                    if (!jButton_ConnectZybo.isEnabled()) {
                        jButton_ConnectZybo.setEnabled(true);                        
                    }
                }
            };
            thread.start();
            

        }
    }//GEN-LAST:event_jButton_ConnectZyboActionPerformed

    private void PortListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PortListActionPerformed
        // TODO add your handling code here:
        if (mySerialPort == null) {
            System.out.println("mySerialPort er ikke defineret! Den giver null!");
            return;
        } else {
            mySerialPort.updatePorts();
        }

    }//GEN-LAST:event_PortListActionPerformed

    private void jButton_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loginActionPerformed

        //Check if the ZYBO is connected first of all
        if (ZYBO_connected.isEnabled()) {
            //We can login
            
        }else{
            //We should wait for the ZYBO to be connected
            
        }

    }//GEN-LAST:event_jButton_loginActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> PortList;
    private javax.swing.JLabel ZYBO_connected;
    private javax.swing.JButton jButton_ConnectZybo;
    private javax.swing.JButton jButton_login;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    void ZYBOConnected() {
        jButton_ConnectZybo.setForeground(Color.GREEN);
        jButton_ConnectZybo.setEnabled(false);
        ZYBO_connected.setForeground(Color.GREEN);
        ZYBO_connected.setText("Status: Forbundet");
        PortList.setEnabled(false);

    }

    void ZYBONOTConnected() {
        jButton_ConnectZybo.setForeground(Color.RED);
        jButton_ConnectZybo.setEnabled(true);
        ZYBO_connected.setForeground(Color.RED);
        ZYBO_connected.setText("Status: Ikke forbundet");
        PortList.setEnabled(true);
    }

    void populateList(SerialPort[] ports) {
        Thread thread;
        thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < ports.length; i++) {
                    PortList.addItem(ports[i].getSystemPortName());
                }
            }
        };
        thread.start();

    }

    void getmySerialPort(MySerialPort mySerialPort) {
        this.mySerialPort = mySerialPort;
    }
}