package com.example.ibooks;
public class Order {

    private String orderId;
    private String bookId;
    private String ownerId;
    private String price;
    private String name;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    private String requesterId;

    private String area,city;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Order() {
    }

    public Order(String orderId, String bookId, String ownerId, String requesterId, String price,String name) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.ownerId = ownerId;
        this.requesterId = requesterId;
        this.price=price;
        this.name=name;
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", requesterId='" + requesterId + '\'' +
                '}';
    }
}
