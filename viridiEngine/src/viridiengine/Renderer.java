/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.util.Arrays;

/**
 *
 * @author Jonnelafin
 */
public class Renderer {
    private int a;
    private int b;
    private String[][] space;
    private int y;
    private int x;
    
    
    public void init(int y, int x){
        this.y = y;
        this.x = x;
        this.space = new String[y][x];
        this.a = (space.length);
        this.b = (space[0].length);
        fill(" . ");
    }
    
    public String[][] gets(){return(this.space);}
    
    void fill(String goal){
        String[][] tmp;
        tmp = this.space;
        for(int c = 0; c < this.y; c++){
            for(int u = 0; u < this.x; u++){
                this.space[c][u] = goal;
            }
        }
//        this.space = tmp;
    }
    void change(int locy,int locx,String to){
        try{
            this.space[locy][locx] = to;
        }
        catch (Exception e){
            System.out.println("Tried writing out of bounds: y(" + locy + "), x(" +locx + ")");
        }
    }
    public int sizey(){return(this.y);}
    public int sizex(){return(this.x);}
    
    
    
    

    

    
}
    
//    for(int i; i < 5; i++){}

