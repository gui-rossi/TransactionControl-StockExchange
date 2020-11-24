/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.util.List;

/**
 *
 * @author Avell B154 PLUS
 */
class id_global{
	static int id;
}

public class Coordenador extends Participante{
    Transacao trans;
    compra_venda venda_negocio;
    Participante compra;
    compra_venda compra_negocio;
    
    Thread t_trans;
    
    Coordenador (compra_venda venda_negocio, compra_venda compra_negocio, float cotacao_atual, int tipo){    	
    	this.venda_negocio = venda_negocio;
        this.cotacao_negociada = cotacao_atual;
        //VENDEDOR VIRA COORDENADOR E TEM ACESSO AOS METODOS DE PARTICIPANTE
        this.setParticipante(venda_negocio, cotacao_atual, venda_negocio.qtde_acoes, compra_negocio.qtde_acoes);
        this.tipo = tipo;
        
        this.compra_negocio = compra_negocio;
        //CRIO PARTICIPANTE COMPRADOR
        Participante compraP = new Participante();
        compraP.setParticipante(compra_negocio, cotacao_atual, venda_negocio.qtde_acoes, compra_negocio.qtde_acoes);
        this.compra = compraP;
        this.compra.tipo = tipo;
    }
    
    public void abrirTransacao(){
        Transacao transc = new Transacao(this, this.compra, this.cotacao_negociada, id_global.id++);
        this.trans = transc;
        this.t_trans = new Thread(this.trans);
        t_trans.start();
    }
    
    public String obterEstadoTransacao(){
        return this.trans.estado;
    }
}
