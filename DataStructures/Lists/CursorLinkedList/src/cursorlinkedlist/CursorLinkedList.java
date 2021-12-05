/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cursorlinkedlist;

import java.util.Objects;

/**
 *
 * @author nurfa
 */
public class CursorLinkedList<T> {

    private static class Node<T> {
        private T element;
        private int next;
        
        Node(T element, int next) {
            this.element = element;
            this.next = next;
        }
    }
    
    private final int cursor;
    private int head;
    private final Node<T>[] cursorSpace;
    private int count;
    private static final int CURSOR_SPACE_SIZE = 100;
    
    {
        this.cursorSpace = new Node[CURSOR_SPACE_SIZE];
        for (int i = 0; i < CURSOR_SPACE_SIZE; i++) {
            this.cursorSpace[i] = new Node<T>(null, i + 1);
        }
        this.cursorSpace[CURSOR_SPACE_SIZE - 1].next = 0;
    }
    
    public CursorLinkedList() {
        this.cursor = 0;
        this.count = 0;
        this.head = -1;
    }
    
    public void append(T element) {
        Objects.requireNonNull(element);
        int availableIndex = this.alloc();
        this.cursorSpace[availableIndex].element = element;
        
        if(this.head == -1) {
            this.head = availableIndex;
        }
        
        int iterator = this.head;
        while(this.cursorSpace[iterator].next != -1) {
            iterator = this.cursorSpace[iterator].next;
        }
        
        this.cursorSpace[iterator].next = availableIndex;
        this.cursorSpace[availableIndex].next = -1;
        
        this.count++;
    }
    
    /**
     * @param pos , the logical index of the element , not the actual one
     * within the [cursorSpace] array . this method should be used to get the
     * index give by indexOf() method.
     * @return T
     */
    public T get(int pos) {
        if(pos >= 0 && pos < this.count) {
            int start = this.head;
            int counter = 0;
            while(start != -1) {
                T element = this.cursorSpace[start].element;
                if(counter == pos) {
                    return element;
                }
                
                start = cursorSpace[start].next;
                counter++;
            }
        }
        
        return null;
    }
    
    /**
     * @param element
     * @return the logical index of the element within the list , not the actual
     * index of the [cursorSpace] array
     */
    public int indexOf(T element) {
        Objects.requireNonNull(element);
        Node<T> iterator = this.cursorSpace[this.head];
        for (int i = 0; i < this.count; i++) {
            if(iterator.element.equals(element)) {
                return i;
            }
            iterator = this.cursorSpace[iterator.next];
        }
        
        return -1;
    }
    
    @Override
    public String toString() {
        String str = "[ ";
        if(this.head != -1) {
            str += this.cursorSpace[this.head].element;
            int start = this.head + 1;
            while(start != -1) {
                T element = this.cursorSpace[start].element;
                str += ", " + element.toString();
                start = this.cursorSpace[start].next;
            }
        }
        str += " ]";
        
        return str;
    }
    
    public void remove(T element) {
        Objects.requireNonNull(element);
        
        // case element is in the head
        T temp = this.cursorSpace[this.head].element;
        int next = this.cursorSpace[this.head].next;
        if(temp.equals(element)) {
            this.free(this.head);
            this.head = next;
        } else {
            int prev = this.head;
            int current = this.cursorSpace[prev].next;
            
            while(current != -1) {
                T currentElement = this.cursorSpace[current].element;
                if(currentElement.equals(element)) {
                    this.cursorSpace[prev].next = this.cursorSpace[current].next;
                    this.free(current);
                    break;
                }
                
                prev = current;
                current = this.cursorSpace[prev].next;
            }
        }
        
        this.count--;
    }
    
    public void removeByIndex(int index) {
        if(index >= 0 && index < this.count) {
            T element = this.get(index);
            this.remove(element);
        }
    }
     
    /**
     * @return the index of the next available node
     */
    private int alloc() {
        // 1. get the index at which the cursor is pointing
        int availableIndex = this.cursorSpace[this.cursor].next;
        
        if(availableIndex == 0) {
            throw new OutOfMemoryError();
        }
        
        // 2. make the cursor point to the next of the @var{availableIndex}
        int availableNext = this.cursorSpace[availableIndex].next;
        this.cursorSpace[this.cursor].next = availableNext;
        
        // this to indicate an end of the list , helpful at testing since any err
        // would throw an outOfBoundException
        this.cursorSpace[availableIndex].next = -1;
        
        return availableIndex;
    }
    
    private void free(int index) {
        Node<T> current = this.cursorSpace[this.cursor];        
        this.cursorSpace[this.cursor].next = index;
        this.cursorSpace[index].element = null;
        this.cursorSpace[index].next = current.next;
    }
}
