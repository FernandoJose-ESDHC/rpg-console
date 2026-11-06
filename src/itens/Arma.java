package itens;

import personagens.Heroi;

/** Equipamento que aumenta o ATAQUE do herói. */
public class Arma extends Equipamento {

    public Arma(String nome, int bonusAtaque) {
        super(nome, bonusAtaque);
    }

    @Override
    public String getDescricao() {
        return "arma, +" + getBonus() + " de ataque";
    }

    @Override
    protected Equipamento equiparEm(Heroi heroi) {
        return heroi.equiparArma(this);
    }
}
