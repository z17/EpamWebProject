package dao;

import java.util.Collection;
import java.util.Map;

public interface InterfaceDao<T> {
    Collection <T> get();
    T getById(final int id);
    int create(final T item);
    void update(final T item);
    void delete(final int id);
}
