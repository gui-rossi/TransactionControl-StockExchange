/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;


/**
 *
 * @author Avell B154 PLUS
 */
public class CliImpl extends UnicastRemoteObject implements InterfaceCli {
	GUI gui;
	JButton accept;
	JButton deny;
	
    CliImpl (String nome, int id, InterfaceServ referenciaServer, GUI gui) throws RemoteException{
        this.gui = gui;
    	referenciaServer.registrarInteresse(nome, id, this);
    }
    
    public void setButtons(JButton accept, JButton deny) {
    	this.accept = accept;
    	this.deny = deny;
    	this.accept.setVisible(false);
    	this.deny.setVisible(false);
    }
    
    @Override
    public void notificar(String texto) throws RemoteException{
        System.out.println(texto);
    }
    
    @Override
    public void requestAction() throws RemoteException {
    	this.accept.setVisible(true);
    	this.deny.setVisible(true);
    }
    
    @Override
    public void hideButtons() throws RemoteException {
    	this.accept.setVisible(false);
    	this.deny.setVisible(false);
    }
    
}
