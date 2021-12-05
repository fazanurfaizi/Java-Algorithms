/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singlelinkedlist;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.lang.reflect.Array;

class Node<T> {
    T data;
    Node next;

    Node(T data) {
        this.data = data;
    }
}

/**
 *
 * @author nurfa
 * @param <T>
 */
public class SingleLinkedList<T> implements List<T>{                       
    private Node<T> front = null;
    private int size;
    
    private void addFirst(T item) {
        Node<T> newNode = new Node<T>(item);                
        if(this.front == null) {
            this.front = newNode;
        } else {
            newNode.next = this.front;
            this.front = newNode;
        }
        
        this.size++;
    }
    
    private void addLast(T item) {
        Node<T> newNode = new Node<T>(item);
        if(this.front == null) {
            this.front = newNode;
        } else {
            Node<T> curr = this.front;
            while(curr.next != null) {
                curr = curr.next;
            }
            curr.next = newNode;
        }
        
        this.size++;
    }
    
    private T removeFirst() {
        Node<T> curr = this.front;
        this.front = curr.next;
        curr.next = null;
        T value = curr.data;
        curr = null;
        this.size--;
        return value;
    }
    
    private T removeLast() {
        Node<T> curr = this.front;
        Node<T> prev = null;
        while(curr.next != null) {
            prev = curr;
            curr = curr.next;
        }
        T value = curr.data;
        prev.next = null;
        curr = null;
        this.size--;
        return value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.front == null);
    }

    @Override
    public boolean contains(Object o) {
        Node<T> curr = this.front;
        
        while(curr != null) {
            if(curr.data.equals(o)) {
                return true;
            }
            
            curr = curr.next;
        }
        
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> previous = null;                       
            private Node<T> current = null;            
            private Node<T> next = front;            
            
            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            @Override
            public T next() {
                if(!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.previous = this.current;
                this.current = this.next;
                this.next = this.next.next;
                return this.current.data;
            }
            
            @Override
            public void remove() {
                if(this.current == null || this.current.data == null) {
                    throw new IllegalStateException();
                } else {
                    this.previous.next = this.current.next;
                }
                this.current = this.previous;
            }
            
            public void add(Node<T> newNode) {
                if(this.current == null || this.current == this.previous) {
                    throw new IllegalStateException();
                }
                
                newNode.next = this.next;
                this.current.next = newNode;
                if(next == null) {
                    next = newNode;
                }
                if(this.current.next != null) {
                    newNode.next = this.current.next;
                }
                this.next = newNode;
            }
        };
    }       

    @Override
    public Object[] toArray() {
        Object []arr = new Object[this.size];
        int index = 0;
        Node<T> curr = this.front;
        
        while(curr != null) {
            arr[index++] = curr.data;
            curr = curr.next;
        }
        
        return arr;
    }
    
    @Override
    public String toString() {
        if(this.front == null) {
            return "SLL masih kosong";
        }
        
        Node<T> curr = this.front;
        String str = "[" + curr.data;
        while(curr.next != null) {
            curr = curr.next;
            str += ", " + curr.data;
        }
        str += "]";
        return str;
    }

    @Override
    public <T> T[] toArray(T[] list) {
        Node<T> curr = (Node<T>) this.front;
        if(list.length < this.size) {
            list = (T[])Array.newInstance(list.getClass().getComponentType(), this.size);
        }
        
        int index = 0;
        Object[] result = list;
        while(curr != null) {
            result[index++] = curr.data;
            curr = curr.next;
        }

        if(list.length > this.size) {
            list[size] = null;
        }

        return list;
    }

    @Override
    public boolean add(T item) {
        boolean added = false;
        Node<T> newNode = new Node<T>(item);
        
        if(this.front == null) {
            this.front = new Node<T>(item);
            added = true;
        } else {            
            Node<T> curr = this.front;
            while(curr.next != null) {
                curr = curr.next;
            }
            
            curr.next = newNode;
            added = true;
        }
        
        if(added)
            this.size++;
        
        return added;
    }           

    @Override
    public boolean remove(Object o) {
        Node<T> curr = this.front;
        Node<T> prev = null;
        
        while(curr != null) {
            if(curr.data.equals(o)) {
                if(prev != null) {
                    prev.next = curr.next;                    
                }
                this.size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }   
        
        return false;
    }        

    @Override
    public boolean containsAll(Collection<?> list) {
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) {
            if(!this.contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }           

    @Override
    public boolean addAll(Collection<? extends T> list) {
        if(list == null) {
            throw new NullPointerException();
        }
        
        boolean added = false;                
        Object[] o = list.toArray();
        for (Object data : o) {            
            this.add((T) data);
            added = true;
        }
        
        return added;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> list) {
        if(list == null || index < 0) {
            throw new NullPointerException();
        }
        
        boolean added = false;                
        Object[] o = list.toArray();
        for (Object data : o) {            
            this.add(index, (T) data);
            added = true;
        }
        
        return added;
    }

    @Override
    public boolean removeAll(Collection<?> list) {
        boolean modified = false;
        Iterator<T> iterator = (Iterator<T>) list.iterator();
        while(iterator.hasNext()) {
            if(list.contains(iterator.next()) != false) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> list) {
        if(list == null) {
            throw new NullPointerException();
        }
        
        boolean changed = false;
        Node<T> curr = this.front;
        while(curr != null) {
            if(!list.contains(curr.data)) {
                remove(curr.data);
            }
            curr = curr.next;
            changed = true;
        }
        
        return changed;
    }        

    @Override
    public void clear() {
        Node<T> curr = this.front;
        while(curr != null) {
            Node<T> prev = curr;
            curr = curr.next;
            prev = null;
        }
        this.front = null;
        this.size = 0;
    }

    @Override
    public T get(int index) {        
        Node<T>current = this.front;        
        T temp = null;
        int n = 0;
        
        if(index >= this.size) {
            return null;
        }
        
        while(current != null) {
            if(n == index) {
                temp = current.data;
                return temp;
            }
            current = current.next;
            n++;
        }
        
        return temp;
    }

    @Override
    public T set(int index, T item) {
        Node<T>current = this.front;        
        int n = 0;
        
        if(index >= this.size) {
            return null;
        }
        
        while(current != null) {
            if(n == index) {
                current.data = item;
                return item;
            }
            current = current.next;
            n++;
        }
        
        return item;
    }

    @Override
    public void add(int index, T item) {
        Node<T> newNode = new Node<T>(item);
        
        if(index == 0) {
            newNode.next =this.front;
            this.front = newNode;
        } else {
            Node<T> curr = this.front;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.next;
            }
            
            newNode.next = curr.next;
            curr.next = newNode;
        }
        
        this.size++;
    } 

    @Override
    public T remove(int index) {
        Node<T> curr = this.front;
        for (int i = 1; i < index; i++) {
            if(curr.next != null) {
                curr = curr.next;
            }
        }
        curr.next = curr.next.next;
        this.size--;
        return curr.data;
    }

    @Override
    public int indexOf(Object data) {
        Node<T> curr = this.front;
        Node<T> o = (Node<T>) data;
        int i = 0;
        while(curr != null) {
            if(o.data.equals(curr.data)) {
                return i;
            } else {
                curr = curr.next;
                i++;
            }
        }
        
        return -1;
    }

    @Override
    public int lastIndexOf(Object data) {
        Node<T> curr = this.front;
        Node<T> o = (Node<T>) data;
        int i = this.size - 1;
        while(curr != null) {
            if(o.data.equals(curr.data)) {
                return i;
            } else {                
                --i;
                curr = curr.next;
            }
        }
        
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {
            private Node<T> previous = null;                       
            private Node<T> current = null;            
            private Node<T> next = front;       
            private int index = 0;            
            
            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            @Override
            public T next() {
                if(!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.previous = this.current;
                this.current = this.next;
                this.next = this.next.next;                
                this.index++;
                return this.current.data;
            }
            
            @Override
            public void remove() {
                if(this.current == null || this.current.data == null) {
                    throw new IllegalStateException();
                } else {
                    this.previous.next = this.current.next;
                }
                this.current = this.previous;
                this.index--;
            }                        

            @Override
            public boolean hasPrevious() {                        
                return this.previous != null;
            }

            @Override
            public T previous() {
                if(!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                
                this.previous = this.current;
                this.current = this.next;
                this.next = this.next.next;                
                this.index++;
                return this.current.data;
            }

            @Override
            public int nextIndex() {
                return this.index++;
            }

            @Override
            public int previousIndex() {
                return this.index--;
            }

            @Override
            public void set(T item) {
                Node<T>current = this.next;        
                int n = 0;                

                while(current != null) {
                    if(n == index) {
                        current.data = item;                        
                    }
                    current = current.next;
                    n++;
                }
            }

            @Override
            public void add(T item) {
                boolean added = false;
                Node<T> newNode = new Node<T>(item);

                if(this.next == null) {
                    this.next = new Node<T>(item);
                    added = true;
                } else {            
                    Node<T> curr = this.next;
                    while(curr.next != null) {
                        curr = curr.next;
                    }

                    curr.next = newNode;
                    added = true;
                }

                if(added)
                    this.index++;
            }
        };        
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListIterator<T>() {
            private Node<T> previous = null;                       
            private Node<T> current = null;   
            private int cursor = index;
            private Node<T> next = front;                               
            
            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            @Override
            public T next() {
                if(!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.previous = this.current;
                this.current = this.next;
                this.next = this.next.next;                
                this.cursor++;
                return this.current.data;
            }
            
            @Override
            public void remove() {
                if(this.current == null || this.current.data == null) {
                    throw new IllegalStateException();
                } else {
                    this.previous.next = this.current.next;
                }
                this.current = this.previous;
                this.cursor--;
            }                        

            @Override
            public boolean hasPrevious() {                        
                return this.previous != null;
            }

            @Override
            public T previous() {
                if(!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                
                this.previous = this.current;
                this.current = this.next;
                this.next = this.next.next;                
                this.cursor++;
                return this.current.data;
            }

            @Override
            public int nextIndex() {
                return this.cursor++;
            }

            @Override
            public int previousIndex() {
                return this.cursor--;
            }

            @Override
            public void set(T item) {
                Node<T>current = this.next;        
                int n = 0;                

                while(current != null) {
                    if(n == index) {
                        current.data = item;                        
                    }
                    current = current.next;
                    n++;
                }
            }

            @Override
            public void add(T item) {
                boolean added = false;
                Node<T> newNode = new Node<T>(item);

                if(this.next == null) {
                    this.next = new Node<T>(item);
                    added = true;
                } else {            
                    Node<T> curr = this.next;
                    while(curr.next != null) {
                        curr = curr.next;
                    }

                    curr.next = newNode;
                    added = true;
                }

                if(added)
                    this.cursor++;
            }
        };
    }

    @Override
    public List<T> subList(int arg0, int arg1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}