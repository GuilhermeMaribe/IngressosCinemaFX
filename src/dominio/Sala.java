
package dominio;

/**
 *
 * @author guilh
 */
public class Sala {
    
    private int id, nAssentos;
    private String nSala;

    public Sala(String nSala, String nAssentos) {
        this.nSala = nSala;
        this.nAssentos = Integer.parseInt(nAssentos);
    }
        
    public Sala(int id, String nSala, String nAssentos) {
        this.id = id;
        this.nSala = nSala;
        this.nAssentos = Integer.parseInt(nAssentos);
    }

    public Sala(int id, String nSala, int nAssentos) {
        this.id = id;
        this.nSala = nSala;
        this.nAssentos = nAssentos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setnSala(String nSala) {
        this.nSala = nSala;
    }
    
    public String getNSala() {
        return nSala;
    }

    public void setnAssentos(int nAssentos) {
        this.nAssentos = nAssentos;
    }
    
    public void setnAssentos(String nAssentos) {
        this.nAssentos = Integer.parseInt(nAssentos);
    }
    
    public int getNAssentos() {
        return nAssentos;
    }
    public String getNAssentos2() {
        String aux = ""+nAssentos;
        return aux;
    }
    
    
}
