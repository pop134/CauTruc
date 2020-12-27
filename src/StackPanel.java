import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author ni3
 */

class StackComponent extends JComponent {
    public synchronized void setValues(GraphicalArrayStack sA) {
        this.stack = sA;
        repaint();
    }

    public synchronized void paintComponent(Graphics g) {

        Graphics2D qG = (Graphics2D) g;
        for (int i = 0; i < stack.size; i++) {
            stack.sA[i].setRect((50 + (i * 60)), (getHeight() / 2) - 30, 60, 60);

            int x = (int) stack.sA[i].getMinX();

            int y = (int) stack.sA[i].getMinY();
            qG.draw(stack.sA[i]);
            qG.drawString(stack.val[i], x + 30, y + 30);


        }

        if (this.stack.size != 0) {
            qG.setStroke(new BasicStroke(4));
            int tl = 80 + (stack.top * 60);
            //int hd = 80 + (stack.head * 60);
            //stack.h.setLine(hd, (getHeight()/2)-37, hd,(getHeight()/2)-80);
            stack.t.setLine(tl, (getHeight() / 2) + 37, tl, (getHeight() / 2) + 80);
            qG.draw(stack.t);
            int h = (getHeight() / 2);
            //int x[] = {hd, hd- 8,hd + 8, hd};
            // int y[] = {h -30, h - 50,h -  50, h - 30};
            int[] x1 = {tl, tl - 8, tl + 8, tl};
            int[] y1 = {h + 30, h + 50, h + 50, h + 30};
            // g.fillPolygon(x1, y1, 4);
            qG.draw(stack.t);
            g.setColor(Color.red);
            g.drawString("Top", tl - 4, (getHeight() / 2) + 95);
            g.setColor(Color.black);
            g.fillPolygon(x1, y1, 4);
            qG.setStroke(new BasicStroke(1));
        }
    }

    GraphicalArrayStack stack;
}


class StackListComponent extends JComponent {
    SingleLinkedList list;
    char flag;
    int operation;
    int temp_x, temp_y, cur_x, cur_y, final_x, final_y, data, last_cur_x, last_cur_y;

    // 1 - insert
    // 2 - delete
    int width, height;

    /* Drawing Generic Shapes like
     * Node
     * Null
     * first
     * Arrow
     */
    private void drawArrow(Graphics g, int x, int y, int flag) {
        // 1 - Right 2- bottom 3- left 4- Up
        if (flag == 1) {
            g.drawLine(x, y, x - 3, y - 3);
            g.drawLine(x, y, x - 3, y + 3);
        } else if (flag == 2) {
            g.drawLine(x, y, x - 3, y - 3);
            g.drawLine(x, y, x + 3, y - 3);
        } else if (flag == 3) {
            g.drawLine(x, y, x + 3, y - 3);
            g.drawLine(x, y, x + 3, y + 3);
        } else if (flag == 4) {
            g.drawLine(x, y, x + 3, y + 3);
            g.drawLine(x, y, x - 3, y + 3);
        }
    }

    private void drawNode(Graphics g, int x, int y, String s) {

        g.drawRect(x, y, 30, 30);
        g.drawRect(x + 30, y, 10, 30);
        g.drawString(s, x + 5, y + 20);
        drawNull(g, x + 37, y);
    }

    private void drawNull(Graphics g, int x, int y) {
        g.drawLine(x, y + 15, x + 20, y + 15);
        g.drawLine(x + 20, y + 15, x + 20, y + 30);
        g.drawLine(x + 15, y + 30, x + 25, y + 30);
    }

    private void drawAnimation(Graphics g, int data) {
        // Finding intermediate points by breshnam line
        int i = 0;
        int dx, dy, p, x, y;

        this.operation = 0;

        temp_x = 20;
        temp_y = 20;
        cur_x = last_cur_x;
        cur_y = last_cur_y;
        dx = final_x - temp_x;
        dy = final_y - temp_y;

        p = 2 * (dy) - (dx);
        x = temp_x;
        y = temp_y;
        g.setColor(Color.BLACK);
        drawNode(g, 20, 20, "" + data);

        while (x <= final_x) {
            if (p < 0) {
                x = x + 1;
                p = p + 2 * (dy);
            } else {
                x = x + 1;
                y = y + 1;
                p = p + 2 * (dy - dx);
            }
            System.out.println("x=" + x + "    y=" + y);
            if (x % 4 == 0) {
                this.update(g);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(listPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                g.setColor(Color.WHITE);
                drawNode(g, final_x, final_y, "" + data);
                g.setColor(Color.BLACK);
                g.drawLine(cur_x + 35, cur_y + 15, x + 20, y + 30);
                drawNode(g, x, y, "" + data);
            }

        }
        g.setColor(Color.white);
        g.drawLine(cur_x + 35, cur_y + 15, x + 20, y + 30);
        drawNode(g, x, y, "" + data);


    }

    public void setValues(SingleLinkedList list, int operation, char flag) {
        this.flag = flag;
        this.list = list;
        this.operation = operation;
        repaint();
    }

    public void drawList(Graphics g) {
        height = this.getHeight();
        width = this.getWidth();

        //  int cur_x, cur_y, step_height, temp_x, temp_y, final_x, final_y;
        int step_height;
        int incr_distance = 50;  // distance between vertical list in resize case
        boolean changed = false;
        cur_x = 20;
        cur_y = 70;
        step_height = 70;
        final_x = cur_x + 60;
        final_y = cur_y;
        Node temp = this.list.first;
        // draw first node

        g.setColor(Color.BLACK);
        while (temp != null) {

            // remove null in list
            g.setColor(Color.WHITE);
            drawNull(g, cur_x + 37, cur_y);
            // draw node
            //g.clearRect(temp_x+3, temp_y+3, 26, 26);


            if (cur_x + 130 > width) {
                final_x = 90;
                step_height += (incr_distance + 30);
                final_y = step_height;

                changed = true;
            } else {
                final_x = cur_x + 60;
                final_y = cur_y;
            }

            g.setColor(Color.black);
            drawNode(g, final_x, final_y, "" + temp.data);
            if (changed) {
                g.drawLine(cur_x + 35, cur_y + 15, cur_x + 70, cur_y + 15);
                g.drawLine(cur_x + 70, cur_y + 15, cur_x + 70, cur_y + (30 + incr_distance / 2));
                g.drawLine(cur_x + 70, cur_y + (30 + incr_distance / 2), 10, cur_y + (30 + incr_distance / 2));
                g.drawLine(10, cur_y + (30 + incr_distance / 2), 10, final_y + 15);
                g.drawLine(10, final_y + 15, final_x, final_y + 15);
                drawArrow(g, final_x, final_y + 15, 1);
                changed = false;
            } else {
                if (final_x != 80) {
                    g.drawLine(cur_x + 35, cur_y + 15, final_x, final_y + 15);
                    drawArrow(g, final_x, final_y + 15, 1);
                }
            }
            // update cur_x and cur_y
            last_cur_x = cur_x;
            last_cur_y = cur_y;
            cur_x = final_x;
            cur_y = final_y;
            data = temp.data;
            // draw remaining node
            temp = temp.next;
        }
        g.setColor(Color.RED);
        g.drawString("Top", final_x + 3, final_y + 77);
        g.setColor(Color.BLACK);
        g.drawLine(final_x + 13, final_y + 68, final_x + 13, final_y + 30);
        drawArrow(g, final_x + 13, final_y + 30, 4);
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("Painted!!!!");
        if (flag == 'e') {
            g.setColor(Color.RED);
            //g.drawString("Underflow : Stack Empty",80, 70);

        }
        drawList(g);
        if (this.operation == 1) {
            System.out.println("Insert operation performed");
            // drawAnimation(g, data);
        }


    }
}

public class StackPanel extends javax.swing.JPanel {

    /**
     * Creates new form StackPanel
     */
    StackComponent scomp;
    GraphicalArrayStack sA;
    StackListComponent lcomp;
    SingleLinkedList listA;

    public StackPanel() {
        initComponents();
        lcomp = new StackListComponent();
        sLinkedPanel.add(lcomp, BorderLayout.CENTER);
        listA = new SingleLinkedList();
        lcomp.setValues(listA, 0, 'n');
        sLinkedPanel.revalidate();
        pushButton.setEnabled(false);
        popButton.setEnabled(false);
        stackResetButton.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stackCenterPanel = new javax.swing.JPanel();
        stackPane = new javax.swing.JTabbedPane();
        sArrayPanel = new javax.swing.JPanel();

        stackArrayNorthPanel = new javax.swing.JPanel();
        pushButton = new javax.swing.JButton();
        pushButton.setIcon(new ImageIcon(StackPanel.class.getResource("/image/push.png")));
        popButton = new javax.swing.JButton();
        popButton.setIcon(new ImageIcon(StackPanel.class.getResource("/image/down.png")));
        sinputText = new javax.swing.JTextField();
        stackSizeButton = new javax.swing.JButton();
        stackSizeText = new javax.swing.JTextField();
        stackSizeLabel = new javax.swing.JLabel();
        stackResetButton = new javax.swing.JButton();
        stackResetButton.setIcon(new ImageIcon(StackPanel.class.getResource("/image/reset.png")));
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        sLinkedPanel = new javax.swing.JPanel();
        stackLinkedNorthPanel1 = new javax.swing.JPanel();
        sLPushButton = new javax.swing.JButton();
        sLPushButton.setIcon(new ImageIcon(StackPanel.class.getResource("/image/push.png")));
        sListPopButton = new javax.swing.JButton();
        sListPopButton.setIcon(new ImageIcon(StackPanel.class.getResource("/image/down.png")));
        sListInputText = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        setLayout(new java.awt.BorderLayout());

        stackCenterPanel.setLayout(new javax.swing.BoxLayout(stackCenterPanel, javax.swing.BoxLayout.LINE_AXIS));

        sArrayPanel.setBackground(new java.awt.Color(254, 254, 254));
        sArrayPanel.setLayout(new java.awt.BorderLayout());

        pushButton.setText("Push");
        pushButton.addActionListener(this::pushButtonActionPerformed);

        popButton.setText("Pop");
        popButton.addActionListener(this::popButtonActionPerformed);

        sinputText.setColumns(5);

        sinputText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sinputTextKeyPressed(evt);
            }
        });

        stackSizeButton.setText("Set Size");
        stackSizeButton.addActionListener(this::stackSizeButtonActionPerformed);

        stackSizeText.setColumns(5);
        stackSizeText.addActionListener(this::stackSizeTextActionPerformed);
        stackSizeText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stackSizeTextKeyPressed(evt);
            }
        });

        stackSizeLabel.setFont(new java.awt.Font("Ubuntu", Font.PLAIN, 18)); // NOI18N
        stackSizeLabel.setForeground(new java.awt.Color(241, 19, 19));
        stackSizeLabel.setText("Stack Size:  ");

        stackResetButton.setText("Reset");
        stackResetButton.addActionListener(this::stackResetButtonActionPerformed);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout stackArrayNorthPanelLayout = new javax.swing.GroupLayout(stackArrayNorthPanel);
        stackArrayNorthPanel.setLayout(stackArrayNorthPanelLayout);
        stackArrayNorthPanelLayout.setHorizontalGroup(
                stackArrayNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(stackArrayNorthPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(sinputText, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pushButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(popButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stackSizeText, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stackSizeButton)
                                .addGap(29, 29, 29)
                                .addComponent(stackSizeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stackResetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        stackArrayNorthPanelLayout.setVerticalGroup(
                stackArrayNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(stackArrayNorthPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(stackArrayNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(stackArrayNorthPanelLayout.createSequentialGroup()
                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(stackArrayNorthPanelLayout.createSequentialGroup()
                                                .addGroup(stackArrayNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(stackArrayNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(sinputText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(pushButton)
                                                                .addComponent(popButton)
                                                                .addComponent(stackSizeButton)
                                                                .addComponent(stackSizeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(stackSizeLabel)
                                                                .addComponent(stackResetButton)))
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );

        sArrayPanel.add(stackArrayNorthPanel, java.awt.BorderLayout.NORTH);

        stackPane.addTab("Stack-Array", sArrayPanel);

        sLinkedPanel.setBackground(new java.awt.Color(254, 254, 254));
        sLinkedPanel.setLayout(new java.awt.BorderLayout());

        sLPushButton.setText("Push");
        sLPushButton.addActionListener(this::sLPushButtonActionPerformed);

        sListPopButton.setText("Pop");
        sListPopButton.addActionListener(this::sListPopButtonActionPerformed);

        sListInputText.setColumns(5);

        sListInputText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sListInputTextKeyPressed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout stackLinkedNorthPanel1Layout = new javax.swing.GroupLayout(stackLinkedNorthPanel1);
        stackLinkedNorthPanel1.setLayout(stackLinkedNorthPanel1Layout);
        stackLinkedNorthPanel1Layout.setHorizontalGroup(
                stackLinkedNorthPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(stackLinkedNorthPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(sListInputText, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sLPushButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(sListPopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(236, 236, 236)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(108, Short.MAX_VALUE))
        );
        stackLinkedNorthPanel1Layout.setVerticalGroup(
                stackLinkedNorthPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(stackLinkedNorthPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(stackLinkedNorthPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(stackLinkedNorthPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(sListInputText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(sLPushButton)
                                                .addComponent(sListPopButton)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sLinkedPanel.add(stackLinkedNorthPanel1, java.awt.BorderLayout.NORTH);

        stackPane.addTab("Stack-Linked List", sLinkedPanel);

        stackCenterPanel.add(stackPane);

        add(stackCenterPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    private void pushButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pushButtonActionPerformed
        if (sinputText.getText().equals("")) {
            //System.out.println("ok");
            return;
        }
        int temp = sA.push(sinputText.getText());

        if (temp == -1) {
            pushButton.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Overflow: Stack Full..!", "alert", JOptionPane.ERROR_MESSAGE);
        }
        popButton.setEnabled(true);
        scomp.setValues(sA);
        sinputText.setText("");

    }//GEN-LAST:event_pushButtonActionPerformed

    private void popButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popButtonActionPerformed
        String temp = sA.pop();
        if (temp == null) {
            popButton.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Underflow: Stack Empty..!", "alert", JOptionPane.ERROR_MESSAGE);
        }
        pushButton.setEnabled(true);
        scomp.setValues(sA);
        sinputText.setText("");
    }//GEN-LAST:event_popButtonActionPerformed

    private void stackSizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stackSizeButtonActionPerformed
        if (stackSizeText.getText().equals(""))
            return;
        if (scomp != null) {
            sArrayPanel.remove(scomp);
        }
        if (!stackSizeText.getText().equals(" ")) {
            stackSizeLabel.setText("Stack Size:   " + stackSizeText.getText());

            stackSizeButton.setEnabled(false);
            pushButton.setEnabled(true);
            popButton.setEnabled(true);
            stackResetButton.setEnabled(true);

        }
        scomp = new StackComponent();

        sArrayPanel.add(scomp, BorderLayout.CENTER);

        sA = new GraphicalArrayStack(Integer.parseInt(stackSizeText.getText()), sArrayPanel.getWidth(), sArrayPanel.getHeight());
        scomp.setValues(sA);
        sArrayPanel.revalidate();

        stackSizeText.setText(null);
    }//GEN-LAST:event_stackSizeButtonActionPerformed

    private void stackResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stackResetButtonActionPerformed
        stackSizeLabel.setText("Stack Size:   ");
        stackSizeButton.setEnabled(true);
        sA.size = 0;
        scomp.setValues(sA);
        pushButton.setEnabled(false);
        popButton.setEnabled(false);
        stackSizeButton.setEnabled(true);

    }//GEN-LAST:event_stackResetButtonActionPerformed

    private void stackSizeTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stackSizeTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (stackSizeText.getText().equals(""))
                return;
            if (scomp != null) {
                sArrayPanel.remove(scomp);
            }
            if (!stackSizeText.getText().equals(" ")) {
                stackSizeLabel.setText("Stack Size:   " + stackSizeText.getText());

                stackSizeButton.setEnabled(false);
                pushButton.setEnabled(true);
                popButton.setEnabled(true);
                stackResetButton.setEnabled(true);

            }
            scomp = new StackComponent();

            sArrayPanel.add(scomp, BorderLayout.CENTER);

            sA = new GraphicalArrayStack(Integer.parseInt(stackSizeText.getText()), sArrayPanel.getWidth(), sArrayPanel.getHeight());
            scomp.setValues(sA);
            sArrayPanel.revalidate();

            stackSizeText.setText(null);
        }
    }//GEN-LAST:event_stackSizeTextKeyPressed

    private void stackSizeTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stackSizeTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stackSizeTextActionPerformed

    private void sLPushButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sLPushButtonActionPerformed
        if (sListInputText.getText().equals("")) {
            //System.out.println("ok");
            return;
        }
        listA.insert(Integer.parseInt(sListInputText.getText()));
        lcomp.setValues(listA, 1, 'n');
        sListInputText.setText("");


    }//GEN-LAST:event_sLPushButtonActionPerformed

    private void sListPopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sListPopButtonActionPerformed
        // TODO add your handling code here:
        if (listA.first == null) {
            JOptionPane.showMessageDialog(null, "Underflow: Stack Empty..!", "alert", JOptionPane.ERROR_MESSAGE);
            lcomp.setValues(listA, 0, 'e');
        } else {
            listA.delete(listA.last.data);
            lcomp.setValues(listA, 0, 'n');
        }


    }//GEN-LAST:event_sListPopButtonActionPerformed

    private void sListInputTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sListInputTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            listA.insert(Integer.parseInt(sListInputText.getText()));
            lcomp.setValues(listA, 1, 'n');
            //    sListInputText.setText("");
            sListInputText.setText(null);
        }
    }//GEN-LAST:event_sListInputTextKeyPressed

    private void sinputTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sinputTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (sinputText.getText().equals("")) {
                //System.out.println("ok");
                return;
            }
            int temp = sA.push(sinputText.getText());

            if (temp == -1) {
                pushButton.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Overflow: Stack Full..!", "alert", JOptionPane.ERROR_MESSAGE);
            }
            popButton.setEnabled(true);
            scomp.setValues(sA);
            sinputText.setText("");
        }
    }//GEN-LAST:event_sinputTextKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JButton popButton;
    private javax.swing.JButton pushButton;
    private javax.swing.JPanel sArrayPanel;
    private javax.swing.JButton sLPushButton;
    private javax.swing.JPanel sLinkedPanel;
    private javax.swing.JTextField sListInputText;
    private javax.swing.JButton sListPopButton;
    private javax.swing.JTextField sinputText;
    private javax.swing.JPanel stackArrayNorthPanel;
    private javax.swing.JPanel stackCenterPanel;
    private javax.swing.JPanel stackLinkedNorthPanel1;
    private javax.swing.JTabbedPane stackPane;
    private javax.swing.JButton stackResetButton;
    private javax.swing.JButton stackSizeButton;
    private javax.swing.JLabel stackSizeLabel;
    private javax.swing.JTextField stackSizeText;
    // End of variables declaration//GEN-END:variables
}
