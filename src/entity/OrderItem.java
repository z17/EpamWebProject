package entity;

/**
 * Отношение между заказами и элементами меню
 */
public class OrderItem {
    private int id;
    private int idOrder;
    private int idItem;
    private int count;

    public OrderItem(int id, int idOrder, int idItem, int count) {
        this.id = id;
        this.idOrder = idOrder;
        this.idItem = idItem;
        this.count = count;
    }

    public OrderItem(int idOrder, int idItem, int count) {
        this.idOrder = idOrder;
        this.idItem = idItem;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public int getIdItem() {
        return idItem;
    }

    public int getCount() {
        return count;
    }
}
