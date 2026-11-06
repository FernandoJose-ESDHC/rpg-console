package personagens;

import combate.CalculadoraDeDano;

/**
 * Interface que representa QUALQUER ser que participa de uma batalha.
 *
 * Tanto heróis quanto inimigos são combatentes. A classe Batalha conversa
 * com eles por meio deste "contrato", sem precisar saber se é um Guerreiro,
 * um Goblin ou o Dragão (abstração + polimorfismo).
 *
 * SOLID - Interface Segregation (I): a interface tem só o que é comum a
 * todo combatente. Cura e habilidade especial ficam em interfaces separadas
 * (Curavel e UsaHabilidade), para ninguém ser obrigado a implementar
 * métodos que não usa.
 */
public interface Combatente {

    String getNome();

    int getVida();

    int getVidaMaxima();

    int getAtaque();

    int getDefesa();

    /** Retorna true enquanto a vida for maior que zero. */
    boolean estaVivo();

    /** Diminui a vida do combatente (nunca abaixo de zero). */
    void receberDano(int dano);

    /**
     * Faz um ataque normal contra o alvo.
     * Retorna uma frase descrevendo o que aconteceu, para a interface mostrar.
     */
    String atacar(Combatente alvo, CalculadoraDeDano calculadora);
}
