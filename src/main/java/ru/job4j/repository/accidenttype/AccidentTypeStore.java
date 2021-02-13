package ru.job4j.repository.accidenttype;

import ru.job4j.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

public interface AccidentTypeStore {

    Optional<AccidentType> getAccidentType(Integer id);

    Collection<AccidentType> getAllAccidentTypes();

    AccidentType addAccidentType(AccidentType accidentType);
}
