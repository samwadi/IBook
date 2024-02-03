package com.example.ibooks;

public class ToDeliver {
    private String deliveryId;
    private String bookId;
    private String requesterId;
    private String ownerId;
    private String city;
    private String area;
    private String deliveryPersonId;
    public ToDeliver() {
        // Default constructor required for Firebase
    }

    public ToDeliver(String deliveryId, String bookId, String requesterId, String ownerId, String city, String area,String deliveryPersonId) {
        this.deliveryId = deliveryId;
        this.bookId = bookId;
        this.requesterId = requesterId;
        this.ownerId = ownerId;
        this.city = city;
        this.area = area;
        this.deliveryPersonId=deliveryPersonId;
    }

    public String getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(String deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
