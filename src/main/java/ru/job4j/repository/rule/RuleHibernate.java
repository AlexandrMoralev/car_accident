package ru.job4j.repository.rule;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.model.Rule;
import ru.job4j.repository.accident.AccidentStore;

import java.util.Collection;
import java.util.Optional;

@Repository
public class RuleHibernate implements RuleStore {

    private final SessionFactory sessionFactory;

    @Autowired
    public RuleHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Rule> getRule(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from ru.job4j.model.Rule r where r.id =: id", Rule.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst();
        }
    }

    @Override
    public Collection<Rule> getAllRules() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from ru.job4j.model.Rule", Rule.class)
                    .list();
        }
    }

    @Override
    public Collection<Rule> getRules(Collection<Integer> ruleIds) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from ru.job4j.model.Rule where id in (:ids)", Rule.class)
                    .setParameterList("ids", ruleIds)
                    .list();
        }
    }

    @Override
    public Rule addRule(Rule rule) {
        try (Session session = sessionFactory.openSession()) {
            session.saveOrUpdate(rule);
            return rule;
        }
    }

}
