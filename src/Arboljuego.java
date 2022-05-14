package src.edd;

import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.Iterator;

/**
 * <p>
 * Clase que crea un arbol simple de enteros.
 * </p>
 */
public class Arboljuego{
    /**
     * Clase interna protegida para vértices.
     */
    public class Vertice implements VerticeArbolBinario<Integer> {

        /** El elemento del vértice. */
        public Integer elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;
        //public String color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public Vertice(Integer elemento) {
          if(elemento!=null){
             this.elemento=elemento;
          }
          //this.color=color;
        }

        /**
         * Nos dice si el vértice tiene vértice padre.
         * @return <tt>true</tt> si el vértice tiene vértice padre, <tt>false</tt>
         *         en otro caso.
         */
        @Override public boolean hayPadre() {
            return padre != null;
        }

        /**
         * Nos dice si el vértice tiene vértice izquierdo.
         * @return <tt>true</tt> si el vértice tiene vértice izquierdo,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
            return izquierdo != null;
        }

        /**
         * Nos dice si el vértice tiene vértice derecho.
         * @return <tt>true</tt> si el vértice tiene vértice derecho, <tt>false</tt>
         *         en otro caso.
         */
        @Override public boolean hayDerecho() {
            return derecho != null;
        }

        /**
         * Regresa el vértice padre del vértice.
         * @return el vértice padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<Integer> padre() {
            if (padre == null){
                throw new NoSuchElementException("El vértice no tiene padre.");
            }
            return padre;
        }

        /**
         * Regresa el vértice izquierdo del vértice.
         * @return el vértice izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<Integer> izquierdo() {
            if (izquierdo == null){
                throw new NoSuchElementException("El vértice no tiene izquierdo.");
            }
            return izquierdo;
        }

        /**
         * Regresa el vértice derecho del vértice.
         * @return el vértice derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<Integer> derecho() {
            if (derecho == null){
                throw new NoSuchElementException("El vértice no tiene derecho.");
            }
            return derecho;
        }

        /**
         * Regresa el elemento del vértice.
         * @return Integer elemento
         */
        @Override public Integer get() {
            return elemento;
        }

        /**
         * Regresa la altura del vértice.
         * 
         * @return la altura del vértice.
         * 
         * Hay que probar. 
         */
        @Override public int altura() {
            
            int alturaIzquierdo = 0;
            int alturaDerecho = 0;
            
            if (hayIzquierdo()){
                alturaIzquierdo = izquierdo.altura();
            }
            if (hayDerecho()){
                alturaDerecho = derecho.altura();
            }
            /*if(this==null){
                alturaDerecho=-1;
                return alturaDerecho;
            }*/
            return 1 + Math.max(alturaIzquierdo, alturaDerecho);
        }

        /**
         * Regresa la profundidad del vértice.
         * 
         * @return la profundidad del vértice.
        */
        @Override public int profundidad() {
            int profundidadPadre = 0;
            if (hayPadre()){
                profundidadPadre = padre.profundidad();
            }
            return 1 + profundidadPadre;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * 
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */

         @Override public boolean equals(Object o){
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked")
            Vertice vertice = (Vertice) o;
            if (!this.elemento.equals(vertice.elemento)) {
                return false;
            }
            return equalsAuxDer(this,vertice) && equalsAuxIzq(this, vertice);
        }

        private boolean equalsAuxDer(Vertice a,Vertice b){
            if(a.hayDerecho() && b.hayDerecho() && b.elemento.equals(a.elemento)){
                return a.derecho.equals(b.derecho);
            }
            if(!a.hayDerecho() && !b.hayDerecho() && b.elemento.equals(a.elemento)){
                return true;
            }
            if(a.hayDerecho() && !b.hayDerecho()){
                return false;
            }
            if (!a.hayDerecho() && b.hayDerecho()) {
                return false;
            }
            return false;
        }

        private boolean equalsAuxIzq(Vertice a, Vertice b) {
            if (a.hayIzquierdo() && b.hayIzquierdo() && b.elemento.equals(a.elemento)) {
                return a.izquierdo.equals(b.izquierdo);
            }
            if (!a.hayIzquierdo() && !b.hayIzquierdo() && b.elemento.equals(a.elemento)) {
                return true;
            }
            if (a.hayIzquierdo() && !b.hayIzquierdo()) {
                return false;
            }
            if (!a.hayIzquierdo() && b.hayIzquierdo()) {
                return false;
            }
            return false;
        }

        public String toString(){
            return this.elemento.toString();
        }

    }

    //Clase privada para un iterador de árboles bst
    private class Iterador implements Iterator<Integer>{
        //pila para ir almacenando los elemetos. primero los de la izq y luego los de la derecha
        Pila<Vertice> pila=new Pila<Vertice>();
        /**
         * Constructor
         */
        public Iterador(){
            //Metemos todos los nodos izquierdos a la pilaa partir de la raíz
            Vertice aux=raiz;
            while(raiz!=null){
                pila.push(raiz);
                if(raiz.hayIzquierdo()){
                    raiz=raiz.izquierdo;
                }else{
                    raiz=null;
                }    
            }
            raiz=aux;
        }
        /**
         * Método para saber si el árbol tiene siguiente elemento
         */
        @Override
        public boolean hasNext(){
            return !pila.isEmpty();
        }
        
        /**
         * Metodo que devuelve el siguiente elemento del arbol
         * Recorrido inorrder
         * @throws NoSuchElementException
         */
        @Override
        public Integer next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Vertice vertice=pila.pop();
            Integer elemento=vertice.elemento;
            if(vertice.hayDerecho()){
                vertice=vertice.derecho;
                while(vertice!=null){
                    pila.push(vertice);
                    if(vertice.hayIzquierdo()){
                        vertice=vertice.izquierdo;
                    }else{
                        vertice=null;
                    }
                }
            }
            return elemento;
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El contador del número de elementos */
    protected int elementos;

    /**
     * Constructor sin parametros 
     * 
    */
    public Arboljuego() {

    }
    
    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(Integer elemento) {
        return new Vertice(elemento);
    }
    
    /**
     * Regresa la altura del vértice.
     * 
     * @return la altura del vértice.
     */
    public int altura() {
        if (isEmpty()){
            return -1;
        }
        return raiz.altura();
    }

    /**
     * Nos dice si el árbol es vacío.
     * 
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    public boolean isEmpty() {
        return raiz == null;
    }
    
    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * 
     * @return el número de elementos en el árbol.
     */
    public int size() {
        return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * 
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    public boolean contains(Integer elemento){
        return busca(elemento) != null;
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <tt>null</tt>.
     * 
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <tt>null</tt> en otro caso.
     */

    public VerticeArbolBinario<Integer> busca(Integer elemento){
        return buscar(raiz,elemento);
    }

    protected Vertice buscar(Vertice v, Integer elemento){
        if(v == null || elemento ==null){
            return null;
        }
        if(v.elemento.equals(elemento)){
            return v;
        }
        Vertice aux = buscar(v.izquierdo, elemento);
        if(aux != null){
            return aux;
        }
        return buscar(v.derecho, elemento);
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * 
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<Integer> raiz(){
        if (raiz ==null) {
            throw new NoSuchElementException();
        }
        return raiz;
    }

    /**
     * Metodo para vaciar el arbol
     */
    public void empty(){
        raiz = null;
        elementos=0;
    }
    

    /**
     * Método auxiliar de toString
     * 
     * @return espacios
     */
    private String dibujaEspacios(int l, int[] a, int n) {
        String s = "";
        for (int i = 0; i < l; i++) {
            if (a[i] == 1) {
                s = s + "│  ";
            } else {
                s = s + "   ";
            }
        }
        return s;
    }

    /**
     * Metodo auxiliar de toString
     *
     */

    private String toString(Vertice v, int l, int[] m) {
        String s = v.toString() + "\n";
        m[l] = 1;

        if (v.izquierdo != null && v.derecho != null) {
            s = s + dibujaEspacios(l, m, m.length);
            s = s + "├─›";
            s = s + toString(v.izquierdo, l + 1, m);
            s = s + dibujaEspacios(l, m, m.length);
            s = s + "└─»";
            m[l] = 0;
            s = s + toString(v.derecho, l + 1, m);
        } else if (v.izquierdo != null) {
            s = s + dibujaEspacios(l, m, m.length);
            s = s + "└─›";
            m[l] = 0;
            s = s + toString(v.izquierdo, l + 1, m);
        } else if (v.derecho != null) {
            s = s + dibujaEspacios(l, m, m.length);
            s = s + "└─»";
            m[l] = 0;
            s = s + toString(v.derecho, l + 1, m);
        }
        return s;
    }
    
    /**
     * Regresa una representación en cadena del árbol.
     * 
     * @return una representación en cadena del árbol.
     */
    @Override
    public String toString() {
        if (this.raiz == null) {
            return "";
        }
        int[] a = new int[this.altura() + 1];
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
        return toString(this.raiz, 0, a);
    }

    /**
     * Compara el árbol con un objeto.
     * 
     * @param o el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */

    @Override public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        Arboljuego arbol = (Arboljuego)o;
        if(isEmpty()){
            return arbol.isEmpty();
        }
        return this.raiz.equals(arbol.raiz);
    }


    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * 
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *                            Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<Integer> vertice) {
        return (Vertice) vertice;
    }

    /**
     * Metodo que regresa un iterador
     * @return
     */
    public Iterator<Integer> iterator(){
        return new Iterador();
    }
}
