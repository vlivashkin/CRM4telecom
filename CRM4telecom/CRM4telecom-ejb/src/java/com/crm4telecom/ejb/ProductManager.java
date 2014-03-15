

package com.crm4telecom.ejb;

import com.crm4telecom.enums.ProductsName;
import com.crm4telecom.jpa.Product;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ProductManager implements ProductManagerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Product getProduct(Long id){
        String sqlQuery = "SELECT u FROM Product u WHERE u.productId = :productId";
         Query query = em.createQuery(sqlQuery).setParameter("productId",id);
        return (Product)query.getResultList().get(0);
    }
    
    @Override
    public Long getProductId(ProductsName productName) {
        Product p = new Product();
        String sqlQuery = "SELECT u.productId FROM Product u WHERE u.name = :name";
         Query query = em.createQuery(sqlQuery).setParameter("name", productName);
        return (Long)query.getResultList().get(0);
    }
    
}
