package ru.job4j.service;

import ru.job4j.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

public interface AccidentTypeService {

    AccidentType saveAccidentType(AccidentType accidentType);

    Collection<AccidentType> getAllAccidentTypes();

    Optional<AccidentType> getAccidentType(Integer id);
}
