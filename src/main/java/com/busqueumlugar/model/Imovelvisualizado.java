package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import com.busqueumlugar.enumerador.TipoImovelEnum;


@Entity
@Table(name = "imovelvisualizado")
public class Imovelvisualizado extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -2920980377862522966L;

	@Id
    @GeneratedValue
    @Column(name = "id")
	private Long id;	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;
        
    @Column(name = "dataVisita")
    @Temporal(TemporalType.DATE)
    private Date dataVisita;
    
    @Column(name = "statusVisita")
    private String statusVisita;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idDonoImovel")    
    private Usuario usuarioDonoImovel;
    
    @Transient
    private String interessadoImovel = ""; // este campo eh usado para indicar se o usuario esta interessado ou nao no imovel
    

    public String getInteressadoImovel() {
		return interessadoImovel;
	}

	public void setInteressadoImovel(String interessadoImovel) {
		this.interessadoImovel = interessadoImovel;
	}

	public Imovelvisualizado() {
    }

    public Imovelvisualizado(Long id) {
        this.id = id;
    }

    public Imovelvisualizado(Long id, long idImovel, long idUsuario, Date dataVisita, int idEstado, int idCidade, int idBairro, String estado, String cidade, String bairro) {
        this.id = id;
        this.dataVisita = dataVisita;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(Date dataVisita) {
        this.dataVisita = dataVisita;
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
        if (!(object instanceof Imovelvisualizado)) {
            return false;
        }
        Imovelvisualizado other = (Imovelvisualizado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.imovelvisitado.Imovelvisitado[ id=" + id + " ]";
    }

    /**
     * @return the statusVisita
     */
    public String getStatusVisita() {
        return statusVisita;
    }

    /**
     * @param statusVisita the statusVisita to set
     */
    public void setStatusVisita(String statusVisita) {
        this.statusVisita = statusVisita;
    }
	
	public String getTipoImovelFmt() {
		if ( imovel != null)
			return TipoImovelEnum.getLabel(this.imovel.getTipoImovel());
		else
			return "";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioDonoImovel() {
		return usuarioDonoImovel;
	}

	public void setUsuarioDonoImovel(Usuario usuarioDonoImovel) {
		this.usuarioDonoImovel = usuarioDonoImovel;
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}
	
	


}
