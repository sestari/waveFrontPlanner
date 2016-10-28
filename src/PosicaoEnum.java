/**
 * Created by andresestari on 22/10/16.
 */
public enum PosicaoEnum {

    TOPO(0), RODAPE(1), ESQUERDA(2), DIREITA(3);

    private int posicao;

    PosicaoEnum(int estado) {
        this.posicao = estado;
    }

    public int getEstado() {
        return posicao;
    }

    public void setEstado(int estado) {
        this.posicao = estado;
    }

}
