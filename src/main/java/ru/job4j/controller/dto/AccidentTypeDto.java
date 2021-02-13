package ru.job4j.controller.dto;

import ru.job4j.model.AccidentType;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class AccidentTypeDto implements Serializable {

    private Integer id;
    private String name;

    public AccidentTypeDto() {
    }

    private AccidentTypeDto(Integer id,
                            String name
    ) {
        this.id = id;
        this.name = name;
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

    public static AccidentTypeDto of(Integer id,
                                     String name
    ) {
        return new AccidentTypeDto(id, name);
    }

    public static AccidentTypeDto fromEntity(AccidentType accidentType) {
        return new AccidentTypeDto(
                accidentType.getId(),
                accidentType.getName()
        );
    }

    public AccidentType toEntity() {
        return AccidentType.of(
                this.getId(),
                this.getName()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccidentTypeDto)) {
            return false;
        }
        AccidentTypeDto that = (AccidentTypeDto) o;
        return id.equals(that.id)
                && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccidentTypeDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }

}
