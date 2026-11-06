package jogo;

import java.util.List;

import combate.Batalha;
import combate.CalculadoraDeDano;
import itens.Armadura;
import itens.Item;
import itens.PocaoDeCura;
import personagens.Arqueiro;
import personagens.Guerreiro;
import personagens.Heroi;
import personagens.Inimigo;
import personagens.Mago;
import ui.EntradaSaida;
import ui.Formatador;
import ui.MenuDeInventario;

/**
 * Controla a partida inteira: criação do herói, sequência de batalhas,
 * acampamento entre as batalhas e o final (vitória ou derrota).
 *
 * A lista de inimigos vem de fora (do Main). Para mudar a campanha, basta
 * mudar a lista no Main; esta classe não precisa ser alterada (O e D).
 */
public class Jogo {

    private final EntradaSaida io;
    private final CalculadoraDeDano calculadora;
    private final List<Inimigo> inimigos;
    private final MenuDeInventario menuDeInventario;

    public Jogo(EntradaSaida io, CalculadoraDeDano calculadora, List<Inimigo> inimigos) {
        this.io = io;
        this.calculadora = calculadora;
        this.inimigos = inimigos;
        this.menuDeInventario = new MenuDeInventario(io);
    }

    /** Ponto de partida da partida. */
    public void iniciar() {
        mostrarTitulo();
        Heroi heroi = criarHeroi();
        darItensIniciais(heroi);

        for (int i = 0; i < inimigos.size(); i++) {
            Inimigo inimigo = inimigos.get(i);
            io.mostrar("\nBatalha " + (i + 1) + " de " + inimigos.size()
                    + ": um " + inimigo.getNome() + " aparece!");

            Batalha batalha = new Batalha(heroi, inimigo, io, calculadora);
            boolean venceu = batalha.executar();

            if (!venceu) {
                io.mostrar("\nFIM DE JOGO. Tente outra estratégia (use as habilidades e os itens!).");
                return;
            }

            receberRecompensa(heroi, inimigo);

            // Não há acampamento depois da última batalha.
            boolean temProximaBatalha = i < inimigos.size() - 1;
            if (temProximaBatalha) {
                acampamento(heroi);
            }
        }

        io.mostrar("\n==========================================");
        io.mostrar("  VITÓRIA! " + heroi.getNome() + " derrotou todos os inimigos!");
        io.mostrar("==========================================");
    }

    private void mostrarTitulo() {
        io.mostrar("==========================================");
        io.mostrar("        RPG DE CONSOLE - A TORRE DO DRAGÃO");
        io.mostrar("==========================================");
        io.mostrar("Vença " + inimigos.size() + " batalhas por turnos para derrotar o Dragão Ancestral.");
    }

    /** Pergunta o nome e a classe e cria o herói correspondente. */
    private Heroi criarHeroi() {
        String nome = io.lerTexto("\nDigite o nome do seu herói:");
        if (nome.isEmpty()) {
            nome = "Herói"; // nome padrão se o jogador só apertar ENTER
        }

        io.mostrar("\nEscolha a classe:");
        io.mostrar("  1) Guerreiro - Vida 120 | ATQ 14 | DEF 8 | Golpe Poderoso (dano dobrado)");
        io.mostrar("  2) Mago      - Vida  90 | ATQ 19 | DEF 4 | Bola de Fogo (dano dobrado, ignora defesa)");
        io.mostrar("  3) Arqueiro  - Vida 100 | ATQ 16 | DEF 5 | Disparo Duplo (dois ataques)");
        int opcao = io.lerOpcao("Classe:", 1, 3);

        Heroi heroi;
        switch (opcao) {
            case 1 -> heroi = new Guerreiro(nome);
            case 2 -> heroi = new Mago(nome);
            default -> heroi = new Arqueiro(nome);
        }
        io.mostrar("\n" + heroi.getNome() + ", o " + heroi.getClasse() + ", inicia a jornada!");
        return heroi;
    }

    /** Todo herói começa com duas poções e uma armadura simples no inventário. */
    private void darItensIniciais(Heroi heroi) {
        heroi.getInventario().adicionar(new PocaoDeCura("Poção de Cura", 35));
        heroi.getInventario().adicionar(new PocaoDeCura("Poção de Cura", 35));
        heroi.getInventario().adicionar(new Armadura("Armadura de Couro", 3));
        io.mostrar("Itens iniciais: 2 Poções de Cura e 1 Armadura de Couro (equipe pelo inventário).");
    }

    /** Se o inimigo derrotado deixou um item, ele vai para o inventário. */
    private void receberRecompensa(Heroi heroi, Inimigo inimigo) {
        Item recompensa = inimigo.getRecompensa();
        if (recompensa != null) {
            heroi.getInventario().adicionar(recompensa);
            io.mostrar("Recompensa: " + recompensa.getNome() + " (" + recompensa.getDescricao()
                    + ") foi para o inventário.");
        }
    }

    /** Pausa entre batalhas: ver status, usar itens ou seguir em frente. */
    private void acampamento(Heroi heroi) {
        boolean seguir = false;
        while (!seguir) {
            io.mostrar("\n~~~ Acampamento ~~~");
            io.mostrar("  1) Seguir para a próxima batalha");
            io.mostrar("  2) Abrir inventário");
            io.mostrar("  3) Ver status do herói");
            int opcao = io.lerOpcao("Escolha:", 1, 3);

            switch (opcao) {
                case 1 -> seguir = true;
                case 2 -> menuDeInventario.abrir(heroi);
                case 3 -> mostrarStatusCompleto(heroi);
            }
        }
    }

    private void mostrarStatusCompleto(Heroi heroi) {
        io.mostrar("\n" + Formatador.linhaDeStatus(heroi.getNome() + " (" + heroi.getClasse() + ")", heroi));
        String arma = heroi.getArma() != null ? heroi.getArma().getNome() : "nenhuma";
        String armadura = heroi.getArmadura() != null ? heroi.getArmadura().getNome() : "nenhuma";
        io.mostrar("Arma: " + arma + " | Armadura: " + armadura);
        io.mostrar("Habilidade: " + heroi.getNomeHabilidade());
    }
}
