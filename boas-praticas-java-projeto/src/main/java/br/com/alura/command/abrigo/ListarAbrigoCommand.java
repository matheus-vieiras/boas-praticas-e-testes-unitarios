package br.com.alura.command.abrigo;

import java.io.IOException;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.command.Command;
import br.com.alura.service.AbrigoService;

public class ListarAbrigoCommand implements Command {

	@Override
	public void execute() {
		try {
			ClientHttpConfiguration client = new ClientHttpConfiguration();
			AbrigoService abrigoService = new AbrigoService(client);
			
			abrigoService.listarAbrigo();
			
		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

}
