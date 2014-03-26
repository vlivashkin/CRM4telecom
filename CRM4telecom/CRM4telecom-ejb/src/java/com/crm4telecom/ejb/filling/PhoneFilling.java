package com.crm4telecom.ejb.filling;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.PhoneNumber;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class PhoneFilling extends FillingDatabase implements PhoneFillingLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected void getDataAndFill(Customer customer) {
        String sqlQuery = "SELECT i FROM PhoneNumber i WHERE i.customerId IS NULL ";
        Query query = em.createQuery(sqlQuery);
        List<PhoneNumber> phoneList = query.getResultList();
        if (phoneList.size() > 0) {
            PhoneNumber phone = phoneList.get(0);
            phone.setCustomerId(customer);
            em.persist(phone);
        }
    }
}
