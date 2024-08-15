package org.film.dao.interfaces;

import org.film.entity.Store;

public interface StoreDAO {
    public void saveStoreById(Store store);
    public void updateStoreById(Store store);
    public void deleteStoreById(Store store);
    public Store getStoreById(int id);
}
