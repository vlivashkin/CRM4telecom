
package com.crm4telecom.ejb;

import com.crm4telecom.enums.ProductsName;
import com.crm4telecom.jpa.Product;
import javax.ejb.Local;

@Local
public interface ProductManagerLocal {
    Long getProductId(ProductsName productName);
    Product getProduct(Long id);
}
