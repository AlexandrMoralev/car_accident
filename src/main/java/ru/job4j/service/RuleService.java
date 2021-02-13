package ru.job4j.service;

import ru.job4j.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleService {

    Rule saveRule(Rule rule);

    Collection<Rule> getAllRules();

    Collection<Rule> getRules(Collection<Integer> ruleIds);

    Optional<Rule> getRule(Integer id);
}
