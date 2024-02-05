/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visitas;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author Gerardo Sánchez Nilo
 */
public class AddVisita extends javax.swing.JFrame {

    ventanaVisitas v;
    visitasOrdinarias[] listaV;
    private int numP = visitasOrdinarias.getNum_parroquias(), indexDecanato = 0, indexMes = 0;
    private boolean validado = false;

    /**
     * Creates new form EditarCSV
     */
    public AddVisita() {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Añadir Parroquia");
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    public AddVisita(visitasOrdinarias[] visitas, ventanaVisitas ventana) {

        listaV = visitas;
        v = ventana;
        initComponents();
        Image icon = null;
        try {
            icon = ImageIO.read(getClass().getResource("/visitas/imagenes/icono.png"));
        } catch (IOException ex) {
            Logger.getLogger(ventanaVisitas.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setIconImage(icon);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Añadir Parroquia");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        seleccionaMes();
        seleccionaDecanato();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        campoNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dia = new javax.swing.JComboBox<>();
        mes = new javax.swing.JComboBox<>();
        campoAnio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        comboDecanato = new javax.swing.JComboBox<>();
        comboHorario = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        campoDir = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        eEstado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setText("Nombre Parroquia");

        campoNombre.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        campoNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoNombreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoNombreFocusLost(evt);
            }
        });
        campoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNombreActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Descripción");

        dia.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        dia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        dia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaActionPerformed(evt);
            }
        });

        mes.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        mes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "May", "Jun", "Jul", "Aug", "Sep", "Oct" }));
        mes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mesActionPerformed(evt);
            }
        });

        campoAnio.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        campoAnio.setText(getAnio());
        campoAnio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoAnioFocusLost(evt);
            }
        });
        campoAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoAnioActionPerformed(evt);
            }
        });
        campoAnio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                campoAnioPropertyChange(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Decanato");

        comboDecanato.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        comboDecanato.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "San Juan Bautista", "Santa Rosa de Lima", "Chapalita", "La Santa Cruz", "Atemajac", "La Visitación", "El Refugio", "Huentitán", "Zapopan Estadio", "Talpita", "Santa Cecilia", "San Ildefonso", "Zalatitán", "Tetlán", "San Andrés", "San Felipe de Jesús", "Analco", "La Luz", "San Pedro", "Miravalle", "Getsenamí de la Cruz", "Lourdes", "Sagrario Metropolitano", "La Paz", "Jesús" }));

        comboHorario.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        comboHorario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5:00 PM", "10:00 AM" }));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Dirección");

        campoDir.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        campoDir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoDirFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoDirFocusLost(evt);
            }
        });
        campoDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoDirActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton1.setText("Añadir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        eEstado.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        eEstado.setText("Estado:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboHorario, 0, 109, Short.MAX_VALUE))
                            .addComponent(campoDir)
                            .addComponent(campoNombre)
                            .addComponent(comboDecanato, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboDecanato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(campoDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(eEstado))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String getAnio() {
        LocalDateTime fecha = LocalDateTime.now();
        System.out.println("Año = " + fecha.getYear());
        return "" + fecha.getYear();
    }

    private void pruebaArch() {

        System.out.println("Ruta Archivo: " + ventanaVisitas.getFile_Path() + " Numero de parroquias: " + visitasOrdinarias.getNum_parroquias());

        try {
            JOptionPane.showMessageDialog(rootPane, "Ultima Parroquia " + listaV[numP].getNombre_parroquia());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void agregaVisita() {
        if (numP < listaV.length && validado == true) {
            //se recuperan los valores desde sus respectivos campos

            //Nombre
            listaV[numP + 1].setNombre_parroquia(campoNombre.getText());
            ///Fecha
            listaV[numP + 1].setDescO(
                    dia.getSelectedItem().toString() + "/"
                    + mes.getSelectedItem() + "/" + campoAnio.getText() + " "
                    + comboHorario.getSelectedItem().toString()
            );
            ///Decanato
            listaV[numP + 1].setDecanato(comboDecanato.getSelectedItem().toString());
            comboDecanato.getSelectedIndex();
            ///Dirección
            listaV[numP + 1].setDireccion(campoDir.getText());

            numP += 1;
            //Se incrementa el conteo de las parroquias
            visitasOrdinarias.setNum_parroquias(numP);
            eEstado.setText("¡Entrada " + (numP) + " añadida con Exito!");
            eEstado.setForeground(Color.GREEN);

            v.escribirCSV();
            v.abrir();
            v.calculaProxima();

            switch (JOptionPane.showConfirmDialog(rootPane, "¿Deseas añadir otra entrada?", "¿Qué Deseas hacer?", JOptionPane.YES_NO_OPTION)) {
                case JOptionPane.YES_OPTION:
                    limpiarCampos();
                    break;
                case JOptionPane.NO_OPTION:
                    dispose();
                    break;
            }

        } else {
            JOptionPane.showMessageDialog(rootPane, "Datos no validos, corrige los datos señalados para continuar", "Error al validar datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    //función que valida si una cadena es Int
    private boolean isInt(String data) {
        try {
            Integer.parseInt(data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean validaCamposT(JTextField campoT) {

        return !(campoT.getText().isEmpty() || campoT.getText().charAt(0) == ' ');
    }

    private void limpiarCampos() {
        campoNombre.setText("");
        campoDir.setText("");
        seleccionaDecanato();
        seleccionaMes();
    }

    private void seleccionaDecanato() {
        String ultimoDecanato = listaV[visitasOrdinarias.getNum_parroquias()].getDecanato();
        indexDecanato = 0;
        for (int x = 0; x < comboDecanato.getItemCount(); x++) {
            if (comboDecanato.getItemAt(x).equals(ultimoDecanato)) {
                indexDecanato = x;
            }
        }

        comboDecanato.setSelectedIndex(indexDecanato);
    }

    private void seleccionaMes() {
        String cadenaDesc = listaV[visitasOrdinarias.getNum_parroquias()].getDescO(), datos[];
        indexMes = 0;

        datos = cadenaDesc.split("/");

        for (int x = 0; x < mes.getItemCount(); x++) {
            if (mes.getItemAt(x).equals(datos[1])) {
                indexMes = x;
            }
        }

        mes.setSelectedIndex(indexMes);
    }

    private void campoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNombreActionPerformed

    private void diaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diaActionPerformed

    private void campoAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoAnioActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_campoAnioActionPerformed

    private void campoDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoDirActionPerformed

    private void mesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mesActionPerformed
        // TODO add your handling code here:
        String elemento = mes.getSelectedItem().toString();
        int seleccionado = dia.getSelectedIndex();
        dia.removeAllItems();
        ///Validación de meses, si requiere 30 o 31 días.
        switch (elemento) {

            case "May":
            case "Jul":
            case "Aug":
            case "Oct":
                for (int x = 1; x <= 31; x++) {
                    dia.addItem("" + x);
                }
                break;

            default:
                for (int x = 1; x <= 30; x++) {
                    dia.addItem("" + x);
                }

        }

        if (seleccionado < dia.getItemCount()) {
            dia.setSelectedIndex(seleccionado);
        } else
            dia.setSelectedIndex(seleccionado - 1);
    }//GEN-LAST:event_mesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        agregaVisita();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void campoAnioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoAnioFocusLost
        // TODO add your handling code here:
        if (!isInt(campoAnio.getText())) {
            campoAnio.setForeground(Color.red);
            validado = false;
        } else {
            campoAnio.setForeground(Color.BLACK);
            validado = true;
        }
    }//GEN-LAST:event_campoAnioFocusLost

    private void campoAnioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_campoAnioPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_campoAnioPropertyChange

    private void campoNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoNombreFocusLost
        // TODO add your handling code here:
        if (!validaCamposT(campoNombre)) {
            System.out.println(campoNombre.getText().length());
            campoNombre.setBackground(Color.red);
            eEstado.setText("Estado: Ingresa un Nombre Valido");
            eEstado.setForeground(Color.red);
            validado = false;
        } else {
            campoNombre.setBackground(Color.white);
            eEstado.setText("Estado:");
            eEstado.setForeground(Color.BLACK);
            validado = true;
        }
    }//GEN-LAST:event_campoNombreFocusLost

    private void campoNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoNombreFocusGained
        // TODO add your handling code here:
        campoNombre.setBackground(Color.white);
        eEstado.setText("Estado:");
        eEstado.setForeground(Color.black);
    }//GEN-LAST:event_campoNombreFocusGained

    private void campoDirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoDirFocusGained
        // TODO add your handling code here:
        campoDir.setBackground(Color.white);
        eEstado.setText("Estado:");
        eEstado.setForeground(Color.black);
    }//GEN-LAST:event_campoDirFocusGained

    private void campoDirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoDirFocusLost
        // TODO add your handling code here:
        if (!validaCamposT(campoDir)) {
            System.out.println(campoDir.getText().length());
            campoDir.setBackground(Color.red);
            eEstado.setText("Estado: Ingresa una Dirección Valida");
            eEstado.setForeground(Color.red);
            validado = false;
        } else {
            campoDir.setBackground(Color.white);
            eEstado.setText("Estado:");
            eEstado.setForeground(Color.BLACK);
            validado = true;
        }
    }//GEN-LAST:event_campoDirFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddVisita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddVisita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddVisita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddVisita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddVisita().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField campoAnio;
    private javax.swing.JTextField campoDir;
    private javax.swing.JTextField campoNombre;
    private javax.swing.JComboBox<String> comboDecanato;
    private javax.swing.JComboBox<String> comboHorario;
    private javax.swing.JComboBox<String> dia;
    private javax.swing.JLabel eEstado;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox<String> mes;
    // End of variables declaration//GEN-END:variables
}