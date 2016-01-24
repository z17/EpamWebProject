package dao;

import java.util.Collection;
import java.util.Map;

public interface InterfaceDao<T> {
    Collection <T> get();
    T getById(int id);
    int create(T item);
    void update(T item);
    void delete(int id);
}
