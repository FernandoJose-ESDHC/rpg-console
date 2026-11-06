package itens;

import personagens.Heroi;

/**
 * Contrato de todo item do jogo.
 *
 * SOLID - Open/Closed (O): para criar um item novo (ex.: um elixir de
 * ataque), basta criar uma classe que implemente Item. O Inventario e os
 * menus continuam funcionando sem nenhuma alteração.
 */
public interface Item {

    String getNome();

    /** Texto curto explicando o efeito, mostrado no menu do inventário. */
    String getDescricao();

    /** Aplica o efeito do item no herói e devolve a descrição do que aconteceu. */
    String usar(Heroi heroi);
}
