package com.tnecesoc.MyInfoDemo.Entity;

/**
 * Created by Tnecesoc on 2016/11/5.
 * Used to observe a method in a inner class.
 */
public class Container<T> {
    private T o;

    public Container() {}

    public Container(T o) {
        this.o = o;
    }

    public T getValue() {
        return o;
    }

    public void setValue(T o) {
        this.o = o;
    }
}
