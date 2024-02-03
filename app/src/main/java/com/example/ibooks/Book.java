package com.example.ibooks;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Book implements Parcelable {
    private String name;
    private String pictureUrl;
    private String description;
    private double price;
    private String faculty;
    private String ownerName;
    private String ownerEmail;
    private int numberOfPages;

    public Book() {
        // Default constructor required for calls to DataSnapshot.getValue(Book.class)
    }

    protected Book(Parcel in) {
        name = in.readString();
        pictureUrl = in.readString();
        description = in.readString();
        price = in.readDouble();
        faculty = in.readString();
        numberOfPages = in.readInt();
        ownerName=in.readString();
        ownerEmail=in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Book(String name, String pictureUrl, String description, double price, String faculty, int numberOfPages,String ownerName,String ownerEmail) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.description = description;
        this.price = price;
        this.faculty = faculty;
        this.numberOfPages = numberOfPages;
        this.ownerName=ownerName;
        this.ownerEmail=ownerEmail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(pictureUrl);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeString(faculty);
        dest.writeInt(numberOfPages);
        dest.writeString(ownerName);
        dest.writeString(ownerEmail);
    }
// Constructors, getters, setters...
}
