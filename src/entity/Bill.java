package entity;

/**
 * Счет
 */
public class Bill {
    private int id;
    private int orderId;
    private boolean paid;
    private int sum;

    public Bill(int id, int orderId, boolean paid, int sum) {
        this.id = id;
        this.orderId = orderId;
        this.paid = paid;
        this.sum = sum;
    }

    public Bill(int orderId, boolean paid, int sum) {
        this.orderId = orderId;
        this.paid = paid;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public boolean isPaid() {
        return paid;
    }

    public int getSum() {
        return sum;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
