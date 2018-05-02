/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.Interface;

import DataAccessLayer.DTO.Client;
import DataAccessLayer.DTO.Contact;
import DataAccessLayer.GenericInterface.GInterface;
import java.sql.Connection;
import java.util.ArrayList;


/**
 *
 * @author team 4
 */
public interface ContactInterface extends GInterface <Client>{

    /**
     *
     * @param obj1
     * @param obj2
     * @param con
     * @return
     */
    public int create (Client obj1,Client obj2,Connection con);

    /**
     *
     * @param obj
     * @param obj2
     * @param con
     * @return
     */
    public Contact retreive (Client obj,Client obj2,Connection con);

    /**
     *
     * @param email
     * @param obj
     * @param con
     * @return
     */
    public int update (String email,Client obj,Connection con);

    /**
     *
     * @param obj1
     * @param obj2
     * @param con
     * @return
     */
    public int delete (Client obj1,Client obj2,Connection con);

    /**
     *
     * @param obj
     * @param con
     * @return
     */
    public ArrayList<Contact> retreiveall (Client obj,Connection con);
    
}
