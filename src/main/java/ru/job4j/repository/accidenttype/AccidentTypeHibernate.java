package ru.job4j.repository.accidenttype;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Repository
public class AccidentTypeHibernate implements AccidentTypeStore {

    private final SessionFactory sessionFactory;

    @Autowired
    public AccidentTypeHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<AccidentType> getAccidentType(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from ru.job4j.model.AccidentType r where r.id =: id", AccidentType.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst();
        }
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from ru.job4j.model.AccidentType", AccidentType.class)
                    .list();
        }
    }

    @Override
    public AccidentType addAccidentType(AccidentType accidentType) {
        try (Session session = sessionFactory.openSession()) {
            session.saveOrUpdate(accidentType);
            return accidentType;
        }
    }

}
