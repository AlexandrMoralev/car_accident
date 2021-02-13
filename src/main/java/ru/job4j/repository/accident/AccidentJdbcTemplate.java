package ru.job4j.repository.accident;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.job4j.model.Accident;

import javax.sql.DataSource;
import java.util.*;

import static java.util.Optional.ofNullable;

//@Repository("accidentJdbcTemplate") // disabled to switch on hibernate store
public class AccidentJdbcTemplate implements AccidentStore {

    //    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public AccidentJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate,
//                                JdbcTemplate jdbcTemplate,
//                                SimpleJdbcInsert simpleJdbcInsert,
                                DataSource dataSource
    ) {

        this.jdbcTemplate = jdbcTemplate;
//        this.simpleJdbcInsert = simpleJdbcInsert;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("accident")
                .usingColumns("name", "text", "address");
    }

    @Override
    public Optional<Accident> getAccident(Integer id) {
//        return ofNullable(this.jdbcTemplate.queryForObject("select id, name, text, address from accident where id = ?", accidentRowMapper, id));
        return ofNullable(
                this.jdbcTemplate.queryForObject(
                        "select id, name, text, address from accident where id = :id",
                        new MapSqlParameterSource().addValue("id", id),
                        accidentRowMapper
                )
        );
    }

    @Override
    public Optional<Accident> getAccidentWithRules(Integer id) {
        return this.getAccident(id);
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return this.jdbcTemplate.query(
                "select id, name, text, address from accident",
                accidentRowMapper
        );
    }

    @Override
    public Collection<Accident> getAllAccidentsWithRules() {
        return this.getAllAccidents();
    }

    @Override
    public Accident addAccident(Accident accident) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        this.jdbcTemplate.update("insert into accident (name, text, address) values (?, ?, ?)",
//                accident.getName(),
//                keyHolder);
//        int accId = ofNullable(keyHolder.getKey())
//                .map(Number::intValue)
//                .orElseThrow(() -> new RuntimeException("Incorrect insertion of accident: " + accident.toString()));
//        return Accident
//                .of(accident)
//                .setId(accId)
//                .build();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", accident.getName());
        parameters.addValue("text", accident.getText());
        parameters.addValue("address", accident.getAddress());
        int accId = simpleJdbcInsert.executeAndReturnKey(parameters).intValue();
        return Accident
                .of(accident)
                .setId(accId)
                .build();
    }

    public int[] batchInsert(List<Accident> accidents) {
        String sql = "insert into accident (name, text, address) values (:name, :text, :address)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(accidents.toArray());
        int[] updateCounts = jdbcTemplate.batchUpdate(sql, batch); // .length => count of inserted
        return updateCounts;
    }

    @Override
    public void updateAccident(Accident updatedAccident) {
//        this.jdbcTemplate.update("update accident set name = ?, text = ?, address = ? where id = ?",
//                updatedAccident.getName(),
//                updatedAccident.getText(),
//                updatedAccident.getAddress(),
//                updatedAccident.getId());
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", updatedAccident.getId());
        parameters.addValue("name", updatedAccident.getName());
        parameters.addValue("text", updatedAccident.getText());
        parameters.addValue("address", updatedAccident.getAddress());
        this.jdbcTemplate.update(
                "update accident set name = :name, text = :text, address = :address where id = :id",
                parameters
        );
    }

    @Override
    public void removeAccident(Integer id) {
//        this.jdbcTemplate.update("delete from accident where id = ?", id);
        this.jdbcTemplate.update(
                "delete from accident where id = :id",
                new MapSqlParameterSource().addValue("id", id)
        );
    }

    public List<Map<String, Object>> getAccidentsGroupedByAddresses() {
        return this.jdbcTemplate.getJdbcOperations().queryForList(
                "select * from accident group by address",
                accidentRowMapper
        ); // TODO mapper
    }

    public Map<String, Object> getAddressStat() {
        String query = "select address, count(*) as count from accident group by address";
        return this.jdbcTemplate.getJdbcOperations().queryForMap(
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
