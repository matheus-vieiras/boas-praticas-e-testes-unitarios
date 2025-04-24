package br.com.alura;

import java.util.Scanner;

import br.com.alura.command.CommandExecutor;
import br.com.alura.command.abrigo.CadastrarAbrigoCommand;
import br.com.alura.command.abrigo.ListarAbrigoCommand;
import br.com.alura.command.pet.ImportarPetsDoAbrigoCommand;
import br.com.alura.command.pet.ListarPetsDoAbrigoCommand;

public class AdopetConsoleApplication {

    public static void main(String[] args) {
    	CommandExecutor executor = new CommandExecutor();
        System.out.println("##### BOAS VINDAS AO SISTEMA ADOPET CONSOLE #####");
        
        try {
            int opcaoEscolhida = 0;
            while (opcaoEscolhida != 5) {
                System.out.println("\nDIGITE O NÚMERO DA OPERAÇÃO DESEJADA:");
                System.out.println("1 -> Listar abrigos cadastrados");
                System.out.println("2 -> Cadastrar novo abrigo");
                System.out.println("3 -> Listar pets do abrigo");
                System.out.println("4 -> Importar pets do abrigo");
                System.out.println("5 -> Sair");

                String textoDigitado = new Scanner(System.in).nextLine();
                opcaoEscolhida = Integer.parseInt(textoDigitado);

                if (opcaoEscolhida == 1) {
                	executor.executeCommand(new ListarAbrigoCommand());
                	
                } else if (opcaoEscolhida == 2) {
                	executor.executeCommand(new CadastrarAbrigoCommand());
                	
                } else if (opcaoEscolhida == 3) {
                	executor.executeCommand(new ListarPetsDoAbrigoCommand());
                	
                } else if (opcaoEscolhida == 4) {
                	executor.executeCommand(new ImportarPetsDoAbrigoCommand());
                
                } else if (opcaoEscolhida == 5) {
                    break;
                } else {
                    System.out.println("NÚMERO INVÁLIDO!");
                    opcaoEscolhida = 0;
                }
            }
            System.out.println("Finalizando o programa...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
