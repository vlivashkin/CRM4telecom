package ejb.beans;

import ejb.jpa.Customers;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Customers> getCustomers() {
       return em.createNamedQuery("Customers.findAll").getResultList();
    }

    @Override
    public void merge(Customers customer) {
        em.merge(customer);
    }

}
