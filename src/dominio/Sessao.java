
package dominio;

import java.time.LocalTime;

/**
 *
 * @author guilh
 */
public class Sessao {
    
    private int id, qtdIngresso;
    private LocalTime horario;
    private Sala sala;
    private Filme filme;

    public Sessao(LocalTime horario, Sala sala, Filme filme) {
        this.id = id;
        this.qtdIngresso = sala.getNAssentos();
        this.horario = horario;
        this.sala = sala;
        this.filme = filme;
    }

    public Sessao(int id, int qtdIngresso, LocalTime horario, Sala sala, Filme filme) {
        this.id = id;
        this.qtdIngresso = qtdIngresso;
        this.horario = horario;
        this.sala = sala;
        this.filme = filme;
    }

    /*public Sessao(String qtdIngresso, String horario, String sala, String filme) {
        this.qtdIngresso = Integer.parseInt(qtdIngresso);
        this.horario = Integer.parseInt(horario);
        this.sala = Integer.parseInt(sala);
        this.filme = Integer.parseInt(filme);
    }*/
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQtdIngresso() {
        return qtdIngresso;
    }

    public void setQtdIngresso(int qtdIngresso) {
        this.qtdIngresso = qtdIngresso;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }
    public void retiraIngresso(){
                qtdIngresso--;    
            }
    public void setQtdIngresso(String QtdIngresso) {
        this.qtdIngresso = Integer.parseInt(QtdIngresso);
    }
    public String getQtdIngresso2() {
        String aux = ""+qtdIngresso;
        return aux;
    }
    /*public void setHorario(String Horario) {
        this.horario = Integer.parseInt(Horario);
    }
    public String getHorario2() {
        String aux = ""+horario;
        return aux;
    }*/
}
