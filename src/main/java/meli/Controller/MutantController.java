package meli.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import meli.Model.Dna;
import meli.Service.MutantService;

@RestController
public class MutantController {

	@Autowired
	private MutantService mService;

	/**
	 * Servicio que recibe un Json con una cadena de ADN y devuelve status 200 si es
	 * mutante o 403 si no lo es.
	 * 
	 * @param dna
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/mutant")
	public ResponseEntity<?> isMutant(@RequestBody Dna dna) throws Exception {
		
		mService.validateRequest(dna.getDna());

		if (mService.isMutant(dna.getDna())) {
			return ResponseEntity.ok().build();
		} else {
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
