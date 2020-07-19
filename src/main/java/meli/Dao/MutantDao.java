package meli.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import meli.Model.MutantData;

@Repository
public class MutantDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String INSERT_MUTANT = "insert into Mutant_Data values(?,?)";

	private static final String QUERY_MUTANTS = "select * from Mutant_data";

	/**
	 * Guarda en la base de datos el registro con el DNA analizado e indica si es o
	 * no mutante.
	 * 
	 * @param dna
	 * @param isMutant
	 */
	public void saveAdn(List<String> dna, boolean isMutant) {

		try {
			jdbcTemplate.update(INSERT_MUTANT, dna.toString(), isMutant);
		} catch (DuplicateKeyException e) {
			throw new DuplicateKeyException("La cadena de ADN enviada ya fue analizada previamente");
		}

	}

	/**
	 * Obtiene de la base de datos un boolean por cada registro que indica si es o
	 * no mutante
	 * 
	 * @return
	 */
	public List<MutantData> getStatistics() {
		return jdbcTemplate.query(QUERY_MUTANTS, new RowMapper<MutantData>() {
			public MutantData mapRow(ResultSet rs, int rowNum) throws SQLException {
				MutantData data = new MutantData();
				data.setMutant(rs.getBoolean("is_Mutant"));
				return data;
			}
		});

	}

}
