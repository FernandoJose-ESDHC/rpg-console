import java.util.List;
import java.util.Random;

import combate.CalculadoraDeDano;
import combate.CalculadoraPadrao;
import itens.Arma;
import itens.PocaoDeCura;
import jogo.Jogo;
import personagens.ChefeDragao;
import personagens.Esqueleto;
import personagens.Goblin;
import personagens.Inimigo;
import ui.Console;
import ui.EntradaSaida;

/**
 * Ponto de entrada do programa.
 *
 * O Main é o "montador" do jogo: é o único lugar que escolhe as classes
 * CONCRETAS (Console, CalculadoraPadrao, quais inimigos) e entrega tudo
 * pronto para o Jogo pelo construtor. Isso é a Inversão de Dependência (D):
 * o Jogo e a Batalha só conhecem as interfaces.
 *
 * Uso:
 *   java -cp out Main        -> partida aleatória
 *   java -cp out Main 42     -> partida com semente 42 (sempre igual, útil para testes)
 */
public class Main {

    public static void main(String[] args) {
        Random random = (args.length > 0) ? new Random(Long.parseLong(args[0])) : new Random();

        EntradaSaida io = new Console();
        CalculadoraDeDano calculadora = new CalculadoraPadrao(random);

        // A campanha: dois inimigos comuns e o chefão, cada um com sua recompensa.
        List<Inimigo> inimigos = List.of(
                new Goblin(new PocaoDeCura("Poção de Cura", 35)),
                new Esqueleto(new Arma("Espada de Aço", 6)),
                new ChefeDragao(null) // o chefão é a última batalha, não precisa deixar item
        );

        Jogo jogo = new Jogo(io, calculadora, inimigos);
        jogo.iniciar();
    }
}
