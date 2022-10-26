package com.example.itis5280_project8.apicalls;

import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {
    String _id, name, photo, region;
    Double price;
    Integer discount;

    public Item() {
        // empty
    }

    public Item(String name, String photo, Double price, Integer discount) {
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Double getDiscountedPrice() {
        double discountedPrice = price - (price * ((double)discount / 100));
        return Math.round(discountedPrice * 100.0) / 100.0;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return _id.equals(item._id) && name.equals(item.name) && photo.equals(item.photo) && region.equals(item.region) && price.equals(item.price) && discount.equals(item.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name, photo, region, price, discount);
    }
}
