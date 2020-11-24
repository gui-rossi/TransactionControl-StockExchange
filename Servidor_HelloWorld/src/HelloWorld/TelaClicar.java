/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import javax.swing.JFrame;

/**
 *
 * @author Avell B154 PLUS
 */
public class TelaClicar implements Runnable{
    Participante part;
    boolean timeout = false;
    
    TelaClicar(Participante part){
        this.part = part;
    }
    
    public void timedOut(){
    	this.timeout = true;
    }
    
    @Override
    public void run(){
    	try {
    		Timeout time_out = new Timeout(this);
            Thread t_time = new Thread(time_out);

            //ENVIO PARA O CLIENTE A OPCAO DE ACEITAR OU REJEITAR
            this.part.cliente_neg.cliente.refCli.requestAction();
            t_time.start();
            while (this.part.cliente_neg.cliente.action == -2 && this.timeout == false){
                
            	Thread.sleep(100);
            }
            
            if (this.timeout) {
            	this.part.cliente_neg.cliente.action = -1;
            	this.part.cliente_neg.cliente.refCli.hideButtons();
            	this.part.estado = "TIMEOUT";
            } else if (this.part.cliente_neg.cliente.action == 1) {
            	t_time.interrupt();
            	this.part.estado = "ACCEPT";
            } else if (this.part.cliente_neg.cliente.action == 0) {
            	t_time.interrupt();
            	this.part.estado = "DENY";
            }
    	} catch (Exception e) {}
    }
}
