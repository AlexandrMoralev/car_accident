package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

import java.util.Collection;
import java.util.Optional;

@Repository("accidentHibernateStore")
public class AccidentHibernate implements AccidentStore {

    private final SessionFactory sessionFactory;

    public AccidentHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Accident> getAccident(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from ru.job4j.model.Accident acc where acc.id =: id", Accident.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst();
        }
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from ru.job4j.model.Accident", Accident.class)
                    .list();
        }
    }

    @Override
    public Accident addAccident(Accident accident) {
        try (Session session = sessionFactory.openSession()) {
            session.save(accident);
            return accident;
        }
    }

    @Override
    public void updateAccident(Accident updatedAccident) {
        try (Session session = sessionFactory.openSession()) {
            session.update(updatedAccident);
        }
    }

    @Override
    public void removeAccident(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.createQuery("delete from ru.job4j.model.Accident acc where acc.id =: id", Accident.class)
                    .setParameter("id", id)
                    .executeUpdate();
        }
    }
}
