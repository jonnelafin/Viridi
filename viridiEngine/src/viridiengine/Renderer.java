/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import static java.lang.Math.round;
import javax.swing.JFrame;
/**
 *
 * @author Jonnelafin
 */
public class Renderer {
    private int a;
    private int b;
    private String[][] space;
    private Color[][] colors;
    public Canvas canvas = new Canvas();
    private int y;
    private int x;
    private JFrame frame;
    colorParser cP;
    int cW;
    int cH;
    
    public void init(int x, int y, JFrame f){
        this.frame = f;
        cP = new colorParser();
        this.x = y;
        this.y = x;
        this.space = new String[y][x];
//        this.colors = new Color[x][y];
        this.a = (space.length);
        this.b = (space[0].length);
//        colorFill(Color.white);
        fill("█", Color.black, "null");
        initVector(767, 562);
    }
    public void initVector(int x, int y){
        canvas.setSize(y, x);
        canvas.setMaximumSize(new Dimension(y,x));
        canvas.setMinimumSize(new Dimension(y,x));
    }
    
    public String[][] gets(){return(this.space);}
    public Color[][] getc(){return(this.colors);}
    
    void fill(String goal, Color gc, String style){
        String[][] tmp;
        tmp = this.space;
        for(int c = 0; c < this.x; c++){
            for(int u = 0; u < this.y; u++){
                this.space[c][u] = cP.parse("█",  gc, style);
                
            }
        }
//        this.space = tmp;
    }
    void vectorFill(Color co, int vec){
        BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null){
            canvas.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        g.setColor(co);
        g.clearRect(0, 0, canvas.getWidth() * -1, canvas.getHeight() * -1);
        canvas.setSize(767, 562);
        g.dispose();
        bs.show();
    }
    void colorFill(Color goal){
        Color[][] tmp;
        tmp = this.colors;
        for(int c = 0; c < this.x; c++){
            for(int u = 0; u < this.y; u++){
                this.colors[c][u] = goal;
            }
        }
    }
    void scan(int fx,int fy,int tx,int ty){
        
    }
    void change(int locy,int locx,String to, Color c, String style){
        try{
            this.space[locx][locy] = cP.parse(to,  c, style);
            
            //this.colors[locx][locy] = c;
        }
        catch (Exception e){
            System.out.println("Tried writing out of bounds: y(" + locy + "), x(" + locx + "): ");
            System.out.println(e);
        }
    }
    void vChange(float locx,float locy,String to, Color c, int vec){
        try{
            
            BufferStrategy bs = canvas.getBufferStrategy();
            if(bs == null){
                canvas.createBufferStrategy(3);
                return;
            }
        
            Graphics g = bs.getDrawGraphics();
            g.setColor(c);
            g.fillRect(round(locx), round(locy), (int) 15.34F, (int) 22.48F);
        
            g.dispose();
            if(vec == 1){
                bs.show();
            }
            
            //this.colors[locx][locy] = c;
        }
        catch (Exception e){
            System.out.println("Tried writing out of bounds: y(" + locy + "), x(" + locx + "): ");
            System.out.println(e);
        }
    }
    public int sizey(){return(this.x);}
    public int sizex(){return(this.y);}
    
    
    
    

    

    
}
    
//    for(int i; i < 5; i++){}

