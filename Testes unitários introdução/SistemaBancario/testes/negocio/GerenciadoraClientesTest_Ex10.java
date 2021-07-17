package negocio;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de teste criada para garantir o funcionamento das principais operações
 * sobre clientes, realizadas pela classe {@link GerenciadoraClientes}.
 * 
 * @author Gustavo Farias
 * @date 21/01/2035 
 */
public class GerenciadoraClientesTest_Ex10 {

	private GerenciadoraClientes gerClientes;
	private int idCLiente01 = 1;
	private	int idCLiente02 = 2;
	
	@Before
	public void setUp() {
	
		/* ========== GIVEN/DADO QUE ========== */
		
		//tenho clientes
		Cliente cliente01 = new Cliente(idCLiente01, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(idCLiente02, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);
		
		//eles estão na lista
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);
		
		gerClientes = new GerenciadoraClientes(clientesDoBanco);
	}

	@After
	public void tearDown() {
		gerClientes.limpa();
	}
	
	/**
	 * Teste básico da pesquisa de um cliente a partir do seu ID.
	 * 
	 * @author Gustavo Farias
	 * @date 21/01/2035
	 */
	@Test
	public void testPesquisaCliente() {

		/* ========== Execução ========== */
		Cliente cliente = gerClientes.pesquisaCliente(idCLiente01);
		
		/* ========== Verificações ========== */
		assertThat(cliente.getId(), is(idCLiente01));
		
	}
	
	/**
	 * Teste básico da pesquisa por um cliente que não existe.
	 * 
	 * @author Gustavo Farias
	 * @date 21/01/2035
	 */
	@Test
	public void testPesquisaClienteInexistente() {

		/* ========== WHEN/QUANDO ========== */
		Cliente cliente = gerClientes.pesquisaCliente(1001);
		
		/* ========== THEN/ENTÃO ========== */
		assertNull(cliente);
		
	}
	
	/**
	 * Teste básico da remoção de um cliente a partir do seu ID.
	 * 
	 * @author Gustavo Farias
	 * @date 21/01/2035
	 */
	@Test
	public void testRemoveCliente() {
		
		/* ========== WHEN/QUANDO ========== */
		boolean clienteRemovido = gerClientes.removeCliente(idCLiente02);
		
		/* ========== THEN/ENTÃO ========== */
		assertThat(clienteRemovido, is(true));
		assertThat(gerClientes.getClientesDoBanco().size(), is(1));
		assertNull(gerClientes.pesquisaCliente(idCLiente02));
		
	}
	
	/**
	 * Teste da tentativa de remoção de um cliente inexistente.
	 * 
	 * @author Gustavo Farias
	 * @date 21/01/2035
	 */
	@Test
	public void testRemoveClienteInexistente() {

	
		/* ========== WHEN/QUANDO ========== */
		boolean clienteRemovido = gerClientes.removeCliente(1001);
		
		/* ========== THEN/ENTÃO ========== */
		assertThat(clienteRemovido, is(false));
		assertThat(gerClientes.getClientesDoBanco().size(), is(2));
		
	}
	
	/**
	 * Validação da idade de um cliente quando a mesma está no intervalo permitido.
	 * 
	 * @author Gustavo Farias
	 * @throws IdadeNaoPermitidaException 
	 * @date 21/01/2035
	 */
	@Test
	public void testClienteIdadeAceitavel() throws IdadeNaoPermitidaException {

		/* ========== GIVEN/DADO QUE ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 25, "guga@gmail.com", 1, true);
		
		/* ========== WHEN/QUANDO ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());
		
		/* ========== THEN/ENTÃO ========== */
		assertTrue(idadeValida);	
	}
	
	/**
	 * Validação da idade de um cliente quando a mesma está no intervalo permitido.
	 * 
	 * @author Gustavo Farias
	 * @throws IdadeNaoPermitidaException 
	 * @date 21/01/2035
	 */
	@Test
	public void testClienteIdadeAceitavel_02() throws IdadeNaoPermitidaException {

		/* ========== GIVEN/DADO QUE ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 18, "guga@gmail.com", 1, true);
		
		/* ========== WHEN/QUANDO ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());
		
		/* ========== THEN/ENTÃO ========== */
		assertTrue(idadeValida);	
	}
	
	/**
	 * Validação da idade de um cliente quando a mesma está no intervalo permitido.
	 * 
	 * @author Gustavo Farias
	 * @throws IdadeNaoPermitidaException 
	 * @date 21/01/2035
	 */
	@Test
	public void testClienteIdadeAceitavel_03() throws IdadeNaoPermitidaException {

		/* ========== GIVEN/DADO QUE ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 65, "guga@gmail.com", 1, true);
		
		/* ========== WHEN/QUANDO  ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());
		
		/* ========== THEN/ENTÃO ========== */
		assertTrue(idadeValida);	
	}
	
	/**
	 * Validação da idade de um cliente quando a mesma está abaixo intervalo permitido.
	 * 
	 * @author Gustavo Farias
	 * @throws IdadeNaoPermitidaException 
	 * @date 21/01/2035
	 */
	@Test
	public void testClienteIdadeAceitavel_04() throws IdadeNaoPermitidaException {

		/* ========== GIVEN/DADO QUE ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 17, "guga@gmail.com", 1, true);

		/* ========== WHEN/QUANDO  ========== */
		try {
			gerClientes.validaIdade(cliente.getIdade());
			fail();
		} catch (Exception e) {
			/* ========== THEN/ENTÃO ========== */
			assertThat(e.getMessage(), is(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA));
		}	
	}
	
	/**
	 * Validação da idade de um cliente quando a mesma está acima intervalo permitido.
	 * 
	 * @author Gustavo Farias
	 * @throws IdadeNaoPermitidaException 
	 * @date 21/01/2035
	 */
	@Test
	public void testClienteIdadeAceitavel_05() throws IdadeNaoPermitidaException {
		
		/* ========== GIVEN/DADO QUE ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 66, "guga@gmail.com", 1, true);
		/* ========== WHEN/QUANDO ========== */
		try {
			gerClientes.validaIdade(cliente.getIdade());
			fail();
		} catch (Exception e) {
			/* ========== THEN/ENTÃO ========== */
			assertThat(e.getMessage(), is(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA));
		}	
	}
	
}

// Valores Limites