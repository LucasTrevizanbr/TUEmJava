package br.ce.wcaquino.servicos;


import static org.hamcrest.CoreMatchers.is;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.ce.wcaquino.builder.FilmeBuilder;
import br.ce.wcaquino.builder.LocacaoBuilder;
import br.ce.wcaquino.builder.UsuarioBuilder;
import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	
	//GIVEN/DADO QUE
	private LocacaoService servicoDeLocacao;
	private SPCService spcService;
	private LocacaoDAO dao;
	private EmailService emailService;
		
	@Rule
	public ErrorCollector erro = new ErrorCollector();
	
	@Rule
	public ExpectedException excecao = ExpectedException.none();
	
	
	
	@Before
	public void setUp() {
		servicoDeLocacao = new LocacaoService();
		
		dao = Mockito.mock(LocacaoDAO.class);
		servicoDeLocacao.setLocacaoDAO(dao);
		
		spcService = Mockito.mock(SPCService.class);
		servicoDeLocacao.setSpcService(spcService);
		
		emailService = Mockito.mock(EmailService.class);
		servicoDeLocacao.setEmailService(emailService);
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
				
		//THEN/ENT?O
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
			Assert.assertThat(e.getMessage(), is("Usuario est? vazio"));
		}
	}
	
	@Test
	public void deveLancarUmaExcecaoDeFilmeVazio() throws FilmeSemEstoqueException, LocadoraException{
		
		// GIVEN/DADO QUE
		Usuario caraQueAlugou = UsuarioBuilder.umUsuario().agora();
		excecao.expect(LocadoraException.class);
		excecao.expectMessage("Lista de filmes est? vazia");
		
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
		
		//THEN/ENT?O
		Assert.assertThat(retorno.getDataRetorno(), MatchersProprios.caiNumaSegunda());
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativoSPC() throws FilmeSemEstoqueException{
		//GIVEN/DADOQUE
		Usuario caraTentandoAlugar = UsuarioBuilder.umUsuario().agora();
		List<Filme> listaDeFilmes = Arrays.asList(
				FilmeBuilder.umFilme().agora(),
				FilmeBuilder.umFilme().agora());
		
		Mockito.when(spcService.possuiNegativacao(caraTentandoAlugar)).thenReturn(true);
		
		//WHEN/QUANDO
		try {
			servicoDeLocacao.alugarFilme(caraTentandoAlugar, listaDeFilmes);
			Assert.fail();
			
			//verifica?a?
			
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usu?rio esta negativado no SPC"));
		}
		
		Mockito.verify(spcService).possuiNegativacao(caraTentandoAlugar);
	}
	
	@Test
	public void deveEnviarEmailAUsuarioComLocacaoAtrasada() {
		//GIVEN/Dado que
		Usuario usuarioComLocacaoAtrasada = UsuarioBuilder.umUsuario().agora();
		List<Locacao> locacoesPendentes = Arrays.asList(
				LocacaoBuilder.umLocacao()
					.comUsuario(usuarioComLocacaoAtrasada)
					.comDataRetorno(DataUtils.obterDataComDiferencaDias(-2))
				.agora());
		
		Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoesPendentes);
		
		//WHEN/Quando
		servicoDeLocacao.notificarAtrasos();
		
		//THEN/Ent?o
		Mockito.verify(emailService).notificarAtraso(usuarioComLocacaoAtrasada);
	}
	

}
