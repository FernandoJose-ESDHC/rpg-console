package personagens;

import combate.CalculadoraDeDano;

/**
 * Mago: pouca vida e defesa, ataque alto.
 * Habilidade "Bola de Fogo": dano mágico de 2x o ataque que IGNORA a defesa do alvo.
 */
public class Mago extends Heroi {

    public Mago(String nome) {
        super(nome, 90, 19, 4, "Bola de Fogo", 3);
    }

    @Override
    public String getClasse() {
        return "Mago";
    }

    @Override
    protected String executarHabilidade(Combatente alvo, CalculadoraDeDano calculadora) {
        int ataqueMagico = getAtaque() * 2;                 // dobro do ataque
        int dano = calculadora.calcular(ataqueMagico, 0);   // defesa 0 = ignora a defesa
        alvo.receberDano(dano);
        return getNome() + " lança BOLA DE FOGO em " + alvo.getNome()
                + " e causa " + dano + " de dano (ignora a defesa)!";
    }
}
