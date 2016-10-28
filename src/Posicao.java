/**
 * Created by andresestari on 22/10/16.
 */
public class Posicao {

    int x;
    int y;
    PosicaoEnum posicao;


    public Posicao(int x, int y, PosicaoEnum posicao){
        this.x = x;
        this.y = y;
        this.posicao = posicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Posicao posicao = (Posicao) o;

        if (x != posicao.x) return false;
        return y == posicao.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
