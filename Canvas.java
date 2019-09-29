/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstswing;
import java.awt.Button;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import static java.lang.System.out;
import java.util.ArrayList;
import javax.swing.*; 
import javafx.util.Pair; 

/**
 *
 * @author charleschung
 */
public class Canvas extends JComponent{
    String str;
    // private Image image;
    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;
    ArrayList<Shape> displayList = new ArrayList<Shape>();
    private boolean newShapeFlag = false;
    String currentTool = "none";
    
    public Canvas(String str) {
        this.str = str;
        this.setPreferredSize(new Dimension(700, 300));
        //Shape s = new Shape("rect");
        Shape s = new Shape(currentTool);
        displayList.add(s);
        // Shape tmp = displayList.get(0);
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                if(newShapeFlag){
                    //Shape s = new Shape("oval");
                    Shape s = new Shape(currentTool);
                    displayList.add(s);
                }
                oldX = e.getX();
                oldY = e.getY();
                Shape tmp = displayList.get(displayList.size()-1);
                tmp.addPoint(oldX,oldY);
                
            }
            
            public void mouseReleased(MouseEvent e) {
                newShapeFlag = true;
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
              // coord x,y when drag mouse
              currentX = e.getX();
              currentY = e.getY();

              if (g2 != null) {
                // draw line if g2 context not null
                g2.drawLine(oldX, oldY, currentX, currentY);
                // refresh draw area to repaint
                repaint();
                // store current coords x,y as olds x,y
                oldX = currentX;
                oldY = currentY;
                Shape tmp = displayList.get(displayList.size()-1);
                if(tmp.type == "stroke"){
                    tmp.addPoint(oldX,oldY);
                }
                else{
                    // out.println(tmp.Point.size());
                    tmp.updatePoint(oldX,oldY);
                }
                
              }
            }
        });
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        /*
        if (image == null) {
          // image to draw null ==> we create
          image = createImage(getSize().width, getSize().height);
          g2 = (Graphics2D) image.getGraphics();
          // enable antialiasing
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          // clear draw area
          clear();
          
        }
        out.print(this.getWidth() + ',');
        out.println(this.getHeight());
        image = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
        g.drawImage(image, 0, 0, null);
        */
        this.setPreferredSize(this.getSize());
        this.revalidate();
        
        g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clear();
        // out.println(displayList.size());
        for(Integer j=0; j<displayList.size();j++){
            Shape tmp = displayList.get(j);
            
            for(Integer i=0; i<tmp.Point.size()-1; i++){
                
                Pair old = tmp.Point.get(i);
                Pair cur = tmp.Point.get(i+1);
                // out.println((int) old.getKey() + " " + (int) old.getValue());
                if(tmp.type == "stroke" || tmp.type == "line"){
                   g2.drawLine((int) old.getKey(), (int) old.getValue(), (int) cur.getKey(), (int) cur.getValue()); 
                }
                else if(tmp.type == "rect"){
                   Integer oldX = (int) old.getKey();
                   Integer oldY = (int) old.getValue();
                   Integer curX = (int) cur.getKey();
                   Integer curY = (int) cur.getValue();
                   
                   Integer rectwidth = Math.abs(curX - oldX);
                   Integer rectheight = Math.abs(curY - oldY);
                   if(curX >= oldX && curY >= oldY) g2.drawRect(oldX, oldY,rectwidth,rectheight);
                   else if(curX >= oldX && curY < oldY ) g2.drawRect(oldX, curY,rectwidth,rectheight);
                   else if(curX < oldX && curY >= oldY ) g2.drawRect(curX, oldY,rectwidth,rectheight);
                   else if(curX < oldX && curY < oldY ) g2.drawRect(curX, curY,rectwidth,rectheight);
                }
                else if(tmp.type == "oval"){
                   Integer oldX = (int) old.getKey();
                   Integer oldY = (int) old.getValue();
                   Integer curX = (int) cur.getKey();
                   Integer curY = (int) cur.getValue();
                   
                   Integer rectwidth = Math.abs(curX - oldX);
                   Integer rectheight = Math.abs(curY - oldY);
                   if(curX >= oldX && curY >= oldY) g2.drawOval(oldX, oldY,rectwidth,rectheight);
                   else if(curX >= oldX && curY < oldY ) g2.drawOval(oldX, curY,rectwidth,rectheight);
                   else if(curX < oldX && curY >= oldY ) g2.drawOval(curX, oldY,rectwidth,rectheight);
                   else if(curX < oldX && curY < oldY ) g2.drawOval(curX, curY,rectwidth,rectheight);
                }
                
            }
        }
        
    };
    
    public void clear() {
        g2.setPaint(Color.white);
        // draw white on entire draw area to clear
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }
    
    public void setTool(String tool) {
        this.currentTool = tool;
        out.println(tool + " selected");
    }
}
