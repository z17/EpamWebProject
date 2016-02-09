package dao;

import java.util.List;

public interface InterfaceDao<T> {
    List<T> get();
    T getById(final int id);
    T create(final T item);
    void update(final T item);
    void delete(final int id);
}
