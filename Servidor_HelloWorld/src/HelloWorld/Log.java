package HelloWorld;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm:ss"); 
	
	synchronized public void openTransaction(Coordenador coord) {
		try {
			FileWriter writer_log_coord = new FileWriter("logs\\Log_TransacoesCoordenador" + ".txt", true);
			FileWriter writer_log_ac1 = new FileWriter("logs\\Log_Transacoes_" + coord.compra_negocio.cliente.nome + ".txt", true);
			FileWriter writer_log_ac2 = new FileWriter("logs\\Log_Transacoes_" + coord.venda_negocio.cliente.nome + ".txt", true);
			
			LocalDateTime now = LocalDateTime.now();
			
			writer_log_ac1.write("---------------------------------------------------------\n");
			writer_log_ac1.write(this.dtf.format(now) + "\n");
			writer_log_ac1.write("Id transacao: " + coord.trans.id + "\n");
			writer_log_ac1.write("Empresa: " + coord.compra_negocio.empresa + "\n");
			writer_log_ac1.write("Quantidade: " + coord.compra_negocio.qtde_acoes + "\n");
			writer_log_ac1.write("Cotacao negociada: " + coord.cotacao_negociada + "\n\n");
			
			writer_log_ac2.write("---------------------------------------------------------\n");
			writer_log_ac2.write(this.dtf.format(now) + "\n");
			writer_log_ac2.write("Id transacao: " + coord.trans.id + "\n");
			writer_log_ac2.write("Empresa: " + coord.venda_negocio.empresa + "\n");
			writer_log_ac2.write("Quantidade: " + coord.venda_negocio.qtde_acoes + "\n");
			writer_log_ac2.write("Cotacao negociada: " + coord.cotacao_negociada + "\n\n");
			
			writer_log_coord.write("---------------------------------------------------------\n");
			writer_log_coord.write(this.dtf.format(now) + "\n");
			writer_log_coord.write("Id transacao: " + coord.trans.id + "\n");
			writer_log_coord.write("Comprador: " + coord.compra_negocio.cliente.nome + "\n");
			writer_log_coord.write("Quantidade: " + coord.compra_negocio.qtde_acoes + "\n");
			writer_log_coord.write("Vendedor: " + coord.venda_negocio.cliente.nome + "\n");
			writer_log_coord.write("Quantidade: " + coord.venda_negocio.qtde_acoes + "\n");
			writer_log_coord.write("Empresa: " + coord.compra_negocio.empresa + "\n");
			writer_log_coord.write("Cotacao negociada: " + coord.cotacao_negociada + "\n\n");
			
			writer_log_coord.close();
			writer_log_ac2.close();
			writer_log_ac1.close();
		}catch(Exception e) {System.out.println(e.getMessage());}
	}
	
	synchronized public void stateTransaction(Participante part) {
		try {
			FileWriter writer_log_coord = new FileWriter("logs\\Log_TransacoesCoordenador" + ".txt", true);
			FileWriter writer_log_part = new FileWriter("logs\\Log_Transacoes_" + part.cliente_neg.cliente.nome + ".txt", true);
			
			LocalDateTime now = LocalDateTime.now();
			
			writer_log_part.write(this.dtf.format(now) + "\n");
			writer_log_part.write("Estado: " + part.estado + "\n\n");
			
			writer_log_coord.write(this.dtf.format(now) + "\n");
			writer_log_coord.write("Participante: " + part.cliente_neg.cliente.nome + " / Estado: " + part.estado + "\n");
			
			writer_log_coord.close();
			writer_log_part.close();
		}catch(Exception e) {}
	}
	
	synchronized public void closeTransaction(Coordenador coord) {
		try {
			FileWriter writer_log_coord = new FileWriter("logs\\Log_TransacoesCoordenador" + ".txt", true);
			FileWriter writer_log_part1 = new FileWriter("logs\\Log_Transacoes_" + coord.compra_negocio.cliente.nome + ".txt", true);
			FileWriter writer_log_part2 = new FileWriter("logs\\Log_Transacoes_" + coord.venda_negocio.cliente.nome + ".txt", true);
			
			writer_log_part1.write("* TRANSACAO = " + coord.trans.estado + " *\n");
			writer_log_part2.write("* TRANSACAO = " + coord.trans.estado + " *\n");
			writer_log_coord.write("* TRANSACAO = " + coord.trans.estado + " *\n");
			
			writer_log_part1.write("---------------------------------------------------------\n\n");
			writer_log_part2.write("---------------------------------------------------------\n\n");
			writer_log_coord.write("---------------------------------------------------------\n\n");
			
			writer_log_coord.close();
			writer_log_part1.close();
			writer_log_part2.close();
		}catch(Exception e) {}
	}
	
	synchronized public void carteiraFinal(Coordenador coord) {
		try {
			FileWriter writer_log_part1 = new FileWriter("logs\\CarteiraFinal_" + coord.compra_negocio.cliente.nome + ".txt", true);
			FileWriter writer_log_part2 = new FileWriter("logs\\CarteiraFinal_" + coord.venda_negocio.cliente.nome + ".txt", true);
			
			LocalDateTime now = LocalDateTime.now();
			
			//PARTICIPANTE 1
			writer_log_part1.write("---------------------------------------------------------\n");
			writer_log_part1.write(this.dtf.format(now) + "\n");
			writer_log_part1.write("Dinheiro: " + coord.compra_negocio.cliente.dinheiro + "\n");
			writer_log_part1.write("Acoes: \n");
			for (int i = 0; i < coord.compra_negocio.cliente.cotacoes_detem.size(); i++) {
				writer_log_part1.write("\tEmpresa: " + coord.compra_negocio.cliente.cotacoes_detem.get(i).acao + ", Qtde:");
				writer_log_part1.write(coord.compra_negocio.cliente.cotacoes_detem.get(i).qtde + "\n");
			}
			writer_log_part1.write("---------------------------------------------------------\n\n");
			
			//PARTICIPANTE 2
			writer_log_part2.write("---------------------------------------------------------\n");
			writer_log_part2.write(this.dtf.format(now) + "\n");
			writer_log_part2.write("Dinheiro: " + coord.venda_negocio.cliente.dinheiro + "\n");
			writer_log_part2.write("Acoes: \n");
			for (int i = 0; i < coord.venda_negocio.cliente.cotacoes_detem.size(); i++) {
				writer_log_part2.write("\tEmpresa: " + coord.venda_negocio.cliente.cotacoes_detem.get(i).acao + ", Qtde:");
				writer_log_part2.write(coord.venda_negocio.cliente.cotacoes_detem.get(i).qtde + "\n");
			}
			writer_log_part2.write("---------------------------------------------------------\n\n");
			
			writer_log_part1.close();
			writer_log_part2.close();
		}catch(Exception e) {}
	}
	
	synchronized public void carteiraIntermediaria(Coordenador coord) {
		try {
			FileWriter writer_log_part1 = new FileWriter("logs\\CarteiraIntermediaria_" + coord.venda_negocio.cliente.nome + ".txt", true);
			FileWriter writer_log_part2 = new FileWriter("logs\\CarteiraIntermediaria_" + coord.compra_negocio.cliente.nome + ".txt", true);
			
			LocalDateTime now = LocalDateTime.now();
			
			//PARTICIPANTE 1
			writer_log_part1.write("---------------------------------------------------------\n");
			writer_log_part1.write(this.dtf.format(now) + "\n");
			writer_log_part1.write("Dinheiro: " + coord.dinheiro_intermed + "\n");
			writer_log_part1.write("Acoes: \n");
			for (int i = 0; i < coord.carteira_intermediaria.size(); i++) {
				writer_log_part1.write("\tEmpresa: " + coord.carteira_intermediaria.get(i).acao + ", Qtde:");
				writer_log_part1.write(coord.carteira_intermediaria.get(i).qtde + "\n");
			}
			writer_log_part1.write("---------------------------------------------------------\n\n");
			
			//PARTICIPANTE 2
			writer_log_part2.write("---------------------------------------------------------\n");
			writer_log_part2.write(this.dtf.format(now) + "\n");
			writer_log_part2.write("Dinheiro: " + coord.compra.dinheiro_intermed + "\n");
			writer_log_part2.write("Acoes: \n");
			for (int i = 0; i < coord.venda_negocio.cliente.cotacoes_detem.size(); i++) {
				writer_log_part2.write("\tEmpresa: " + coord.compra.carteira_intermediaria.get(i).acao + ", Qtde:");
				writer_log_part2.write(coord.compra.carteira_intermediaria.get(i).qtde + "\n");
			}
			writer_log_part2.write("---------------------------------------------------------\n\n");
			
			writer_log_part1.close();
			writer_log_part2.close();
		}catch(Exception e) {}
	}
}
