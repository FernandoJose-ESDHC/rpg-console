package ui;

import java.util.Scanner;

/**
 * Implementação de EntradaSaida usando o terminal (System.out e Scanner).
 *
 * SOLID - Single Responsibility (S): esta é a ÚNICA classe do projeto
 * que usa System.out e Scanner. Toda a leitura e escrita passa por aqui.
 */
public class Console implements EntradaSaida {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void mostrar(String texto) {
        System.out.println(texto);
    }

    @Override
    public int lerOpcao(String pergunta, int min, int max) {
        // Repete até o jogador digitar um número válido dentro do intervalo.
        while (true) {
            System.out.print(pergunta + " ");
            String linha = lerLinha().trim();
            try {
                int numero = Integer.parseInt(linha);
                if (numero >= min && numero <= max) {
                    return numero;
                }
            } catch (NumberFormatException e) {
                // Não era número: cai na mensagem abaixo e pergunta de novo.
            }
            System.out.println("Opção inválida. Digite um número de " + min + " a " + max + ".");
        }
    }

    @Override
    public String lerTexto(String pergunta) {
        System.out.print(pergunta + " ");
        return lerLinha().trim();
    }

    /**
     * Lê uma linha do teclado. Se a entrada acabar (ex.: Ctrl+D ou fim de
     * um arquivo redirecionado), encerra o jogo de forma educada.
     */
    private String lerLinha() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nEntrada encerrada. Até a próxima!");
            System.exit(0);
        }
        return scanner.nextLine();
    }
}
