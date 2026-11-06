package personagens;

import combate.CalculadoraDeDano;
import itens.Item;

/**
 * Chefão final. É um Inimigo (herança) que também tem habilidade especial
 * (implementa UsaHabilidade).
 *
 * Sobrescreve agir(): quando o "Sopro de Fogo" está pronto ele usa a
 * habilidade; senão faz um ataque normal. A Batalha continua chamando só
 * inimigo.agir(...) - por isso o chefão pode substituir qualquer outro
 * inimigo sem quebrar nada (Liskov).
 */
public class ChefeDragao extends Inimigo implements UsaHabilidade {

    private static final int RECARGA_SOPRO = 3;
    private int recargaRestante = 2; // o primeiro sopro vem na 3ª rodada

    public ChefeDragao(Item recompensa) {
        super("Dragão Ancestral", 150, 17, 8, recompensa);
    }

    @Override
    public String agir(Combatente alvo, CalculadoraDeDano calculadora) {
        if (habilidadePronta()) {
            return usarHabilidade(alvo, calculadora);
        }
        String resultado = atacar(alvo, calculadora);
        avancarTurno();
        // Avisa o jogador quando o sopro vai acontecer na próxima rodada.
        if (habilidadePronta()) {
            resultado += "\n>> O Dragão inspira profundamente... o SOPRO DE FOGO vem na próxima rodada!";
        }
        return resultado;
    }

    @Override
    public String getNomeHabilidade() {
        return "Sopro de Fogo";
    }

    @Override
    public int getRecargaRestante() {
        return recargaRestante;
    }

    @Override
    public boolean habilidadePronta() {
        return recargaRestante == 0;
    }

    /** Sopro de Fogo: 1,5x o ataque, ignorando metade da defesa do alvo. */
    @Override
    public String usarHabilidade(Combatente alvo, CalculadoraDeDano calculadora) {
        recargaRestante = RECARGA_SOPRO;
        int dano = calculadora.calcular(getAtaque() * 3 / 2, alvo.getDefesa() / 2);
        alvo.receberDano(dano);
        return getNome() + " usa SOPRO DE FOGO em " + alvo.getNome()
                + " e causa " + dano + " de dano!";
    }

    @Override
    public void avancarTurno() {
        if (recargaRestante > 0) {
            recargaRestante--;
        }
    }
}
