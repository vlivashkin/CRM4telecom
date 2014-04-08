package com.crm4telecom.web.beans;

import javax.annotation.PostConstruct;
import org.primefaces.model.LazyDataModel;

public interface ISearchBean<T> {

    public void init();

    public LazyDataModel<T> getLazyModel();

    public T getSelected();

    public void setSelected(T selected);

    public void onRowSelect();
}
