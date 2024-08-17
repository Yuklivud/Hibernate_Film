package org.film.dao.interfaces;

import org.film.entity.Store;

import java.util.List;

public interface StoreDAO {
    public void saveStoreById(Store store);
    public void updateStoreById(Store store);
    public void deleteStoreById(Store store);
    public Store getStoreById(int id);
    public List<Store> getAllStores();
}
