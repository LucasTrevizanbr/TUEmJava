package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector erro = new ErrorCollector();
	
	@Rule
	public ExpectedException excecao = ExpectedException.none();
	
	@Test
	public void testeLocacao() throws Exception {
		
		//GIVEN/DADO QUE
		LocacaoService servicoDeLocacao = new LocacaoService();
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Piranhas do caribe",4,30.00);
		
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, filmeAlugado);;
				
		//THEN/ENTÃO
		erro.checkThat(objetoLocacao.getValor(), is(30.00));
		erro.checkThat(isMesmaData(objetoLocacao.getDataLocacao(), new Date()), is(true));
		erro.checkThat(isMesmaData(objetoLocacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));		
			
	}
	
	@Test(expected = Exception.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		
		//GIVEN/DADO QUE
		LocacaoService servicoDeLocacao = new LocacaoService();
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Piranhas do caribe",0,30.00);
		
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, filmeAlugado);;
			
	}
	
	@Test
	public void testeLocacao_filmeSemEstoque2(){
		
		//GIVEN/DADO QUE
		LocacaoService servicoDeLocacao = new LocacaoService();
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Piranhas do caribe",0,30.00);
		
		
		//WHEN/QUANDO
		try {
			Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, filmeAlugado);
			Assert.fail("Deveria executar uma exceção!");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		};
			
	}
	
	@Test
	public void testeLocacao_filmeSemEstoque3() throws Exception{
		
		//GIVEN/DADO QUE
		LocacaoService servicoDeLocacao = new LocacaoService();
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Piranhas do caribe",0,30.00);
		
		excecao.expect(Exception.class);
		excecao.expectMessage("Filme sem estoque");	
			
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, filmeAlugado);
		
		
	}

}
