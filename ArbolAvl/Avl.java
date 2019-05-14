package ArbolAvl;

/**
 *
 * @author Rodrigo Mamani
 * @param <T> tipo T tiene que extender de comparable para comparar
 */
public class Avl<T extends Comparable<T>> {
class nodoAvl{

    T valor;
    nodoAvl der;
    nodoAvl izq;
    nodoAvl padre;
    int altura;
    int factor;

    public nodoAvl(T valor) {
        this.valor = valor;
    }
}

    private nodoAvl root;
    
    public int altura() {
        if (root == null) {
            return 0;
        }
        return root.altura;
    }
    
    public boolean buscar(T valor) {
        return buscar(valor, root);
    }
    private boolean buscar(T valor, nodoAvl root) {
        if (root != null) {
            if (valor.compareTo(root.valor) == 0) {
                return true;
            }
            if (valor.compareTo(root.valor) < 0) {
                return buscar(valor, root.izq);
            } else if (valor.compareTo(root.valor) > 0) {
                return buscar(valor, root.der);
            }
        }
        return false;
    }
    public boolean insertar(T valor) {
        if (valor == null)return false;
        if (!buscar(valor, root)) {
            root = insertar(valor,root);
            return true;
            }       
       return false; 
    }
    private nodoAvl insertar(T valor, nodoAvl nodo) {

        if (nodo == null) {
            return new nodoAvl(valor);
        }
            
        if (valor.compareTo(nodo.valor)< 0) {
            nodo.izq = insertar(valor, nodo.izq);
        } else {
            nodo.der = insertar(valor, nodo.der);
        }
        actualizarBalance(nodo);
        return reequilibrar(nodo);
    }
    private void actualizarBalance(nodoAvl nodo) {
        int alturaIzq = (nodo.izq == null) ? -1 : nodo.izq.altura;
        int alturaDer = (nodo.der == null) ? -1 : nodo.der.altura;

        nodo.altura = 1 + max(alturaIzq, alturaDer);

        nodo.factor = alturaDer - alturaIzq;

    }
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }
    private nodoAvl reequilibrar(nodoAvl nodo) {

        if (nodo.factor == -2) { // arbol cargado a la izquierda
            if (nodo.izq.factor <= 0) {//rotacion a la derecha simple
                return rotacionDer(nodo);
            } else {
                nodo.izq = rotacionIzq(nodo.izq);
                return rotacionDer(nodo);//rotacion a la derecha doble   
            }
        } else if (nodo.factor == 2) {
            if (nodo.der.factor >= 0) {
                return rotacionIzq(nodo); //rotacion a la izquierda simple
            } else {
                nodo.der = rotacionDer(nodo.der);
                return rotacionIzq(nodo); // rotacion a la izquierda doble
            }
        }
        return nodo;
    }
    private nodoAvl rotacionDer(nodoAvl nodo) {
        nodoAvl aux = nodo.izq;
        nodo.izq = aux.der;
        aux.der = nodo;
        actualizarBalance(nodo);
        actualizarBalance(aux);
        return aux;
    }
    private nodoAvl rotacionIzq(nodoAvl nodo) {
        nodoAvl aux = nodo.der;
        nodo.der = aux.izq;
        aux.izq = nodo;
        actualizarBalance(nodo);
        actualizarBalance(aux);
        return aux;
    }
    public boolean eliminar(T valor){
        if(valor == null)return false;
        if(buscar(valor,root)){
            root =eliminar(valor,root);
            return true;
        }
        return false;
    }
    private nodoAvl eliminar(T valor , nodoAvl nodo){
        if(nodo == null) return null;
        if(valor.compareTo(nodo.valor)<0){ // si es menor sub-arbol izq
            nodo.izq = eliminar(valor,nodo.izq);
        }else if(valor.compareTo(nodo.valor) > 0){ // si es mayor sub.arbol der
            nodo.der = eliminar(valor,nodo.der);
        }else{
            if(nodo.izq == null){
                return nodo.der;
            }else if(nodo.der == null){
                return nodo.izq;
            }else{
                if(nodo.izq.altura > nodo.der.altura){
                    T aux = buscarMax(nodo.izq);
                    nodo.valor= aux;
                    nodo.izq = eliminar(aux,nodo.izq);
                }else{
                    T aux = buscarMin(nodo.der);
                    nodo.valor= aux;
                    nodo.der = eliminar(aux,nodo.der);
                }
            }
        }
        actualizarBalance(nodo);
        return reequilibrar(nodo);
    }
    private T buscarMin(nodoAvl nodo){
   
        while(nodo.izq != null)
            nodo = nodo.izq;
        return (T) nodo.valor;   
    }
    private T buscarMax(nodoAvl nodo){
        while(nodo.der!=null)
            nodo=nodo.der;
        return (T) nodo.valor; 
    }
    public void inOrden(){
        inOrden(root);
        System.out.println();
    }
    private void inOrden(nodoAvl nodo){
        if (nodo.valor != null) {
            System.out.print("<");
            if (nodo.izq != null) {
                inOrden(nodo.izq);
            } else {
                System.out.print("*");
            }
            System.out.print(nodo.valor+"#"+nodo.factor+","+nodo.altura+"#");
            if (nodo.der != null)  {
                inOrden(nodo.der);
            } else {
                System.out.print("*");
            }
            System.out.print(">");
        }
    }  
}

