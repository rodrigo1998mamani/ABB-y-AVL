package ArbolBinario;

/**
 *
 * @author Rodrigo Mamani
 */
public class ABB <T extends Comparable<T>>{

    private T valor;
    private ABB izq;
    private ABB der;
    private ABB padre;

    private void insertarConPadre(T dato, ABB padre) {
        if (valor == null) {
            valor = dato;
            this.padre = padre;
        } else if (dato.compareTo(this.valor) < 0) {
            if (izq == null) {
                izq = new ABB();
            }
            izq.insertarConPadre(dato, this);
        } else if (dato.compareTo(this.valor) > 0 ) {
            if (der == null) {
                der = new ABB();
            }
            der.insertarConPadre(dato, this);
        }
    }

    public void insertar(T dato) {
        insertarConPadre(dato, null);
    }

    public boolean buscar(T dato) {
        if (valor != null) {
            if(dato.compareTo(this.valor) == 0) return true;
            if (dato.compareTo(this.valor) < 0 && izq != null) {
                return izq.buscar(dato);
            } else if (dato.compareTo(this.valor) > 0 && izq != null) {
                return der.buscar(dato);
            } else {
                return false;
            }
        }else{
            return false;
        }
    }

    private ABB buscarMin() {
        if (izq != null && izq.valor != null) {
            return izq.buscarMin();
        } else {
            return this;
        }
    }

    private void eliminarNodo() {
        if (izq != null && der != null) { // caso 1 : eliminar un nodo con 2 hijos 
            ABB aux = der.buscarMin();
            this.valor = (T) aux.valor;
            der.eliminar(aux.valor);
        } else if (izq != null || der != null) {// caso 2 : eliminar un nodo con 1 hijo
            ABB auxiliar;
            if (izq != null) {
                auxiliar = izq;
            } else {
                auxiliar = der;
            }
            this.valor = (T) auxiliar.valor;
            this.izq = auxiliar.izq;
            this.der = auxiliar.der;

        } else {// caso 3 : eliminar un nodo hoja
            if (padre != null) {
                if (this == padre.der) {
                    padre.der = null;
                } else if (this == padre.izq) {
                    padre.izq = null;
                }
                padre = null;
            }
            valor = null;
        }
    }

    
    public void eliminar(T dato) {
        if (valor != null) {
            if (dato.compareTo(this.valor) == 0) {
                eliminarNodo();
            } else if (dato.compareTo(this.valor)<0) {
                izq.eliminar(dato);
            } else if (dato.compareTo(this.valor)>0) {
                der.eliminar(dato);
            }
        }
    }
 
    public void inOrden() {
        if (valor != null) {
            System.out.print("<");
            if (izq != null) {
                izq.inOrden();
            } else {
                System.out.print("*");
            }
            System.out.print(valor);
            if (der != null)  {
                der.inOrden();
            } else {
                System.out.print("*");
            }
            System.out.print(">");
        }
    }

    
    public boolean esHoja() {
        return valor != null && izq == null && der == null;
    }

    
    public boolean esVacio() {
        return valor == null;
    }

}
