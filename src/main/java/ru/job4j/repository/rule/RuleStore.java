package ru.job4j.repository.rule;

import ru.job4j.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleStore {

    Optional<Rule> getRule(Integer id);

    Collection<Rule> getAllRules();

    Collection<Rule> getRules(Collection<Integer> ruleIds);

    Rule addRule(Rule rule);

}
