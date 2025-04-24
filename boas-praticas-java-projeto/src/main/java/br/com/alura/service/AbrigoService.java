package br.com.alura.service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Abrigo;

public class AbrigoService {
	
	private ClientHttpConfiguration client;
	
	public AbrigoService(ClientHttpConfiguration client) {
		this.client = client;
	}
	
	Scanner scanner = new Scanner(System.in);
	
	public void listarAbrigo() throws IOException, InterruptedException {
		String uri = "http://localhost:8080/abrigos";
		HttpResponse<String> response = client.dispararRequisicaoGet(uri);
		String responseBody = response.body();
		
		Abrigo abrigos[] = new ObjectMapper().readValue(responseBody, Abrigo[].class);
		List<Abrigo> abrigoList = Arrays.stream(abrigos).toList();
		
		if(abrigoList.isEmpty()) {
			System.out.println("Não há abrigos cadastrados");
		} else {
			mostrarAbrigos(abrigoList);
		}
	}
	
	private void mostrarAbrigos(List<Abrigo> abrigoList) {
		System.out.println("Abrigos cadastrados:");
		for (Abrigo abrigo : abrigoList) {
			long id = abrigo.getId();
			String nome = abrigo.getNome();
			System.out.println(id + " - " + nome);
		}
	}

	public void cadastrarAbrigo() throws IOException, InterruptedException {
	    System.out.println("Digite o nome do abrigo:");
	    String nome = scanner.nextLine();

	    System.out.println("Digite o telefone do abrigo:");
	    String telefone = scanner.nextLine();

	    System.out.println("Digite o email do abrigo:");
	    String email = scanner.nextLine();

		Abrigo abrigo = new Abrigo(nome, telefone, email);
		
		String uri = "http://localhost:8080/abrigos";
		HttpResponse<String> response = client.dispararRequisicaoPost(abrigo, uri);

		int statusCode = response.statusCode();
		String responseBody = response.body();
		if (statusCode == 200) {
			System.out.println("Abrigo cadastrado com sucesso!");
			System.out.println(responseBody);
		} else if (statusCode == 400 || statusCode == 500) {
			System.out.println("Erro ao cadastrar o abrigo:");
			System.out.println(responseBody);
		}
	}

}
