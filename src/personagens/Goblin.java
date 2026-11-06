package personagens;

import itens.Item;

/** Inimigo simples: fraco e rápido de derrotar. Usa o ataque padrão de Inimigo. */
public class Goblin extends Inimigo {

    public Goblin(Item recompensa) {
        super("Goblin", 45, 12, 3, recompensa);
    }
}
