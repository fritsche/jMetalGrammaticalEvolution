package org.uma.jmetal.designpatterns;

public interface Memento<T> {
    
    public void restore(T originator);
    
    public void setState(T originator);
}
