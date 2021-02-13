package ru.job4j.repository.accident;

import ru.job4j.model.Accident;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Optional.ofNullable;

//@Repository("accidentMem") // disabled to switch on hibernate store
public class AccidentMem implements AccidentStore {

    private final AtomicInteger index;
    private final Map<Integer, Accident> store;

    public AccidentMem() {
        this.index = new AtomicInteger(1);
        this.store = new HashMap<>();
    }

    @Override
    public Optional<Accident> getAccident(Integer id) {
        return ofNullable(this.store.get(id));
    }

    @Override
    public Optional<Accident> getAccidentWithRules(Integer id) {
        return this.getAccident(id);
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return new ArrayList<>(this.store.values());
    }

    @Override
    public Collection<Accident> getAllAccidentsWithRules() {
        return this.getAllAccidents();
    }

    @Override
    public Accident addAccident(Accident accident) {
        if (accident.getId() == null) {
            Accident acc = Accident.of(accident).setId(index.getAndIncrement()).build();
            return this.store.put(acc.getId(), acc);
        }
        return null;
    }

    @Override
    public void updateAccident(Accident updatedAccident) {
        this.store.replace(updatedAccident.getId(), updatedAccident);
    }

    @Override
    public void removeAccident(Integer id) {
        this.store.remove(id);
    }
}
