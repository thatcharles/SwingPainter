/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstswing;

import java.util.ArrayList;
import javafx.util.Pair; 

/**
 *
 * @author charleschung
 */
public class Shape {
    
    String type;
    ArrayList<Pair<Integer,Integer>> Point = new ArrayList<>();
    
    public Shape(String str) {
        this.type = str; 
    }
    
    public void addPoint(Integer x, Integer y){
        Pair newPoint = new Pair(x,y); 
        Point.add(newPoint);
    }
    
    public void updatePoint(Integer x, Integer y){
        Pair newPoint = new Pair(x,y); 
        if(Point.size() <= 1){
            Point.add(newPoint);
        }
        else{
            Point.set(Point.size()-1,newPoint);
        }
        
    }

    
}

