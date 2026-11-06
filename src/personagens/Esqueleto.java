package personagens;

import itens.Item;

/** Inimigo simples: um pouco mais resistente que o Goblin. Usa o ataque padrão de Inimigo. */
public class Esqueleto extends Inimigo {

    public Esqueleto(Item recompensa) {
        super("Esqueleto", 60, 14, 5, recompensa);
    }
}
