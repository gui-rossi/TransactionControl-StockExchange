package HelloWorld;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Sistema implements Runnable {
	InterfaceServ servRef;
	
	Sistema(InterfaceServ referenciaServidor) throws RemoteException{
	        this.servRef = referenciaServidor;
	}
	
	/*Thread que fica sempre rodando checando se rolou venda e compra*/
	@Override
	public void run() {
		while (true) {
			try {
				/*checo se tem alguem comprando ou vendendo acoes e realizo o negocio*/
				servRef.checaNegocios();
				/*checo se preciso notificar alguem sobre ganho ou perda*/
				servRef.checaNotificacoes();
			}catch(Exception e) {}
		}
	}
}
