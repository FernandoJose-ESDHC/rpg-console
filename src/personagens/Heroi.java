package personagens;

import combate.CalculadoraDeDano;
import itens.Arma;
import itens.Armadura;
import itens.Inventario;

/**
 * Classe abstrata para os personagens controlados pelo jogador.
 *
 * Um herói é um Personagem (herança) que, além disso:
 * - pode ser curado (implementa Curavel);
 * - tem uma habilidade especial com recarga (implementa UsaHabilidade);
 * - tem um inventário e pode equipar uma arma e uma armadura (composição).
 *
 * Cada classe concreta (Guerreiro, Mago, Arqueiro) só precisa dizer
 * O QUE a sua habilidade faz, no método executarHabilidade().
 */
public abstract class Heroi extends Personagem implements Curavel, UsaHabilidade {

    // Composição: o herói "tem um" inventário e "tem" equipamentos.
    private final Inventario inventario = new Inventario();
    private Arma arma;           // pode ser null (sem arma equipada)
    private Armadura armadura;   // pode ser null (sem armadura equipada)

    private final String nomeHabilidade;
    private final int recargaMaxima;   // rodadas de espera após usar a habilidade
    private int recargaRestante = 0;   // começa em 0: habilidade pronta na 1ª rodada

    protected Heroi(String nome, int vidaMaxima, int ataque, int defesa,
                    String nomeHabilidade, int recargaMaxima) {
        super(nome, vidaMaxima, ataque, defesa);
        this.nomeHabilidade = nomeHabilidade;
        this.recargaMaxima = recargaMaxima;
    }

    /** Nome da classe do herói, ex.: "Guerreiro". Cada subclasse informa o seu. */
    public abstract String getClasse();

    /** O que a habilidade especial faz. Cada subclasse implementa do seu jeito (polimorfismo). */
    protected abstract String executarHabilidade(Combatente alvo, CalculadoraDeDano calculadora);

    // ---------- Atributos com bônus de equipamento ----------

    /** Ataque = ataque base + bônus da arma (se houver). */
    @Override
    public int getAtaque() {
        int bonus = (arma != null) ? arma.getBonus() : 0;
        return super.getAtaque() + bonus;
    }

    /** Defesa = defesa base + bônus da armadura (se houver). */
    @Override
    public int getDefesa() {
        int bonus = (armadura != null) ? armadura.getBonus() : 0;
        return super.getDefesa() + bonus;
    }

    // ---------- Curavel ----------

    @Override
    public int curar(int quantidade) {
        int vidaAntes = getVida();
        definirVida(getVida() + quantidade); // definirVida não deixa passar do máximo
        return getVida() - vidaAntes;
    }

    // ---------- UsaHabilidade ----------

    @Override
    public String getNomeHabilidade() {
        return nomeHabilidade;
    }

    @Override
    public int getRecargaRestante() {
        return recargaRestante;
    }

    @Override
    public boolean habilidadePronta() {
        return recargaRestante == 0;
    }

    /**
     * Regra igual para todos os heróis: só usa se estiver pronta e,
     * depois de usar, entra em recarga. O efeito em si fica com a subclasse.
     */
    @Override
    public String usarHabilidade(Combatente alvo, CalculadoraDeDano calculadora) {
        if (!habilidadePronta()) {
            return nomeHabilidade + " ainda está em recarga (" + recargaRestante + " rodada(s)).";
        }
        recargaRestante = recargaMaxima;
        return executarHabilidade(alvo, calculadora);
    }

    @Override
    public void avancarTurno() {
        if (recargaRestante > 0) {
            recargaRestante--;
        }
    }

    // ---------- Inventário e equipamentos ----------

    public Inventario getInventario() {
        return inventario;
    }

    public Arma getArma() {
        return arma;
    }

    public Armadura getArmadura() {
        return armadura;
    }

    /** Equipa a nova arma e devolve a anterior (ou null), para voltar ao inventário. */
    public Arma equiparArma(Arma novaArma) {
        Arma anterior = this.arma;
        this.arma = novaArma;
        return anterior;
    }

    /** Equipa a nova armadura e devolve a anterior (ou null). */
    public Armadura equiparArmadura(Armadura novaArmadura) {
        Armadura anterior = this.armadura;
        this.armadura = novaArmadura;
        return anterior;
    }
}
