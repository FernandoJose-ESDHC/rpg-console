import combate.CalculadoraDeDano;
import itens.Arma;
import personagens.Arqueiro;
import personagens.ChefeDragao;
import personagens.Goblin;
import personagens.Guerreiro;
import personagens.Mago;

/**
 * Testes simples das regras do jogo, sem bibliotecas externas.
 * Cada verificação imprime PASSOU ou FALHOU.
 *
 * Como rodar (a partir da pasta do projeto, depois de compilar o jogo em out/):
 *   javac -encoding UTF-8 -cp out -d out testes/TesteRegras.java
 *   java -cp out TesteRegras
 */
public class TesteRegras {

    private static int passou = 0;
    private static int falhou = 0;

    /**
     * Calculadora "falsa" que sempre causa 1 de dano.
     * Só é possível trocar a regra assim porque o jogo depende da INTERFACE
     * CalculadoraDeDano (Inversão de Dependência): o teste fica previsível.
     */
    static class DanoFixo implements CalculadoraDeDano {
        @Override
        public int calcular(int ataque, int defesa) {
            return 1;
        }
    }

    private static void verificar(String descricao, boolean condicao) {
        System.out.println((condicao ? "PASSOU  " : "FALHOU  ") + descricao);
        if (condicao) {
            passou++;
        } else {
            falhou++;
        }
    }

    public static void main(String[] args) {
        CalculadoraDeDano danoFixo = new DanoFixo();

        // 1 e 2: a vida fica sempre entre 0 e o máximo (encapsulamento)
        Guerreiro guerreiro = new Guerreiro("Teste");
        verificar("cura com vida cheia recupera 0", guerreiro.curar(35) == 0 && guerreiro.getVida() == 120);
        guerreiro.receberDano(500);
        verificar("vida nunca fica negativa", guerreiro.getVida() == 0 && !guerreiro.estaVivo());

        // 3: a poção não passa da vida máxima
        Mago mago = new Mago("Teste");
        mago.receberDano(20);                       // 90 -> 70
        verificar("cura não passa do máximo (70 + 35 -> 90)", mago.curar(35) == 20 && mago.getVida() == 90);

        // 4 e 5: equipamentos somam bônus e a arma antiga volta para o inventário
        Arqueiro arqueiro = new Arqueiro("Teste");
        arqueiro.getInventario().adicionar(new Arma("Arco Curto", 2));
        arqueiro.getInventario().adicionar(new Arma("Arco Longo", 5));
        arqueiro.getInventario().usarItem(0, arqueiro);   // equipa o Arco Curto
        verificar("arma soma no ataque (16 + 2 = 18)", arqueiro.getAtaque() == 18);
        arqueiro.getInventario().usarItem(0, arqueiro);   // troca pelo Arco Longo
        verificar("trocar de arma devolve a antiga ao inventário",
                arqueiro.getAtaque() == 21
                && arqueiro.getInventario().quantidade() == 1
                && arqueiro.getInventario().getItens().get(0).getNome().equals("Arco Curto"));

        // 6: a lista do inventário não pode ser alterada por fora
        boolean somenteLeitura;
        try {
            arqueiro.getInventario().getItens().clear();
            somenteLeitura = false;
        } catch (UnsupportedOperationException e) {
            somenteLeitura = true;
        }
        verificar("lista do inventário é somente leitura", somenteLeitura);

        // 7 e 8: recarga da habilidade
        Goblin goblin = new Goblin(null);               // 45 de vida
        arqueiro.usarHabilidade(goblin, danoFixo);      // Disparo Duplo: 1 + 1 de dano
        verificar("habilidade entra em recarga após o uso", !arqueiro.habilidadePronta() && arqueiro.getRecargaRestante() == 3);
        String resposta = arqueiro.usarHabilidade(goblin, danoFixo);
        verificar("usar a habilidade em recarga não causa dano",
                resposta.contains("ainda está em recarga") && goblin.getVida() == 43);

        // 9: o Dragão usa o Sopro de Fogo nas rodadas 3 e 7
        ChefeDragao dragao = new ChefeDragao(null);
        Guerreiro alvo = new Guerreiro("Alvo");
        StringBuilder sequencia = new StringBuilder();
        for (int rodada = 1; rodada <= 7; rodada++) {
            boolean usouSopro = dragao.agir(alvo, danoFixo).contains("SOPRO DE FOGO em");
            sequencia.append(usouSopro ? "S" : "n");
        }
        verificar("Dragão usa o sopro nas rodadas 3 e 7 (" + sequencia + ")", sequencia.toString().equals("nnSnnnS"));

        System.out.println("\n" + passou + " passaram, " + falhou + " falharam.");
    }
}
