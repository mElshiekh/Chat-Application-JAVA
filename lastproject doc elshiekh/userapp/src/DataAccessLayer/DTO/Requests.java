/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.DTO;

/**
 *
 * @author abdalla
 */
public class Requests implements java.io.Serializable {
    private String senderEmail;
    private String recevierEmail;

    public String getEmailSender() {
        return senderEmail;
    }

    public void setEmailSender(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getEmailReciever() {
        return recevierEmail;
    }

    public void setEmailReciever(String recevierEmail) {
        this.recevierEmail = recevierEmail;
    }
    
}
