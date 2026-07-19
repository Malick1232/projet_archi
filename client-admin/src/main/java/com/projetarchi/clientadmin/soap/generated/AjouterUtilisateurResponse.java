
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
 *         &lt;element name="utilisateur" type="{http://siteactualites.com/soap/utilisateurs}utilisateurInfo"/>
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
    "utilisateur"
})
@XmlRootElement(name = "ajouterUtilisateurResponse")
public class AjouterUtilisateurResponse {

    @XmlElement(required = true, namespace = "http://siteactualites.com/soap/utilisateurs")
    protected UtilisateurInfo utilisateur;

    /**
     * Obtient la valeur de la propriété utilisateur.
     * 
     * @return
     *     possible object is
     *     {@link UtilisateurInfo }
     *     
     */
    public UtilisateurInfo getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit la valeur de la propriété utilisateur.
     * 
     * @param value
     *     allowed object is
     *     {@link UtilisateurInfo }
     *     
     */
    public void setUtilisateur(UtilisateurInfo value) {
        this.utilisateur = value;
    }

}
