package combate;

/**
 * Abstração da regra de dano.
 *
 * SOLID - Dependency Inversion (D): personagens e batalha dependem desta
 * INTERFACE, não de uma regra fixa. A implementação concreta é escolhida
 * no Main e "injetada" (passada pelo construtor).
 *
 * SOLID - Open/Closed (O): para trocar a fórmula (ex.: modo difícil ou
 * golpes críticos), cria-se outra classe que implementa esta interface,
 * sem mexer no código que já existe.
 */
public interface CalculadoraDeDano {

    /** Calcula o dano de um ataque contra uma defesa. Deve retornar pelo menos 1. */
    int calcular(int ataque, int defesa);
}
