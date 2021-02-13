package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.accident.AccidentStore;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccidentServiceImpl implements AccidentService {

    private final AccidentStore store;

    @Autowired
    public AccidentServiceImpl(@Qualifier("accidentHibernate")AccidentStore store) {
        this.store = store;
    }

    @Override
    public Accident saveAccident(Accident accident) {
        return store.addAccident(accident);
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return store.getAllAccidents();
    }

    @Override
    public Collection<Accident> getAllAccidentsWithRules() {
        return store.getAllAccidentsWithRules();
    }

    @Override
    public Optional<Accident> getAccident(Integer id) {
        return store.getAccident(id);
    }

    @Override
    public Optional<Accident> getAccidentWithRules(Integer id) {
        return store.getAccidentWithRules(id);
    }


    @Override
    public void updateAccident(Accident updatedAccident) {
        store.updateAccident(updatedAccident);
    }

    @Override
    public void removeAccident(Integer id) {
        store.removeAccident(id);
    }

}
