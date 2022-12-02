package it.prova.dottori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.dottori.model.Dottore;
import it.prova.dottori.service.DottoreService;

@SpringBootApplication
public class DottoriApplication implements CommandLineRunner {

	@Autowired
	private DottoreService dottoreServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(DottoriApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		// DOTTORE
		if (dottoreServiceInstance.findByCodicedottore("DOTTORE1") == null) {
			Dottore dottore1 = new Dottore("Tachi", "Pirina", "DOTTORE1");
			dottoreServiceInstance.inserisciNuovo(dottore1);
			dottore1.setCodiceFiscalePaziente("RMBSNB08L60H501V");
			dottoreServiceInstance.aggiorna(dottore1);
		}
		
		if (dottoreServiceInstance.findByCodicedottore("DOTTORE2") == null) {
			Dottore dottore2 = new Dottore("Ibu", "Profene", "DOTTORE2");
			dottoreServiceInstance.inserisciNuovo(dottore2);
		}

	}

}
