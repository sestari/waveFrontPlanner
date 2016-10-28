/**
 * Created by andresestari on 18/10/16.
 */
public class Estado {

    private EstadoEnum estadoEnum;
    private int valor = 0;

    public Estado(EstadoEnum estadoEnum){
        if(estadoEnum == EstadoEnum.OBSTACULO){
            valor = -1;
        }else  if(estadoEnum == EstadoEnum.ROBO){
            valor = 2;
        }
        this.estadoEnum = estadoEnum;
    }

    public EstadoEnum getEstadoEnum() {
        return estadoEnum;
    }

    public void setEstadoEnum(EstadoEnum estadoEnum) {
        this.estadoEnum = estadoEnum;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
