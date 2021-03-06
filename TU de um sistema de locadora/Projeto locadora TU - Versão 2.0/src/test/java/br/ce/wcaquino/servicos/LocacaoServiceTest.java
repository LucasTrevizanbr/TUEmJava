package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
		
		//GIVEN/ DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Seu jorge",4,30.00);
		Filme filmeAlugado2 = new Filme("Parque dos dinos",2,20.00);
		List<Filme> listaDeFilmes = new ArrayList();
		listaDeFilmes.add(filmeAlugado);
		listaDeFilmes.add(filmeAlugado2);
		
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, listaDeFilmes);
				
		//THEN/ENT?O
		erro.checkThat(objetoLocacao.getValor(), is(50.00));
		erro.checkThat(isMesmaData(objetoLocacao.getDataLocacao(), new Date()), is(true));
		erro.checkThat(isMesmaData(objetoLocacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		
						
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarUmaExcecaoEmFilmeSemEstoquqe() throws Exception {
		
		//GIVEN/DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Seu jorge",0,30.00);
		Filme filmeAlugado2 = new Filme("Parque dos dinos",2,20.00);
		List<Filme> listaDeFilmes = new ArrayList();
		listaDeFilmes.add(filmeAlugado);
		listaDeFilmes.add(filmeAlugado2);
		
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, listaDeFilmes);
			
	}
	
	@Test
	public void deveLancarUmaExcecaoDeUsuarioVazio() throws FilmeSemEstoqueException {
		
		// GIVEN/DADO QUE
		Filme filmeAlugado = new Filme("Seu jorge",4,30.00);
		Filme filmeAlugado2 = new Filme("Parque dos dinos",2,20.00);
		List<Filme> listaDeFilmes = new ArrayList();
		listaDeFilmes.add(filmeAlugado);
		listaDeFilmes.add(filmeAlugado2);
		
		// WHEN/QUANDO
		try {
			Locacao objetoLocacao = servicoDeLocacao.alugarFilme(null, listaDeFilmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario est? vazio"));
		}
	}
	
	@Test
	public void deveLancarUmaExcecaoDeFilmeVazio() throws FilmeSemEstoqueException, LocadoraException{
		
		// GIVEN/DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
		excecao.expect(LocadoraException.class);
		excecao.expectMessage("Lista de filmes est? vazia");
		
		// WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, null);
		
	}
	
	@Test
	public void deveDarUmDescontoDe25PctNoTerceiroFilme() throws FilmeSemEstoqueException, LocadoraException {
		
		//GIVEN/ DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
		List<Filme> listaDeFilmes = Arrays.asList(
				new Filme("Seu jorge",2,12.00),
				new Filme("Parque dos dinos",2,12.00),
				new Filme("Z?do",3,12.00));
		
		//WHEN/ QUANDO
		Locacao resultado = servicoDeLocacao.alugarFilme(caraQueAlugou, listaDeFilmes);
		
		
		//THEN/ ENT?O
		Assert.assertThat(resultado.getValor(), is(33.00));
		
	}
	
	@Test
	public void deveDarUmDescontoDe50PctNoQuartoFilme() throws FilmeSemEstoqueException, LocadoraException {
		
		//GIVEN/ DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
		List<Filme> listaDeFilmes = Arrays.asList(
				new Filme("Seu jorge",2,12.00),
				new Filme("Parque dos dinos",2,12.00),
				new Filme("Z?do",3,12.00),
				new Filme("Loiola",2,12.00));
		
		//WHEN/ QUANDO
		Locacao resultado = servicoDeLocacao.alugarFilme(caraQueAlugou, listaDeFilmes);
		
		
		//THEN/ ENT?O
		//12+12+9+6;
		Assert.assertThat(resultado.getValor(), is(39.00));
		
	}
	
	@Test
	public void deveDarUmDescontoDe75PctNoQuintoFilme() throws FilmeSemEstoqueException, LocadoraException {
		
		//GIVEN/ DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
		List<Filme> listaDeFilmes = Arrays.asList(
				new Filme("Seu jorge",2,12.00),
				new Filme("Parque dos dinos",2,12.00),
				new Filme("Z?do",3,12.00),
				new Filme("Loiola",2,12.00),
				new Filme("Loki",2,12.00));
		
		//WHEN/ QUANDO
		Locacao resultado = servicoDeLocacao.alugarFilme(caraQueAlugou, listaDeFilmes);
		
		
		//THEN/ ENT?O
		//12+12+9+6+3;
		Assert.assertThat(resultado.getValor(), is(42.00));
		
	}
	
	@Test
	public void deveDarUmDescontoDe100PctNoSextoFilme() throws FilmeSemEstoqueException, LocadoraException {
		
		//GIVEN/ DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
		List<Filme> listaDeFilmes = Arrays.asList(
				new Filme("Seu jorge",2,12.00),
				new Filme("Parque dos dinos",2,12.00),
				new Filme("Z?do",3,12.00),
				new Filme("Loiola",2,12.00),
				new Filme("Loki",2,12.00),
				new Filme("Superman",2,12.00));
		
		//WHEN/ QUANDO
		Locacao resultado = servicoDeLocacao.alugarFilme(caraQueAlugou, listaDeFilmes);
		
		//THEN/ ENT?O
		//12+12+9+6+3+0;
		Assert.assertThat(resultado.getValor(), is(42.00));
		
	}
	

}
