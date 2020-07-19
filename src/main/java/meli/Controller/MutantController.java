package meli.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import meli.Dao.MutantDao;
import meli.Model.Dna;
import meli.Service.MutantService;

@RestController
public class MutantController {

	@Autowired
	private MutantService mService;

	@Autowired
	private MutantDao mDao;

	/**
	 * Servicio que recibe un Json con una cadena de ADN y devuelve status 200 si es
	 * mutante o 403 si no lo es.
	 * 
	 * @param dna
	 * @return
	 */
	@PostMapping(value = "/mutant")
	public ResponseEntity<?> isMutant(@RequestBody Dna dna) {

		if (mService.isMutant(dna.getDna())) {
			mDao.saveAdn(dna.getDna(), true);
			return ResponseEntity.ok().build();
		} else {
			mDao.saveAdn(dna.getDna(), false);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

	/**
	 * Devuelve en un json la cantidad de ADN analizados divididos en mutante o no
	 * mutante y el ratio
	 * 
	 * @return
	 */
	@GetMapping(value = "/stats")
	public ResponseEntity<String> statistics() {
		return new ResponseEntity<String>(mService.getStatistics().toString(), HttpStatus.OK);
	}

}
