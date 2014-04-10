package com.crm4telecom.web.beans;

import org.primefaces.model.LazyDataModel;

public interface IListBean<T> {

    public void init();

    public LazyDataModel<T> getLazyModel();

    public T getSelected();

    public void setSelected(T selected);

    public void onRowSelect();
}
