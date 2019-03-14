package com.foreseers.chat.bean;

public class ShopBean {

    /**
     * productId : com.foreseers.chat.vip30
     * type : inapp
     * price : HK$79.90
     * price_amount_micros : 79900000
     * price_currency_code : HKD
     * title : VIP30天 (注定)
     * description : VIP30天
     */

    private String productId;
    private String type;
    private String price;
    private int price_amount_micros;
    private String price_currency_code;
    private String title;
    private String description;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPrice_amount_micros() {
        return price_amount_micros;
    }

    public void setPrice_amount_micros(int price_amount_micros) {
        this.price_amount_micros = price_amount_micros;
    }

    public String getPrice_currency_code() {
        return price_currency_code;
    }

    public void setPrice_currency_code(String price_currency_code) {
        this.price_currency_code = price_currency_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
