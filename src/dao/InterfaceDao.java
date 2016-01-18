package dao;

import java.util.ArrayList;

public interface InterfaceDao<T> {
    ArrayList<T> get();
    T getById(int id);
    int create(T item);
    void update(T item);
    void delete(int id);
}
