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
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Avell B154 PLUS
 */
//classe com os ativos globais
class Cotacao_Global {

    String[] acoes;
    float[] precos;
    int[] multiplicador_importancia;
    int[] inversor;

    int num_acoes = 5;

    Cotacao_Global() {
        this.acoes = new String[num_acoes];
        this.precos = new float[num_acoes];
        this.multiplicador_importancia = new int[num_acoes];
        this.inversor = new int[num_acoes];

        this.acoes[0] = "petrobras";
        this.precos[0] = 10;
        this.multiplicador_importancia[0] = 4;
        this.inversor[0] = 1;

        this.acoes[1] = "azul";
        this.precos[1] = 6;
        this.multiplicador_importancia[1] = 3;
        this.inversor[1] = 1;

        this.acoes[2] = "oi";
        this.precos[2] = 2;
        this.multiplicador_importancia[2] = 1;
        this.inversor[2] = 1;

        this.acoes[3] = "facebook";
        this.precos[3] = 15;
        this.multiplicador_importancia[3] = 5;
        this.inversor[3] = 1;

        this.acoes[4] = "exxonmobil";
        this.precos[4] = 12;
        this.multiplicador_importancia[4] = 5;
        this.inversor[4] = 1;
    }
}

//classe onde armazeno os ativos que o acionista quer receber notificacao
//tipo pode ser 'ganho' ou 'perda'
class cotacoes_notificacao {

    String cotacoes;
    int limite;
    String tipo;

    cotacoes_notificacao(String cotacoes, int limite, String tipo) {
        this.cotacoes = cotacoes;
        this.limite = limite;
        this.tipo = tipo;
    }
}

//classe que armazena os ativos de interesse, que o acionista nao tem
class cotacoes_interesse {

    String cotacoes;

    cotacoes_interesse(String cotacoes) {
        this.cotacoes = cotacoes;
    }
}

//classe que armazena ativos que o acionista tem
class cotacoes_detem {

    String acao;
    int qtde;

    cotacoes_detem(String acao, int qtde) {
        this.acao = acao;
        this.qtde = qtde;
    }
}

//classe que armazena cada acionista
class cliente {

    String nome;
    int id;
    float dinheiro;
    int action = -2;
    InterfaceCli refCli;

    List<cotacoes_interesse> cotacoes_interesse = new ArrayList<cotacoes_interesse>();
    List<cotacoes_detem> cotacoes_detem = new ArrayList<cotacoes_detem>();
    List<cotacoes_notificacao> cotacoes_notificacao = new ArrayList<cotacoes_notificacao>();

    cliente() {
        this.dinheiro = 500;
        Cotacao_Global cotacoes = new Cotacao_Global();
        for (int i = 0; i < cotacoes.num_acoes; i++) {
            cotacoes_detem cotacoes_detem = new cotacoes_detem(cotacoes.acoes[i], 5);
            this.cotacoes_detem.add(cotacoes_detem);
        }

    }
}

//classe que armazena as compras e vendas de todo o sistema
//tipo_negocio pode ser 'compra' ou 'venda'
class compra_venda {
    
    cliente cliente;
    int id_cliente;
    String tipo_negocio;
    String empresa;
    int qtde_acoes;
    int valor_MinimoMaximo;
    InterfaceCli refC;

    compra_venda(InterfaceCli refC, int id_cliente, String tipo_negocio, String empresa, int qtde_acoes, int valor_MinimoMaximo) {
        this.refC = refC;
        this.id_cliente = id_cliente;
        this.tipo_negocio = tipo_negocio;
        this.empresa = empresa;
        this.qtde_acoes = qtde_acoes;
        this.valor_MinimoMaximo = valor_MinimoMaximo;
    }
}

public class ServImpl extends UnicastRemoteObject implements InterfaceServ {

	List<Coordenador> transacoes_perdidas = new ArrayList<Coordenador>();
    List<cliente> acionistas = new ArrayList<cliente>();
    List<compra_venda> negocios = new ArrayList<compra_venda>();
    Cotacao_Global cotacao_global = new Cotacao_Global();

    protected ServImpl() throws RemoteException {

    }

    /*funcao chamada assim que expira o tempo de negocio (compra ou venda) de um acionista*/
    synchronized public void encerrarNegocio(int id, String empresa, String tipo_negocio) throws RemoteException, InterruptedException {
        //percorro a lista de negocios, compras ou vendas do sistema
        for (compra_venda buyers_sellers : negocios) {
            /*se o negocio ainda existe, significa que expirou e tiro ele da lista*/
            if (buyers_sellers.id_cliente == id && buyers_sellers.empresa.contains(empresa) && buyers_sellers.tipo_negocio.contains(tipo_negocio)) {
                System.out.println("id: " + buyers_sellers.id_cliente + " ainda quer realizar " + buyers_sellers.tipo_negocio + " de " + buyers_sellers.qtde_acoes + " ativo(s) da companhia " + buyers_sellers.empresa + ", mas o tempo expirou. Retirando pedido.");
                negocios.remove(buyers_sellers);
            } /*se o negocio ja foi vendido ou comprado*/ else {
                System.out.println("Negocio ja finalizado, terminando thread...");
            }
        }
    }

    /*funcao chamada para criar um pedido de venda de ativo*/
 /*params 
	 * qtde: qtde de ativos a ser vendido
	 * preco_minimo: preco minimo que quero vender no ativo
	 * empresa: codigo do ativo 'petrobras', 'oi' e etc
	 * tempo: tempo maximo at� o pedido expirar
	 * id: id do usuario que esta fazendo o pedido*/
    @Override
    public void pedidoVenda(int qtde, int preco_minimo, String empresa, int tempo, int id) throws RemoteException {
        //percorro os acionistas
        for (cliente clientes : acionistas) {
            //acho o acionista que colocou pedido de venda
            if (clientes.id == id) {
                //percorro as acoes das empresas que ele tem
                for (cotacoes_detem detem : clientes.cotacoes_detem) {
                    //acho a empresa que ele quer vender acao
                    if (detem.acao.contains(empresa)) {
                        //se ele quer vender mais do que tem
                        if (detem.qtde < qtde) {
                            System.out.println(clientes.nome + " quer vender " + qtde + " acoes e s� tem " + detem.qtde + " acao cancelada");
                        } else if (detem.qtde >= qtde) {
                            //crio instancia de venda
                            compra_venda venda = new compra_venda(clientes.refCli, id, "venda", empresa, qtde, preco_minimo);
                            this.negocios.add(venda);
                            System.out.println(clientes.nome + " pedido de venda colocado");
                        }
                        break;
                    }
                }
                break;
            }
        }

        /*crio uma thread que vai finalizar o pedido de venda assim que acabar o timer*/
        CompraVenda venda = new CompraVenda(id, empresa, "venda", tempo, this);
        Thread t_venda = new Thread(venda);
        t_venda.start();
    }

    /*funcao chamada para criar um pedido de compra de ativo
	 * checo aqui se o acionista tem dinheiro suficiente para fazer o pedido de compra
	 * se nao tiver considerando o pior caso (limite de compra), nao realizo o pedido*/
 /*params 
	 * qtde: qtde de ativos a ser comprado
	 * preco_maximo: preco maximo que quero pagar no ativo
	 * empresa: codigo do ativo 'petrobras', 'oi' e etc
	 * tempo: tempo maximo at� o pedido expirar
	 * id: id do usuario que esta fazendo o pedido*/
    @Override
    public void pedidoCompra(int qtde, int preco_maximo, String empresa, int tempo, int id) throws RemoteException {
        //percorro os acionistas
        for (cliente clientes : acionistas) {
            //acho o acionista que colocou pedido de compra
            if (clientes.id == id) {
                //percorro as acoes das empresas que ele tem
                for (cotacoes_detem detem : clientes.cotacoes_detem) {
                    //acho a empresa que ele quer comprar acao
                    if (detem.acao.contains(empresa)) {
                        //se ele quer comprar, mas nao tem dinheiro. Percorro as cotacoes pra achar valor corrente
                        float cotacao_corrente = 0;
                        for (int i = 0; i < cotacao_global.num_acoes; i++) {
                            if (empresa.contains(cotacao_global.acoes[i])) {
                                cotacao_corrente = cotacao_global.precos[i];
                                break;
                            }
                        }
                        //verifico se o acionista ja tem pedidos de compra, pego o pior caso (valor minimoMaximo, que no caso de compras � Maximo e multiplico pela quantidade de ativos)
                        int total_em_compras_ja_feitas = 0;
                        for (compra_venda compras_vendas : negocios) {
                            if (compras_vendas.id_cliente == id && compras_vendas.tipo_negocio.contains("compra")) {
                                total_em_compras_ja_feitas = total_em_compras_ja_feitas + (compras_vendas.valor_MinimoMaximo * compras_vendas.qtde_acoes);
                            }
                        }
                        //se ele tem menos dinheiro do que cotacao atual ou se ele ja fez compras, considerando que ele compre o pior caso de cotacao
                        if (clientes.dinheiro < qtde * cotacao_corrente || clientes.dinheiro < total_em_compras_ja_feitas) {
                            System.out.println(clientes.nome + " quer comprar " + qtde * cotacao_corrente + " em acoes, mas so possui " + clientes.dinheiro + ", ou ja possui muitos pedidos de compra. Acao cancelada");
                        } //se ele tem dinheiro pra comprar as acoes, faco o pedidod de compra
                        else if (clientes.dinheiro >= qtde * cotacao_corrente) {
                            //crio instancia da classe de compra
                            compra_venda compra = new compra_venda(clientes.refCli, id, "compra", empresa, qtde, preco_maximo);
                            this.negocios.add(compra);
                        }
                        break;
                    }
                }
                break;
            }
        }

        /*crio uma thread que vai finalizar o pedido de venda assim que acabar o timer*/
        CompraVenda compra = new CompraVenda(id, empresa, "compra", tempo, this);
        Thread t_compra = new Thread(compra);
        t_compra.start();
    }

    /*funcao chamada a todo instante na thread infinita, checa sempre os negocios correntes*/
    @Override
    synchronized public void checaNegocios() throws RemoteException {
        boolean break_out = false;
        //compras_vendas == VENDEDOR
        for (compra_venda compras_vendas : negocios) {
            //compras_vendas_aux == COMPRADOR
            for (compra_venda compras_vendas_aux : negocios) {
                /*acho duas pessoas que querem negocios na mesma empresa*/
                if (compras_vendas.empresa.contains(compras_vendas_aux.empresa)) {
                    //resgato a cotacao atual do ativo
                    float cotacao_atual = 0;
                    for (int i = 0; i < cotacao_global.num_acoes; i++) {
                        if (compras_vendas.empresa.contains(cotacao_global.acoes[i])) {
                            cotacao_atual = cotacao_global.precos[i];
                        }
                    }
                    
                    //se um destes 2 quer comprar e o outro vender, e o vendedor vende por um valor menor que o comprador e a cotacao atual esta entre eles
                    if (compras_vendas.tipo_negocio == "venda" && compras_vendas_aux.tipo_negocio == "compra"
                            && compras_vendas.valor_MinimoMaximo <= cotacao_atual
                            && cotacao_atual <= compras_vendas_aux.valor_MinimoMaximo) {
                    	int tipo;
                        //1 se o comprador compra mais acoes do que o vendedor vende
                        if (compras_vendas.qtde_acoes < compras_vendas_aux.qtde_acoes) {
                        	tipo = 1;
                            for (cliente clientes : acionistas) {
                                //achou vendedor: coloca dinheiro na conta 
                                if (clientes.id == compras_vendas.id_cliente) {
                                	compras_vendas.cliente = clientes;
                                } 
                                //achou comprador: tira dinheiro da conta 
                                else if (clientes.id == compras_vendas_aux.id_cliente) {
                                	compras_vendas_aux.cliente = clientes;
                                }
                            }
                            
                            Coordenador coord = new Coordenador(compras_vendas, compras_vendas_aux, cotacao_atual, tipo);
                            coord.abrirTransacao();
                            
                            
                            //retira a venda do vendedor
                            negocios.remove(compras_vendas_aux);
                            negocios.remove(compras_vendas);

                            break_out = true;
                            break;
                        } 
                        //2 se o vendedor vende mais acoes do que o comprador compra
                        else if (compras_vendas.qtde_acoes > compras_vendas_aux.qtde_acoes) {
                        	tipo = 2;
                            for (cliente clientes : acionistas) {
                                //achou vendedor: 
                            	if (clientes.id == compras_vendas.id_cliente) {
                            		compras_vendas.cliente = clientes;
                                }
                                //achou comprador:
                                else if (clientes.id == compras_vendas_aux.id_cliente) {
                                	compras_vendas_aux.cliente = clientes;
                                }
                            }

                            Coordenador coord = new Coordenador(compras_vendas, compras_vendas_aux, cotacao_atual, tipo);
                            coord.abrirTransacao();
                            
                            //retira a compra do comprador
                            negocios.remove(compras_vendas);
                            negocios.remove(compras_vendas_aux);
                            
                            break_out = true;
                            break;
                        } 
                        //3 se o vendedor e o comprador compram acoes de mesma quantidade
                        else if (compras_vendas.qtde_acoes == compras_vendas_aux.qtde_acoes) {
                        	tipo = 3;
                            for (cliente clientes : acionistas) {
                                //achou vendedor: coloca dinheiro na conta, tira a quantidade de acoes do nome dele
                                if (clientes.id == compras_vendas.id_cliente) {
                                    compras_vendas.cliente = clientes;
                                } 
                                //achou comprador: tira dinheiro da conta, coloca mais acoes no nome dele
                                else if (clientes.id == compras_vendas_aux.id_cliente) {
                                    compras_vendas_aux.cliente = clientes;    
                                }
                            }
                            
                            Coordenador coord = new Coordenador(compras_vendas, compras_vendas_aux, cotacao_atual, tipo);
                            coord.abrirTransacao();

                            //retira a venda do vendedor e do comprador
                            negocios.remove(compras_vendas);
                            negocios.remove(compras_vendas_aux);

                            break_out = true;
                            break;
                        }

                    }
                }
            }
            if (break_out) {
                break;
            }
        }
    }

    /*funcao que obtem as cotacoes dos ativos de interesse, ou seja, ativos das listas de interesse e detem*/
    @Override
    public void obterCotacoes(int id) throws RemoteException {
        try {
            //percorro acionistas
            for (int i = 0; i < this.acionistas.size(); i++) {
                //achei o acionista
                if (this.acionistas.get(i).id == id) {
                    //percorro a lista de interesses dele
                    for (int j = 0; j < this.acionistas.get(i).cotacoes_interesse.size(); j++) {
                        System.out.println("\n" + this.acionistas.get(i).nome);
                        for (int k = 0; k < cotacao_global.num_acoes; k++) {
                            //printo as cotacoes de interesse dele
                            if (this.acionistas.get(i).cotacoes_interesse.get(j).cotacoes.contains(cotacao_global.acoes[k])) {
                                System.out.println("Acao de interesse: " + cotacao_global.acoes[k] + " de preco: " + cotacao_global.precos[k]);
                            }
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /*funcao que remove uma acao que o acionista nao quer mais obter cotacoes*/
    @Override
    public void removerCotacaoDesinteressante(String cotacao, int id) throws RemoteException {
        boolean nao_possui = true;
        //percorro acionistas
        for (int i = 0; i < this.acionistas.size(); i++) {
            //achei o acionista
            if (this.acionistas.get(i).id == id) {
                for (int j = 0; j < this.acionistas.get(i).cotacoes_interesse.size(); j++) {
                    //achei a cotacao que ele quer remover
                    if (this.acionistas.get(i).cotacoes_interesse.get(j).cotacoes.contains(cotacao)) {
                        nao_possui = false;
                        System.out.println(this.acionistas.get(i).cotacoes_interesse.get(j).cotacoes + " removida do " + this.acionistas.get(i).nome);
                        this.acionistas.get(i).cotacoes_interesse.remove(j);
                    }
                }
                //se nao possuir a cotacao
                if (nao_possui) {
                    System.out.println(this.acionistas.get(i).nome + " nao possui esse ativo sendo monitorado na lista de interesses");
                }
            }
        }
    }

    /*insere ativo de interesse na lista para obter cotacoes*/
    @Override
    public void inserirCotacaoInteressante(String cotacao, int id) throws RemoteException {
        boolean ja_detem = false;
        for (int i = 0; i < this.acionistas.size(); i++) {
            if (this.acionistas.get(i).id == id) {
                for (int k = 0; k < this.acionistas.get(i).cotacoes_interesse.size(); k++) {
                    if (this.acionistas.get(i).cotacoes_interesse.get(k).cotacoes.contains(cotacao)) {
                        ja_detem = true;
                    }
                }
                if (ja_detem) {
                    System.out.println("Voce ja possui cotacao na lista de interesses");
                } else {
                    cotacoes_interesse cot = new cotacoes_interesse(cotacao);
                    this.acionistas.get(i).cotacoes_interesse.add(cot);
                    System.out.println(cotacao + " adicionada na lista de interesses de " + this.acionistas.get(i).nome);
                }

            }
        }
    }

    /*funcao chamada toda hora na thread infinita, checa as notificacoes de todos os acionistas*/
    public void checaNotificacoes() throws RemoteException {
        //percorro os acionistas
        for (cliente clientes : acionistas) {
            //percorro a lista de notificacoes de cada acionista vendo se tem notificacao a emitir
            for (cotacoes_notificacao notificacao : clientes.cotacoes_notificacao) {
                //percorro os ativos globais procurando uma empresa correspondente
                for (int i = 0; i < cotacao_global.num_acoes; i++) {
                    //achou um ativo a ser notificado na lista de cotacoes globais
                    if (cotacao_global.acoes[i].contains(notificacao.cotacoes)) {
                        /*se ele possui uma notificacao de perda e o proximo valor de cotacao for menor que o limite de perda*/
                        if (notificacao.tipo.contains("perda") && cotacao_global.precos[i] - cotacao_global.multiplicador_importancia[i] < notificacao.limite) {
                            clientes.refCli.notificar(clientes.nome + " as acoes da " + notificacao.cotacoes + " atingiram o limite de " + notificacao.tipo + " (Cotacao atual: " + cotacao_global.precos[i] + ")");
                            clientes.cotacoes_notificacao.remove(notificacao);
                        } /*se ele possui uma notificacao de ganho e o proximo valor de cotacao for maior que o limite de ganho*/ else if (notificacao.tipo.contains("ganho") && cotacao_global.precos[i] + cotacao_global.multiplicador_importancia[i] > notificacao.limite) {
                            clientes.refCli.notificar(clientes.nome + " as acoes da " + notificacao.cotacoes + " atingiram o limite de " + notificacao.tipo + " (Cotacao atual: " + cotacao_global.precos[i] + ")");
                            clientes.cotacoes_notificacao.remove(notificacao);
                        }
                    }
                }
            }
        }
    }

    /*os limites tem que ser entre 0 e 100 limites mninimo e maximo que os ativos alcancam
	 * insiro notificacao na lista de notificacoes do acionista*/
    @Override
    public void inserirInteresseNotificacao(int id_usuario, String empresa, int limite, String tipo) throws RemoteException {
        //procuro o acionista
        for (cliente clientes : acionistas) {
            //achei o acionista
            if (clientes.id == id_usuario) {
                //boleano para ver se ele tem o ativo na lista de interesses
                boolean tem_ativo_nos_interesses = false;
                //percorro a lista de interesses dele
                for (cotacoes_interesse interesse : clientes.cotacoes_interesse) {
                    if (interesse.cotacoes.contains(empresa)) {
                        tem_ativo_nos_interesses = true;
                    }
                }
                //se ele tem na lista de interesses o ativo, adiciono a notificacao
                if (tem_ativo_nos_interesses) {
                    cotacoes_notificacao not = new cotacoes_notificacao(empresa, limite, tipo);
                    clientes.cotacoes_notificacao.add(not);
                    System.out.println(not.cotacoes + " adicionada na lista de notificacao do " + clientes.nome);
                } //nao possui ativo no interesse, nao coloca notificacao
                else {
                    System.out.println("Acionista nao possui ativo na lista de interesses");
                }
            }
        }
    }

    /*funcao de criacao de um novo acionista*/
    @Override
    public void registrarInteresse(String nome, int id, InterfaceCli referenciaCliente) throws RemoteException {
        cliente cliente_novo = new cliente();
        cliente_novo.nome = nome;
        cliente_novo.id = id;
        cliente_novo.refCli = referenciaCliente;
        acionistas.add(cliente_novo);

        System.out.println(nome + ", interesse no mercado de a��es registrado");
    }

    /*funcao criada para ver melhor as informacoes de cada acionista*/
    @Override
    public void verInfosAcionista(int id) throws RemoteException {
        for (cliente clientes : acionistas) {
            if (clientes.id == id) {
                System.out.println("\nNome: " + clientes.nome);
                System.out.println("ID: " + clientes.id);
                System.out.println("Dinheiro: " + clientes.dinheiro);
                System.out.println("Detenho: ");
                for (cotacoes_detem detenho : clientes.cotacoes_detem) {
                    System.out.println("\tEmpresa: " + detenho.acao + ". Quantidade: " + detenho.qtde);
                }
                System.out.println("Interesse: ");
                for (cotacoes_interesse interesse : clientes.cotacoes_interesse) {
                    System.out.println("\tEmpresa: " + interesse.cotacoes);
                }
                System.out.println("Notificacao: ");
                for (cotacoes_notificacao notificacao : clientes.cotacoes_notificacao) {
                    System.out.println("\tEmpresa: " + notificacao.cotacoes + ". Limite: " + notificacao.limite + ". Tipo: " + notificacao.tipo);
                }
            }
        }
    }

    @Override
    public void actionResponse(int id, int action) throws RemoteException{
    	for (cliente cliente : acionistas) {
    		//achei o acionista para mudar a acao
    		if (cliente.id == id) {
    			cliente.action = action;
    		}
    	}
    }
    
    /*
     * Funcao que altera os valores globais de cotacoes
     * Sem muito segredo, cada cotaacao tem um valor de importancia,
     * este valor de importancia serve para somar ao valor atual do ativo, empresas grandes tem valor de importancia alto (0 - 5)
     * o que significa que empresas grandes tem valores que oscilam mais rapidos, ja que o valor incrementado � maior.
     * Todas as cotacoes vao de 0 a 100 no valor de cada ativo 
     * */
    @Override
    public void mudaCotacaoGlobal() throws RemoteException {
        for (int i = 0; i < this.cotacao_global.num_acoes; i++) {
            if (cotacao_global.precos[i] + cotacao_global.multiplicador_importancia[i] * cotacao_global.inversor[i] > 100
                    || cotacao_global.precos[i] + cotacao_global.multiplicador_importancia[i] * cotacao_global.inversor[i] < 1) {
                cotacao_global.inversor[i] = cotacao_global.inversor[i] * -1;
            } else {
                cotacao_global.precos[i] = cotacao_global.precos[i] + cotacao_global.multiplicador_importancia[i] * cotacao_global.inversor[i];
            }
        }
    }
}
