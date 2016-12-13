package com.busqueumlugar.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.StatusImovelEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;
import com.mysql.jdbc.StringUtils;

@Entity
@Table(name = "imovelcomentario")
public class Imovelcomentario  implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;    
    
    @Column(name = "dataComentario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataComentario;    
    
    @Column(name = "comentario")
    private String comentario;    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuarioComentario")    
    private Usuario usuarioComentario;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;
    
        
    @Column(name = "status")
    private String status; // novo, lido
    
    @Transient
    private Date dataUltimoComentario;
    
    @Transient
    private String descUltimoComentario;
    
    @Transient
    private int totalComentarios;

    @Transient
    private String comentarioResumido = "";
   

	public int getTotalComentarios() {
		return totalComentarios;
	}

	public void setTotalComentarios(int totalComentarios) {
		this.totalComentarios = totalComentarios;
	}

	public Imovelcomentario() {
    }

    public Imovelcomentario(Long id) {
        this.id = id;
    }

    public Imovelcomentario(Long id, Date dataComentario, String comentario, long idImovel, long idUsuarioComentario) {
        this.id = id;
        this.dataComentario = dataComentario;
        this.comentario = comentario;       
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(Date dataComentario) {
        this.dataComentario = dataComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
        if (!(object instanceof Imovelcomentario)) {
            return false;
        }
        Imovelcomentario other = (Imovelcomentario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.imovel.imovelcomentario.Imovelcomentario[ id=" + id + " ]";
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the dataUltimoComentario
     */
    public Date getDataUltimoComentario() {
        return dataUltimoComentario;
    }

    /**
     * @param dataUltimoComentario the dataUltimoComentario to set
     */
    public void setDataUltimoComentario(Date dataUltimoComentario) {
        this.dataUltimoComentario = dataUltimoComentario;
    }

    /**
     * @return the descUltimoComentario
     */
    public String getDescUltimoComentario() {
        return descUltimoComentario;
    }

    /**
     * @param descUltimoComentario the descUltimoComentario to set
     */
    public void setDescUltimoComentario(String descUltimoComentario) {
        this.descUltimoComentario = descUltimoComentario;
    }

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public Usuario getUsuarioComentario() {
		return usuarioComentario;
	}

	public void setUsuarioComentario(Usuario usuarioComentario) {
		this.usuarioComentario = usuarioComentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getComentarioResumido() {
		if (! StringUtils.isNullOrEmpty(comentario)){
			if (comentario.length() <= 25 )
				return comentario;
			else
				return comentario.substring(0, 24) + "  ...";
		}
		else		
			return comentarioResumido;
	}

	public void setComentarioResumido(String comentarioResumido) {
		this.comentarioResumido = comentarioResumido;
	}

}
