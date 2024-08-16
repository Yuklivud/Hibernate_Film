package org.film;

import org.film.dao.iml.FilmDAOImpl;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, String> objectProperties = new HashMap<String, String>();
        objectProperties.put("title", "myTitle");
        objectProperties.put("description", "some description");
        objectProperties.put("length", "30");
        objectProperties.put("releaseYear", "2020");
        objectProperties.put("language", "1");
        objectProperties.put("originalLanguage", "1");
        objectProperties.put("rentalRate", "9.5");
        objectProperties.put("rentalDuration", "5");
        objectProperties.put("replacementCost", "50.0");
        objectProperties.put("specialFeatures", "Trailers");
        FilmDAOImpl filmDAO = new FilmDAOImpl();
        filmDAO.addNewFilm(objectProperties);
    }
}