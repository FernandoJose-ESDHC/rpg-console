package ui;

/**
 * Abstração de "como o jogo conversa com o jogador".
 *
 * SOLID - Dependency Inversion (D): o jogo (Jogo, Batalha, menus) depende
 * desta interface, e não diretamente do System.out / Scanner. Assim daria
 * para trocar o console por outra interface (ou por uma entrada simulada
 * em testes) sem mudar as regras do jogo.
 */
public interface EntradaSaida {

    /** Mostra um texto para o jogador. */
    void mostrar(String texto);

    /** Pergunta um número e só aceita valores entre min e max. */
    int lerOpcao(String pergunta, int min, int max);

    /** Pergunta um texto livre (ex.: o nome do herói). */
    String lerTexto(String pergunta);
}
