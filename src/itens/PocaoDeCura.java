package itens;

import personagens.Heroi;

/** Item consumível que recupera uma quantidade fixa de vida. */
public class PocaoDeCura implements Item {

    private final String nome;
    private final int quantidade;

    public PocaoDeCura(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDescricao() {
        return "recupera até " + quantidade + " de vida";
    }

    @Override
    public String usar(Heroi heroi) {
        int recuperado = heroi.curar(quantidade);
        return heroi.getNome() + " bebe " + nome + " e recupera " + recuperado + " de vida.";
    }
}
