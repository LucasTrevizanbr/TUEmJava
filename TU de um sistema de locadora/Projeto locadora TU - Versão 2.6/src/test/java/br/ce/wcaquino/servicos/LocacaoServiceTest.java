package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.builder.FilmeBuilder;
import br.ce.wcaquino.builder.UsuarioBuilder;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.DiaSemanaMatcher;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	
	//GIVEN/DADO QUE
	private LocacaoService servicoDeLocacao;
		
	@Rule
	public ErrorCollector erro = new ErrorCollector();
	
	@Rule
	public ExpectedException excecao = ExpectedException.none();
	
	
	
	@Before
	public void setUp() {
		servicoDeLocacao = new LocacaoService();
	}
	
	
	
	@Test
	public void deveAlugarFilme() throws Exception {
		
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//GIVEN/ DADO QUE
		Usuario caraQueAlugou = UsuarioBuilder.umUsuario().agora();
		
		Filme filmeAlugado = FilmeBuilder.umFilme().agora();
		Filme filmeAlugado2 = FilmeBuilder.umFilme().agora();
		List<Filme> listaDeFilmes = new ArrayList();
		listaDeFilmes.add(filmeAlugado);
		listaDeFilmes.add(filmeAlugado2);
		
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, listaDeFilmes);
				
		//THEN/ENTÃO
		erro.checkThat(objetoLocacao.getValor(), is(24.00));
		erro.checkThat(objetoLocacao.getDataLocacao(), MatchersProprios.ehHoje());
		erro.checkThat(objetoLocacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDeDias(1));
						
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarUmaExcecaoEmFilmeSemEstoquqe() throws Exception {
		
		//GIVEN/DADO QUE
		Usuario caraQueAlugou = UsuarioBuilder.umUsuario().agora();
		Filme filmeAlugado = FilmeBuilder.umFilme().semEstoque().agora();
		Filme filmeAlugado2 = FilmeBuilder.umFilme().agora();
		List<Filme> listaDeFilmes = new ArrayList();
		listaDeFilmes.add(filmeAlugado);
		listaDeFilmes.add(filmeAlugado2);
		
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, listaDeFilmes);
			
	}
	
	@Test
	public void deveLancarUmaExcecaoDeUsuarioVazio() throws FilmeSemEstoqueException {
		
		// GIVEN/DADO QUE
		Filme filmeAlugado = FilmeBuilder.umFilme().agora();
		Filme filmeAlugado2 = FilmeBuilder.umFilme().agora();
		List<Filme> listaDeFilmes = new ArrayList();
		listaDeFilmes.add(filmeAlugado);
		listaDeFilmes.add(filmeAlugado2);
		
		// WHEN/QUANDO
		try {
			Locacao objetoLocacao = servicoDeLocacao.alugarFilme(null, listaDeFilmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario está vazio"));
		}
	}
	
	@Test
	public void deveLancarUmaExcecaoDeFilmeVazio() throws FilmeSemEstoqueException, LocadoraException{
		
		// GIVEN/DADO QUE
		Usuario caraQueAlugou = UsuarioBuilder.umUsuario().agora();
		excecao.expect(LocadoraException.class);
		excecao.expectMessage("Lista de filmes está vazia");
		
		// WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, null);
		
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		//GIVEN/DADO QUE
		Usuario caraQueAlugou = UsuarioBuilder.umUsuario().agora();
		List<Filme> listaDeFilmes = Arrays.asList(
				FilmeBuilder.umFilme().agora(),
				FilmeBuilder.umFilme().agora());
		
		//WHEN/QUANDO
		Locacao retorno = servicoDeLocacao.alugarFilme(caraQueAlugou, listaDeFilmes);
		
		//THEN/ENTÃO
		Assert.assertThat(retorno.getDataRetorno(), MatchersProprios.caiNumaSegunda());
	}
	

}
