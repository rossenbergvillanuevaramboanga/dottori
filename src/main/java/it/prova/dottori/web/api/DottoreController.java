package it.prova.dottori.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.dottori.dto.DottoreDTO;
import it.prova.dottori.model.Dottore;
import it.prova.dottori.service.DottoreService;
import it.prova.dottori.web.api.exception.DottoreInVisitaException;
import it.prova.dottori.web.api.exception.DottoreNotFoundException;
import it.prova.dottori.web.api.exception.DottoreNotInServizioException;
import it.prova.dottori.web.api.exception.IdNotNullForInsertException;

@RestController
@RequestMapping("/api/dottore")
public class DottoreController {

	@Autowired
	private DottoreService dottoreService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DottoreDTO createUtente(@Valid @RequestBody DottoreDTO dottoreInput) {

		if (dottoreInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Dottore dottore = dottoreInput.buildDottoreModel();
		dottoreService.inserisciNuovo(dottore);

		return DottoreDTO.buildDottoreDTOModel(dottore);
	}

	@GetMapping("/{id}")
	public DottoreDTO findById(@PathVariable(value = "id", required = true) Long id) {
		
		Dottore dottore = dottoreService.caricaSingoloElemento(id);

		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con id: " + id);

		return DottoreDTO.buildDottoreDTOModel(dottore);
	}
	
	@GetMapping("/find/{codicefiscale}")
	public DottoreDTO findByCodiceFiscalePaziente(@PathVariable(value = "codicefiscale", required = true) String codicefiscale) {
		
		Dottore dottore = dottoreService.findByCodicefiscalepaziente(codicefiscale);

		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con paziente: " + codicefiscale);

		return DottoreDTO.buildDottoreDTOModel(dottore);
	}

	@GetMapping
	public List<DottoreDTO> getAll() {
		return DottoreDTO.buildDottoreDTOListFromModelList(dottoreService.listAll());
	}

	@PutMapping("/{id}")
	public DottoreDTO update(@Valid @RequestBody DottoreDTO dottoreInput, @PathVariable(required = true) Long id) {
		Dottore dottore = dottoreService.caricaSingoloElemento(id);

		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con id: " + id);

		dottoreInput.setId(id);
		Dottore dottoreAggiornato = dottoreService.aggiorna(dottoreInput.buildDottoreModel());
		return DottoreDTO.buildDottoreDTOModel(dottoreAggiornato);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Dottore dottore = dottoreService.caricaSingoloElemento(id);
		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con id: " + id);
		dottoreService.rimuovi(dottore);
	}
	
	@GetMapping("/verifica/{codicedottore}")
	public DottoreDTO findByCodiceDottore(@PathVariable(value = "codicedottore", required = true) String codicedottore) {
		
		Dottore dottore = dottoreService.findByCodicedottore(codicedottore);

		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con codice: " + codicedottore);
		
		if (dottore.getInServizio() == false)
			throw new DottoreNotInServizioException("Dottore Not InServizio con codice: " + codicedottore);
		
		if (dottore.getInVisita() == true || dottore.getCodiceFiscalePaziente() != null)
			throw new DottoreInVisitaException("Dottore Not InServizio con codice: " + codicedottore);
		
		dottore.setId(null);
		return DottoreDTO.buildDottoreDTOModel(dottore);
	}
	
	//TODO impostaInVista e terminaVisita
	@PostMapping("/impostaInVisita")
	public DottoreDTO impostaInVisita(@Valid @RequestBody DottoreDTO dottoreInput) {
		
		Dottore dottore = dottoreService.findByCodicedottore(dottoreInput.getCodiceDottore());

		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con Codice Dottore: " + dottoreInput.getCodiceDottore());
		
		if (dottore.getInServizio() == false)
			throw new DottoreNotInServizioException("Dottore Not InServizio con codice: " + dottoreInput.getCodiceDottore());
		
		if (dottore.getInVisita() == true || dottore.getCodiceFiscalePaziente() != null)
			throw new DottoreInVisitaException("Dottore Not InServizio con codice: " + dottoreInput.getCodiceDottore());
		
		dottore.setCodiceFiscalePaziente(dottoreInput.getCodiceFiscalePaziente());
		dottore.setInVisita(true);
		
		Dottore dottoreAggiornato = dottoreService.aggiorna(dottore);
		return DottoreDTO.buildDottoreDTOModel(dottoreAggiornato);
	}
	
	@PostMapping("/terminaVisita")
	public DottoreDTO terminaVisita(@Valid @RequestBody DottoreDTO dottoreInput) {
		
		Dottore dottore = dottoreService.findByCodicefiscalepaziente(dottoreInput.getCodiceFiscalePaziente());
		
		//Modificare Exception
		if (dottore == null)
			throw new DottoreNotFoundException("Non esiste dottore con il paziente: " + dottoreInput.getCodiceFiscalePaziente());
		
		dottore.setCodiceFiscalePaziente(null);
		dottore.setInVisita(null);
		
		Dottore dottoreAggiornato = dottoreService.aggiorna(dottore);
		return DottoreDTO.buildDottoreDTOModel(dottoreAggiornato);
		
	}

}
