package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Rule;
import ru.job4j.repository.rule.RuleStore;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {

    private final RuleStore store;

    public RuleServiceImpl(RuleStore store) {
        this.store = store;
    }

    @Override
    public Rule saveRule(Rule rule) {
        return this.store.addRule(rule);
    }

    @Override
    public Collection<Rule> getAllRules() {
        return this.store.getAllRules();
    }

    @Override
    public Collection<Rule> getRules(Collection<Integer> ruleIds) {
        if (ruleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return this.store.getRules(ruleIds);
    }

    @Override
    public Optional<Rule> getRule(Integer id) {
        return this.store.getRule(id);
    }
}
