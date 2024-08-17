package org.film.dao.interfaces;

import org.film.entity.Film;

import java.util.List;

public interface FilmDAO {
    void saveFilm(Film film);
    Film getFilm(int id);
    void updateFilm(Film film);
    void deleteFilmById(Film film);
    List<Film> getAllFilms();
}
