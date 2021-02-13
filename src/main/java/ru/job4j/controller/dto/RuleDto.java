package ru.job4j.controller.dto;

import ru.job4j.model.Rule;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class RuleDto implements Serializable {

    private Integer id;
    private String name;

    public RuleDto() {
    }

    private RuleDto(Integer id,
                    String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static RuleDto of(Integer id,
                             String name
    ) {
        return new RuleDto(id, name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RuleDto fromEntity(Rule rule) {
        return new RuleDto(
                rule.getId(),
                rule.getName()
        );
    }

    public Rule toEntity() {
        return Rule.of(
                this.getId(),
                this.getName()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RuleDto)) {
            return false;
        }
        RuleDto that = (RuleDto) o;
        return id.equals(that.id)
                && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RuleDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
