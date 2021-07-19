package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	
	//GIVEN/DADO QUE
	LocacaoService servicoDeLocacao = new LocacaoService();
		
	@Rule
	public ErrorCollector erro = new ErrorCollector();
	
	@Rule
	public ExpectedException excecao = ExpectedException.none();
	
	
	
	@Before
	public void setUp() {
		System.out.println("Antes de um método de teste");
		LocacaoService servicoDeLocacao = new LocacaoService();
	}
	
	@After
	public void tearDown() {
		System.out.println("Depois de um método de teste");
		
	}
	
	@BeforeClass
	public static void setUpClass() {
		System.out.println("Before apenas uma vez antes da classe");

	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println("After somente uma vez depois do fim da classe");

	}
	
	@Test
	public void testeLocacao() throws Exception {
		
		//GIVEN/ DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Piranhas do caribe",4,30.00);
		
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, filmeAlugado);;
				
		//THEN/ENTÃO
		erro.checkThat(objetoLocacao.getValor(), is(30.00));
		erro.checkThat(isMesmaData(objetoLocacao.getDataLocacao(), new Date()), is(true));
		erro.checkThat(isMesmaData(objetoLocacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));		
			
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		
		//GIVEN/DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Piranhas do caribe",0,30.00);
		
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, filmeAlugado);
			
	}
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		
		// GIVEN/DADO QUE
		Filme filmeAlugado = new Filme("Piranhas do caribe",2,30.00);
		
		// WHEN/QUANDO
		try {
			Locacao objetoLocacao = servicoDeLocacao.alugarFilme(null, filmeAlugado);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario está vazio"));
		}
	}
	
	@Test
	public void testLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException{
		
		// GIVEN/DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
		excecao.expect(LocadoraException.class);
		excecao.expectMessage("Filme está vazio");
		
		// WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, null);
		
	}
	

}
