package personagens;

import combate.CalculadoraDeDano;

/**
 * Interface para quem possui uma habilidade especial com tempo de recarga.
 *
 * SOLID - Interface Segregation (I): os heróis e o chefão (Dragão)
 * implementam esta interface; os inimigos comuns (Goblin, Esqueleto) não,
 * porque não têm habilidade especial.
 */
public interface UsaHabilidade {

    String getNomeHabilidade();

    /** Quantas rodadas ainda faltam para a habilidade poder ser usada. */
    int getRecargaRestante();

    /** true quando a recarga chegou a zero. */
    boolean habilidadePronta();

    /** Executa a habilidade contra o alvo e devolve a descrição do resultado. */
    String usarHabilidade(Combatente alvo, CalculadoraDeDano calculadora);

    /** Chamado a cada rodada para diminuir a recarga em 1. */
    void avancarTurno();
}
