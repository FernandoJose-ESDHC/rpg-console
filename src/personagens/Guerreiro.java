package personagens;

import combate.CalculadoraDeDano;

/**
 * Guerreiro: muita vida e defesa, ataque médio.
 * Habilidade "Golpe Poderoso": ataca com o DOBRO do ataque.
 */
public class Guerreiro extends Heroi {

    public Guerreiro(String nome) {
        // nome, vida, ataque, defesa, habilidade, recarga
        super(nome, 120, 14, 8, "Golpe Poderoso", 3);
    }

    @Override
    public String getClasse() {
        return "Guerreiro";
    }

    @Override
    protected String executarHabilidade(Combatente alvo, CalculadoraDeDano calculadora) {
        int dano = calculadora.calcular(getAtaque() * 2, alvo.getDefesa());
        alvo.receberDano(dano);
        return getNome() + " usa GOLPE PODEROSO em " + alvo.getNome()
                + " e causa " + dano + " de dano!";
    }
}
