package combate;

import java.util.Random;

/**
 * Regra de dano padrão do jogo:
 *   dano base = ataque - (defesa / 2)
 *   depois aplica uma variação aleatória entre 80% e 120%
 *   o dano mínimo é 1 (todo ataque que acerta causa algum dano)
 *
 * SOLID - Single Responsibility (S): esta classe só calcula dano.
 */
public class CalculadoraPadrao implements CalculadoraDeDano {

    private final Random random;

    /**
     * Recebe o Random de fora: no jogo normal é aleatório; nos testes podemos
     * passar um Random com "semente" fixa para repetir sempre a mesma partida.
     */
    public CalculadoraPadrao(Random random) {
        this.random = random;
    }

    @Override
    public int calcular(int ataque, int defesa) {
        int danoBase = ataque - defesa / 2;
        double variacao = 0.8 + random.nextDouble() * 0.4; // número entre 0.8 e 1.2
        int dano = (int) Math.round(danoBase * variacao);
        return Math.max(1, dano);
    }
}
