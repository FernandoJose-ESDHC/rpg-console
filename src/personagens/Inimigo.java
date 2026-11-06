package personagens;

import combate.CalculadoraDeDano;
import itens.Item;

/**
 * Classe base dos inimigos.
 *
 * O comportamento padrão de um inimigo no seu turno é o ataque normal.
 * Inimigos diferentes podem sobrescrever agir() para ter outro
 * comportamento (é o que o ChefeDragao faz) - polimorfismo.
 *
 * Cada inimigo pode deixar um item de recompensa ao ser derrotado.
 */
public abstract class Inimigo extends Personagem {

    private final Item recompensa; // pode ser null (sem recompensa)

    protected Inimigo(String nome, int vidaMaxima, int ataque, int defesa, Item recompensa) {
        super(nome, vidaMaxima, ataque, defesa);
        this.recompensa = recompensa;
    }

    /**
     * O que o inimigo faz no seu turno. A Batalha só chama este método e
     * não sabe (nem precisa saber) qual inimigo está do outro lado.
     */
    public String agir(Combatente alvo, CalculadoraDeDano calculadora) {
        return atacar(alvo, calculadora);
    }

    public Item getRecompensa() {
        return recompensa;
    }
}
