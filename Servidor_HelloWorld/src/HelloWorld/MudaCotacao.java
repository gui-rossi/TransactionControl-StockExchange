package HelloWorld;

import java.rmi.RemoteException;

public class MudaCotacao implements Runnable{
	InterfaceServ servRef;
	
	MudaCotacao(InterfaceServ referenciaServidor) throws RemoteException{
	        this.servRef = referenciaServidor;
	}
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
				servRef.mudaCotacaoGlobal();
			} catch (InterruptedException e) {} catch (RemoteException e) {}
			
		}
	}
}
