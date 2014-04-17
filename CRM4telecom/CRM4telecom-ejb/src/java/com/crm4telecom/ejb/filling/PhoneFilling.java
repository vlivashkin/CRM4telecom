package com.crm4telecom.ejb.filling;

import com.crm4telecom.enums.IpStatus;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.PhoneNumber;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

@Stateless
public class PhoneFilling extends FillingDatabase implements PhoneFillingLocal {

    private transient final Logger log = Logger.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    protected void getDataAndAlloc(Customer customer) {
        String sqlQuery = "SELECT i FROM PhoneNumber i WHERE i.customerId IS NULL";
        Query query = em.createQuery(sqlQuery);
        List<PhoneNumber> phoneList = query.getResultList();
        if (phoneList.size() > 0) {
            PhoneNumber phoneNumber = phoneList.get(0);
            phoneNumber.setCustomerId(customer);
            phoneNumber.setStatus(IpStatus.ACTIVE);
            em.persist(phoneNumber);

            if (log.isInfoEnabled()) {
                log.info("Customer : " + customer + " now get phone number : " + phoneNumber.getPhoneNumber());
            }
        } else {
            if (log.isEnabledFor(Priority.WARN)) {
                log.warn("All phone numbers is locked, so customer : " + customer + " can't get new phone number");
            }
        }
    }

    @Override
    protected void getDataAndFree(Customer customer) {
        String sqlQuery = "SELECT i FROM PhoneNumber i WHERE i.customerId = :customer";
        Query query = em.createQuery(sqlQuery).setParameter("customer", customer);
        List<PhoneNumber> ipList = query.getResultList();
        if (ipList.size() > 0) {
            PhoneNumber phoneNumber = ipList.get(0);
            phoneNumber.setCustomerId(null);
            phoneNumber.setStatus(IpStatus.UNPLUGGED);
            em.merge(phoneNumber);

            if (log.isInfoEnabled()) {
                log.info("Customer : " + customer + " now get out phone number: " + phoneNumber.getPhoneNumber());
            }
        } else {
            if (log.isEnabledFor(Priority.WARN)) {
                log.warn("No phone numbers of " + customer);
            }
        }
    }
}
