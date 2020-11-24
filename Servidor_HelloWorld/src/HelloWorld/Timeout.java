/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

/**
 *
 * @author Avell B154 PLUS
 */
public class Timeout implements Runnable {
    TelaClicar notClicked;
    
    Timeout(TelaClicar awaitClick){
        this.notClicked = awaitClick;
    }
    
    @Override
    public void run(){
        try {
            //espera 8 segundos e da timeout se um dos dois nao responder
            Thread.sleep(8000);
        }catch(Exception e) {}
        this.notClicked.timedOut();
    }
}
