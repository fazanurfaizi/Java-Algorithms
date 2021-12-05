/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circlelinkedlist;

/**
 *
 * @author nurfa
 * @param <T>
 */
public class CircleLinkedList<T> {

    private static class Node<T> {
        Node<T> next;
        T value;
        
        private Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
    
    private int size;
    private Node<T> head = null;
    
    // Keeping a tail pointer to keep track of the end of list
    private Node<T> tail = null;
    
    public CircleLinkedList() {
        this.head = new Node<T>(null, this.head);
        this.tail = this.head;
        this.size = 0;
    }
    
    public int getSize() {
        return this.size;
    }
    
    /**
     * for the sake of simplify this class will only contain the append function or addLast other
     * add functions can be implemented however this is the basses of them all really.
     * @param value
     */
    public void append(T value) {
        if(value == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }
        
        if(this.tail == null) {
            this.tail = new Node<T>(value, this.head);
            this.head.next = this.tail;
        } else {
            this.tail.next = new Node<T>(value, this.head);
            this.tail = this.tail.next;
        }
        
        this.size++;
    }
    
    @Override
    public String toString() {
        Node<T> current = this.head.next;
        String str = "[ " + current.value;
        while(current.next != this.head) {
            str += ", " + current.next.value;            
            current = current.next;
        }
        return str + "]";
    }
    
    public T remove(int pos) {
        if(pos > this.size || pos < 0) {
            throw new IndexOutOfBoundsException("position cannot be greater than size or negative");            
        }
        
        Node<T> current = this.head;
        for (int i = 1; i <= pos; i++) {
            current = current.next;
        }
        
        Node<T> destroy = current.next;
        T saved = destroy.value;
        
        current.next = current.next.next;
        
        if(destroy == this.tail) {
            this.tail = current;
        }
        
        destroy = null;
        this.size--;
        
        return saved;
    }
    
    public static void main(String[] args) {
        CircleLinkedList<Integer> list = new CircleLinkedList<Integer>();
        System.out.println("Initial list size: " + list.getSize());
        list.append(12);
        list.append(40);
        list.append(50);
        list.append(10);
        list.remove(2);
        System.out.println(list.toString());
    }
    
}
