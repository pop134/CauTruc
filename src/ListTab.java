
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.Border;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class ListComponent extends JComponent {
    SingleLinkedList list;
    int operation;
    int temp_x, temp_y, cur_x, cur_y, final_x, final_y, data, last_cur_x, last_cur_y;

    // 1 - insert
    // 2 - delete
    int width, height;
    int interX, interY;

    /* Drawing Generic Shapes like
     * Node
     * Null
     * first
     * Arrow
     */ListComponent() {
        // interX = 80;
        //interY = 70;
    }

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

    private void drawAnimation() {
        // Finding intermediate points by breshnam line
        int i = 0;
        int dx, dy, p, x, y;
        int prevx, prevy;
        this.operation = 1;

        temp_x = 20;
        temp_y = 20;
        cur_x = last_cur_x;
        cur_y = last_cur_y;
        dx = 60 + final_x - temp_x;
        dy = final_y - temp_y;

        p = 2 * (dy) - (dx);
        x = temp_x;
        y = temp_y;
        while (x <= final_x || y <= final_y) {

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
                interX = x;
                interY = y;

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    // Logger.getLogger(listPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                repaint();
            }

        }
        this.operation = 0;

    }

    public void setValues(SingleLinkedList list, int operation) {
        this.list = list;
        this.operation = operation;
        if (this.operation == 1) {
            Thread t1 = new Thread(this::drawAnimation);
            t1.start();

        }
        repaint();
    }

    void drawInterPath(Graphics g) {
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
        // draw first node
        g.setColor(Color.RED);
        drawNode(g, cur_x, cur_y, "");

        g.setColor(Color.BLACK);
        while (temp != null && temp.next != null) {


            // remove null in list
            g.setColor(Color.WHITE);
            drawNull(g, cur_x + 37, cur_y);
            // drl node
            //g.clearRect(temp_x+3, temp_y+3, 26, 26);


            if (cur_x + 130 > width) {
                final_x = 20;
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
                g.drawLine(cur_x + 35, cur_y + 15, final_x, final_y + 15);
                drawArrow(g, final_x, final_y + 15, 1);
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
        assert temp != null;
        drawNode(g, interX, interY, "" + temp.data);
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
        // draw first node
        g.setColor(Color.RED);
        drawNode(g, cur_x, cur_y, "");

        g.setColor(Color.BLACK);
        while (temp != null) {

            // remove null in list
            g.setColor(Color.WHITE);
            drawNull(g, cur_x + 37, cur_y);
            // draw node
            //g.clearRect(temp_x+3, temp_y+3, 26, 26);


            if (cur_x + 130 > width) {
                final_x = 20;
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
                g.drawLine(cur_x + 35, cur_y + 15, final_x, final_y + 15);
                drawArrow(g, final_x, final_y + 15, 1);
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

    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("Painted!!!!" + this.operation);


        if (this.operation == 1) {
            System.out.println("Insert operation performed");
            drawInterPath(g);
        } else {
            drawList(g);
        }
    }
}

public class ListTab extends javax.swing.JPanel {


    /**
     * Creates new form listPanel
     */
    ListComponent lcomp;

    SingleLinkedList listA, listCircular, dlistnull, dlistCircular;
    private JButton singlydeleteButton;

    public ListTab() {
        initComponents();
        lcomp = new ListComponent();

        singlyListPanel.add(lcomp, BorderLayout.CENTER);

        listA = new SingleLinkedList();
        listCircular = new SingleLinkedList();
        dlistnull = new SingleLinkedList();
        dlistCircular = new SingleLinkedList();

        lcomp.setValues(listA, 0);
        singlyListPanel.revalidate();

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listPane = new javax.swing.JTabbedPane();
        singlyListPanel = new javax.swing.JPanel() {
            public void paint(Graphics g) {
                super.paint(g);

            }

        };
        singlyListNorthPanel = new javax.swing.JPanel();
        singlyaddNodeText = new javax.swing.JTextField();
        singlyaddButton = new javax.swing.JButton();
        singlyaddButton.setIcon(new ImageIcon(ListTab.class.getResource("/image/insert.png")));
        qinputText18 = new javax.swing.JTextField();
        jButton31 = new javax.swing.JButton();
        jButton31.setIcon(new ImageIcon(ListTab.class.getResource("/image/delete.png")));
        setLayout(new java.awt.BorderLayout());

        textPanel = new javax.swing.JPanel(new BorderLayout());
        textPanel1 = new javax.swing.JPanel(new BorderLayout());
        textPanel2 = new javax.swing.JPanel(new BorderLayout());

        discribeText = new javax.swing.JTextArea(" - Định nghĩa danh sách (list): ", 10, 30);
        discribeText.append("Danh sách là cấu trúc dữ liệu trừu tường mà bao gồm một chuỗi những mục (item) và chúng ta cần quan tâm tới vị trí của những mục này.");
        discribeText.append("\n - Sử dụng phương thức ADD để thêm mục (item) mới vào danh sach, DELETE để xóa mục trong danh sách.");
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


        singlyListPanel.setBackground(new java.awt.Color(254, 254, 254));
        singlyListPanel.setLayout(new java.awt.BorderLayout());

        singlyaddNodeText.setColumns(5);
        singlyaddNodeText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                singlyaddNodeTextKeyPressed(evt);
            }
        });

        singlyaddButton.setText("Add");
        singlyaddButton.addActionListener(this::singlyaddButtonActionPerformed);

        qinputText18.setColumns(5);

        qinputText18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                qinputText18KeyPressed(evt);
            }
        });

        jButton31.setText("Delete");
        jButton31.addActionListener(this::jButton31ActionPerformed);

        javax.swing.GroupLayout singlyListNorthPanelLayout = new javax.swing.GroupLayout(singlyListNorthPanel);
        singlyListNorthPanel.setLayout(singlyListNorthPanelLayout);
        singlyListNorthPanelLayout.setHorizontalGroup(
                singlyListNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(singlyListNorthPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(singlyaddNodeText, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(singlyaddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(qinputText18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton31)
                                .addContainerGap(304, Short.MAX_VALUE))
        );
        singlyListNorthPanelLayout.setVerticalGroup(
                singlyListNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(singlyListNorthPanelLayout.createSequentialGroup()
                                .addGroup(singlyListNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(singlyListNorthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(singlyaddButton)
                                                .addComponent(singlyaddNodeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(qinputText18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jButton31))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JLabel label = new JLabel("Định nghĩa");
        textPanel1.add(label, BorderLayout.NORTH);
        textPanel1.setBorder(getBorder());
        textPanel1.add(discribeTextScroll, BorderLayout.CENTER);

        textPanel2.add(runningTextScroll);

        textPanel.add(textPanel1, BorderLayout.NORTH);
        textPanel.add(textPanel2, BorderLayout.SOUTH);

        singlyListPanel.add(singlyListNorthPanel, BorderLayout.NORTH);
        singlyListPanel.add(textPanel, BorderLayout.EAST);
        listPane.addTab("List", singlyListPanel);

        add(listPane, BorderLayout.CENTER);
    }

    private void singlyaddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_singlyaddButtonActionPerformed
        // TODO add your handling code here:
        int input = Integer.parseInt(singlyaddNodeText.getText());
        listA.insert(input);
        runningText.append("Mục (item): " + input + " được thêm vào danh sách\n");
        lcomp.setValues(listA, 1);
        singlyaddNodeText.setText("");
    }//GEN-LAST:event_singlyaddButtonActionPerformed


    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        int deleteNode = Integer.parseInt(qinputText18.getText());
        runningText.append("Mục (item): " + deleteNode + " bị xóa khỏi danh sách\n");
        if (listA.delete(deleteNode) == null) {
            qinputText18.setText("Not Found");
        } else {
            qinputText18.setText("");
        }
        lcomp.setValues(listA, 0);
    }

    private void singlyaddNodeTextKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            listA.insert(Integer.parseInt(singlyaddNodeText.getText()));
            lcomp.setValues(listA, 1);
            singlyaddNodeText.setText("");
        }

    }

    private void qinputText18KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (listA.delete(Integer.parseInt(qinputText18.getText())) == null) {
                qinputText18.setText("Not Found");
            } else {
                qinputText18.setText("");
            }
            lcomp.setValues(listA, 0);
        }
    }

    private javax.swing.JButton jButton31;
    private javax.swing.JTabbedPane listPane;
    private javax.swing.JTextField qinputText18;
    private javax.swing.JPanel singlyListNorthPanel;
    private javax.swing.JPanel textPanel;
    private javax.swing.JPanel textPanel1;
    private javax.swing.JPanel textPanel2;
    private javax.swing.JPanel singlyListPanel;
    private javax.swing.JButton singlyaddButton;
    private javax.swing.JTextField singlyaddNodeText;
    private javax.swing.JTextArea discribeText;
    private javax.swing.JTextArea runningText;
}
