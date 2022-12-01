package it.prova.dottori.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import it.prova.dottori.model.Dottore;

public class DottoreDTO {

	private Long id;

	@NotBlank(message = "{dottore.nome.notblank}")
	private String nome;
	@NotBlank(message = "{dottore.cognome.notblank}")
	private String cognome;
	@NotBlank(message = "{dottore.codicedottore.notblank}")
	private String codiceDottore;

	private String codiceFiscalePaziente;

	private Boolean inVisita;

	private Boolean inServizio;

	public DottoreDTO() {
		// TODO Auto-generated constructor stub
	}

	public DottoreDTO(Long id, String nome, String cognome, String codiceDottore, String codiceFiscalePaziente,
			Boolean inVisita, Boolean inServizio) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceDottore = codiceDottore;
		this.codiceFiscalePaziente = codiceFiscalePaziente;
		this.inVisita = inVisita;
		this.inServizio = inServizio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceDottore() {
		return codiceDottore;
	}

	public void setCodiceDottore(String codiceDottore) {
		this.codiceDottore = codiceDottore;
	}

	public String getCodiceFiscalePaziente() {
		return codiceFiscalePaziente;
	}

	public void setCodiceFiscalePaziente(String codiceFiscalePaziente) {
		this.codiceFiscalePaziente = codiceFiscalePaziente;
	}

	public Boolean getInVisita() {
		return inVisita;
	}

	public void setInVisita(Boolean inVisita) {
		this.inVisita = inVisita;
	}

	public Boolean getInServizio() {
		return inServizio;
	}

	public void setInServizio(Boolean inServizio) {
		this.inServizio = inServizio;
	}

	public Dottore buildDottoreModel() {
		return new Dottore(id, nome, cognome, codiceDottore, codiceFiscalePaziente, inVisita, inServizio);
	}

	public static DottoreDTO buildDottoreDTOModel(Dottore dottoreModel) {
		return new DottoreDTO(dottoreModel.getId(), dottoreModel.getNome(), dottoreModel.getCognome(),
				dottoreModel.getCodiceDottore(), dottoreModel.getCodiceFiscalePaziente(), dottoreModel.getInVisita(),
				dottoreModel.getInServizio());
	}
	
	public static List<DottoreDTO> buildDottoreDTOListFromModelList(List<Dottore> modelList){
		return modelList.stream().map(entity -> DottoreDTO.buildDottoreDTOModel(entity)).collect(Collectors.toList());
	}
	
	public static Set<DottoreDTO> buildDottoreDTOSetFromModelSet(Set<Dottore> modelSet){
		return modelSet.stream().map(entity -> DottoreDTO.buildDottoreDTOModel(entity)).collect(Collectors.toSet());
	}

}
