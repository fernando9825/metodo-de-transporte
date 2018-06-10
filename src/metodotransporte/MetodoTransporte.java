/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodotransporte;

import java.util.Arrays;

/**
 *
 * @author striker
 */
public class MetodoTransporte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int[] demanda = {10, 10, 10};
        int[] oferta = {7, 12, 11};
        int[][] matriz = {{1, 2, 6}, {0, 4, 2}, {3, 1, 5}};

        int comprobarBalance = comprobarBalance(demanda, oferta);

        switch (comprobarBalance) {
            //El sistema esta balanceado; demanda = oferta
            case constantes.balanceado:
                int[][] solBasica = esquinaNorOeste(demanda, oferta);
                System.out.println(Arrays.deepToString(solBasica));
                boolean degeneracion = comprobarDegeneracion(solBasica);
                if (!degeneracion) {
                    //No existe degeneración
                    System.out.println("m + n -1, satisface el número de asignaciones");
                    System.out.println("No hay degeneración.\n\n");
                    pruebaOptimidad(matriz, solBasica);
                } else {
                    //Existe degeneración
                }
                break;
            //la demanda es mayor que la oferta
            case constantes.demandaMayor:
                break;
            //la oferta es mayor que la demanda
            default:
                break;
        }

    }

    public static void pruebaOptimidad(int[][] matriz, int[][] solBasica) {

        //Paso 4
        // a) Determinar matriz de costos
        int[][] matrizCostos = new int[matriz.length][matriz[0].length];

        for (int i = 0; i < solBasica.length; i++) {
            for (int j = 0; j < solBasica[i].length; j++) {
                if (solBasica[i][j] != 0) {
                    matrizCostos[i][j] = matriz[i][j];
                } else {
                    matrizCostos[i][j] = Integer.MIN_VALUE;
                }
            }
        }

        System.out.println("a) Matriz de costos\n\n");
        System.out.println(Arrays.deepToString(matrizCostos));

        //Paso B), Matriz con Ui y Vj
        int[] Ui = new int[matrizCostos.length];
        int[] Vj = new int[matrizCostos[0].length];

        System.out.println("Ui: " + Arrays.toString(Ui));
        System.out.println("Vi: " + Arrays.toString(Vj));
        int indiceUi = 0, indiceVj = 0;

        for (int i = 0; i < matrizCostos.length; i++) {

            for (int j = 0; j < matrizCostos[i].length; j++) {
                if (matrizCostos[i][j] != Integer.MIN_VALUE) {

                    if (i == 0 && j == 0) {
                        //Valores iniciales de Ui y Vj
                        Vj[indiceVj] = 0;
                        Ui[indiceUi] = matrizCostos[i][j] + Vj[indiceUi];
                        indiceUi++;
                        indiceVj++;
                        
                        for(int k = indiceUi; k< Ui.length; k++){
                            Ui[k] = Integer.MIN_VALUE;
                        }
                        for(int k = indiceVj; k< Vj.length; k++){
                            Vj[k] = Integer.MIN_VALUE;
                        }
                        
                    }else{
                        
                        if(matriz[i][j] == 0){
                            System.out.println("El elemento de la matriz es cero");
                            
                            if((Ui[indiceUi] == 0) && (Vj[indiceVj] == 0)){
                                System.out.println("Ambos son cero");
                            }else{
                                if(Ui[indiceUi] != 0){
                                    System.out.println("UI no es cero");
                                    Vj[indiceVj] = Ui[indiceUi] + matrizCostos[i][j];
                                    indiceUi++;
                                    indiceVj++;
                                    
                                }else if(Vj[indiceVj] != 0){
                                    System.out.println("VJ no es cero");
                                    Ui[indiceUi] = Vj[indiceVj] + matrizCostos[i][j];
                                }
                            }
                            
                        }
                        
                        if(Ui[indiceUi] == Integer.MIN_VALUE){
                            System.out.println("Ui es cero");
                            Vj[indiceVj] = Ui[indiceUi] + matrizCostos[i][j];
                        }
                        
                        if(Vj[indiceVj] == Integer.MIN_VALUE){
                            System.out.println("Vj es cero");
                        }
//                        System.out.println("entre aqui");
                    }
                    

                    
                    
                }
            }

        }

//        Vj[indiceVj] = Ui[indiceUi] + matrizCostos[i][j];
//
//                    Ui[indiceUi] = Vj[indiceVj] + matrizCostos[i][j];
//                    indiceUi++;
//                    indiceVj++;
////                    if (Ui[indiceUi] != 0) {
////
////                    } else if (Vj[indiceVj] != 0) {
////                        
////                    }
        System.out.println("\n");
        System.out.println("Ui: " + Arrays.toString(Ui));
        System.out.println("Vi: " + Arrays.toString(Vj));
    }

    public static boolean comprobarDegeneracion(int[][] solBasica) {
        int contador = 0;
        for (int[] a : solBasica) {
            for (int b : a) {
                if (b != 0) {
                    contador++;
                }
            }
        }
        //Si retorna true, existe degeneración
        return !(contador == (solBasica.length + solBasica[0].length - 1));
    }

    public static int[][] esquinaNorOeste(int[] demanda, int[] oferta) {
        int[][] solBasica = new int[oferta.length][demanda.length];
        int demandaValor = 0, ofertaValor = 0;
        int fila = 0, columna = 0;
        while (true) {
            if ((fila == demanda.length) || (columna == oferta.length)) {
                break;
            }

            demandaValor = demanda[fila];
            ofertaValor = oferta[columna];

            if (demanda[fila] > oferta[columna]) {
                solBasica[columna][fila] = ofertaValor;
                demanda[fila] = (demandaValor - ofertaValor);
                oferta[columna] = 0;
                columna++;
            } else if (oferta[columna] > demanda[fila]) {
                solBasica[columna][fila] = demandaValor;
                oferta[columna] = (ofertaValor - demandaValor);
                demanda[fila] = 0;
                fila++;
            } else {
                //son iguales
                solBasica[columna][fila] = demandaValor;
                demanda[fila] = 0;
                oferta[columna] = 0;
                columna++;
                fila++;
            }

        }
        return solBasica;
    }

    public static int comprobarBalance(int[] demanda, int[] oferta) {

        int demandaValor = 0;
        int ofertaValor = 0;
        /*
            return
            
            0 ---> Balanceado
            1 ---> demanda > oferta
            2 ---> oferta > demanda
         */

        for (int d : demanda) {
            demandaValor += d;
        }
        for (int o : oferta) {
            ofertaValor += o;
        }

        if (demandaValor == ofertaValor) {
            System.out.println("El sistema esta balanceado");
            return 0;
        } else {
            if (demandaValor > ofertaValor) {
                System.out.println("La demanda excede a la oferta");
                return 1;
            } else {
                System.out.println("La oferta excede a la demanda");
                return 2;
            }
        }
    }

}

class constantes {

    //Constantes del método comprobarBalance
    public static final int balanceado = 0;
    public static final int demandaMayor = 1;
    public static final int ofertaMayor = 0;

    //constante del método comprobarDegeneracion
    public static boolean degeneracion = false;//false --> no hay degeneración, true sí
}
