package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.accidenttype.AccidentTypeStore;
import ru.job4j.repository.rule.RuleStore;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccidentTypeServiceImpl implements AccidentTypeService {

    private final AccidentTypeStore store;

    public AccidentTypeServiceImpl(AccidentTypeStore store) {
        this.store = store;
    }

    @Override
    public AccidentType saveAccidentType(AccidentType rule) {
        return this.store.addAccidentType(rule);
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return this.store.getAllAccidentTypes();
    }

    @Override
    public Optional<AccidentType> getAccidentType(Integer id) {
        return this.store.getAccidentType(id);
    }
}
