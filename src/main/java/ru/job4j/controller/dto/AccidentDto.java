package ru.job4j.controller.dto;

import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class AccidentDto implements Serializable {

    private Integer id;
    private String name;
    private String text;
    private String address;
    private Integer typeId;
    private String typeName;
    private Collection<Integer> ruleIds;
    private String ruleNames;

    public AccidentDto() {
    }

    public AccidentDto(Integer id,
                       String name,
                       String text,
                       String address,
                       Integer typeId,
                       String typeName,
                       Collection<Integer> ruleIds,
                       String ruleNames
    ) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.address = address;
        this.typeId = typeId;
        this.typeName = typeName;
        this.ruleIds = ruleIds;
        this.ruleNames = ruleNames;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getAddress() {
        return address;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public Collection<Integer> getRuleIds() {
        return ruleIds;
    }

    public String getRuleNames() {
        return ruleNames;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setRuleIds(Collection<Integer> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public void setRuleNames(String ruleNames) {
        this.ruleNames = ruleNames;
    }

    public Accident toEntity(AccidentType type, Collection<Rule> rules) {
        Accident.Builder entity = Accident.newBuilder();
        ofNullable(this.id).ifPresent(entity::setId);
        ofNullable(this.name).ifPresent(entity::setName);
        ofNullable(this.text).ifPresent(entity::setText);
        ofNullable(this.address).ifPresent(entity::setAddress);
        ofNullable(type).ifPresent(entity::setType);
        entity.setRules(rules);
        return entity.build();
    }

    public Accident toEntity() {
        return this.toEntity(null, Collections.emptyList());
    }

    public static AccidentDto fromEntity(Accident accident) {
        return new AccidentDto(
                accident.getId(),
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                ofNullable(accident.getType()).map(AccidentType::getId).orElse(null),
                ofNullable(accident.getType()).map(AccidentType::getName).orElse(null),
                accident.getRules().stream().map(Rule::getId).collect(Collectors.toList()),
                accident.getRules().stream().map(Rule::getName).collect(Collectors.joining(", "))
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccidentDto)) {
            return false;
        }
        AccidentDto accident = (AccidentDto) o;
        return Objects.equals(getId(), accident.getId())
                && Objects.equals(getName(), accident.getName())
                && Objects.equals(getText(), accident.getText())
                && Objects.equals(getAddress(), accident.getAddress())
                && Objects.equals(getTypeId(), accident.getTypeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getText(), getAddress(), getTypeId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccidentDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
