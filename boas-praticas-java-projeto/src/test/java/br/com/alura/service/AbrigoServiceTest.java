package br.com.alura.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Abrigo;

public class AbrigoServiceTest {

	private ClientHttpConfiguration client = mock(ClientHttpConfiguration.class);
	private AbrigoService abrigoService = new AbrigoService(client);
	private HttpResponse<String> response = mock(HttpResponse.class);
	private Abrigo abrigo = new Abrigo("Teste", "61981880392", "abrigo_alura@gmail.com");

	@Test
	public void deveVerificarQuandoContemAbrigo() throws IOException, InterruptedException {
	        abrigo.setId(0L);
	        String expectedAbrigosCadastrados = "Abrigos cadastrados:";
	        String expectedIdENome = "0 - Teste";

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PrintStream printStream = new PrintStream(baos);
	        System.setOut(printStream);

	        when(response.body()).thenReturn("[{"+abrigo.toString()+"}]");
	        when(client.dispararRequisicaoGet("http://localhost:8080/abrigos")).thenReturn(response);

	        abrigoService.listarAbrigo();

	        String[] lines = baos.toString().split(System.lineSeparator());
	        String actualAbrigosCadastrados = lines[0];
	        String actualIdENome = lines[1];

	        Assertions.assertEquals(expectedAbrigosCadastrados, actualAbrigosCadastrados);
	        Assertions.assertEquals(expectedIdENome, actualIdENome);
	}
	
	@Test
	public void deveVerificarQuandoNaoContemAbrigo() throws IOException, InterruptedException {
	        abrigo.setId(0L);
	        String expected = "Não há abrigos cadastrados";

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PrintStream printStream = new PrintStream(baos);
	        System.setOut(printStream);

	        when(response.body()).thenReturn("[]");
	        when(client.dispararRequisicaoGet("http://localhost:8080/abrigos")).thenReturn(response);

	        abrigoService.listarAbrigo();

	        String[] lines = baos.toString().split(System.lineSeparator());
	        String actual = lines[0];

	        Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void deveCadastrarUmNovoAbrigo() throws IOException, InterruptedException {
			String input = "Petmais\n13991898933\npetmais@gmail.com\n";
		    System.setIn(new ByteArrayInputStream(input.getBytes()));

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PrintStream printStream = new PrintStream(baos);
	        System.setOut(printStream);

	        when(client.dispararRequisicaoPost(any(Abrigo.class), eq("http://localhost:8080/abrigos"))).thenReturn(response);
	        when(response.statusCode()).thenReturn(200);

	        abrigoService.cadastrarAbrigo();

	        String output = baos.toString();
	        Assertions.assertTrue(output.contains("Abrigo cadastrado com sucesso!"));
	}
}
