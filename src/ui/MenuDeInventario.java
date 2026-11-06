package ui;

import java.util.List;

import itens.Item;
import personagens.Heroi;

/**
 * Menu de texto do inventário. Usado na batalha e no acampamento.
 *
 * SOLID - Single Responsibility (S): o Inventario guarda os itens; esta
 * classe só MOSTRA a lista e pergunta qual item usar.
 */
public class MenuDeInventario {

    private final EntradaSaida io;

    public MenuDeInventario(EntradaSaida io) {
        this.io = io;
    }

    /**
     * Mostra os itens e deixa o jogador escolher um.
     * Retorna true se algum item foi usado e false se o jogador voltou
     * (ou se o inventário estava vazio).
     */
    public boolean abrir(Heroi heroi) {
        if (heroi.getInventario().estaVazio()) {
            io.mostrar("O inventário está vazio.");
            return false;
        }

        List<Item> itens = heroi.getInventario().getItens();
        io.mostrar("\n--- Inventário de " + heroi.getNome() + " ---");
        for (int i = 0; i < itens.size(); i++) {
            Item item = itens.get(i);
            io.mostrar("  " + (i + 1) + ") " + item.getNome() + " - " + item.getDescricao());
        }
        io.mostrar("  0) Voltar");

        int escolha = io.lerOpcao("Qual item usar?", 0, itens.size());
        if (escolha == 0) {
            return false;
        }
        // O jogador vê a lista começando em 1, mas a lista em Java começa em 0.
        io.mostrar(heroi.getInventario().usarItem(escolha - 1, heroi));
        return true;
    }
}
