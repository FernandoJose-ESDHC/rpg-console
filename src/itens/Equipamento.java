package itens;

import personagens.Heroi;

/**
 * Classe abstrata para itens que o herói EQUIPA (arma, armadura).
 * Guarda o nome e o bônus; cada subclasse decide em qual "espaço" equipar.
 */
public abstract class Equipamento implements Item {

    private final String nome;
    private final int bonus;

    protected Equipamento(String nome, int bonus) {
        this.nome = nome;
        this.bonus = bonus;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public int getBonus() {
        return bonus;
    }

    /**
     * Equipa no herói. Se ele já tinha um equipamento no mesmo espaço,
     * o antigo volta para o inventário (não se perde).
     */
    @Override
    public String usar(Heroi heroi) {
        Equipamento anterior = equiparEm(heroi);
        String mensagem = heroi.getNome() + " equipa " + nome + ".";
        if (anterior != null) {
            heroi.getInventario().adicionar(anterior);
            mensagem += " (" + anterior.getNome() + " voltou para o inventário)";
        }
        return mensagem;
    }

    /** Coloca este equipamento no herói e devolve o que estava antes (ou null). */
    protected abstract Equipamento equiparEm(Heroi heroi);
}
