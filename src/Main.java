
import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author ni3
 */
public class Main extends javax.swing.JFrame {

    static listPanel lp;
    static StackPanel stp;
    static QueuePanel qp;
    static TreePanel tp;
    static javax.swing.JPanel mainCenterPanel;

    public Main() {
        initComponents();
        mainCenterPanel = new javax.swing.JPanel();
        lp = new listPanel();
        stp = new StackPanel();
        qp = new QueuePanel();
        tp = new TreePanel();


        int x = (Toolkit.getDefaultToolkit().getScreenSize().width / 2);
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);


        setPanelSize();
        mainCenterPanel.setLayout(new BoxLayout(mainCenterPanel, BoxLayout.PAGE_AXIS));

        mainCenterPanel.add(lp);
        mainCenterPanel.add(stp);
        mainCenterPanel.add(tp);
        mainCenterPanel.add(qp);
        getContentPane().add(mainCenterPanel, BorderLayout.CENTER);
        hidePanels();
        this.pack();


    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        llMenu = new javax.swing.JMenuItem();
        treeMenu = new javax.swing.JMenuItem();
        stackMenu = new javax.swing.JMenuItem();
        queueMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Structures Visualization");
        setMinimumSize(new java.awt.Dimension(800, 550));
        addWindowStateListener(this::formWindowStateChanged);

        jMenu1.setText("Data Structure");

        // Linked list hide menu
        llMenu.setText("Linked List");
        llMenu.addActionListener(this::llMenuActionPerformed);
        jMenu1.add(llMenu);

        // Stack hide menu
        stackMenu.setText("Stack");
        stackMenu.addActionListener(this::stackMenuActionPerformed);
        jMenu1.add(stackMenu);

        // Queue hide menu
        queueMenu.setText("Queue");
        queueMenu.addActionListener(this::queueMenuActionPerformed);
        jMenu1.add(queueMenu);

        // BST hide menu
        treeMenu.setText("Tree");
        treeMenu.addActionListener(this::treeMenuActionPerformed);
        jMenu1.add(treeMenu);

        jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    void setPanelSize() {
        int w = this.getWidth();
        int h = this.getHeight();//- animationPanel.getHeight();
        mainCenterPanel.setSize(w, h);

        lp.setSize(w, h);
        stp.setSize(w, h);
        qp.setSize(w, h);
        tp.setSize(w, h);

        System.out.println("Width = " + w + "\nHeight = " + h);

    }

    void hidePanels() {
        stp.setVisible(false);
        qp.setVisible(false);
        tp.setVisible(false);
        lp.setVisible(false);

    }

    private void llMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_llMenuActionPerformed
        hidePanels();
        lp.setVisible(true);

    }//GEN-LAST:event_llMenuActionPerformed

    private void treeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treeMenuActionPerformed
        hidePanels();
        tp.setVisible(true);
    }//GEN-LAST:event_treeMenuActionPerformed

    private void stackMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stackMenuActionPerformed
        hidePanels();
        stp.setVisible(true);
    }//GEN-LAST:event_stackMenuActionPerformed

    private void queueMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queueMenuActionPerformed
        hidePanels();
        qp.setVisible(true);
    }//GEN-LAST:event_queueMenuActionPerformed


    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        //setPanelSize();// TODO add your handling code here:
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_formWindowStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JMenuItem llMenu;
    private javax.swing.JMenuItem queueMenu;
    private javax.swing.JMenuItem stackMenu;
    private javax.swing.JMenuItem treeMenu;
    // End of variables declaration//GEN-END:variables
}
