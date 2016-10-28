/**
 * Created by andresestari on 18/10/16.
 */
public enum EstadoEnum {

    VAZIO(0), OBSTACULO(1), DESTINO(2), ROBO(3);

    private int estado;

    EstadoEnum(int estado) {
        this.estado = estado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}