package it.prova.dottori.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.prova.dottori.model.Dottore;

public interface DottoreRepository extends CrudRepository<Dottore, Long> {

	Optional<Dottore> findByCodiceDottore(String string);

	Optional<Dottore> findByCodiceFiscalePaziente(String codiceFiscalePaziente);



}
