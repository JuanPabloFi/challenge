package meli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import meli.Dao.MutantDao;
import meli.Model.MutantData;
import meli.Service.MutantService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MutantServiceTest {

	@Autowired
	MutantService mService;

	@Mock
	MutantDao mDao;
	/**
	 * Analiza 3 cadenas distintas de adn mutante y verifica que el analisis de
	 * verdadero. dna1 tiene 4 caracteres iguales seguidos de forma horizontal dna2
	 * tiene 4 caracteres iguales seguidos de forma vertical dna3 tiene 4 caracteres
	 * iguales seguidos de forma oblicua
	 */
	@Test
	public void isMutantTest() {

		String[] dna1 = { "ATGCAA", "CAGTGC", "TTGTGT", "AGAAGG", "CCACTA", "TCAAAA" };
		String[] dna2 = { "ATGCAA", "CAGTGC", "TTGTGT", "AGAAGT", "CCACTT", "TCACTT" };
		String[] dna3 = { "ATGCAA", "CAGTGC", "TTATGT", "AGAAGG", "CCACAA", "TCCCTA" };
		List<String> dnaList1 = Arrays.asList(dna1);
		List<String> dnaList2 = Arrays.asList(dna2);
		List<String> dnaList3 = Arrays.asList(dna3);

		assertTrue(mService.isMutant(dnaList1));
		assertTrue(mService.isMutant(dnaList2));
		assertTrue(mService.isMutant(dnaList3));

	}

	/**
	 * Analiza una cadena de ADN humano y verifica que el metodo isMutant devuelva
	 * falso.
	 */
	@Test
	public void isNotMutantTest() {
		String[] dna = { "ATGCAA", "CAGTGC", "TTGTGT", "AGAAGG", "CCACTA", "TCACTG" };
		List<String> dnaList = Arrays.asList(dna);
		
		Mockito.doNothing().when(mDao).saveAdn(Mockito.anyList(), Mockito.anyBoolean());
		assertFalse(mService.isMutant(dnaList));

	}

	/**
	 * Hace un llamado al metodo getStatistics para verificar que haga el llamado
	 * correspondiente al metodo correcto del Dao y calcule bien las estadisticas.
	 * 
	 * @throws JSONException
	 */
	@Test
	public void testStats() throws JSONException {
		
		MutantData data = new MutantData();
		MutantData data2 = new MutantData();
		MutantData data3 = new MutantData();
		data.setMutant(true);
		data2.setMutant(false);
		data3.setMutant(false);
		List<MutantData> listData = new ArrayList<>();
		listData.add(data);
		listData.add(data2);
		listData.add(data3);

		Mockito.when(mDao.getStatistics()).thenReturn(listData);
		mService.setmDao(mDao);
		
		JSONObject json = mService.getStatistics();
		assertEquals(json.get("count_mutant_dna"), 1.0);
	}
	
	@Test
	public void validateRequestTest() throws Exception {
		List<String> lista = new ArrayList<>();
		lista.add("ATGCAA");
		lista.add("CAGTGC");
		lista.add("TTGTGT");
		lista.add("AGAAGG");
		mService.validateRequest(lista);
	}

}
