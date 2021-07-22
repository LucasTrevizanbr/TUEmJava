package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTeste {
	
	

	@InjectMocks
	private LocacaoService servicoDeLocacao;
	
	@Mock
	private SPCService spcService;
	
	@Mock
	private LocacaoDAO dao;
	
	@Mock
	private EmailService emailService;
	
	@Parameter
	public List<Filme> listaDeFilmes;
	
	@Parameter(value = 1)
	public Double valorLocacao;
	
	@Parameter(value = 2)
	public String cenario;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	private static Filme filme1 = new Filme("Legal 1",2,12.00);
	private static Filme filme2 = new Filme("Legal 2",2,12.00);
	private static Filme filme3 = new Filme("Legal 3",2,12.00);
	private static Filme filme4 = new Filme("Legal 4",2,12.00);
	private static Filme filme5 = new Filme("Legal 5",2,12.00);
	private static Filme filme6 = new Filme("Legal 6",2,12.00);
	
	@Parameters(name = "{2}")
	public static Collection<Object[]> pegarParametros(){
		return Arrays.asList(new Object [][]{
			{Arrays.asList(filme1,filme2,filme3), 33.00,"3 filmes 25%"},
			{Arrays.asList(filme1,filme2,filme3,filme4), 39.00, "4 filmes 50%"},
			{Arrays.asList(filme1,filme2,filme3,filme4,filme5), 42.00,"5 filmes 75%"},
			{Arrays.asList(filme1,filme2,filme3,filme4,filme5,filme6), 42.00,"6 filmes 100%"},
		});
	}
	
	
	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		
		//GIVEN/ DADO QUE
		Usuario caraQueAlugou = new Usuario("Lucas");
	
		//WHEN/ QUANDO
		Locacao resultado = servicoDeLocacao.alugarFilme(caraQueAlugou, listaDeFilmes);
		
		
		//THEN/ ENTÃO
		Assert.assertThat(resultado.getValor(), is(valorLocacao));
		
	}

}
