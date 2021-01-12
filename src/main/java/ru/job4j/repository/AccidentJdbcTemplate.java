package ru.job4j.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

import java.util.*;

import static java.util.Optional.ofNullable;

@Repository("accidentJdbcTemplate") // disabled to switch on hibernate store
public class AccidentJdbcTemplate implements AccidentStore {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccidentJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Accident> getAccident(Integer id) {
        return ofNullable(this.jdbcTemplate.queryForObject("select id, name, text, address from accident where id = ?", accidentRowMapper, id));
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return this.jdbcTemplate.query("select id, name, text, address from accident", accidentRowMapper);
    }

    @Override
    public Accident addAccident(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update("insert into accident (name, text, address) values (?, ?, ?)",
                accident.getName(),
                keyHolder);
        int accId = ofNullable(keyHolder.getKey())
                .map(Number::intValue)
                .orElseThrow(() -> new RuntimeException("Incorrect insertion of accident: " + accident.toString()));
        return Accident
                .of(accident)
                .setId(accId)
                .build();
    }

    @Override
    public void updateAccident(Accident updatedAccident) {
        this.jdbcTemplate.update("update accident set name = ?, text = ?, address = ? where id = ?",
                updatedAccident.getName(),
                updatedAccident.getText(),
                updatedAccident.getAddress(),
                updatedAccident.getId());
    }

    @Override
    public void removeAccident(Integer id) {
        this.jdbcTemplate.update("delete from accident where id = ?", id);
    }

    public List<Map<String, Object>> getAccidentsGroupedByAddresses() {
        return this.jdbcTemplate.queryForList("select * from accident group by address", accidentRowMapper);
    }

    public Map<String, Object> getAddressStat() {
        String query = "select address, count(*) as count from accident group by address";
        return this.jdbcTemplate.queryForMap(
                "select * from accident group by address",
                (ResultSetExtractor<Map<String, Integer>>) rs -> {
                    Map<String, Integer> stats = new TreeMap<>();
                    while (rs.next()) {
                        String address = rs.getString("address");
                        int count = rs.getInt("count");
                        stats.put(address, count);
                    }
                    return stats;
                }
        );
    }

    private final RowMapper<Accident> accidentRowMapper = (resultSet, rowNum) -> Accident.newBuilder()
            .setId(resultSet.getInt("id"))
            .setName(resultSet.getString("name"))
            .setText(resultSet.getString("text"))
            .setAddress(resultSet.getString("address"))
            .build();
}
