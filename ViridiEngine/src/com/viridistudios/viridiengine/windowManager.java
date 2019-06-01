/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viridistudios.viridiengine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.NumericShaper.Range;
import java.beans.EventHandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.round;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author Jonnelafinelse
 */
public class windowManager extends JFrame implements Runnable, ActionListener {
    //Screen components
    public LinkedList<Object> content = new LinkedList<>();
    
    public int rayDetail = 0;
    public int vector = 1;
    public int renderRays = 1;
    public LinkedList<Vector> record;
    colorParser cP = new colorParser();
    Timer timer = new Timer(15, this);
    float[][] red;
    int tickC = 0;
    int number;
    String screen;
    String[][] tmp;
    Renderer renderer = new Renderer();
    JLabel area;
    
    public int xd;
    public int yd;
    double lastTime;
    
    //GAMEOBJECTS:
//    Player p1;
    int co = 0;
    objectManager oM;
    //objectManager oM = new objectManager();
    LinkedList<gameObject> objects;
    //VARIABLES FOR TICKS:
    int tx;
    int ty;
    String ta;
    private float txf;
    private float tyf;
//    
    public vectorArea vA;
    kick k;
    private Input input;
    public windowManager(kick ki, objectManager o, int xd, int yd, VSRadManager a){
        
        this.oM = o;
        this.xd = xd;
        this.yd = yd;
        System.out.println("Initializing main input: " + ki);
        this.k = ki;
        input = new Input(k);
        System.out.println("out main input: " + k);
    }
    private AudioSource aM;
    VSRadManager rads;
    
    @Override
    public void run() {
        this.rads = new VSRadManager(xd, yd, oM);
        
        timer.setRepeats(true);
        timer.start();
        
        //Initiate screen size
        float size = 1F;
        float w = 767*size;
        float h = 562*size;
        float aspect = w / h;
        System.out.println(aspect);
        
//        int xd = 50;
//        int yd = 25;
        //xd = (int) (w / 15.34);
        //yd = (int) (h / 22.48);
        this.setTitle("ViridiEngine");
        this.setSize((int) Math.ceil(w), (int) Math.ceil(h*1.05F));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        area = new JLabel(screen);
        float Daspect = xd / yd;
        System.out.println(Daspect);
        float fontSize = (float) (Daspect * 7.5);
        area.setFont(new Font("monospaced", Font.PLAIN, (int) fontSize));
        //area.setFont(new Font("courier_new", Font.PLAIN, (int) fontSize));
        
        //final Font currFont = area.getFont();
        //area.setFont(new Font("test", currFont.getStyle(), currFont.getSize()));
//        area.setFont("MONOSPACED");
        area.setForeground(Color.black);
        area.setBackground(Color.black);
        
        this.add(area);
        content.add(area);
        //fresh();
        System.out.println("Initializing engine...");
        this.requestFocusInWindow();
        this.addKeyListener(input);
        this.addMouseMotionListener(input);
        this.setVisible(true);
        getContentPane().setBackground( Color.black );
        
        synchronized(renderer) {
            renderer.init(xd, yd, this);
        }
        red = new float[xd][yd];
        //canvas = renderer.canvas;
        vA = new vectorArea();
        this.add(vA);
        content.add(vA);
        vA.init((int)w, (int)h);
        tmp = renderer.gets();
        
        //rads = new VSRadManager(xd, yd, oM);
        rads.add(25, 12, 2, new Color(1, 1, 1), 1);
        //rads.add(25, 12, 4, new Color(1, 1, 1), 0);
        //rads.add(12, 1, 1, new Color(0, 0, 10));
        screen = "";
        
        //fresh();
        System.out.println("Done initializing engine!");
        
//        for (String[] y : tmp)
//        {  
//            for (String x : y)
//            {
//                System.out.print(y);
//                screen = screen + y;
//            }
//            System.out.println();
//            screen = screen + "\n";
//        }
        
        
        area.setText(fetch(renderer));
//        area.setEditable(false);
        
        
        
        
        //SUMMON TEST
        
        //levelLoader lL = new levelLoader("/src/viridiengine/levels/out.txt", oM);
        
        
        
        record = recorder.read("/src/com/viridistudios/viridiengine/records/recorded.txt");
        //oM.addObject(new Player(3, 3, "playback", "█", 1F, Color.blue, 11));
        //Add AudioSource
/*        try {
        aM = new AudioSource();
        } catch (LineUnavailableException ex) {
        Logger.getLogger(windowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
        Logger.getLogger(windowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        Logger.getLogger(windowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        ready = true;
    }
    public boolean ready;
    //Function for reshfreshing the screen
    private void fresh(){
        this.removeAll();
        for(Object i : content){
            //this.add((Component) i);
        }
    }
    
    public boolean running = true;
    @Override
    public void actionPerformed(ActionEvent e) {
        //fresh();
        
        this.number = Integer.parseInt(Integer.toString(tickC).substring(0, 1));
        if(vector == 0){
            area.setVisible(true);
            vA.setVisible(false);
        }
        if(vector == 1){
            area.setVisible(false);
            vA.setVisible(true);
        }
        if(running == true){
            tick();
            tickC++;
        }
        revalidate();
        repaint();
    }
    public void loadLevel(String level) throws URISyntaxException{
        oM.removeLevel();
        levelLoader lL = new levelLoader(level, oM);
    }
    Vector las;
    public Recorder recorder = new Recorder();
    boolean ve = false;
    void tick(){
        //rads.removeA();
        //rads.add(25, 12, 4);
        if(renderRays == 1){
            if(rayDetail == 0){
                rads.recalculate("player1", 1);
            }
            if(rayDetail == 1){
                rads.recalculate("none", 0);
            }
            red = rads.read();
        }
        if(tickC < record.size()){
            if(oM.findGameObject("playback") != 99999999){
                Vector loc = record.get(tickC);
                System.out.println("Playing back frame " + tickC + ": " + loc.represent());
                oM.getObjectByTag("playback").setLocation(loc);
            }
        }
        else{
            tickC = -1;
        }
        //vector = input.ve;
        
//        aM.play();
        recorder.record(oM);
        //UPDATE ARRAY
        class xyac
        {
            float x;
            float y;  
            String a;
            Color c;
            Vector last;
            private xyac(float tx, float ty, String ta, Color tc, Vector last) {
                this.x = tx; this.y = ty; this.a = ta; this.c = tc; this.last = last;
            }
        }
;
        LinkedList<xyac> lis = new LinkedList<xyac>();
        LinkedList<Vector> points = new LinkedList<>();
        LinkedList<Color> colors = new LinkedList<>();
        objects = oM.getObjects();
        int xp = 0, yp = 0;
        
        
        for(float[] x : red){
            for(float y : x){
                Color c = new Color(0,0,0);
                c = rads.colors[xp][yp];
                double i = y * 1F;
                if(i > 255){
                    i = 255;
                }
                float r = 0,g = 0,b = 0;
                //System.out.println();
                float brightness = 0.001F;
                try{r = rads.colors[xp][yp].getRed() * (y*brightness);}catch(Exception e){r = 0F;}
                try{g = rads.colors[xp][yp].getGreen() * (y*brightness);}catch(Exception e){g = 0F;}
                try{b = rads.colors[xp][yp].getBlue() * (y*brightness);}catch(Exception e){b = 0F;}
                if(r > 255){r = 255;}
                if(g > 255){g = 255;}
                if(b > 255){b = 255;}
                
                points.add(new Vector(xp,yp));
                colors.add(new Color((int) r,(int) g,(int) b));
                renderer.change(xp, yp,"█",new Color((int) i,(int) i,(int) i), "n");
                //renderer.vChange(xp * 15.34F, yp * 22.48F, new Color((int) i,(int) i,(int) i));
                yp++;
            }
            xp++;
            yp = 0;
        }
//        oM.doPhysics(renderer);
        for(gameObject p : objects){
//            renderer.change(tx, ty, "█", Color.WHITE);
//            System.out.println(p.getTag().getClass() + " " + "static".getClass());
            p.update(renderer, oM);
            p.checkInput(input);
            
//            oM.doPhysics(renderer, p);
            this.txf = p.getX();
            this.tyf = p.getY();
            this.tx = round(p.getX());
            this.ty = round(p.getY());
            this.las = new Vector(p.lastX, p.lastY);
            p.lastX = txf;
            p.lastY = tyf;
            ta = p.gAppearance();
            Color tc = p.getColor();
//            p.update(renderer);
            if(p.getTag().contains(new String("player1"))){
//                oM.addObject(new Player(tx, ty, "null", "█", 1F, Color.MAGENTA));
                //rads.add(tx, ty, 1);
//                aM.setVolume(d/10);
//                System.out.println(aM.getVolume());
            }else{}
            if(p.getTag().contains(new String("player2"))){
//                oM.addObject(new Player(tx, ty, "null", "█", 1F, Color.CYAN,co+3));
//                co++;
            }
//            System.out.println((p.getVX() + 1F) * (p.getVY()+1F));
            if(p.getTag().contains(new String("null")) && p.hits > 7){
//                oM.removeObject(p);
            }
        
//          System.out.println("pelaaja: x:" + tx + " y:" + ty);
/////////////////            renderer.change(tx, ty, ta, tc);
            points.add(new Vector(txf, tyf));
            
            float r = tc.getRed();
            float g = tc.getGreen();
            float b = tc.getBlue();
            r = (r * 0.55F + (rads.read()[tx][ty] * 0.55F) / 2 );if(r > 255){r = 255;}
            g = (g * 0.55F + (rads.read()[tx][ty] * 0.55F) / 2 );if(g > 255){g = 255;}
            b = (b * 0.55F + (rads.read()[tx][ty] * 0.55F) / 2 );if(b > 255){b = 255;}
            colors.add(new Color((int)r,(int)g,(int)b));
            lis.add(new xyac(tx,ty,ta,tc,las));


            //RENDER
//            renderer.fill(Integer.toString(number));
        }
        renderer.fill("█", Color.BLACK, "null");
        //renderer.vectorFill(Color.BLACK, vector);
        //Render
        
        //renderer.canvas.clean();
        vA.update(points, colors);
        for(xyac a : lis){
            renderer.change((int) (a.x), (int) (a.y), a.a, a.c, "n");
            //lM.vChange(a.last.x * 15.34F, a.last.y * 22.48F, a.a, Color.black, vector);
            //renderer.vChange(a.x * 15.34F, a.y * 22.48F, a.c);
        }
        
        area.setText(fetch(renderer));
    }
    String fetch(Renderer render)
    {
//        System.out.println("Started fetching!");
        int cx = 0;
        int cy = 0;
        
        //RENDER
        tmp = render.gets();
        Color[][] colors = render.getc();
        
        screen = "<html>";
        
        for (String[] y : tmp)
        {  
            
            screen = screen + "<p>";
            for (String x : y)
            {
                
//                System.out.print(y);
                try
                {
                    
                    screen += x;
                }
                catch(Exception e)
                {
                    System.out.println("failed to fetch screen at: " + cx + " " + cy);
                }
            }
            cx++;
        }
        screen = screen + "</p>";
//            System.out.println();
        cy++;
            
        
        screen = screen + "</html>";
//        System.out.println(colors);
//        System.out.println("Done fetching!");
        return(screen);
    }

    void record() {
        try {
            
            recorder.write(recorder.recorded, "/src/com/viridistudios/viridiengine/records/recorded.txt");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(windowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(windowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
