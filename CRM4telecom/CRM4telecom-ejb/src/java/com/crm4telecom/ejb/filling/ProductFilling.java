package com.crm4telecom.ejb.filling;

import com.crm4telecom.jpa.Order;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.CustomersProducts;
import com.crm4telecom.jpa.CustomersProductsPK;
import com.crm4telecom.jpa.Product;
import java.util.Date;

@Stateless
public class ProductFilling {

    @PersistenceContext
    private EntityManager em;

    public boolean addProduct(Order order) {
        CustomersProducts customersProduct = new CustomersProducts();
        Date date = new Date();
        customersProduct.setStartDate(date);
        CustomersProductsPK customersProductPK = new CustomersProductsPK();
        customersProductPK.setCustomerId(order.getCustomerId());
        customersProductPK.setProductId(order.getProduct().getProductId());
        customersProduct.setCustomerProductsPK(customersProductPK);
        em.merge(customersProduct);
        em.flush();

        return true;
    }

}
