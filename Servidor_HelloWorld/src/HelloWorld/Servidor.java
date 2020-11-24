/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Avell B154 PLUS
 */
public class Servidor {
    static int portaSN = 1099;
    
    public static void main(String[] args) throws NotBoundException {
        try {
            Registry referenciaServicoNomes;
            InterfaceServ referenciaServidor = new ServImpl();
            
            id_global.id = 0;
            
            referenciaServicoNomes = LocateRegistry.createRegistry(portaSN);
            referenciaServicoNomes.rebind("HelloWorld", referenciaServidor);
            
            /*Coloco pra rodar a thread que fica sempre verificando os pedidos de compra e venda*/
            Sistema sis = new Sistema(referenciaServidor);
            Thread t_Sis = new Thread(sis);
            t_Sis.start();
            MudaCotacao muda = new MudaCotacao(referenciaServidor);
            Thread t_Muda = new Thread(muda);
            t_Muda.start();
            
            System.out.println("Servidor iniciado\n");
            
            
        } catch (RemoteException ex) {
        	System.out.println("Erro no servidor " + ex.getMessage());
        }

    }
}
