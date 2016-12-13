package com.busqueumlugar.model;

import java.io.Serializable;

/**
 *
 * @author Israel
 */
public class EmailImovel implements Serializable {
    
    private String textoEmail;
    private String assunto;
    private String to;
    private String acao;

    /**
     * @return the textoEmail
     */
    public String getTextoEmail() {
        return textoEmail;
    }

    /**
     * @param textoEmail the textoEmail to set
     */
    public void setTextoEmail(String textoEmail) {
        this.textoEmail = textoEmail;
    }

    /**
     * @return the assunto
     */
    public String getAssunto() {
        return assunto;
    }

    /**
     * @param assunto the assunto to set
     */
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the acao
     */
    public String getAcao() {
        return acao;
    }

    /**
     * @param acao the acao to set
     */
    public void setAcao(String acao) {
        this.acao = acao;
    }
    
    
}
