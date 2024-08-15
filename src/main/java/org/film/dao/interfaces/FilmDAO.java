package org.film.dao.interfaces;

import org.film.entity.Film;

public interface FilmDAO {
    public void saveFilm(Film film);
    public Film getFilm(int id);
    public void updateFilm(Film film);
    public void deleteFilmById(Film film);
}
