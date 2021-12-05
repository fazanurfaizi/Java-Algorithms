/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singlelinkedlist;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author nurfa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SingleLinkedList<String> list = new SingleLinkedList<>();
        System.out.println("SSL isEmpty ? : " + list.isEmpty());
        System.out.println("Size SSL : " + list.size());
        list.add("Merah");   
        list.add("Kuning");
        list.add(0, "Ungu");
        list.add("Merah");
        list.add("Biru");
        System.out.println("List 1 : " + list.toString());
        System.out.println("Index 1 : " + list.get(1));
        list.set(0, "Ungu 2");
        System.out.println("List 2 : " + list.toString());
        System.out.println("Size SSL : " + list.size());
    }        
    
}
