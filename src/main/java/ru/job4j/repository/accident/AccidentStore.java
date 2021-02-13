package ru.job4j.repository.accident;

import ru.job4j.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentStore {

    Optional<Accident> getAccident(Integer id);

    Optional<Accident> getAccidentWithRules(Integer id);

    Collection<Accident> getAllAccidents();

    Collection<Accident> getAllAccidentsWithRules();

    Accident addAccident(Accident accident);

    void updateAccident(Accident updatedAccident);

    void removeAccident(Integer id);
}
