package br.com.alura.command.pet;

import java.io.IOException;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.command.Command;
import br.com.alura.service.PetService;

public class ImportarPetsDoAbrigoCommand implements Command {

	@Override
	public void execute() {
		try {
			ClientHttpConfiguration client = new ClientHttpConfiguration();
			PetService petService = new PetService(client);
			
			petService.importarPetsDoAbrigo();
			
		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
}
