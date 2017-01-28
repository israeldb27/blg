package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "relatorioacessoperfil")
public class Relatorioacessoperfil implements Serializable {
    
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue    
    @Column(name = "id")
    private Long id;    
        
    @Column(name = "idRelatorio")
    private long idRelatorio;    
        
    @Column(name = "perfilUsuario")
    private String perfilUsuario;
    
    @Column(name = "dataAcesso")
    @Temporal(TemporalType.DATE)
    private Date dataAcesso;

    public Relatorioacessoperfil() {
    }

    public Relatorioacessoperfil(Long id) {
        this.id = id;
    }

    public Relatorioacessoperfil(Long id, long idRelatorio, String perfilUsuario) {
        this.id = id;
        this.idRelatorio = idRelatorio;
        this.perfilUsuario = perfilUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdRelatorio() {
        return idRelatorio;
    }

    public void setIdRelatorio(long idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public String getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(String perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    public Date getDataAcesso() {
        return dataAcesso;
    }

    public void setDataAcesso(Date dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relatorioacessoperfil)) {
            return false;
        }
        Relatorioacessoperfil other = (Relatorioacessoperfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.relatorio.Relatorioacessoperfil[ id=" + id + " ]";
    }
    
}
