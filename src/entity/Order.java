package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private int id;
    private int userId;
    private LocalDateTime time;
    private int price;
    private OrderStatus status;
    private ArrayList<Item> items;

    public Order(int userId, LocalDateTime time, int price, OrderStatus status) {
        this.userId = userId;
        this.time = time;
        this.price = price;
        this.status = status;
        this.items = new ArrayList<>();
    }

    public Order(int id, int userId, LocalDateTime time, int price, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.price = price;
        this.status = status;
        this.items = new ArrayList<>();
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

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
