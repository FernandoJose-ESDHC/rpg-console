package itens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import personagens.Heroi;

/**
 * Guarda os itens do herói.
 *
 * SOLID - Single Responsibility (S): esta classe só cuida da lista de
 * itens (adicionar, listar, usar/remover). Ela não lê teclado nem imprime
 * nada; quem mostra o menu é a classe ui.MenuDeInventario.
 */
public class Inventario {

    private final List<Item> itens = new ArrayList<>();

    public void adicionar(Item item) {
        itens.add(item);
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    public int quantidade() {
        return itens.size();
    }

    /**
     * Devolve a lista apenas para LEITURA (encapsulamento):
     * quem chama consegue ver os itens, mas não consegue alterar a lista.
     */
    public List<Item> getItens() {
        return Collections.unmodifiableList(itens);
    }

    /**
     * Retira o item da posição indicada e aplica o efeito no herói.
     * O item é retirado ANTES de usar: poções somem, e equipamentos passam
     * para o "corpo" do herói (o equipamento antigo volta para cá).
     */
    public String usarItem(int indice, Heroi heroi) {
        Item item = itens.remove(indice);
        return item.usar(heroi);
    }
}
