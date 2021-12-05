/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cursorlinkedlist;

/**
 *
 * @author nurfa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CursorLinkedList<Integer> list = new CursorLinkedList<>();
        list.append(12);
        list.append(32);
        list.append(42);
        list.append(52);
        System.out.println(list.get(0));
        list.remove(12);
        list.removeByIndex(0);
        System.out.println(list.toString());
        System.out.println(list.indexOf(42));
    }
    
}
