/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 *
 * @author Avell B154 PLUS
 */
public interface InterfaceServ extends Remote {
	void registrarInteresse(String nome, int id, InterfaceCli referenciaCliente) throws RemoteException;
	void removerCotacaoDesinteressante (String cotacao, int id) throws RemoteException;
	void inserirCotacaoInteressante (String cotacao, int id) throws RemoteException;
	void obterCotacoes (int id) throws RemoteException;
	void pedidoCompra (int qtde, int valor_minimo, String empresa, int tempo, int id) throws RemoteException;
	void pedidoVenda (int qtde, int valor_minimo, String empresa, int tempo, int id) throws RemoteException;
	void checaNegocios () throws RemoteException;
	void mudaCotacaoGlobal () throws RemoteException;
	void checaNotificacoes () throws RemoteException;
	void inserirInteresseNotificacao (int id_usuario, String empresa, int limite, String tipo) throws RemoteException;
	void verInfosAcionista (int id) throws RemoteException;

	void actionResponse(int id, int action) throws RemoteException;
}