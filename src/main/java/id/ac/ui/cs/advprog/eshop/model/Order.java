package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        this.status = "WAITING_PAYMENT";

        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this(id, products, orderTime, author);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (isValidStatus(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean isValidStatus(String status) {
        return status.equals("WAITING_PAYMENT") || status.equals("FAILED") ||
               status.equals("SUCCESS") || status.equals("CANCELLED");
    }
}
