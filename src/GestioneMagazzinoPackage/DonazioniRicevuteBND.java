/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GestioneMagazzinoPackage;
import ConsegnaViveriPackage.Viveri;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manfredi
 */
public class DonazioniRicevuteBND extends javax.swing.JFrame {

    /**
     * Creates new form DonazioniRicevute
     */
    
    GestioneMagazzinoControl gestioneMagazzinoControl;
    PopUp popUpConferma = new PopUp();
    List<Viveri> daRimuovere = new ArrayList<>();
    public DonazioniRicevuteBND() {
        initComponents();
    }
    public void mostra(GestioneMagazzinoControl gestioneMagazzinoControl){
        this.gestioneMagazzinoControl = gestioneMagazzinoControl;
        this.setVisible(true);
    }
    
    public void mostra(GestioneMagazzinoControl gestioneMagazzinoControl, List<Donazioni> donazioni){
        this.gestioneMagazzinoControl = gestioneMagazzinoControl;
        this.setVisible(true);
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Viveri");
        model.addColumn("Quantità");
        
        for(Donazioni d : donazioni){
            Object[] rowData = {d.viveri, d.QTAViveri};
            model.addRow(rowData);
        }

        this.TabellaDonazioni.setModel(model);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        TabellaDonazioni = new javax.swing.JTable();
        ConfermaCaricoButton = new javax.swing.JButton();
        ModificaDatiButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Donazioni Ricevute");

        TabellaDonazioni.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Viveri", "Quantità"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TabellaDonazioni);

        ConfermaCaricoButton.setText("Conferma Carico");
        ConfermaCaricoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfermaCaricoButtonActionPerformed(evt);
            }
        });

        ModificaDatiButton.setText("Modifica Dati");
        ModificaDatiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificaDatiButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ModificaDatiButton)
                                .addGap(276, 276, 276)
                                .addComponent(ConfermaCaricoButton))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConfermaCaricoButton)
                    .addComponent(ModificaDatiButton))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ConfermaCaricoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfermaCaricoButtonActionPerformed
        this.setVisible(false);
        this.popUpConferma.mostraConferma(gestioneMagazzinoControl);
    }//GEN-LAST:event_ConfermaCaricoButtonActionPerformed

    private void ModificaDatiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificaDatiButtonActionPerformed
        int qta = 0;
        for(int i = 0; i<this.TabellaDonazioni.getRowCount(); i++){
            String tmp = this.TabellaDonazioni.getValueAt(i, 1).toString();
            qta = Integer.valueOf(tmp);
            
                
                if(qta < Integer.valueOf(this.TabellaDonazioni.getValueAt(i, 1).toString())){
                    //Aggiungo un oggetto di tipo viveri alla lista dei viveri da rimuovere
                    this.daRimuovere.add(new Viveri((String) this.TabellaDonazioni.getValueAt(i,0),(Integer) this.TabellaDonazioni.getValueAt(i, 1) - qta));
                }
            }
        
        this.gestioneMagazzinoControl.rimuovi(this.daRimuovere,true);
        this.setVisible(false);
        this.removeAllRowsFromTable(this.TabellaDonazioni);
        this.gestioneMagazzinoControl.popUp.mostraConferma(gestioneMagazzinoControl);
    }//GEN-LAST:event_ModificaDatiButtonActionPerformed
    
    private void removeAllRowsFromTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while(model.getRowCount() > 0){
            model.removeRow(0);
        }
    }
    
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
            java.util.logging.Logger.getLogger(DonazioniRicevuteBND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DonazioniRicevuteBND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DonazioniRicevuteBND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DonazioniRicevuteBND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DonazioniRicevuteBND().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ConfermaCaricoButton;
    private javax.swing.JButton ModificaDatiButton;
    private javax.swing.JTable TabellaDonazioni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
