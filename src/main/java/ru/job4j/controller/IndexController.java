package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.aop.annotation.ShowExecutionTime;
import ru.job4j.controller.dto.AccidentDto;
import ru.job4j.service.AccidentService;

import java.util.stream.Collectors;

@Controller
public class IndexController {

    private final AccidentService accidentService;

    @Autowired
    public IndexController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @ShowExecutionTime
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute(
                "accidents",
                accidentService.getAllAccidentsWithRules().stream()
                        .map(AccidentDto::fromEntity)
                        .collect(Collectors.toList())
        );
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "index";
    }
}
