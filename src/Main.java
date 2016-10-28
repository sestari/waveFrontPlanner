import java.util.ArrayList;
import java.util.List;

//import lejos.nxt.Motor;

public class Main {

    // y,x
    private Estado estados[][] = new Estado[7][7];

    private int posicaoX;
    private int posicaoY;

    List<Posicao> posicao = new ArrayList<Posicao>();

    private int posicaoDestinoX;
    private int posicaoDestinoY;

    public void init() {

        populaFake();
        identificaRobo();
        waveFrontPlanner();
        imprimirEstado();


        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                if (estados[y][x].getEstadoEnum() == EstadoEnum.DESTINO) {
                    posicaoDestinoX = x;
                    posicaoDestinoY = y;
                    break;
                }
            }
        }

        percorreMenorCaminho(posicaoDestinoY, posicaoDestinoX);
        Posicao posicaoAtual = new Posicao(posicao.get(posicao.size() - 1).x, posicao.get(posicao.size() - 1).y, PosicaoEnum.RODAPE);

        for (int i = posicao.size() - 1; i >= 0; i--) {
            if (posicaoAtual.posicao != posicao.get(i).posicao) {
                if (posicao.get(i).posicao == PosicaoEnum.DIREITA) {
                    direita();
                } else if (posicao.get(i).posicao == PosicaoEnum.ESQUERDA) {
                    esquerda();
                } else {

                    if (posicao.get(i).posicao == PosicaoEnum.TOPO) {
                        if (posicaoAtual.posicao == PosicaoEnum.RODAPE) {
                            esquerda();
                            esquerda();
                        } else {
                            if (posicao.get(i).x > posicaoAtual.x) {
                                esquerda();
                            } else {
                                direita();
                            }
                        }
                    } else if (posicao.get(i).posicao == PosicaoEnum.RODAPE) {
                        if (posicaoAtual.posicao == PosicaoEnum.RODAPE) {
                            esquerda();
                            esquerda();
                        } else {
                            if (posicao.get(i).x > posicaoAtual.x) {
                                direita();
                            } else {
                                esquerda();
                            }
                        }
                    }
                }
            }

            posicaoAtual = posicao.get(i);

            // Motor.A.rotate(-560, true);
            // Motor.B.rotate(-560);
            System.out.println("ANDA");
        }
    }

    public void direita() {
        System.out.println("DIREITA");
        /*Motor.A.rotate(30, true);
        Motor.B.rotate(30);
        Motor.B.rotate(290);
        Motor.A.rotate(-290);

        Motor.A.rotate(-30, true);
        Motor.B.rotate(-30);*/

    }

    public void esquerda() {
        System.out.println("ESQUERDA");
        /*Motor.A.rotate(30, true);
        Motor.B.rotate(30);

        Motor.A.rotate(290);
        Motor.B.rotate(-290);

        Motor.A.rotate(-35, true);
        Motor.B.rotate(-35);*/

    }

    public void percorreMenorCaminho(int y, int x) {

        Posicao posicao = retornaMenorCaminho(y, x);

        if (estados[y][x].getEstadoEnum() != EstadoEnum.ROBO) {
            System.out.println(posicao.y + ":" + posicao.x + " - "
                    + posicao.posicao.toString());
            this.posicao.add(posicao);
            percorreMenorCaminho(posicao.y, posicao.x);
        }
    }

    public void imprimirEstado() {

        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                if (estados[y][x].getEstadoEnum() == EstadoEnum.ROBO) {
                    System.out.print("(" + estados[y][x].getValor() + ")");
                } else {
                    if (estados[y][x].getValor() < 0
                            || estados[y][x].getValor() > 9) {
                        System.out.print(" " + estados[y][x].getValor() + " ");
                    } else {
                        System.out.print(" 0" + estados[y][x].getValor() + " ");
                    }
                }

            }
            System.out.println("");
        }

        System.out.println("");
        System.out.println("");

        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                if (estados[y][x].getEstadoEnum() == EstadoEnum.ROBO) {
                    System.out.print(" RO ");
                } else if (estados[y][x].getEstadoEnum() == EstadoEnum.DESTINO) {
                    System.out.print(" DE ");
                } else if (estados[y][x].getValor() == -1) {
                    System.out.print(" ** ");
                } else if (estados[y][x].getValor() > 0) {

                    if(posicao.equals(estados[y][x])){
                        System.out.print(" -- ");
                    }else {
                       System.out.print(" 00 ");
                    }
                }

            }
            System.out.println("");
        }
    }

    public void waveFrontPlanner() {

        int incremento;

        boolean para = false;

        //MAPEAMENTO INICIAL
      /*  for (int y = 0; y < estados.length; y++) {
            if(para){
                break;
            }

            incremento = (posicaoY - y);
            if (incremento < 0) {
                incremento = incremento * -1;
            }

            // esqueda
            int pos = 2;
            for (int x = posicaoX; x >= 0; x--) {
                if (estados[y][x].getEstadoEnum() == EstadoEnum.OBSTACULO) {
                    estados[y][x].setValor(-1);
                    para = true;
                    break;
                }
                estados[y][x].setValor(pos + incremento);
                pos++;
            }

            // esquerda
            pos = 2;
            for (int x = posicaoX; x < 7; x++) {
                if (estados[y][x].getEstadoEnum() == EstadoEnum.OBSTACULO) {
                    estados[y][x].setValor(-1);
                    para = true;
                    break;
                }
                estados[y][x].setValor(pos + incremento);
                pos++;
            }
        } */

        while(!todosMapeados()) {
            mapeiaHorizontal();
            mapeiaVertical();
        }


    }

    private boolean todosMapeados(){
        for (int y = 0; y < estados.length; y++) {
            for (int x = 0; x < estados.length; x++) {
                if(estados[y][x].getEstadoEnum() == EstadoEnum.VAZIO && estados[y][x].getValor() == 0){
                    return false;
                }
            }
        }
        return true;
    }

    private void mapeiaHorizontal() {
        int incremento = 0;

        // DIREITA PRA ESQUERDA
        for (int y = 0; y < estados.length; y++) {

            incremento = 0;

            for (int x = 6; x >= 0; x--) {
                if (estados[y][x].getEstadoEnum() == EstadoEnum.OBSTACULO) {
                    incremento = 0;
                } else if (incremento > 0 && estados[y][x].getValor() == 0) {
                    incremento++;
                    estados[y][x].setValor(incremento);
                } else if (estados[y][x].getValor() > 0) {
                    incremento = estados[y][x].getValor();
                }
            }
        }

        // ESQUERDA PRA DIREITA
        for (int y = 0; y < estados.length; y++) {

            incremento = 0;

            for (int x = 0; x < estados.length; x++) {
                if (estados[y][x].getEstadoEnum() == EstadoEnum.OBSTACULO) {
                    incremento = 0;
                } else if (incremento > 0 && estados[y][x].getValor() == 0) {
                    incremento++;
                    estados[y][x].setValor(incremento);
                } else if (estados[y][x].getValor() > 0) {
                    incremento = estados[y][x].getValor();
                }
            }
        }
    }

    private void mapeiaVertical() {
        int incremento = 0;

        // mepeia de cima pra baixo
        for (int x = 0; x < estados.length; x++) {
            incremento = 0;

            for (int y = 0; y < estados.length; y++) {

                if (estados[y][x].getEstadoEnum() == EstadoEnum.OBSTACULO) {
                    incremento = 0;
                } else if (incremento > 0 && estados[y][x].getValor() == 0) {
                    incremento++;
                    estados[y][x].setValor(incremento);

                } else if (estados[y][x].getValor() != 0) {

                    incremento = estados[y][x].getValor();

                }
            }
        }


        // mapeia de baixo pra cima
        for (int x = 0; x < estados.length; x++) {
            incremento = 0;
            for (int y = 6; y >= 0; y--) {

                if (estados[y][x].getEstadoEnum() == EstadoEnum.OBSTACULO) {
                    incremento = 0;
                } else if (incremento > 0 && estados[y][x].getValor() == 0) {
                    incremento++;
                    estados[y][x].setValor(incremento);

                } else if (estados[y][x].getValor() != 0) {

                    incremento = estados[y][x].getValor();

                }
            }
        }
    }

    public Posicao retornaMenorCaminho(int y, int x) {

        Integer valor = null;
        Integer xEscolhido = null;
        Integer yEscolhido = null;
        PosicaoEnum posicao = null;

        if ((x - 1) > -1) {
            if ((valor == null || (valor != null && estados[y][x - 1]
                    .getValor() < valor))
                    && estados[y][x - 1].getEstadoEnum() != EstadoEnum.OBSTACULO) {
                valor = estados[y][x - 1].getValor();
                xEscolhido = x - 1;
                yEscolhido = y;
                posicao = PosicaoEnum.DIREITA;
            }
        }

        if ((x + 1) < 7) {
            if ((valor == null || (valor != null && estados[y][x + 1]
                    .getValor() < valor))
                    && estados[y][x + 1].getEstadoEnum() != EstadoEnum.OBSTACULO) {
                valor = estados[y][x + 1].getValor();
                xEscolhido = x + 1;
                yEscolhido = y;
                posicao = PosicaoEnum.ESQUERDA;
            }
        }

        if ((y - 1) > -1) {
            if ((valor == null || (valor != null && estados[y - 1][x]
                    .getValor() < valor))
                    && estados[y - 1][x].getEstadoEnum() != EstadoEnum.OBSTACULO) {
                valor = estados[y - 1][x].getValor();
                xEscolhido = x;
                yEscolhido = y - 1;
                posicao = PosicaoEnum.RODAPE;
            }
        }

        if ((y + 1) < 7) {
            if ((valor == null || (valor != null && estados[y + 1][x]
                    .getValor() < valor))
                    && estados[y + 1][x].getEstadoEnum() != EstadoEnum.OBSTACULO) {
                valor = estados[y + 1][x].getValor();
                xEscolhido = x;
                yEscolhido = 1 + y;
                posicao = PosicaoEnum.TOPO;
            }
        }

        return new Posicao(xEscolhido, yEscolhido, posicao);
    }

    public void identificaRobo() {
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                if (estados[y][x].getEstadoEnum() == EstadoEnum.ROBO) {
                    posicaoX = x;
                    posicaoY = y;
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new Main().init();
    }

    private void populaFake() {
        estados[0][0] = new Estado(EstadoEnum.DESTINO);
        estados[0][1] = new Estado(EstadoEnum.OBSTACULO);
        estados[0][2] = new Estado(EstadoEnum.VAZIO);
        estados[0][3] = new Estado(EstadoEnum.VAZIO);
        estados[0][4] = new Estado(EstadoEnum.VAZIO);
        estados[0][5] = new Estado(EstadoEnum.VAZIO);
        estados[0][6] = new Estado(EstadoEnum.VAZIO);

        estados[1][0] = new Estado(EstadoEnum.VAZIO);
        estados[1][1] = new Estado(EstadoEnum.VAZIO);
        estados[1][2] = new Estado(EstadoEnum.VAZIO);
        estados[1][3] = new Estado(EstadoEnum.VAZIO);
        estados[1][4] = new Estado(EstadoEnum.OBSTACULO);
        estados[1][5] = new Estado(EstadoEnum.VAZIO);
        estados[1][6] = new Estado(EstadoEnum.VAZIO);

        estados[2][0] = new Estado(EstadoEnum.VAZIO);
        estados[2][1] = new Estado(EstadoEnum.OBSTACULO);
        estados[2][2] = new Estado(EstadoEnum.VAZIO);
        estados[2][3] = new Estado(EstadoEnum.VAZIO);
        estados[2][4] = new Estado(EstadoEnum.VAZIO);
        estados[2][5] = new Estado(EstadoEnum.VAZIO);
        estados[2][6] = new Estado(EstadoEnum.VAZIO);

        estados[3][0] = new Estado(EstadoEnum.OBSTACULO);
        estados[3][1] = new Estado(EstadoEnum.VAZIO);
        estados[3][2] = new Estado(EstadoEnum.VAZIO);
        estados[3][3] = new Estado(EstadoEnum.OBSTACULO);
        estados[3][4] = new Estado(EstadoEnum.OBSTACULO);
        estados[3][5] = new Estado(EstadoEnum.ROBO);
        estados[3][6] = new Estado(EstadoEnum.VAZIO);

        estados[4][0] = new Estado(EstadoEnum.VAZIO);
        estados[4][1] = new Estado(EstadoEnum.VAZIO);
        estados[4][2] = new Estado(EstadoEnum.VAZIO);
        estados[4][3] = new Estado(EstadoEnum.VAZIO);
        estados[4][4] = new Estado(EstadoEnum.VAZIO);
        estados[4][5] = new Estado(EstadoEnum.VAZIO);
        estados[4][6] = new Estado(EstadoEnum.VAZIO);

        estados[5][0] = new Estado(EstadoEnum.VAZIO);
        estados[5][1] = new Estado(EstadoEnum.OBSTACULO);
        estados[5][2] = new Estado(EstadoEnum.VAZIO);
        estados[5][3] = new Estado(EstadoEnum.VAZIO);
        estados[5][4] = new Estado(EstadoEnum.VAZIO);
        estados[5][5] = new Estado(EstadoEnum.VAZIO);
        estados[5][6] = new Estado(EstadoEnum.VAZIO);

        estados[6][0] = new Estado(EstadoEnum.VAZIO);
        estados[6][1] = new Estado(EstadoEnum.VAZIO);
        estados[6][2] = new Estado(EstadoEnum.VAZIO);
        estados[6][3] = new Estado(EstadoEnum.VAZIO);
        estados[6][4] = new Estado(EstadoEnum.OBSTACULO);
        estados[6][5] = new Estado(EstadoEnum.VAZIO);
        estados[6][6] = new Estado(EstadoEnum.VAZIO);
    }
}
