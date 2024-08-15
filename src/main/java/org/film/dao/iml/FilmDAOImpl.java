package org.film.dao.iml;

import org.film.dao.interfaces.FilmDAO;
import org.film.entity.Film;
import org.film.entity.Language;
import org.film.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class FilmDAOImpl implements FilmDAO {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override
    public void saveFilm(Film film) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(film);
            tx.commit();
        } catch (Throwable e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void addNewFilm(Map<String, String> parameters) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Short languageId = Short.parseShort(parameters.get("language"));
            Short originalLanguageId = Short.parseShort(parameters.get("originalLanguage"));

            Language language = session.get(Language.class, languageId);
            Language originalLanguage = session.get(Language.class, originalLanguageId);

            if (language == null || originalLanguage == null) {
                throw new IllegalArgumentException("Invalid language or original language ID");
            }

            Film film = new Film();
            film.setTitle(parameters.get("title"));
            film.setDescription(parameters.get("description"));
            film.setLength(Integer.parseInt(parameters.get("length")));
            film.setReleaseYear(Integer.parseInt(parameters.get("releaseYear")));
            film.setLanguage(language);
            film.setOriginalLanguage(originalLanguage);
            film.setRentalRate(BigDecimal.valueOf(Double.parseDouble(parameters.get("rentalRate"))));
            film.setRentalDuration(Short.parseShort(parameters.get("rentalDuration")));
            film.setReplacementCost(BigDecimal.valueOf(Double.parseDouble(parameters.get("replacementCost"))));
            film.setSpecialFeatures(parameters.get("specialFeatures"));
            film.setLastUpdate(Instant.now());

            session.save(film);
            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Throwable rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        }
    }


    @Override
    public Film getFilm(int id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Film.class, id);
        }
    }

    @Override
    public void updateFilm(Film film) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.update(film);
            tx.commit();
        } catch (Throwable e){
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFilmById(Film film) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.delete(film);
            tx.commit();
        } catch (Throwable e){
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
