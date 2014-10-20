package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.ejb.ProcessingLocal;
import com.crm4telecom.enums.OrderType;
import com.crm4telecom.orchestrator.OrderStatus;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import com.crm4telecom.web.util.JSFHelper;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

@Named
@ViewScoped
public class OrderInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private OrderManagerLocal om;
    @EJB
    private ProcessingLocal pl;

    @Inject
    private OrderValidationBean ov;

    @Inject
    private OrderCommentBean oc;
    
    @Inject
    private OrderSummBean os;

    public OrderSummBean getOs() {
        return os;
    }
    
    public void syncOS() {
        String product = ov.getProduct();
        os.setProduct(product.substring(product.indexOf(' ') + 1));
        os.setOnetimePrice(String.valueOf(ov.getGm().getProduct(ov.getProductId()).getOnetimePayment()));
        os.setMonthlyPrice(String.valueOf(ov.getGm().getProduct(ov.getProductId()).getMonthlyPayment()));
    }
    
    public String getTotalCost() {
        if (os == null) return "—";
        Long installationFee = 0L, onetimePrice = 0L;
        if (os.getInstallationFee() != null && !"—".equals(os.getInstallationFee())) {
            installationFee = Long.parseLong(os.getInstallationFee().replace(".00", ""));
        }
        if (os.getOnetimePrice() != null && !"—".equals(os.getOnetimePrice())) {
            onetimePrice = Long.parseLong(os.getOnetimePrice().replace(".00", ""));
        }
        Long totalCost = installationFee + onetimePrice;
        if (totalCost == 0L) return "—";
        return String.valueOf(totalCost) + ".00";
    }
    
    public String getTotalMonthly() {
        if (os == null ||
                os.getMonthlyPrice() == null || "—".equals(os.getMonthlyPrice())) {
            return "0.00";
        }
        return os.getMonthlyPrice();
    }
    
    private Long id;
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        order = om.getOrder(id);
        ov.init(order);
        oc.init(order);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        ov.init(order);
        oc.init(order);
    }

    public OrderValidationBean getOv() {
        return ov;
    }

    public OrderCommentBean getComment() {
        return oc;
    }

    public String getClosed() {
        if (order != null && OrderStatus.CLOSED.equals(order.getStatus())) {
            return "disabled";
        } else {
            return "";
        }
    }

    public void create() {
        Order orderNew = new Order();
        ov.fillOrder(orderNew);
        om.createOrder(orderNew);
        order = orderNew;
        
        JSFHelper helper = new JSFHelper();
        helper.redirect("order_info", "id", order.getOrderId().toString());
    }

    public void modify() {
        ov.fillOrder(order);
        om.modifyOrder(order);
        
        JSFHelper helper = new JSFHelper();
        helper.redirect("order_info", "id", order.getOrderId().toString());
    }

    public void toCustomer() {
        JSFHelper helper = new JSFHelper();
        helper.redirect("customer_info", "id", order.getCustomer().getCustomerId().toString());
    }
        
    public List<String> completeOrder(String order) {
        return pl.completeOrder(order);
    }

    public List<OrderProcessing> getOrderSteps() {
        if (order != null) {
            return pl.getOrderSteps(order);
        }
        return Collections.EMPTY_LIST;
    }

    public void nextStep() {
        pl.tryNextStep(order);
    }

    public void cancelOrder() {
        pl.cancelOrder(order);
    }
    
    public Boolean testOrderType() {
        return ov.getType() == OrderType.CONNECT;
    }
}