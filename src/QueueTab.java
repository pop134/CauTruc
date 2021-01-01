import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

class QueueComponent extends JComponent {
    public synchronized void setValues(GraphicalArrayQueue qA) {
        this.queue = qA;
        repaint();
    }

    public synchronized void paintComponent(Graphics g) {

        Graphics2D qG = (Graphics2D) g;
        for (int i = 0; i < queue.size; i++) {
            queue.qA[i].setRect((50 + (i * 60)), (getHeight() / 2) - 30, 60, 60);

            int x = (int) queue.qA[i].getMinX();

            int y = (int) queue.qA[i].getMinY();
            qG.draw(queue.qA[i]);
            qG.drawString(queue.val[i], x + 30, y + 30);


        }

        if (this.queue.size != 0) {
            qG.setStroke(new BasicStroke(4));
            int tl = 80 + (queue.tail * 60);
            int hd = 80 + (queue.head * 60);
            queue.h.setLine(hd, (getHeight() / 2) - 37, hd, (getHeight() / 2) - 80);
            queue.t.setLine(tl, (getHeight() / 2) + 37, tl, (getHeight() / 2) + 80);
            qG.draw(queue.h);
            int h = (getHeight() / 2);
            int[] x = {hd, hd - 8, hd + 8, hd};
            int[] y = {h - 30, h - 50, h - 50, h - 30};
            int[] x1 = {tl, tl - 8, tl + 8, tl};
            int[] y1 = {h + 30, h + 50, h + 50, h + 30};
            g.fillPolygon(x, y, 4);
            qG.draw(queue.t);
            g.setColor(Color.red);
            g.drawString("Tail", tl - 4, (getHeight() / 2) + 95);
            g.drawString("Head", hd - 4, (getHeight() / 2) - 87);
            g.setColor(Color.black);
            g.fillPolygon(x1, y1, 4);
            qG.setStroke(new BasicStroke(1));
        }

    }

    GraphicalArrayQueue queue;
}


class QListComponent extends JComponent {
    SingleLinkedList list;
    int operation;
    int temp_x, temp_y, cur_x, cur_y, final_x, final_y, data, last_cur_x, last_cur_y;
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
                    Logger.getLogger(ListTab.class.getName()).log(Level.SEVERE, null, ex);
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

    public void setValues(SingleLinkedList list, int operation) {
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

        Node temp = this.list.first;
        final_x = cur_x + 60;
        final_y = cur_y;

        // draw first node
        g.setColor(Color.RED);
        g.drawString("Head", cur_x, cur_y + 20);
        g.setColor(Color.BLACK);
        g.drawLine(cur_x + 33, cur_y + 15, cur_x + 59, cur_y + 15);
        drawArrow(g, cur_x + 59, cur_y + 15, 1);


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
                changed = false;
            } else {
                g.drawLine(cur_x + 35, cur_y + 15, final_x, final_y + 15);

            }
            drawArrow(g, final_x, final_y + 15, 1);
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
        g.drawString("Tail", final_x + 3, final_y + 77);

        g.setColor(Color.BLACK);
        g.drawLine(final_x + 13, final_y + 68, final_x + 13, final_y + 30);
        drawArrow(g, final_x + 13, final_y + 30, 4);
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("Painted!!!!");

        drawList(g);
        if (this.operation == 1) {
            System.out.println("Insert operation performed");
            // drawAnimation(g, data);
        }


    }
}


public class QueueTab extends javax.swing.JPanel {

    /**
     * Creates new form QueuePanel
     */
    QueueComponent qcomp;
    GraphicalArrayQueue qA;
    QListComponent lcomp;
    SingleLinkedList listA;

    public QueueTab() {
        initComponents();
        sizeText.setText(null);
        lcomp = new QListComponent();
        qLinkedPanel.add(lcomp, BorderLayout.CENTER);
        listA = new SingleLinkedList();
        lcomp.setValues(listA, 0);
        enqueueButton.setEnabled(false);
        dequeueButton.setEnabled(false);
        resetButton.setEnabled(false);
        resetButton2.setEnabled(false);
        qLinkedPanel.revalidate();
    }

    private void initComponents() {

        queueCenterPanel = new javax.swing.JPanel();
        queuePane = new javax.swing.JTabbedPane();
        qArrayPanel = new javax.swing.JPanel() {

            public void paint(Graphics g) {
                super.paint(g);
                System.out.println("here");
            }

        };
        queueNorthPanel = new javax.swing.JPanel();
        enqueueButton = new javax.swing.JButton();
        enqueueButton.setIcon(new ImageIcon(QueueTab.class.getResource("/image/push.png")));
        dequeueButton = new javax.swing.JButton();
        dequeueButton.setIcon(new ImageIcon(QueueTab.class.getResource("/image/down.png")));
        qinputText = new javax.swing.JTextField();
        sizeButton = new javax.swing.JButton();
        sizeText = new javax.swing.JTextField();
        sizeLabel = new javax.swing.JLabel();
        resetButton = new javax.swing.JButton();
        resetButton.setIcon(new ImageIcon(QueueTab.class.getResource("/image/reset.png")));
        resetButton2 = new javax.swing.JButton();
        resetButton2.setIcon(new ImageIcon(QueueTab.class.getResource("/image/reset.png")));
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        qLinkedPanel = new javax.swing.JPanel();

        queueNorthLinkedPanel = new javax.swing.JPanel();
        qListEnqueueButton = new javax.swing.JButton();
        qListEnqueueButton.setIcon(new ImageIcon(QueueTab.class.getResource("/image/push.png")));
        dequeueButton1 = new javax.swing.JButton();
        dequeueButton1.setIcon(new ImageIcon(QueueTab.class.getResource("/image/down.png")));
        qListinputText = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();

        setLayout(new java.awt.BorderLayout());

        queueCenterPanel.setLayout(new javax.swing.BoxLayout(queueCenterPanel, javax.swing.BoxLayout.LINE_AXIS));

        qArrayPanel.setBackground(new java.awt.Color(254, 254, 254));
        qArrayPanel.setLayout(new java.awt.BorderLayout());

        enqueueButton.setText("Enqueue");
        enqueueButton.addActionListener(this::enqueueButtonActionPerformed);

        dequeueButton.setText("Dequeue");
        dequeueButton.addActionListener(this::dequeueButtonActionPerformed);

        qinputText.setColumns(5);
        qinputText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                qinputTextKeyPressed(evt);
            }
        });

        sizeButton.setText("Set Size");
        sizeButton.addActionListener(this::sizeButtonActionPerformed);


        sizeText.setColumns(5);
        sizeText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sizeTextKeyPressed(evt);
            }
        });

        sizeLabel.setFont(new java.awt.Font("Ubuntu", Font.PLAIN, 18)); // NOI18N
        sizeLabel.setForeground(new java.awt.Color(241, 19, 19));
        sizeLabel.setText("Kích thước:  ");

        resetButton.setText("Reset");
        resetButton.addActionListener(this::resetButtonActionPerformed);

        resetButton2.setText("Reset");
        resetButton2.addActionListener(this::setResetButton2ActionPerformed);

        textPanel = new javax.swing.JPanel(new BorderLayout());
        textPanel1 = new javax.swing.JPanel(new BorderLayout());
        textPanel2 = new javax.swing.JPanel(new BorderLayout());

        discribeText = new javax.swing.JTextArea(" - Định nghĩa hàng đợi (queue): ", 10, 30);
        discribeText.append("hàng đợi là một cấu trúc dữ liệu trừu tượng mà trong đó việc xóa dữ liệu được thực hiện ở dữ liệu đầu tiên được thêm vào");
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

        atextPanel = new javax.swing.JPanel(new BorderLayout());
        atextPanel1 = new javax.swing.JPanel(new BorderLayout());
        atextPanel2 = new javax.swing.JPanel(new BorderLayout());

        adiscribeText = new javax.swing.JTextArea(" - Định nghĩa hàng đợi (queue): ", 10, 30);
        adiscribeText.append("hàng đợi là một cấu trúc dữ liệu trừu tượng mà trong đó việc xóa dữ liệu được thực hiện ở dữ liệu đầu tiên được thêm vào");
        adiscribeText.setLineWrap(true);
        adiscribeText.setWrapStyleWord(true);

        adiscribeText.setEditable(false);
        adiscribeText.setEditable(false);

        JScrollPane adiscribeTextScroll = new JScrollPane(adiscribeText);

        arunningText = new javax.swing.JTextArea(10, 30);
        arunningText.setLineWrap(true);
        arunningText.setWrapStyleWord(true);
        arunningText.setEditable(false);
        JScrollPane arunningTextScroll = new JScrollPane(arunningText);

        JLabel alabel = new JLabel("Định nghĩa");
        atextPanel1.add(alabel, BorderLayout.NORTH);
        atextPanel1.setBorder(getBorder());
        atextPanel1.add(adiscribeTextScroll, BorderLayout.CENTER);

        atextPanel2.add(arunningTextScroll);

        atextPanel.add(atextPanel1, BorderLayout.NORTH);
        atextPanel.add(atextPanel2, BorderLayout.SOUTH);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout queueNorthPanelLayout = new javax.swing.GroupLayout(queueNorthPanel);
        queueNorthPanel.setLayout(queueNorthPanelLayout);
        queueNorthPanelLayout.setHorizontalGroup(
                queueNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(queueNorthPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(qinputText, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enqueueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dequeueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sizeText, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sizeButton)
                                .addGap(29, 29, 29)
                                .addComponent(sizeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        queueNorthPanelLayout.setVerticalGroup(
                queueNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(queueNorthPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(queueNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(queueNorthPanelLayout.createSequentialGroup()
                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(queueNorthPanelLayout.createSequentialGroup()
                                                .addGroup(queueNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(queueNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(qinputText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(enqueueButton)
                                                                .addComponent(dequeueButton)
                                                                .addComponent(sizeButton)
                                                                .addComponent(sizeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(sizeLabel)
                                                                .addComponent(resetButton)))
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );

        qArrayPanel.add(queueNorthPanel, java.awt.BorderLayout.NORTH);
        qArrayPanel.add(atextPanel, BorderLayout.EAST);


        queuePane.addTab("Queue-Array", qArrayPanel);

        qLinkedPanel.setBackground(new java.awt.Color(254, 254, 254));
        qLinkedPanel.setLayout(new java.awt.BorderLayout());

        qListEnqueueButton.setText("Enqueue");
        qListEnqueueButton.addActionListener(this::qListEnqueueButtonActionPerformed);

        dequeueButton1.setText("Dequeue");
        dequeueButton1.addActionListener(this::dequeueButton1ActionPerformed);

        qListinputText.setColumns(5);
        qListinputText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                qListinputTextKeyPressed(evt);
            }
        });

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout queueNorthLinkedPanelLayout = new javax.swing.GroupLayout(queueNorthLinkedPanel);
        queueNorthLinkedPanel.setLayout(queueNorthLinkedPanelLayout);
        queueNorthLinkedPanelLayout.setHorizontalGroup(
                queueNorthLinkedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(queueNorthLinkedPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(qListinputText, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(qListEnqueueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dequeueButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 322, Short.MAX_VALUE)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(resetButton2)
                                .addGap(82, 82, 82))
        );
        queueNorthLinkedPanelLayout.setVerticalGroup(
                queueNorthLinkedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(queueNorthLinkedPanelLayout.createSequentialGroup()
                                .addGroup(queueNorthLinkedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(queueNorthLinkedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(resetButton2)
                                                .addComponent(qListinputText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(qListEnqueueButton)
                                                .addComponent(dequeueButton1)))


                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        qLinkedPanel.add(queueNorthLinkedPanel, java.awt.BorderLayout.NORTH);
        qLinkedPanel.add(textPanel, BorderLayout.EAST);


        queuePane.addTab("Queue-Linked", qLinkedPanel);

        queueCenterPanel.add(queuePane);

        add(queueCenterPanel, java.awt.BorderLayout.CENTER);
    }

    private void dequeueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dequeueButtonActionPerformed
        String s = qA.dequeue();
        arunningText.append("Mục (item): " + s + " bị xóa khỏi danh sách\n");
        if (s == null) {
            dequeueButton.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Underflow: Queue Empty..!", "alert", JOptionPane.ERROR_MESSAGE);
        }
        enqueueButton.setEnabled(true);
        qcomp.setValues(qA);
        qinputText.setText("");// TODO add your handling code here:

    }

    private void sizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeButtonActionPerformed

        if (sizeText.getText().equals(""))
            return;

        if (qcomp != null) {
            qArrayPanel.remove(qcomp);
        }
        if (!sizeText.getText().equals(" ")) {
            sizeLabel.setText("Kích thước:   " + sizeText.getText());
            arunningText.append("Độ dài của danh sách được khởi tạo là: " + sizeText.getText() + "\n");

            sizeButton.setEnabled(false);
            enqueueButton.setEnabled(true);
            dequeueButton.setEnabled(true);
            resetButton.setEnabled(true);
        }
        qcomp = new QueueComponent();

        qArrayPanel.add(qcomp, BorderLayout.CENTER);

        qA = new GraphicalArrayQueue(Integer.parseInt(sizeText.getText()), qArrayPanel.getWidth(), qArrayPanel.getHeight());
        qcomp.setValues(qA);
        qArrayPanel.revalidate();

        sizeText.setText(null);
    }//GEN-LAST:event_sizeButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        sizeLabel.setText("Kích thước:   ");
        sizeButton.setEnabled(true);
        enqueueButton.setEnabled(false);
        dequeueButton.setEnabled(false);
        resetButton.setEnabled(false);
        arunningText.setText("");
        qA.size = 0;
        qcomp.setValues(qA);
    }//GEN-LAST:event_resetButtonActionPerformed


    private void setResetButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        qListEnqueueButton.setEnabled(true);
        dequeueButton1.setEnabled(false);
        resetButton2.setEnabled(false);
        runningText.setText("");
        listA = new SingleLinkedList();
        lcomp.setValues(listA, 0);
    }//GEN-LAST:event_resetButtonActionPerformed

    private void sizeTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sizeTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (sizeText.getText().equals(""))
                return;

            if (qcomp != null) {
                qArrayPanel.remove(qcomp);
            }
            if (!sizeText.getText().equals(" ")) {
                sizeLabel.setText("Kích thước:   " + sizeText.getText());

                sizeButton.setEnabled(false);
                enqueueButton.setEnabled(true);
                dequeueButton.setEnabled(true);
                resetButton.setEnabled(true);
            }
            qcomp = new QueueComponent();

            qArrayPanel.add(qcomp, BorderLayout.CENTER);

            qA = new GraphicalArrayQueue(Integer.parseInt(sizeText.getText()), qArrayPanel.getWidth(), qArrayPanel.getHeight());
            qcomp.setValues(qA);
            qArrayPanel.revalidate();

            sizeText.setText(null);

        }
    }//GEN-LAST:event_sizeTextKeyPressed

    private void enqueueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enqueueButtonActionPerformed
        int a = qA.enqueue(qinputText.getText());
        if (a == -1) {
            JOptionPane.showMessageDialog(null, "Overflow: Queue Full..!", "alert", JOptionPane.ERROR_MESSAGE);
            enqueueButton.setEnabled(false);
        }
        arunningText.append("Mục (item): " + qinputText.getText() + " được thêm vào danh sách\n");
        dequeueButton.setEnabled(true);
        qcomp.setValues(qA);
        qinputText.setText("");

    }//GEN-LAST:event_enqueueButtonActionPerformed

    private void dequeueButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dequeueButton1ActionPerformed
        if (listA.first == null)
            JOptionPane.showMessageDialog(null, "Underflow: Queue Empty..!", "alert", JOptionPane.ERROR_MESSAGE);
        else {
            runningText.append("Mục (item): " + listA.first.data + " bị xóa khỏi danh sách\n");
            listA.delete(listA.first.data);

        }
        lcomp.setValues(listA, 0);
    }//GEN-LAST:event_dequeueButton1ActionPerformed

    private void qListEnqueueButtonActionPerformed(java.awt.event.ActionEvent evt) {

        listA.insert(Integer.parseInt(qListinputText.getText()));
        resetButton2.setEnabled(true);
        dequeueButton1.setEnabled(true);
        runningText.append("Mục (item): " + Integer.parseInt(qListinputText.getText()) + " được thêm vào danh sách\n");
        lcomp.setValues(listA, 1);
        qListinputText.setText("");

    }

    private void qinputTextKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int a = qA.enqueue(qinputText.getText());
            if (a == -1) {
                JOptionPane.showMessageDialog(null, "Overflow: Queue Full..!", "alert", JOptionPane.ERROR_MESSAGE);
                enqueueButton.setEnabled(false);
            }
            dequeueButton.setEnabled(true);
            qcomp.setValues(qA);
            qinputText.setText("");

        }
    }

    private void qListinputTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qListinputTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            listA.insert(Integer.parseInt(qListinputText.getText()));
            lcomp.setValues(listA, 1);
            qListinputText.setText("");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dequeueButton;
    private javax.swing.JButton dequeueButton1;
    private javax.swing.JButton enqueueButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JPanel qArrayPanel;
    private javax.swing.JPanel qLinkedPanel;
    private javax.swing.JButton qListEnqueueButton;
    private javax.swing.JTextField qListinputText;
    private javax.swing.JTextField qinputText;
    private javax.swing.JPanel queueCenterPanel;
    private javax.swing.JPanel queueNorthLinkedPanel;
    private javax.swing.JPanel queueNorthPanel;
    private javax.swing.JTabbedPane queuePane;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton resetButton2;
    private javax.swing.JButton sizeButton;
    private javax.swing.JLabel sizeLabel;
    private javax.swing.JTextField sizeText;
    private javax.swing.JPanel textPanel;
    private javax.swing.JPanel textPanel1;
    private javax.swing.JPanel textPanel2;
    private javax.swing.JTextArea discribeText;
    private javax.swing.JTextArea runningText;
    private javax.swing.JPanel atextPanel;
    private javax.swing.JPanel atextPanel1;
    private javax.swing.JPanel atextPanel2;
    private javax.swing.JTextArea adiscribeText;
    private javax.swing.JTextArea arunningText;
    // End of variables declaration//GEN-END:variables
}
