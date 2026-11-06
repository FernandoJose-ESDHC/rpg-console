package ui;

import personagens.Combatente;

/**
 * Funções auxiliares para montar textos da tela (barra de vida, status).
 * Só monta Strings; quem imprime é a EntradaSaida.
 */
public class Formatador {

    private static final int TAMANHO_BARRA = 20;

    // Construtor privado: a classe só tem métodos static, não precisa ser instanciada.
    private Formatador() {
    }

    /** Ex.: [##########----------] para quem está com metade da vida. */
    public static String barraDeVida(int vida, int vidaMaxima) {
        int cheios = (int) Math.round((double) vida / vidaMaxima * TAMANHO_BARRA);
        return "[" + "#".repeat(cheios) + "-".repeat(TAMANHO_BARRA - cheios) + "]";
    }

    /** Linha com nome, barra de vida, vida atual e atributos. */
    public static String linhaDeStatus(String titulo, Combatente c) {
        return String.format("%-24s %s %3d/%-3d  ATQ %2d  DEF %2d",
                titulo, barraDeVida(c.getVida(), c.getVidaMaxima()),
                c.getVida(), c.getVidaMaxima(), c.getAtaque(), c.getDefesa());
    }
}
