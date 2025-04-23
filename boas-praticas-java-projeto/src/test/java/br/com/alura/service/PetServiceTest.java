package br.com.alura.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.alura.client.ClientHttpConfiguration;

public class PetServiceTest {

    private ClientHttpConfiguration client = mock(ClientHttpConfiguration.class);
    private PetService petService = new PetService(client);
    private HttpResponse<String> response = mock(HttpResponse.class);

    @Test
    public void deveListarPetsDoAbrigoQuandoIdOuNomeForValido() throws Exception {
        // Simular entrada do usuário
        String input = "abrigo_teste\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Simular retorno do response
        String json = """
                [
                    {"id": 1, "tipo": "Cachorro", "nome": "Rex", "raca": "Labrador", "idade": 3},
                    {"id": 2, "tipo": "Gato", "nome": "Mimi", "raca": "Siamês", "idade": 2}
                ]
                """;

        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn(json);
        when(client.dispararRequisicaoGet("http://localhost:8080/abrigos/abrigo_teste/pets")).thenReturn(response);

        // Capturar a saída do console
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // Executar método
        petService.listarPetsDoAbrigo();

        // Verificações
        String output = baos.toString();
        Assertions.assertTrue(output.contains("Pets cadastrados:"));
        Assertions.assertTrue(output.contains("1 - Cachorro - Rex - Labrador - 3 ano(s)"));
        Assertions.assertTrue(output.contains("2 - Gato - Mimi - Siamês - 2 ano(s)"));
    }
    
    @Test
    public void deveExibirMensagemDeErroSeStatusFor404ou500() throws Exception {
    	// Simular entrada do usuário
        String input = "abrigo_invalido\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    	
        when(response.statusCode()).thenReturn(404);
        when(response.body()).thenReturn("[]");
        when(client.dispararRequisicaoGet("http://localhost:8080/abrigos/abrigo_invalido/pets")).thenReturn(response);

        // Capturar a saída do console
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // Executar método
        petService.listarPetsDoAbrigo();

        // Verificações
        String output = baos.toString();
        Assertions.assertTrue(output.contains("ID ou nome não cadastrado!"));
    }
}
