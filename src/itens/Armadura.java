package itens;

import personagens.Heroi;

/** Equipamento que aumenta a DEFESA do herói. */
public class Armadura extends Equipamento {

    public Armadura(String nome, int bonusDefesa) {
        super(nome, bonusDefesa);
    }

    @Override
    public String getDescricao() {
        return "armadura, +" + getBonus() + " de defesa";
    }

    @Override
    protected Equipamento equiparEm(Heroi heroi) {
        return heroi.equiparArmadura(this);
    }
}
