/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayesiannetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author sorantes
 */
public final class Network {
    public Map<String,Node> graph;
    public Network(ArrayList<String> list){
        this.graph = new HashMap();
      createNetwork(list);  
    }

    public void createNetwork(ArrayList<String> list){
        ArrayList<ArrayList<String>> Nodeslst = GetNodesInf(list);
        for (int i = 0; i < Nodeslst.size(); i++) {
            String Name = Nodeslst.get(i).get(0).toLowerCase().trim().replace(",", "");
            ArrayList<String> Predecessorslst = new ArrayList<>();
            ArrayList<Integer> nodePrelst = new ArrayList<>();
            String Predecessors = Nodeslst.get(i).get(1);
            boolean isroot = false;
            int j = 1;
            if(!isNumeric(Predecessors.replace(",", ""))){
                Predecessorslst = new ArrayList<>(Arrays.asList(Predecessors.split(","))); 
                j++; 
                for (int k = 0; k < Predecessorslst.size(); k++) {
                    String preName = Predecessorslst.get(k).toLowerCase().trim().replace(",", "");
                    Node temp = graph.get(preName);
                    nodePrelst.add(temp.ID);
                }
            }
            else
                isroot = true;
            ArrayList<String> Probabilities = new ArrayList<>(Nodeslst.get(i).subList(j, Nodeslst.get(i).size()));
            ArrayList<Double> doblist = new ArrayList<>(Probabilities.stream()
                    .map(s -> s.replace(",", ""))
                    .map(s -> Double.parseDouble(s))
                    .collect(Collectors.toList()));
                    
            Node newNode = new Node(Name, i, nodePrelst, doblist, isroot);
            graph.put(Name, newNode);
        } 
    }

    public ArrayList<ArrayList<String>> GetNodesInf(ArrayList<String> datalst){
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        int indexA = 0;
        boolean numbersBlock = false;
        ArrayList<String> temp = new ArrayList<>();
        for(int x=0; x < datalst.size(); x++) {
            if(isNumeric(datalst.get(x)))
                numbersBlock = true;
            else if(numbersBlock){
                numbersBlock = false;
                temp =  new ArrayList<>(datalst.subList(indexA, x));
                list.add((ArrayList<String>) temp);
                indexA = x;
            }
        }
        temp =  new ArrayList<>(datalst.subList(indexA, datalst.size()));
        list.add((ArrayList<String>) temp);
        return list;
    }
    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
}
