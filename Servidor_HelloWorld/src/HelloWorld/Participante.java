/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Avell B154 PLUS
 */
public class Participante {
    compra_venda cliente_neg;
    float cotacao_negociada;
    List<cotacoes_detem> carteira_intermediaria = new ArrayList<cotacoes_detem>();
    float dinheiro_intermed;
    String estado = "PREPARANDO";
    int tipo;
    int quantidades_ativos_comprador;
    int quantidades_ativos_vendedor;
    
    public void setParticipante(compra_venda cliente_neg, float cotacao_negociada, int venda_qtde, int compra_qtde){
    	this.quantidades_ativos_vendedor = venda_qtde;
    	this.quantidades_ativos_comprador = compra_qtde;
    	this.cotacao_negociada = cotacao_negociada;
        this.cliente_neg = cliente_neg;
    }
    
    public Participante getParticipante(){
        return this;
    }
    
    public void preparar(){
        //copio carteira real para intermed
    	for (cotacoes_detem intermed : this.cliente_neg.cliente.cotacoes_detem)
    		this.carteira_intermediaria.add(new cotacoes_detem(intermed.acao, intermed.qtde));
    	
    	this.dinheiro_intermed = this.cliente_neg.cliente.dinheiro;
    	/*------------------------------------------------------------------------------------*/
    	
    	//faco alteracoes na carteira intermediaria
    	//SE O COMPRADOR COMPRA MAIS ATIVOS DO QUE O VENDEDOR VENDE
    	if (this.tipo == 1) {
    		if (this.cliente_neg.tipo_negocio.contains("compra")){
    			//TRATO COMPRADOR
    			boolean ja_detia_acao = false;
    			
                this.dinheiro_intermed = this.dinheiro_intermed - (this.cotacao_negociada * this.quantidades_ativos_vendedor);
                for (cotacoes_detem detem_intermed : this.carteira_intermediaria) {
                    if (this.cliente_neg.empresa.contains(detem_intermed.acao)) {
                        //coloca mais acoes no nome dele
                    	detem_intermed.qtde = detem_intermed.qtde + this.quantidades_ativos_vendedor;
                        ja_detia_acao = true;
                    }
                }
                //se nao tinha a acao na lista, adiciona na lista
                if (!ja_detia_acao) {
                    cotacoes_detem det = new cotacoes_detem(this.cliente_neg.empresa, this.quantidades_ativos_vendedor);
                    this.cliente_neg.cliente.cotacoes_detem.add(det);
                }
    		}else if (this.cliente_neg.tipo_negocio.contains("venda")){
    			//TRATO VENDEDOR
    			this.dinheiro_intermed = this.dinheiro_intermed + (this.cotacao_negociada * this.cliente_neg.qtde_acoes);
                for (cotacoes_detem detem_intermed : this.carteira_intermediaria) {
                    if (this.cliente_neg.empresa.contains(detem_intermed.acao)) {
                        //tira a quantidade de acoes do nome dele
                    	detem_intermed.qtde = detem_intermed.qtde - this.cliente_neg.qtde_acoes;
                        //se terminou com 0 acoes, remove da lista
                        if (detem_intermed.qtde <= 0) {
                            this.cliente_neg.cliente.cotacoes_detem.remove(detem_intermed);
                        }
                    }
                }
    		} 	
    	}
    	//SE O VENDEDOR VENDE MAIS ATIVOS DO QUE O COMPRADOR COMPRA
    	else if (this.tipo == 2) {
    		//TRATO COMPRADOR
    		if (this.cliente_neg.tipo_negocio.contains("compra")){
    			boolean ja_detia_acao = false;
    			
    			this.dinheiro_intermed = this.dinheiro_intermed - (this.cotacao_negociada * this.cliente_neg.qtde_acoes);
                for (cotacoes_detem detem_intermed : this.carteira_intermediaria) {
                    if (this.cliente_neg.empresa.contains(detem_intermed.acao)) {
                    	detem_intermed.qtde = detem_intermed.qtde + this.cliente_neg.qtde_acoes;
                        ja_detia_acao = true;
                    }
                }
                //se nao tinha a acao na lista, adiciona na lista
                if (!ja_detia_acao) {
                    cotacoes_detem det = new cotacoes_detem(this.cliente_neg.empresa, this.cliente_neg.qtde_acoes);
                    this.carteira_intermediaria.add(det);
                }
    		}
    		//TRATO VENDEDOR
    		else if (this.cliente_neg.tipo_negocio.contains("venda")){
    			this.dinheiro_intermed = this.dinheiro_intermed + (this.cotacao_negociada * this.quantidades_ativos_comprador);
                for (cotacoes_detem detem_intermed : this.carteira_intermediaria) {
                    if (this.cliente_neg.empresa.contains(detem_intermed.acao)) {
                    	detem_intermed.qtde = detem_intermed.qtde - this.quantidades_ativos_comprador;
                    }
                }
    		} 	
    	} 
    	//SE O COMPRADOR COMPRA ACOES DA MESMA QUANTIDADE QUE O VENDEDOR VENDE
    	else if (this.tipo == 3) {
    		if (this.cliente_neg.tipo_negocio.contains("compra")){
    			//TRATO O COMPRADOR
    			boolean ja_detia_acao = false;
                
                this.dinheiro_intermed = this.dinheiro_intermed - (this.cotacao_negociada * this.cliente_neg.qtde_acoes);
                for (cotacoes_detem detem_intermed : this.carteira_intermediaria) {
                    if (this.cliente_neg.empresa.contains(detem_intermed.acao)) {
                        detem_intermed.qtde = detem_intermed.qtde + this.cliente_neg.qtde_acoes;
                        ja_detia_acao = true;
                        break;
                    }
                }
                //se nao tinha a acao na lista, adiciona na lista
                if (!ja_detia_acao) {
                    cotacoes_detem det = new cotacoes_detem(this.cliente_neg.empresa, this.cliente_neg.qtde_acoes);
                    this.carteira_intermediaria.add(det);
                }
    		}else if (this.cliente_neg.tipo_negocio.contains("venda")){
    			//TRATO O VENDEDOR
    			this.dinheiro_intermed = this.dinheiro_intermed + (this.cotacao_negociada * this.cliente_neg.qtde_acoes);
                for (cotacoes_detem detem_intermed : this.carteira_intermediaria) {
                    if (this.cliente_neg.empresa.contains(detem_intermed.acao)) {
                        detem_intermed.qtde = detem_intermed.qtde - this.cliente_neg.qtde_acoes;
                        //se terminou com 0 acoes, remove da fila
                        if (detem_intermed.qtde <= 0) {
                        	this.carteira_intermediaria.remove(detem_intermed);
                        	break;
                        }
                    }
                }
    		} 
    	}
    }
    synchronized public void efetivar(){
    	//percorro a carteira intermediaria pegando cada acao que la tinha e coloco na real
    	if (this.cliente_neg.tipo_negocio.contains("compra")) {
    		boolean achouAtivoNovo;
    		for (cotacoes_detem intermed : this.carteira_intermediaria) {
    			achouAtivoNovo = false;
        		for (cotacoes_detem real : this.cliente_neg.cliente.cotacoes_detem) {
        			//Achei ativo da carteira intermediaria na carteira real
        			if (intermed.acao.contains(real.acao)) {
        				achouAtivoNovo = true;
        				real.qtde = intermed.qtde;
        			}
        		}
        		//se nao achei ativo da carteira intermediaria na carteira real, adiciono na carteira real
        		if (!achouAtivoNovo)
        			this.cliente_neg.cliente.cotacoes_detem.add(new cotacoes_detem(intermed.acao, intermed.qtde));
        	}
    	}else if (this.cliente_neg.tipo_negocio.contains("venda")) {
    		boolean achouAtivoVelho;
    		for (cotacoes_detem real : this.cliente_neg.cliente.cotacoes_detem) {
    			achouAtivoVelho = false;
        		for (cotacoes_detem intermed : this.carteira_intermediaria) {
        			//Achei ativo da carteira real na carteira intermediaria
        			if (real.acao.contains(intermed.acao)) {
        				achouAtivoVelho = true;
        				real.qtde = intermed.qtde;
        			}
        		}
        		//se nao achei ativo da carteira intermediaria na carteira real, removo da carteira real
        		if (!achouAtivoVelho) {
        			this.cliente_neg.cliente.cotacoes_detem.remove(real);
        			break;
        		}
        	}
    	}
    	
    	this.cliente_neg.cliente.dinheiro = this.dinheiro_intermed;
    }
    public void abortar(){
        //removo o que tinha na carteira intermediaria
    	this.carteira_intermediaria.clear();
    	this.dinheiro_intermed = 0;
    }
}
