package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.controller.dto.AccidentDto;
import ru.job4j.controller.dto.AccidentTypeDto;
import ru.job4j.controller.dto.RuleDto;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Controller
public class AccidentController {

    private final AccidentService accidentService;

    // TODO use cache
    private final Map<Integer, AccidentTypeDto> accidentTypes = Map.of(
            1, AccidentTypeDto.of(1, "Cars only"),
            2, AccidentTypeDto.of(2, "Car and a human"),
            3, AccidentTypeDto.of(3, "Car and a bicycle"),
            4, AccidentTypeDto.of(4, "Other")
    );

    private final Map<Integer, RuleDto> rules = Map.of(
            1, RuleDto.of(1, "Rule #1"),
            2, RuleDto.of(2, "Rule #2"),
            3, RuleDto.of(3, "Rule #3"),
            4, RuleDto.of(4, "Rule #4"),
            5, RuleDto.of(5, "Rule #5")
    );

    private final Supplier<Comparator<AccidentTypeDto>> accidentTypesComparator = () -> Comparator.comparing(AccidentTypeDto::getId);
    private final Supplier<Comparator<RuleDto>> ruleComparator = () -> Comparator.comparing(RuleDto::getId);

    @Autowired
    public AccidentController(AccidentService accidentService
    ) {
        this.accidentService = accidentService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(
                "types",
                accidentTypes.values().stream().sorted(accidentTypesComparator.get()).collect(Collectors.toList())
        );
        model.addAttribute(
                "rules",
                rules.values().stream().sorted(ruleComparator.get()).collect(Collectors.toList())
        );
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AccidentDto accident, HttpServletRequest req) {
        Collection<Rule> selectedRules = getRules(req);
        AccidentType accidentType = getAccidentType(accident.getTypeId());
        accidentService.saveAccident(accident.toEntity(accidentType, selectedRules));
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String getAccidentToUpdate(@RequestParam("id") int accidentId, Model model) {
        accidentService.getAccidentWithRules(accidentId).ifPresentOrElse(
                acc -> model.addAttribute("accident", AccidentDto.fromEntity(acc)),
                () -> model.addAttribute("error", "Accident not found.")
        );
        return "accident/edit";
    }


    @PostMapping("/edit")
    public String edit(@ModelAttribute("accident") AccidentDto accident) {
        accidentService.updateAccident(accident.toEntity());
        return "redirect:/";
    }

    private Collection<Rule> getRules(HttpServletRequest req) {
        return ofNullable(req.getParameterValues("rIds"))
                .stream()
                .flatMap(v -> Arrays.stream(v)
                        .map(String::strip)
                        .filter(not(String::isEmpty))
                        .map(Integer::valueOf)
                )
                .map(rules::get)
                .filter(Objects::nonNull)
                .map(RuleDto::toEntity)
                .collect(Collectors.toSet());
    }

    private AccidentType getAccidentType(Integer typeId) {
        return ofNullable(accidentTypes.get(typeId))
                .map(AccidentTypeDto::toEntity)
                .orElse(null);
    }

}