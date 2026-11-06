package personagens;

/**
 * Interface para quem pode ser curado (por exemplo, com uma poção).
 *
 * SOLID - Interface Segregation (I): só os heróis implementam esta
 * interface. Os inimigos não usam poções, então não são obrigados a ter
 * um método curar() que nunca seria usado.
 */
public interface Curavel {

    /**
     * Recupera vida, sem passar da vida máxima.
     * Retorna quanto de vida foi realmente recuperado.
     */
    int curar(int quantidade);
}
