/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 *
 * @author Avell B154 PLUS
 */
public interface InterfaceCli extends Remote {
	void notificar(String texto) throws RemoteException;
	void requestAction() throws RemoteException;
	void hideButtons() throws RemoteException;
}
