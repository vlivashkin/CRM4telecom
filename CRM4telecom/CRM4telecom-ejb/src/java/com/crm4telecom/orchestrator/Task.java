package com.crm4telecom.orchestrator;

import com.crm4telecom.enums.OrderType;
import java.util.HashMap;

public abstract class Task {
    private final String label;
    private HashMap<String, String> parameters;

    public Task(String label) {
        this.label = label;
    }
    
    public HashMap<String,String> getParameters(){
        return parameters;
    }
    
    public void setParameters(HashMap<String,String> parameters){
        this.parameters=parameters;
    }
    
    public Long getProductId(){
        return Long.parseLong(parameters.get("Product"));
    }
    
    public Long getCustomerId(){
        return Long.parseLong(parameters.get("Customer"));
    }
    
    public Long getOrderId(){
        return Long.parseLong(parameters.get("Order"));
    }
    
    public OrderType getOrderType(){
        if(Boolean.parseBoolean(parameters.get("Type"))){
            return OrderType.CONNECT;
        }else{
           return OrderType.DISCONNECT; 
        }
    }
    
    public TaskType getType(){
        return null;
    }

    public boolean run() {
        return true;
    }

    public String getLabel() {
        return label;
    }

}
