package com.jsfcourse.employee;
import jakarta.faces.context.FacesContext;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.jsf.entities.UserData;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class LazyUserDataModel extends LazyDataModel<UserData> {

    private final List<UserData> datasource;

    public LazyUserDataModel(List<UserData> datasource) {
        this.datasource = datasource;
    }

    @Override
    public UserData getRowData(String rowKey) {
        return datasource.stream()
                .filter(user -> user.getId_user() == Integer.parseInt(rowKey))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getRowKey(UserData user) {
        return String.valueOf(user.getId_user());
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) datasource.stream()
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .count();
    }

    @Override
    public List<UserData> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        List<UserData> filteredData = datasource.stream()
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .collect(Collectors.toList());

        if (sortBy != null && !sortBy.isEmpty()) {
            filteredData.sort(Comparator.comparing(UserData::getUsername));
        }

        return filteredData.subList(first, Math.min(first + pageSize, filteredData.size()));
    }

    private boolean filter(FacesContext context, Collection<FilterMeta> filterBy, UserData user) {
        return true;
    }
}