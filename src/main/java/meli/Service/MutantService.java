package meli.Service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meli.Dao.MutantDao;
import meli.Model.MutantData;

@Service
public class MutantService {

	@Autowired
	private MutantDao mDao;

	private char[][] matriz;
	
	/**
	 * Devuelve true o false dependiendo si el DNA representa un mutante o no.
	 * 
	 * @param dna
	 * @return
	 */
	public boolean isMutant(List<String> dna) {
		popularMatriz(dna);

		if (calcularHorizontales(matriz) || calcularVerticales(matriz) || calcularDiagonales(matriz)) {
			mDao.saveAdn(dna, true);
			return true;
		} else {
			mDao.saveAdn(dna, false);
			return false;
		}
	}

	/**
	 * @param mat
	 * @return
	 */
	private boolean calcularDiagonales(char[][] mat) {
		for (int y = 0; y < mat.length - 3; y++) {
			for (int x = 0; x < mat.length - 3; x++) {
				if (mat[x][y] == mat[x + 1][y + 1] && mat[x][y] == mat[x + 2][y + 2] && mat[x][y] == mat[x + 3][y + 3])
					return true;
			}
		}
		return false;
	}

	/**
	 * @param mat
	 * @return
	 */
	private boolean calcularVerticales(char[][] mat) {
		for (int x = 0; x < mat.length; x++) {
			for (int y = 0; y < mat.length - 3; y++) {
				if (mat[x][y] == mat[x][y + 1] && mat[x][y] == mat[x][y + 2] && mat[x][y] == mat[x][y + 3])
					return true;
			}
		}
		return false;
	}

	/**
	 * @param mat
	 * @return
	 */
	private boolean calcularHorizontales(char[][] mat) {
		for (int y = 0; y < mat.length; y++) {
			for (int x = 0; x < mat.length - 3; x++) {
				if (mat[x][y] == mat[x + 1][y] && mat[x][y] == mat[x + 2][y] && mat[x][y] == mat[x + 3][y])
					return true;
			}
		}
		return false;
	}

	/**
	 * Llena una matriz a partir de una lista de String.
	 * 
	 * @param dna
	 */
	private void popularMatriz(List<String> dna) {
		int n = dna.size();
		matriz = new char[n][n];
		int y = 0;
		for (String cadena : dna) {
			for (int i = 0; i < n; i++) {
				matriz[i][y] = cadena.charAt(i);
			}
			y++;
		}

	}

	/**
	 * Metodo que llama al Dao para obtener la cantidad de mutantes y no mutantes
	 * analizados.
	 * 
	 * @return
	 */
	public JSONObject getStatistics() {
		List<MutantData> lista = mDao.getStatistics();
		float count_mutant_dna = 0;
		float count_human_dna = 0;
		float ratio = 0.0F;

		for (MutantData mutantData : lista) {
			if (mutantData.isMutant()) {
				count_mutant_dna++;
			} else {
				count_human_dna++;
			}
		}
		if (count_human_dna > 0) {
			ratio = count_mutant_dna / count_human_dna;
		}

		return buildJson(count_mutant_dna, count_human_dna, ratio);

	}

	/**
	 * @param count_mutant_dna
	 * @param count_human_dna
	 * @param ratio
	 * @return
	 */
	private JSONObject buildJson(float count_mutant_dna, float count_human_dna, float ratio) {
		JSONObject json = new JSONObject();
		json.put("count_mutant_dna", count_mutant_dna);
		json.put("count_human_dna", count_human_dna);
		json.put("ratio", ratio);
		return json;
	}

	public MutantDao getmDao() {
		return mDao;
	}

	public void setmDao(MutantDao mDao) {
		this.mDao = mDao;
	}

	public void validateRequest(List<String> dna) throws Exception {
		if (dna.size() < 4) {
			throw new Exception("La cadena de adn debe ser al menos de 4x4");
		}
		for (String string : dna) {
			if (!string.matches("[ATCG]+")) {
				throw new Exception("La cadena debe tener solo las letras A, T, C o G");
			};
		}
		
	}
}
