package combate;

import personagens.Heroi;
import personagens.Inimigo;
import ui.EntradaSaida;
import ui.Formatador;
import ui.MenuDeInventario;

/**
 * Controla UMA batalha por turnos entre o herói e um inimigo.
 *
 * SOLID aplicado aqui:
 * - S: a classe só cuida da ordem dos turnos. O cálculo do dano está na
 *   CalculadoraDeDano e a leitura/escrita de texto está em EntradaSaida.
 * - O: funciona com qualquer inimigo novo sem ser alterada, porque só
 *   chama inimigo.agir(...).
 * - L: recebe um Inimigo qualquer (Goblin, Esqueleto ou ChefeDragao) e um
 *   Heroi qualquer (Guerreiro, Mago ou Arqueiro) - todos funcionam igual.
 * - D: depende das interfaces EntradaSaida e CalculadoraDeDano, recebidas
 *   pelo construtor, e não de Console/CalculadoraPadrao diretamente.
 */
public class Batalha {

    private final Heroi heroi;
    private final Inimigo inimigo;
    private final EntradaSaida io;
    private final CalculadoraDeDano calculadora;
    private final MenuDeInventario menuDeInventario;
    private int rodada = 1;

    public Batalha(Heroi heroi, Inimigo inimigo, EntradaSaida io,
                   CalculadoraDeDano calculadora) {
        this.heroi = heroi;
        this.inimigo = inimigo;
        this.io = io;
        this.calculadora = calculadora;
        this.menuDeInventario = new MenuDeInventario(io);
    }

    /**
     * Executa a batalha até alguém cair.
     * Retorna true se o herói venceu e false se foi derrotado.
     */
    public boolean executar() {
        io.mostrar("\n==========================================");
        io.mostrar("  BATALHA: " + heroi.getNome() + " x " + inimigo.getNome());
        io.mostrar("==========================================");

        while (heroi.estaVivo() && inimigo.estaVivo()) {
            mostrarStatus();
            turnoDoHeroi();

            // O inimigo só age se ainda estiver vivo depois do ataque do herói.
            if (inimigo.estaVivo()) {
                io.mostrar(inimigo.agir(heroi, calculadora));
            }

            heroi.avancarTurno(); // diminui a recarga da habilidade
            rodada++;
        }

        if (heroi.estaVivo()) {
            io.mostrar("\n*** " + inimigo.getNome() + " foi derrotado! ***");
            return true;
        }
        io.mostrar("\n*** " + heroi.getNome() + " caiu em combate... ***");
        return false;
    }

    /** Mostra a rodada e a vida/atributos dos dois lados. */
    private void mostrarStatus() {
        io.mostrar("\n--------------- Rodada " + rodada + " ---------------");
        io.mostrar(Formatador.linhaDeStatus(heroi.getNome() + " (" + heroi.getClasse() + ")", heroi));
        io.mostrar(Formatador.linhaDeStatus(inimigo.getNome(), inimigo));
    }

    /**
     * Turno do jogador. Repete o menu até ele fazer uma ação válida
     * (ex.: se tentar usar a habilidade em recarga, pode escolher de novo).
     */
    private void turnoDoHeroi() {
        boolean acaoRealizada = false;

        while (!acaoRealizada) {
            io.mostrar("\nO que " + heroi.getNome() + " vai fazer?");
            io.mostrar("  1) Atacar");
            io.mostrar("  2) " + heroi.getNomeHabilidade() + " " + textoDaRecarga());
            io.mostrar("  3) Usar item (" + heroi.getInventario().quantidade() + " no inventário)");
            int opcao = io.lerOpcao("Escolha:", 1, 3);

            switch (opcao) {
                case 1 -> {
                    io.mostrar(heroi.atacar(inimigo, calculadora));
                    acaoRealizada = true;
                }
                case 2 -> {
                    if (heroi.habilidadePronta()) {
                        io.mostrar(heroi.usarHabilidade(inimigo, calculadora));
                        acaoRealizada = true;
                    } else {
                        io.mostrar("A habilidade ainda não está pronta. Escolha outra ação.");
                    }
                }
                case 3 -> acaoRealizada = menuDeInventario.abrir(heroi);
            }
        }
    }

    /** Texto que aparece ao lado da habilidade no menu. */
    private String textoDaRecarga() {
        if (heroi.habilidadePronta()) {
            return "[PRONTA]";
        }
        return "[recarga: " + heroi.getRecargaRestante() + " rodada(s)]";
    }
}
