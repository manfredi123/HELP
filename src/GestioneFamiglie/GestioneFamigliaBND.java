/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GestioneFamiglie;

/**
 *
 * @author manfredi
 */
public class GestioneFamigliaBND extends javax.swing.JFrame {
    
    /**
     * Creates new form GestioneFamigliaBND
     */
    GestioneFamiglieControl gestioneFamiglieControl;
   
    
    public GestioneFamigliaBND() {
        initComponents();
    }
    
    public void mostra(GestioneFamiglieControl gestioneFamiglieControl){
        this.gestioneFamiglieControl = gestioneFamiglieControl;
        this.setVisible(true);
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
        AggiungiFamigliaButton = new javax.swing.JButton();
        AggiungiComponenteButton = new javax.swing.JButton();
        EliminaComponenteButton = new javax.swing.JButton();
        ModificaBisogniButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Gestione Famiglie");

        AggiungiFamigliaButton.setText("Aggiungi Famiglia");
        AggiungiFamigliaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AggiungiFamigliaButtonActionPerformed(evt);
            }
        });

        AggiungiComponenteButton.setText("Aggiungi Componente");
        AggiungiComponenteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AggiungiComponenteButtonActionPerformed(evt);
            }
        });

        EliminaComponenteButton.setText("Elimina Componente");
        EliminaComponenteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminaComponenteButtonActionPerformed(evt);
            }
        });

        ModificaBisogniButton.setText("Modifica Bisogni");
        ModificaBisogniButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificaBisogniButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Indietro");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AggiungiFamigliaButton)
                                    .addComponent(ModificaBisogniButton))
                                .addGap(51, 51, 51)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(EliminaComponenteButton)
                                    .addComponent(AggiungiComponenteButton)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(jLabel1)))
                        .addGap(0, 26, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AggiungiFamigliaButton, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(AggiungiComponenteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModificaBisogniButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EliminaComponenteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void EliminaComponenteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminaComponenteButtonActionPerformed
        this.gestioneFamiglieControl.createInserimentoCFElimina();
    }//GEN-LAST:event_EliminaComponenteButtonActionPerformed

    private void AggiungiFamigliaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AggiungiFamigliaButtonActionPerformed
        this.gestioneFamiglieControl.createSchermataDatiPreliminari();
        this.setVisible(false);
        
    }//GEN-LAST:event_AggiungiFamigliaButtonActionPerformed

    private void AggiungiComponenteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AggiungiComponenteButtonActionPerformed
        this.gestioneFamiglieControl.createInserimentoCF();
        this.setVisible(false);
        
    }//GEN-LAST:event_AggiungiComponenteButtonActionPerformed

    private void ModificaBisogniButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificaBisogniButtonActionPerformed
        this.gestioneFamiglieControl.createInserimentoCFBisogni();
        this.setVisible(false);
    }//GEN-LAST:event_ModificaBisogniButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.gestioneFamiglieControl.schermataRappresentante.mostra();
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(GestioneFamigliaBND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestioneFamigliaBND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestioneFamigliaBND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestioneFamigliaBND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestioneFamigliaBND().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AggiungiComponenteButton;
    private javax.swing.JButton AggiungiFamigliaButton;
    private javax.swing.JButton EliminaComponenteButton;
    private javax.swing.JButton ModificaBisogniButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
