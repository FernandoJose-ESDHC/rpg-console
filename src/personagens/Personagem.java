package personagens;

import combate.CalculadoraDeDano;

/**
 * Classe ABSTRATA com tudo o que heróis e inimigos têm em comum.
 *
 * - Abstração: não existe um "Personagem" genérico no jogo; só se criam
 *   subclasses concretas (Guerreiro, Goblin...). Por isso a classe é abstract.
 * - Encapsulamento: os atributos são private. Ninguém de fora altera a vida
 *   diretamente; é preciso passar por receberDano() ou pelo método protegido
 *   definirVida(), que garante que a vida fique entre 0 e a vida máxima.
 * - Herança: Heroi e Inimigo herdam desta classe e reaproveitam o código.
 */
public abstract class Personagem implements Combatente {

    private final String nome;
    private final int vidaMaxima;
    private int vida;
    private final int ataque;
    private final int defesa;

    protected Personagem(String nome, int vidaMaxima, int ataque, int defesa) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima; // todo personagem começa com a vida cheia
        this.ataque = ataque;
        this.defesa = defesa;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getVida() {
        return vida;
    }

    @Override
    public int getVidaMaxima() {
        return vidaMaxima;
    }

    /** Ataque base. A classe Heroi sobrescreve para somar o bônus da arma. */
    @Override
    public int getAtaque() {
        return ataque;
    }

    /** Defesa base. A classe Heroi sobrescreve para somar o bônus da armadura. */
    @Override
    public int getDefesa() {
        return defesa;
    }

    @Override
    public boolean estaVivo() {
        return vida > 0;
    }

    @Override
    public void receberDano(int dano) {
        definirVida(vida - dano);
    }

    /**
     * Ataque normal: pede à calculadora o valor do dano, aplica no alvo
     * e devolve uma frase para ser exibida. Serve para heróis e inimigos.
     */
    @Override
    public String atacar(Combatente alvo, CalculadoraDeDano calculadora) {
        int dano = calculadora.calcular(getAtaque(), alvo.getDefesa());
        alvo.receberDano(dano);
        return nome + " ataca " + alvo.getNome() + " e causa " + dano + " de dano.";
    }

    /**
     * Único jeito das subclasses mudarem a vida.
     * Mantém o valor sempre entre 0 e a vida máxima (encapsulamento).
     */
    protected void definirVida(int novaVida) {
        if (novaVida < 0) {
            novaVida = 0;
        }
        if (novaVida > vidaMaxima) {
            novaVida = vidaMaxima;
        }
        this.vida = novaVida;
    }
}
