package org.film.dao.interfaces;

import org.film.entity.Store;

import java.util.List;

public interface StoreDAO {
    void saveStoreById(Store store);
    void updateStoreById(Store store);
    void deleteStoreById(Store store);
    Store getStoreById(int id);
    List<Store> getAllStores();
}
