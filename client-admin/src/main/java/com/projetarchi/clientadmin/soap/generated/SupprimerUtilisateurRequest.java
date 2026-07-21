
package com.projetarchi.clientadmin.soap.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="jeton" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "jeton",
    "id"
})
@XmlRootElement(name = "supprimerUtilisateurRequest")
public class SupprimerUtilisateurRequest {

    @XmlElement(required = true, namespace = "http://siteactualites.com/soap/utilisateurs")
    protected String jeton;
    @XmlElement(namespace = "http://siteactualites.com/soap/utilisateurs")
    protected long id;

    /**
     * Obtient la valeur de la propriété jeton.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJeton() {
        return jeton;
    }

    /**
     * Définit la valeur de la propriété jeton.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJeton(String value) {
        this.jeton = value;
    }

    /**
     * Obtient la valeur de la propriété id.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

}
