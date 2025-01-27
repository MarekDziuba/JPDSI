package com.jsfcourse.employee;
import java.io.Serializable;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class LazySorter<T> implements Comparator<T>, Serializable {

    private final String sortField;
    private final SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(T o1, T o2) {
        try {
            PropertyDescriptor pd1 = new PropertyDescriptor(sortField, o1.getClass());
            PropertyDescriptor pd2 = new PropertyDescriptor(sortField, o2.getClass());
            Method getter1 = pd1.getReadMethod();
            Method getter2 = pd2.getReadMethod();

            Object value1 = getter1.invoke(o1);
            Object value2 = getter2.invoke(o2);

            Comparable<Object> comp1 = (Comparable<Object>) value1;
            Comparable<Object> comp2 = (Comparable<Object>) value2;

            int result = comp1.compareTo(comp2);

            return SortOrder.DESCENDING.equals(sortOrder) ? -result : result;
        } catch (Exception e) {
            throw new RuntimeException("Error during sorting", e);
        }
    }
}
