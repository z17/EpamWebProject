package entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int id;
    private int userId;
    private LocalDateTime time;
    private int price;
    private OrderStatus status;
    private Map<Item, Integer> items;

    public Order(int userId, LocalDateTime time, int price, OrderStatus status) {
        this.userId = userId;
        this.time = time;
        this.price = price;
        this.status = status;
        this.items = new HashMap<>();
    }

    public Order(int id, int userId, LocalDateTime time, int price, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.price = price;
        this.status = status;
        this.items = new HashMap<>();
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

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    public void addItem(Integer count, Item item) {
        items.put(item, count);
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
