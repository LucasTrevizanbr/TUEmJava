package negocio;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GerenciadoraClientesTest_Ex2 {

	private GerenciadoraClientes gerClientes;

	@Test
	public void testPesquisaCliente() {

		// criando alguns clientes
		Cliente cliente01 = new Cliente(1, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(2, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 2, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);
		
		gerClientes = new GerenciadoraClientes(clientesDoBanco);
		
		Cliente cliente = gerClientes.pesquisaCliente(1);
		
		assertThat(cliente.getId(), is(1));
		
	}
	
	@Test
	public void testRemoveCliente() {

		//Dado que eu tenha cliente no banco e eles estejam na lista de clientes do banco.
		Cliente cliente01 = new Cliente(1, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(2, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 2, true);
		
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);
		
		/*Quando eu chamar meu gerenciador de clientes usando minha lista de clientes como parâmetro
		 e chamar seu método de remoção usando um id de cliente:
		 */
		gerClientes = new GerenciadoraClientes(clientesDoBanco);
		
		boolean clienteRemovido = gerClientes.removeCliente(2);
		
		//Então
		//ele vai verificar se o cliente removido deu "verdadeiro":
		assertThat(clienteRemovido, is(true));
		//Adicionalmente verifico se o tamanho da lista diminuiu para 1
		assertThat(gerClientes.getClientesDoBanco().size(), is(1));
		//e verificando se o retorno da minha pesquisa do cliente que foi removido retornará true;
		assertNull(gerClientes.pesquisaCliente(2));
		
	}

}
