package src.edd;
import java.util.InputMismatchException;
import java.util.Scanner;

import src.edd.Arboljuego;
import src.edd.Ficha;
import src.edd.VerticeArbolBinario;

import java.util.Random;
import java.util.Iterator;
import java.io.File;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;
import java.io.FileNotFoundException;

public class Encerrado{
    private static Scanner sc = new Scanner(System.in);
    private static Scanner sc2 = new Scanner(System.in);
    public static String rojo="\u001B[31m";
    public static String azul="\u001B[34m";
    public static String reset="\u001B[0m";
    public static String modo="";

    /**
     * Metodo menu
     */
    public static void menu() {
        // Imprimir opciones
        System.out.print("\033[H\033[2J");
        System.out.println("\nEncerrado\n***Menu***");
        System.out.println("Selecciona una opcion (Ingresa el numero)");
        System.out.println("\n(1) Ver instrucciones");
        System.out.println("(2) Jugar");
        System.out.println("(3) Salir");
        System.out.println("A lo largo del juego podrá teclear \"random\" para cambiar a modo random o \"minimax\" para cambiar a modo minimax");
        System.out.println("¡Disfrute el juego!");

        // Leer la respuesta
        boolean h = true;
        //variable para manejar el switch
        int respuesta = 0;
        do {
            try {
                respuesta = sc.nextInt();
                //si la respuesta no es un caso definido entonces lanzaremos que la opción no es válida.
                if (respuesta < 1 || respuesta > 3) {
                    System.out.println("\u001B[33m" + "La opcion que ingresaste no es valida" + "\u001B[0m");
                } else {
                    h = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Opcion no valida");
                sc.next();
            }
        } while (h);

        switch (respuesta) {

            case 1:// imprimir instrucciones
                try {
                    File f = new File("src/Reglas.txt");
                    Scanner s = new Scanner(f);
                    while (s.hasNextLine()) {
                        System.out.println(s.nextLine());
                    }
                    System.out.println("\nPresione cualquier tecla seguida de enter para volver al menu");
                    sc.next();
                    menu();
                } catch (FileNotFoundException e) {
                    System.out.println("El archivo no existe");
                }
                break;

            case 3:// Salir del juego
                h = false;
                System.out.println("Saliendo...");
                System.exit(0);
                break;

            case 2:// jugar
                Ficha[][] tablero=new Ficha[3][3]; 
                Ficha[] azules=new Ficha[2];
                Ficha[] rojos=new Ficha[2];
                System.out.print("\033[H\033[2J");
                System.out.println("¡Hola! Bienvenido a Conectado");
                boolean incorrecto=true;
                Ficha blanca=null;
                do{
                    System.out.println("¿Cuál es la disposición inicial de fichas que deseas?");
                    System.out.println("1) Default");
                    System.out.println("2) Otra");
                    try{
                        int r=sc.nextInt();
                        
                        switch (r) {
                            case 1:
                                tablero[0][0]=new Ficha(rojo,1,0,0);
                                rojos[0]=tablero[0][0];
                                tablero[2][2]=new Ficha(rojo,2,2,2);
                                rojos[1]=tablero[2][2];
                                tablero[1][1]=new Ficha("blanco",0,1,1);
                                blanca=tablero[1][1];
                                tablero[0][2]=new Ficha(azul,1,0,2);
                                azules[0]=tablero[0][2];
                                tablero[2][0]=new Ficha(azul,2,2,0);
                                azules[1]=tablero[2][0];
                                
                                incorrecto=false;
                                break;
                            case 2:
                                System.out.println("¿Cuál es la disposición inicial de fichas que deseas?");
                                mostrarPos();
                                System.out.println("Fichas rojas (computadora): (ingresa el numero en que quieres que se encuentre la primera ficha roja)");
                                rojos[0]=verifica(tablero, rojo,1);
                                System.out.println("Fichas rojas (computadora): (ingresa el numero en que quieres que se encuentre la segunda ficha roja)");
                                rojos[1]=verifica(tablero, rojo,2);
                                System.out.println("Fichas azules: (ingresa el numero en que quieres que se encuentre la primera ficha azul)");
                                azules[0]=verifica(tablero, azul,1);
                                System.out.println("Fichas azules: (ingresa el numero en que quieres que se encuentre la segunda ficha azul)");
                                azules[1]=verifica(tablero,azul,2);
                                
                                if(tablero[0][0]==null){
                                    blanca=new Ficha("blanco",0,0,0);
                                    tablero[0][0]=blanca;
                                } else if(tablero[0][2]==null){
                                    blanca=new Ficha("blanco",0,0,2);
                                    tablero[0][2]=blanca;
                                }else if(tablero[1][1]==null){
                                    blanca=new Ficha("blanco",0,1,1);
                                    tablero[1][1]=blanca;
                                }else if(tablero[2][0]==null){
                                    blanca=new Ficha("blanco",0,2,0);
                                    tablero[2][0]=blanca;
                                }else if(tablero[2][2]==null){
                                    blanca=new Ficha("blanco",0,2,2);                                        
                                    tablero[2][2]=blanca;
                                }
                                incorrecto=false;
                                    break;
                            default:
                                System.out.println("Opcion no valida");
                                break;
                            }
                    }catch(InputMismatchException e){
                        System.out.println("Al parecer no ingresaste un numero");
                        sc.next();
                    }
                }while(incorrecto);
                muestraTablero(tablero);
                incorrecto=true;
                int empieza=0;
                int r=0;
                do{
                    System.out.println("¿Quien empieza el juego?");
                    System.out.println("1) Computadora");
                    System.out.println("2) Jugador");
                    try{
                        r=sc.nextInt();
                        if(r==1||r==2){
                            empieza=r;
                            incorrecto=false;
                        }else{
                            System.out.println("Opcion no valida");
                        }
                    }catch(InputMismatchException e){
                        System.out.println("Probablemente no ingresaste un numero");
                        sc.next();
                    }
                }while(incorrecto);
                incorrecto=true;
                do{
                    System.out.println("Selecciona el modo de juego");
                    System.out.println("1) Random");
                    System.out.println("2) Minimax");
                    try{
                        r=sc.nextInt();
                        if(r==1){
                            modo="random";
                            incorrecto=false;
                        }else if(r==2){
                            modo="minimax";
                            incorrecto=false;
                        }else{
                            System.out.println("Opcion no valida");
                        }
                    }catch(InputMismatchException e){
                        System.out.println("Probablemente no ingresaste un numero");
                        sc.next();
                    }
                }while(incorrecto);
                String ganador=jugar(tablero, empieza, azules,rojos,blanca);
                System.out.println("Ganador del juego: "+ganador);

            break;
            default:
                break;
        }
        
    }// FIN DE MENU

    /**
     * Metodo para verificar las posiciones iniciales de las fichas
     * @param tablero
     * @param color
     */
    public static Ficha verifica(Ficha[][] tablero, String color, int numero){
        boolean incorrecto=true;
        Ficha actual;
        do{
            try{
                int respuesta=sc.nextInt();
                if(respuesta<1||respuesta>5){
                    System.out.println("Posicion no valida");
                }else{
                    switch (respuesta) {
                        case 1:
                            if(tablero[0][0]==null){
                                actual=new Ficha(color,numero,0,0);
                                tablero[0][0]=actual;
                                return actual;
                            }else{
                                System.out.println("Posicion no valida");
                            }
                            break;
                        case 2:
                        if(tablero[0][2]==null){
                            actual=new Ficha(color,numero,0,2);
                            tablero[0][2]=actual;
                            return actual;
                        }else{
                            System.out.println("Posicion no valida");
                        }
                            break;
                        case 3:
                        if(tablero[1][1]==null){
                            actual=new Ficha(color, numero,1,1);
                            tablero[1][1]=actual;
                            return actual;
                        }else{
                            System.out.println("Posicion no valida");
                        }
                            
                            break;
                        case 4:
                        if(tablero[2][0]==null){
                            actual=new Ficha(color, numero, 2, 0);
                            tablero[2][0]=actual;
                            return actual;

                        }else{
                            System.out.println("Posicion no valida");
                        }
                            break;
                        case 5:
                        if(tablero[2][2]==null){
                            actual=new Ficha(color, numero, 2, 2);
                            tablero[2][2]=actual;
                            return actual;
                        }else{
                            System.out.println("Posicion no valida");
                        }
                            break;
                    
                        default:
                            break;
                    }
                }
            }catch(InputMismatchException e){
                System.out.println("Probablemente no ingresaste un numero");
                sc.next();
            }
        }while(incorrecto);
        return null;
    }

    /**
     * MEtodo para mostrar las posiciones validas del tablero
     */
    public static void mostrarPos(){
        int[][] posiciones=new int[3][3];
        posiciones[0][0]=1;
        posiciones[0][2]=2;
        posiciones[1][1]=3;
        posiciones[2][0]=4;
        posiciones[2][2]=5;
        for(int i=0; i<3;i++){
            for(int j=0;j<3;j++){
                if(posiciones[i][j]==0){
                    System.out.print(" - ");
                }else{
                    System.out.print(" "+posiciones[i][j]+" ");
                }
            }
            System.out.println();
        }
    }

    public static void muestraTablero(Ficha[][] tablero){
        for(int i=0;i<3;i++){
            for(int j=0; j<3;j++){
                if(tablero[i][j]==null){
                    System.out.print("  -");
                }else{
                    System.out.print("  "+tablero[i][j]);
                }
                
            }
            System.out.println();
        }
    }

    public void modo(){

    }

    public static boolean verificaTirada(Ficha fichaAmover, Ficha blanca, Ficha[][] tablero ){
        //Posicion donde esta la ficha blanca (el unico lugar al que se puede mover una ficha)
        int PosDestinoX=blanca.posicionX;
        int PorDestinoY=blanca.posicionY;
        //Si la ficha se debe mover al centro, todas se pueden mover
        if(PosDestinoX==1&&PorDestinoY==1){
            return true;
        }
        //Posicion donde se encuentra la ficha a mover
        int aMoverX=fichaAmover.posicionX;
        int aMoverY=fichaAmover.posicionY;
        //Si la ficha a mover está en el cento, entonces se puede mover a donde sea
        if(aMoverX==1&&aMoverY==1){
            return true;
        }
        //Si ninguna esta en el centro, entonces la coordenada x es 0 o 2
        if(aMoverX==0){
            if(aMoverY==0){//Coordenada Y en 0
                //Verificamos si la ficha se puedemover a la posicion (0,2)
                if(tablero[0][2]!=null&&tablero[0][2].color.equals("blanco")){
                    return true;
                }
                //Verificamos si la ficha se puedemover a la posicion (2,0)
                if(tablero[2][0]!=null&&tablero[2][0].color.equals("blanco")){
                    return true;
                }
                return false;//Si no entró a los if, la ficha no se puede mover
            }else if(aMoverY==2){//Coordenada Y en 2
                //Verificamos si la ficha se puedemover a la posicion (0,0)
                if(tablero[0][0]!=null&&tablero[0][0].color.equals("blanco")){
                    return true;
                }
                //Verificamos si la ficha se puedemover a la posicion (2,2)
                if(tablero[2][2]!=null&&tablero[2][2].color.equals("blanco")){
                    return true;
                }
                return false;//Si no entró a los if, la ficha no se puede mover
            }
        }else if(aMoverX==2){
            if(aMoverY==0){//Coordenada Y en 0
                //Verificamos si la ficha se puedemover a la posicion (0,0)
                if(tablero[0][0]!=null&&tablero[0][0].color.equals("blanco")){
                    return true;
                }
                return false;//Si no entró a los if, la ficha no se puede mover
            }else if(aMoverY==2){//Coordenada Y en 2
                //Verificamos si la ficha se puedemover a la posicion (0,2)
                if(tablero[0][2]!=null&&tablero[0][2].color.equals("blanco")){
                    return true;
                }
                return false;//Si no entró a los if, la ficha no se puede mover
            }
        }
        return false;
    }

    /**
     * 
     * @param tablero
     * @param empieza 1 si es la computadora, 2 si es el jugador
     */
    public static String jugar(Ficha[][] tablero, int empieza, Ficha[] azules, Ficha[] rojos, Ficha blanca){
        boolean noTermina=true;
        int actual=empieza;
        do{
            if(actual==1){//turno de la computadora
                System.out.println("****Turno de la computadora****");
                System.out.println("Tablero antes de tirar");
                muestraTablero(tablero);
                if(modo.equals("random")){
                    boolean pieza1=verificaTirada(rojos[0],blanca, tablero);
                    boolean pieza2=verificaTirada(rojos[1],blanca, tablero);
                    if(pieza1||pieza2){
                        int aux=0;
                        if(pieza1&&pieza2){
                            Random random=new Random();
                            boolean r=random.nextBoolean();
                            if(r){
                                aux=1;
                            }
                        }else{
                            if(pieza2){
                                aux=1;
                            }
                        }
                        //sacamos la posicion en la que se encuentra la pieza roja actualmente
                        int posPiezaX=rojos[aux].posicionX;
                        int posPiezaY=rojos[aux].posicionY;
                        //En la posicion dode estaba la pieza ponemos a la pieza blanca
                        tablero[posPiezaX][posPiezaY]=blanca;
                        //En donde estaba la pieza blanca ponemos la pieza roja
                        tablero[blanca.posicionX][blanca.posicionY]=rojos[aux];
                        //cambiamos los atributos de posicion de la pieza roja
                        rojos[aux].posicionX=blanca.posicionX;
                        rojos[aux].posicionY=blanca.posicionY;
                        //cambiamos los atributos de posicion de la pieza blanca
                        blanca.posicionX=posPiezaX;
                        blanca.posicionY=posPiezaY;
                    }else{
                        noTermina=false;
                        System.out.println("No hay movimientos disponibles para la computadora");
                        return "Jugador";
                    }
                    System.out.println("Tablero despues de tirar");
                    muestraTablero(tablero);
                }else{
                    minimax(rojos,tablero);
                }
                actual=2;
            }else{//turno del jugaador
                System.out.println("***Tu turno***");
                System.out.println("Tablero antes de tirar");
                muestraTablero(tablero);
                //Verificar si las piezas azules se pueden mover
                boolean pieza1=verificaTirada(azules[0],blanca, tablero);
                boolean pieza2=verificaTirada(azules[1],blanca, tablero);
                if(pieza1&&pieza2){//si ambas fichas azules se pueden mover
                    boolean incorrecto=true;
                    do{
                        System.out.println("Elige el numero de la ficha que quieres mover");
                        try{
                            int r=sc.nextInt();
                            if(r==1||r==2){
                                int aux=0;
                                if(r==2){
                                    aux=1;
                                }
                                int posPiezaX=azules[aux].posicionX;
                                int posPiezaY=azules[aux].posicionY;
                                tablero[posPiezaX][posPiezaY]=blanca;
                                tablero[blanca.posicionX][blanca.posicionY]=azules[aux];
                                azules[aux].posicionX=blanca.posicionX;
                                azules[aux].posicionY=blanca.posicionY;
                                blanca.posicionX=posPiezaX;
                                blanca.posicionY=posPiezaY;
                                incorrecto=false;
                            }else{
                                System.out.println("Opcion no valida");
                            }
                        }catch(InputMismatchException e){
                            System.out.println("Parece que no ingresaste un numero");
                            sc.next();
                        }
                    }while(incorrecto);
                }else if(pieza1||pieza2){//si solo una de las dos piezas azules se puede mover
                    System.out.println("Solo puedes mover una pieza. Presiona cualquier tecla seguida de enter para continuar");
                    sc2.nextLine();
                    int aux=0;//la posicion del arreglo
                    if(pieza2){
                        aux=1;
                    }
                    //Actualizamos las posiciones x y y de las fichas y en el tablero
                    int posPiezaX=azules[aux].posicionX;
                    int posPiezaY=azules[aux].posicionY;
                    tablero[posPiezaX][posPiezaY]=blanca;
                    tablero[blanca.posicionX][blanca.posicionY]=azules[aux];
                    azules[aux].posicionX=blanca.posicionX;
                    azules[aux].posicionY=blanca.posicionY;
                    blanca.posicionX=posPiezaX;
                    blanca.posicionY=posPiezaY;
                }else{
                    noTermina=false;
                    System.out.println("No hay movimentos disponibles para el jugador");
                    return"Computadora";
                }
                System.out.println("Tablero despues de tirar");
                muestraTablero(tablero);
                actual=1;//ahora sera el turno de la computadora
            }

            //verificar si ya termina el juego
        }while(noTermina);
        return "";
    }

    public static void minimax(Ficha[] rojos, Ficha[][] tablero){
        //Hacemos copias para trabajar sobre ellos
        Ficha[][] copiaTablero=new Ficha[3][3];
        Ficha[] copiaRojos=new Ficha[2];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                copiaTablero[i][j]=tablero[i][j].clone();
            }
        }
        copiaRojos[0]=rojos[0].clone();
        copiaRojos[1]=rojos[1].clone();

        Arboljuego<Integer> arbol=new Arboljuego<Integer>();
        recursivo(arbol,copiaTablero,copiaRojos);
    }

    public static void recursivo(Arboljuego<Integer> arbol, Ficha[][] tablero, Ficha[] rojos){
        if(arbol.isEmpty()){
            arbol.raiz=arbol.new Vertice(-2);
        }
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args){
        menu();
    }
}
