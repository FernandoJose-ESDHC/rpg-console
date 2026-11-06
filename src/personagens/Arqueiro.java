package personagens;

import combate.CalculadoraDeDano;

/**
 * Arqueiro: atributos equilibrados.
 * Habilidade "Disparo Duplo": faz dois ataques seguidos na mesma rodada.
 */
public class Arqueiro extends Heroi {

    public Arqueiro(String nome) {
        super(nome, 100, 16, 5, "Disparo Duplo", 3);
    }

    @Override
    public String getClasse() {
        return "Arqueiro";
    }

    @Override
    protected String executarHabilidade(Combatente alvo, CalculadoraDeDano calculadora) {
        int primeiro = calculadora.calcular(getAtaque(), alvo.getDefesa());
        alvo.receberDano(primeiro);
        int segundo = calculadora.calcular(getAtaque(), alvo.getDefesa());
        alvo.receberDano(segundo);
        return getNome() + " usa DISPARO DUPLO em " + alvo.getNome() + ": "
                + primeiro + " + " + segundo + " = " + (primeiro + segundo) + " de dano!";
    }
}
