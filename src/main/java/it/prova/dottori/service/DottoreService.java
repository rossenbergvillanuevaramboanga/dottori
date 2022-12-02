package it.prova.dottori.service;

import java.util.List;

import it.prova.dottori.model.Dottore;

public interface DottoreService {

	// CRUD
	public List<Dottore> listAll();

	public Dottore caricaSingoloElemento(Long id);

	public Dottore aggiorna(Dottore dottoreInstance);

	public void inserisciNuovo(Dottore dottoreInstance);

	public void rimuovi(Dottore dottoreInstance);

	public Dottore findByCodicedottore(String string);

	public Dottore findByCodicefiscalepaziente(String codiceFiscalePaziente);

}
