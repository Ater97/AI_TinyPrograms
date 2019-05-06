/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayesiannetwork;

import java.util.ArrayList;


/**
 *
 * @author sorantes
 */
public class Node {
    
    /*
    Name,
    Table varibles
    probabilities
    */
    public String Name;
    public int ID;
    public ArrayList<Integer> Predecessorslst;
    public ArrayList<Integer> Successorslst;
    public ArrayList<Double> Probabilities;
    public boolean isRoot;
    
    public Node(String name, int id, ArrayList<Integer> predecessorslst,ArrayList<Double> probabilities, boolean root){
        this.Name = name;
        this.ID = id;
        this.Predecessorslst = predecessorslst;
        this.Probabilities = probabilities;
        this.isRoot = root;
    }
    
}
