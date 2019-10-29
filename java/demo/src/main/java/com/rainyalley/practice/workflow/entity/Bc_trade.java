package com.rainyalley.practice.workflow.entity;

public class Bc_trade {

    private String id;
    private String type;
    private String product_id;
    private String pay_frozen_id;
    private String user_id;
    private String trade_num;
    private String frozen_num_bak;
    private String processed_num;
    private String price;
    private String total_amount;
    private String status;
    private String create_time;
    private String update_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPay_frozen_id() {
        return pay_frozen_id;
    }

    public void setPay_frozen_id(String pay_frozen_id) {
        this.pay_frozen_id = pay_frozen_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTrade_num() {
        return trade_num;
    }

    public void setTrade_num(String trade_num) {
        this.trade_num = trade_num;
    }

    public String getFrozen_num_bak() {
        return frozen_num_bak;
    }

    public void setFrozen_num_bak(String frozen_num_bak) {
        this.frozen_num_bak = frozen_num_bak;
    }

    public String getProcessed_num() {
        return processed_num;
    }

    public void setProcessed_num(String processed_num) {
        this.processed_num = processed_num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
