package HelloWorld;

import java.util.Random;

/**
 *
 * @author Avell B154 PLUS
 */
public class Transacao implements Runnable{
    String estado = "";
    int id; //id entre 1 e 10000
    Coordenador coord;
    Participante compra;
    float cotacao_par;
    
    Log logs = new Log();
    
    Thread t_C;
    Thread t_V;
    
    Transacao (Coordenador venda, Participante compra, float cotacao_par, int id) {
        Random r = new Random();
        this.id = id;
    	this.cotacao_par = cotacao_par;
        this.coord = venda;
        this.compra = compra;
        this.start();
    }
    
    public void logCarteiraFinal() {
    	this.logs.carteiraFinal(this.coord);
    }
    
    public void logCarteiraIntermed() {
    	this.logs.carteiraIntermediaria(this.coord);
    }
    
    public void openLogTrans() {
    	this.logs.openTransaction(this.coord);
    }
    
    public void stateLogTrans() {
    	this.logs.stateTransaction(this.compra);
    	this.logs.stateTransaction(this.coord);
    }
    
    public void closeLogTrans() {
    	this.logs.closeTransaction(this.coord);
    }
    
    public void setEstadoFinal(String estado_part1, String estado_part2){
    	
    }
    
    public void start(){
        //crio uma thread para comprador
        TelaClicar compra_t = new TelaClicar(this.coord);
        this.t_C = new Thread(compra_t);
        //crio uma thread para vendedor
        TelaClicar venda_t = new TelaClicar(this.compra);
        this.t_V = new Thread(venda_t);
    }
    
    @Override
    public void run(){
        try{
        	this.openLogTrans();
        	
            this.compra.preparar();
            this.coord.preparar();
            this.estado = "PREPARANDO";
            
            this.logCarteiraIntermed();
            this.stateLogTrans();
            
            this.t_C.start();
            this.t_V.start();
            
            this.t_C.join();
            this.t_V.join();
            
            //se alguem tomou timeout ou alguem abortou
            if (this.compra.cliente_neg.cliente.action == -1 || this.coord.cliente_neg.cliente.action == -1 ||
            	this.compra.cliente_neg.cliente.action == 0 || this.coord.cliente_neg.cliente.action == 0){
                throw new Exception("an error occurred...");
            //se os ddois aceitaram
            }else if (this.compra.cliente_neg.cliente.action == 1 && this.coord.cliente_neg.cliente.action == 1){
                this.compra.efetivar();
                this.coord.efetivar();
                this.estado = "EFETUADA";
                this.stateLogTrans();
            }
        }catch(Exception e){
        	this.compra.abortar();
            this.coord.abortar();
            this.estado = "ABORTADA";
            this.stateLogTrans();
        }
        
        this.closeLogTrans();
        this.logCarteiraFinal();
        
        this.compra.cliente_neg.cliente.action = -2;
        this.coord.cliente_neg.cliente.action = -2;
        
    }
}
