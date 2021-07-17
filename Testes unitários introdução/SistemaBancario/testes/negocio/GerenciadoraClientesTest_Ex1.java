package negocio;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GerenciadoraClientesTest_Ex1 {

	@Test
	public void testPesquisaCliente() {

		//Dado que eu tenha clientes com uma conta no banco:
		Cliente cliente01 = new Cliente(1, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(2, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 2, true);
		
		//e esses clientes estejam inseridos na base do banco:
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);
		
		//QUANDO quando eu chamar meu gerenciador de clientes:
		GerenciadoraClientes gerClientes = new GerenciadoraClientes(clientesDoBanco);
		
		//e executar seu método de pesquisa
		Cliente cliente = gerClientes.pesquisaCliente(1);
		
		//ENTÃO ele me retorna o cliente procurado:
		/*o asserThat esta verificando se a informação que o cliente buscado retornou é igual ao
		parâmetro passado a direita, "então o id do meu cliente é 1?"*/
		assertThat(cliente.getId(), is(1));
		assertThat(cliente.getEmail(), is("gugafarias@gmail.com"));
		
	}

}
