package entity;

public class Item {
    private int id;
    private String name;
    private boolean inStock;
    private int price;
    private String description;
    private String image;

    public Item(int id, String name, boolean inStock, int price, String description, String image) {
        this.id = id;
        this.name = name;
        this.inStock = inStock;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isInStock() {
        return inStock;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
