
package com.projetarchi.clientadmin.soap.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="succes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "succes"
})
@XmlRootElement(name = "supprimerUtilisateurResponse")
public class SupprimerUtilisateurResponse {

    @XmlElement(namespace = "http://siteactualites.com/soap/utilisateurs")
    protected boolean succes;

    /**
     * Obtient la valeur de la propriété succes.
     * 
     */
    public boolean isSucces() {
        return succes;
    }

    /**
     * Définit la valeur de la propriété succes.
     * 
     */
    public void setSucces(boolean value) {
        this.succes = value;
    }

}
