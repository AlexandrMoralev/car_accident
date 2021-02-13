package ru.job4j.repository.accident;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.model.Accident;

import java.util.Collection;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository("accidentHibernate")
@Transactional
public class AccidentHibernate implements AccidentStore {

    private final SessionFactory sessionFactory;

    @Autowired
    public AccidentHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Accident> getAccident(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("select distinct acc from ru.job4j.model.Accident acc where acc.id =: id", Accident.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst();
        }
    }

    @Override
    public Optional<Accident> getAccidentWithRules(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("select distinct acc from ru.job4j.model.Accident acc left join fetch acc.rules where acc.id =: id", Accident.class)

                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst();
        }
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("select distinct acc from ru.job4j.model.Accident", Accident.class)
                    .list();
        }
    }

    @Override
    public Collection<Accident> getAllAccidentsWithRules() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("select distinct acc from ru.job4j.model.Accident acc left join fetch acc.rules", Accident.class)
                    .list();
        }
    }

    @Override
    public Accident addAccident(Accident accident) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            accident.getRules().forEach(session::save);
            ofNullable(accident.getType()).ifPresent(session::save);
            session.save(accident);
            session.getTransaction().commit();
            return accident;
        }
    }

    @Override
    public void updateAccident(Accident updatedAccident) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Accident accident = session.load(Accident.class, updatedAccident.getId());
            accident.setName(updatedAccident.getName());
            accident.setText(updatedAccident.getText());
            accident.setAddress(updatedAccident.getAddress());
            session.persist(accident);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeAccident(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from ru.job4j.model.Accident acc where acc.id =: id", Accident.class)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }
}
