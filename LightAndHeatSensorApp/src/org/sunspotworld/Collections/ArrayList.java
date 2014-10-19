/*
 * Very limitied array list implementation
 * allows memebers to be added and removed, also allows for random access.
 * DOES NOT SUPPORT:
 * set(int i, Obj o)
 * 
 */
package org.sunspotworld.Collections;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.RandomAccess;

/**
 *
 * @author Babblebase
 */
public class ArrayList extends AbstractList implements 
    RandomAccess, Cloneable, Serializable 
{
    
    private int numberOfMembers = 0; //keeps track of number of members
    private int space = 0; // keeps track of array size!
    private static final int INNITIAL_SIZE = 10;
    public static final int NOT_FOUND = -1;
    private Object members[];
    
    /**
     * constructs default ArrayList of size 10
     */
    public ArrayList()
    {
        members = new Object[INNITIAL_SIZE];
        space = members.length;
    }
    /**
     * constructs ArrayList of size length
     * @param length 
     */
    public ArrayList(int length)
    {
        members = new Object[length];
        space = members.length;
    }
    /**
     * get element at index 
     * @param i int
     * @return Object Object
     */
    public Object get(int i) {
        if(i>numberOfMembers || i<0){
            throw new IndexOutOfBoundsException
                ("Index: " + i + "Is out of bounds");
        }
        return members[i];
    }
    /**public void add(Object e){
        if(e == null)
            throw new NullPointerException();
        if(size == space)
            increaseSize();
    }*/
    /** METHOD BELOW IS BROKE :(
    @Override
    public void add(int ind, E e) {
        if(ind < 0 || ind > size) {
            throw new ArrayIndexOutOfBoundsException(ind);
        }
        if(e == null) {
            throw new NullPointerException();
        }
        if(size == space) {
            increaseSize();
        }
        //confuse shuffle :p
        for(int i = space; i > ind; i--) {
            members[i] = members[i - 1];
        }
        members[ind] = e;
        size++;    /**public void add(E e){
        if(e == null)
            throw new NullPointerException();
        if(size == space)
            increaseSize();
    }*/
    /** METHOD BELOW IS BROKE :(
    @Override
    public void add(int ind, E e) {
        if(ind < 0 || ind > size) {
            throw new ArrayIndexOutOfBoundsException(ind);
        }
        if(e == null) {
            throw new NullPointerException();
        }
        if(size == space) {
            increaseSize();
        }
        //confuse shuffle :p
        for(int i = space; i > ind; i--) {
            members[i] = members[i - 1];
        }
        members[ind] = e;
        size++;
    }*/
    
    /**
     * removes element at specified position
     * @param member int 
     * @return object E 
     */
    public Object remove(int ind)
    {
       if(ind > numberOfMembers || ind < 0)
           throw new IndexOutOfBoundsException("The index: " + ind
                   + "is out of bounds!");
       Object temp = members[ind];
       for(int i=ind + 1 ; i<numberOfMembers; i++)
           members[i - 1] = members[i];
       numberOfMembers--;
       return (Object)temp;
    }
    /**
     * remove first occurrence of specified Object
     * @param o Object
     * @return boolean
     */
    public boolean remove(Object o)
    {
        int ind = find(o);
        if(ind == NOT_FOUND)
            return false;
        remove(ind);
        return true;    
    }
    /**
     * calls Object.hashcode to check for equality
     * @param o comparing list.
     * @return true if equal false otherwise
     */
    public boolean equals(Object o)
    {
        if(o.hashCode() == this.hashCode())
            return true;
        return false;
    }
    /**
     * finds and returns index of specified object in list.
     * returns -1 if not found
     * @param o Object
     * @return index int
     */
    public int find(Object o){
        if(o == null)
            throw new NullPointerException();
        for(int i=0; i<numberOfMembers; i++)
            if(members[i].equals(o))
                return i;
        return NOT_FOUND;
    }
    /**
     * returns number of members currently in ArrayList
     * @return numberOfMembers int
     */
    public int size()
    {
        return numberOfMembers;
    }
    /**
     * doubles array size.
     */
    private void increaseSize()
    {
        Object[] newArray = new Object[members.length * 2];
        System.arraycopy(this.members, 0, newArray, 0, members.length);
        members = newArray;
    }
}
