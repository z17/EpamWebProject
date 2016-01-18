package entity;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private int userId;
    private LocalDateTime time;
    private int price;
    private OrderStatus status;

    public Order(int userId, LocalDateTime time, int price, OrderStatus status) {
        this.userId = userId;
        this.time = time;
        this.price = price;
        this.status = status;
    }

    public Order(int id, int userId, LocalDateTime time, int price, OrderStatus status) {

        this.id = id;
        this.userId = userId;
        this.time = time;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
