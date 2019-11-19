/*
 * The MIT License
 *
 * Copyright 2019 guest-ayksrm.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package PBEngine.Rendering;

import JFUtils.dVector;
import JFUtils.quickTools;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author guest-ayksrm
 */
public class MapPanel extends javax.swing.JPanel {

    /**
     * Creates new form MapPanel
     */
    PBEngine.Supervisor sup;
    public MapPanel(PBEngine.Supervisor sup) {
        this.sup = sup;
        //initComponents();
        this.jPanel1 = new minimap(this);
        this.add(jPanel1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();

        jTextPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setViewportView(jTextPane1);

        jPanel1.setBackground(new java.awt.Color(214, 255, 214));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
class minimap extends JPanel{
    MapPanel map;
    
    public minimap(MapPanel map){
        super();
        this.map = map;
    }
    
    @Override
    public void paintComponent(Graphics g){
        Dimension currentSize = getParent().getSize();
        this.setSize(currentSize);
        int w = currentSize.width;
        int h = currentSize.height;
        
        //Clear
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, w, h);
        
        //Paint
        if(this.map.sup.Logic.getLevelMap() != null){
            String[][] fullmap = quickTools.clone2d(map.sup.Logic.getLevelMap());
            dVector current = map.sup.Logic.getCurrentLevelCoord();
            String[][] toSee = new String[3][3];
            for(dVector dir : quickTools.vectorDirs4){
                toSee[(int)(1+dir.x)][(int)(1+dir.y)] = fullmap[(int)(current.x+dir.x)][(int)(current.y+dir.y)];
            }
            
            int sizeW = w / 3;
            int sizeH = h / 3;
            
            for(int x : new JFUtils.Range(toSee.length)){
                for(int y : new JFUtils.Range(toSee[0].length)){
                    if(x == 1 && y == 1){
                        g.setColor(Color.yellow);
                    }
                    else{
                        g.setColor(Color.green);
                    }
                    g.fillRect(sizeW*x, sizeW*(x+1), sizeH*h, sizeH*(h+1));
                }
            }
        }
        else{
            g.setColor(Color.red);
            g.drawString("no map", 0, 0);
        }
    }
}