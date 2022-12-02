package it.prova.dottori.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.dottori.model.Dottore;
import it.prova.dottori.repository.DottoreRepository;

@Service
public class DottoreServiceImpl implements DottoreService {

	@Autowired
	private DottoreRepository repository;

	@Transactional(readOnly = true)
	public List<Dottore> listAll() {

		return (List<Dottore>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Dottore caricaSingoloElemento(Long id) {

		return repository.findById(id).orElse(null);
	}

	@Transactional
	public Dottore aggiorna(Dottore dottoreInstance) {

		Dottore dottoreReloaded = repository.findById(dottoreInstance.getId()).orElse(null);
		if (dottoreReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		dottoreReloaded.setNome(dottoreInstance.getNome());
		dottoreReloaded.setCognome(dottoreInstance.getCognome());
		dottoreReloaded.setCodiceDottore(dottoreInstance.getCodiceDottore());
		if (dottoreInstance.getCodiceFiscalePaziente() != null)
			dottoreReloaded.setCodiceFiscalePaziente(dottoreInstance.getCodiceFiscalePaziente());
		dottoreReloaded.setInVisita(dottoreInstance.getInVisita());
		dottoreReloaded.setInServizio(dottoreInstance.getInServizio());
		return repository.save(dottoreReloaded);
	}

	@Transactional
	public void inserisciNuovo(Dottore dottoreInstance) {

		dottoreInstance.setInServizio(true);
		if (dottoreInstance.getCodiceFiscalePaziente() == null
				|| dottoreInstance.getCodiceFiscalePaziente().isEmpty()) {
			dottoreInstance.setInVisita(false);
		} else {
			dottoreInstance.setInVisita(true);
		}

		repository.save(dottoreInstance);

	}

	@Transactional
	public void rimuovi(Dottore dottoreInstance) {
		repository.delete(dottoreInstance);

	}

	@Transactional(readOnly = true)
	public Dottore findByCodicedottore(String string) {
		// TODO Auto-generated method stub
		return repository.findByCodiceDottore(string).orElse(null);
	}

	@Transactional(readOnly = true)
	public Dottore findByCodicefiscalepaziente(String codiceFiscalePaziente) {
		// TODO Auto-generated method stub
		return repository.findByCodiceFiscalePaziente(codiceFiscalePaziente).orElse(null);
	}

}
