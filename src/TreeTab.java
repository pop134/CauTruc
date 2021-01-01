import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;
import java.awt.Component;


class TreeComponent extends JComponent {

    public synchronized void setValues(GraphicalTree gt, char f, String v) {
        this.tree = gt;
        flag = f;
        val = v;
        repaint();
    }


    public synchronized void paintComponent(Graphics g) {
        int gap;
        if (flag == 'i') {
            tree.insert(val, g);
            gap = tree.depth(tree.root);
            gap = gap * gap * 10;
            tree.draw(tree.root, g, getWidth() / 2, 50, getWidth() / 2, 50, 0, gap);
        } else if (flag == 'd') {
            if (tree.delete(val, g) == 1) {
                gap = tree.depth(tree.root);
                gap = gap * gap * 10;
                tree.draw(tree.root, g, getWidth() / 2, 50, getWidth() / 2, 50, 0, gap);
            }
        } else if (flag == 'f') {
            if (tree.find(val, g) == 1) {
                gap = tree.depth(tree.root);
                gap = gap * gap * 10;
                tree.draw(tree.root, g, getWidth() / 2, 50, getWidth() / 2, 50, 0, gap);
                //JOptionPane.showMessageDialog(null, val+" Found..!", "alert", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (tree != null) {
            gap = tree.depth(tree.root);
            gap = gap * gap * 10;
            tree.draw(tree.root, g, getWidth() / 2, 50, getWidth() / 2, 50, 0, gap);
        }
        flag = 'k';


    }

    String val;
    char flag;
    GraphicalTree tree;
}


public class TreeTab extends javax.swing.JPanel {

    /**
     * Creates new form TreePanel
     */
    TreeComponent comp;
    GraphicalTree gt;

    public TreeTab() {
        initComponents();
        comp = new TreeComponent();
        bstPanel.add(comp, BorderLayout.CENTER);
        gt = new GraphicalTree();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        treeCenterPanel = new javax.swing.JPanel();
        treePane = new javax.swing.JTabbedPane();
        bstPanel = new javax.swing.JPanel();
        bstNorthPanel = new javax.swing.JPanel();
        bstInsertButton = new javax.swing.JButton();
        bstInsertButton.setIcon(new ImageIcon(TreeTab.class.getResource("/image/insert.png")));
        bstDelButton = new javax.swing.JButton();
        bstDelButton.setIcon(new ImageIcon(TreeTab.class.getResource("/image/delete.png")));
        bstFindButton = new javax.swing.JButton();
        bstFindButton.setIcon(new ImageIcon(TreeTab.class.getResource("/image/find.png")));
        bstResetButton = new javax.swing.JButton();
        bstResetButton.setIcon(new ImageIcon(TreeTab.class.getResource("/image/reset.png")));
        bstInsertText = new javax.swing.JTextField();
        bstDelText = new javax.swing.JTextField();
        bstFindText = new javax.swing.JTextField();
        setLayout(new java.awt.BorderLayout());

        bstResetButton.setText("Reset");
        bstResetButton.addActionListener(this::setResetButtonActionPerformed);
        treeCenterPanel.setLayout(new javax.swing.BoxLayout(treeCenterPanel, javax.swing.BoxLayout.LINE_AXIS));

        bstPanel.setBackground(new java.awt.Color(254, 254, 254));
        bstPanel.setLayout(new java.awt.BorderLayout());

        bstInsertButton.setText("Insert");
        bstInsertButton.addActionListener(this::bstInsertButtonActionPerformed);

        bstDelButton.setText("Delete");
        bstDelButton.addActionListener(this::bstDelButtonActionPerformed);

        bstInsertText.setColumns(10);
        bstInsertText.addActionListener(this::bstInsertTextActionPerformed);
        bstInsertText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bstInsertTextKeyPressed(evt);
            }
        });

        textPanel = new javax.swing.JPanel(new BorderLayout());
        textPanel1 = new javax.swing.JPanel(new BorderLayout());
        textPanel2 = new javax.swing.JPanel(new BorderLayout());

        discribeText = new javax.swing.JTextArea("- Định nghĩa cây (tree): ", 10, 30);
        discribeText.append("cây là một cấu trúc dữ liệu gồm một tập hữu hạn các nút, giữa các nút có một quan hệ phân cấp gọi là quan hệ \"cha - con\". Nút mà không có cha được gọi là nút gốc, nút không có con được gọi là nút lá.\n");
        discribeText.append("- Khái niệm cây nhị phân: Là cây mà mỗi nút có tối đa 2 cây con.\n" +
                "- Khái niệm cây nhị phân tìm kiếm: Là cây nhị phân mà cây con bên trái có giá trị các nút nhỏ hơn nút cha (của cây con đó) và cây con bên phải có giá trị các nút lớn hơn nút cha (của cây con đó)\n");
        discribeText.append(" - Thao tác chính:\n");
        discribeText.append("INSERT: Thêm phần tử mới vào cây nhị phân\n");
        discribeText.append("DELETE: Xóa phần trong cây nhị phân\n");
        discribeText.setLineWrap(true);
        discribeText.setWrapStyleWord(true);

        discribeText.setEditable(false);
        discribeText.setEditable(false);

        JScrollPane discribeTextScroll = new JScrollPane(discribeText);

        runningText = new javax.swing.JTextArea(10, 30);
        runningText.setLineWrap(true);
        runningText.setWrapStyleWord(true);
        runningText.setEditable(false);
        JScrollPane runningTextScroll = new JScrollPane(runningText);

        JLabel label = new JLabel("Định nghĩa");
        textPanel1.add(label, BorderLayout.NORTH);
        textPanel1.setBorder(getBorder());
        textPanel1.add(discribeTextScroll, BorderLayout.CENTER);

        textPanel2.add(runningTextScroll);

        textPanel.add(textPanel1, BorderLayout.NORTH);
        textPanel.add(textPanel2, BorderLayout.SOUTH);
        javax.swing.GroupLayout bstNorthPanelLayout = new javax.swing.GroupLayout(bstNorthPanel);
        bstNorthPanelLayout.setHorizontalGroup(
                bstNorthPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(bstNorthPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(bstInsertText, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(bstInsertButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(bstDelText, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(bstDelButton)
                                .addGap(22)
                                .addComponent(bstFindText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(bstFindButton)
                                .addComponent(bstResetButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bstNorthPanelLayout.setVerticalGroup(
                bstNorthPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(bstNorthPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(bstNorthPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(bstInsertButton)
                                        .addComponent(bstDelButton)
                                        .addComponent(bstFindButton)
                                        .addComponent(bstInsertText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bstDelText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bstFindText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bstResetButton)

                                )
                                .addContainerGap())
        );
        bstNorthPanelLayout.linkSize(SwingConstants.HORIZONTAL, bstInsertText, bstDelText, bstFindText);
        bstNorthPanel.setLayout(bstNorthPanelLayout);

        bstPanel.add(bstNorthPanel, java.awt.BorderLayout.PAGE_START);
        bstPanel.add(textPanel, BorderLayout.EAST);
        treePane.addTab("Binary Search Tree", bstPanel);

        treeCenterPanel.add(treePane);

        add(treeCenterPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void bstInsertTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bstInsertTextActionPerformed

    }//GEN-LAST:event_bstInsertTextActionPerformed

    private void bstDelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bstDelButtonActionPerformed
        bstInsertButton.setEnabled(false);
        bstDelButton.setEnabled(false);
        bstFindButton.setEnabled(false);

        comp.setValues(gt, 'd', bstDelText.getText());
        runningText.append("Nút " + bstDelText.getText() + " bị xóa khỏi cây\n");

        bstDelText.setText("");

        bstInsertButton.setEnabled(true);
        bstDelButton.setEnabled(true);
        bstFindButton.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_bstDelButtonActionPerformed

    private void bstInsertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bstInsertButtonActionPerformed
        bstInsertButton.setEnabled(false);
        bstDelButton.setEnabled(false);
        bstFindButton.setEnabled(false);

        comp.setValues(gt, 'i', bstInsertText.getText());
        runningText.append("Nút " + bstInsertText.getText() + " được thêm vào cây\n");

        bstInsertText.setText("");

        bstInsertButton.setEnabled(true);
        bstDelButton.setEnabled(true);
        bstFindButton.setEnabled(true);
        bstResetButton.setEnabled(true);
    }//GEN-LAST:event_bstInsertButtonActionPerformed

    private void setResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        bstInsertButton.setEnabled(true);
        bstDelButton.setEnabled(false);
        bstFindButton.setEnabled(false);
        bstResetButton.setEnabled(false);
        runningText.setText("");
        gt = new GraphicalTree();
        comp.setValues(gt, '0', "0");
    }

    private void bstInsertTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bstInsertTextKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            bstInsertButton.setEnabled(false);
            bstDelButton.setEnabled(false);
            bstFindButton.setEnabled(false);

            comp.setValues(gt, 'i', bstInsertText.getText());
            bstInsertText.setText("");

            bstInsertButton.setEnabled(true);
            bstDelButton.setEnabled(true);
            bstFindButton.setEnabled(true);
        }

    }//GEN-LAST:event_bstInsertTextKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bstDelButton;
    private javax.swing.JTextField bstDelText;
    private javax.swing.JButton bstFindButton;
    private javax.swing.JTextField bstFindText;
    private javax.swing.JButton bstInsertButton;
    private javax.swing.JButton bstResetButton;
    private javax.swing.JTextField bstInsertText;
    private javax.swing.JPanel bstNorthPanel;
    private javax.swing.JPanel bstPanel;
    private javax.swing.JPanel treeCenterPanel;
    private javax.swing.JTabbedPane treePane;
    private javax.swing.JPanel textPanel;
    private javax.swing.JPanel textPanel1;
    private javax.swing.JPanel textPanel2;
    private javax.swing.JTextArea discribeText;
    private javax.swing.JTextArea runningText;
    // End of variables declaration//GEN-END:variables
}