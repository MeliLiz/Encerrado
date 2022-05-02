package src.edd;
import java.util.Iterator;
public class Ficha {
    public String color;
    public int numero;
    public int posicionX;
    public int posicionY;
    //public Lista<Integer> posicionesDisponibles;
    //public Iterator<Integer> iterador;

    public Ficha(String color, int numero, int posicionX, int posicionY){
        this.color=color;
        this.numero=numero;
        this.posicionX=posicionX;
        this.posicionY=posicionY;
        //posicionesDisponibles=new Lista<Integer>();
        //iterador=posicionesDisponibles.iterator();
    }

    public String toString(){
        if(this.color.equals("blanco")){
            return "᯽";
        }else{
            return color+"᳃"+numero+"\u001B[0m";
        }
    }

    public Ficha clone(){
        Ficha ficha=new Ficha(this.color,this.numero,this.posicionX,this.posicionY);
        return ficha;
    }

}
