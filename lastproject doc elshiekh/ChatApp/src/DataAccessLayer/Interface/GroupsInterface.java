/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.Interface;



import DataAccessLayer.DTO.Client;
import DataAccessLayer.DTO.Groups;
import DataAccessLayer.GenericInterface.GInterface;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author Team 4.
 */
public interface GroupsInterface extends GInterface <Groups>{

    /**
     *
     * @param name
     * @param con
     * @return
     */
    public Groups retreiveByName (String name,Connection con);

    /**
     *
     * @param con
     * @return
     */
    public ArrayList<Groups> retreiveAll (Connection con);

    /**
     *
     * @param id
     * @param con
     * @return
     */
    public ArrayList<Client> reteriveClients(String id,Connection con);    
}
