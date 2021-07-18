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

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector erro = new ErrorCollector();
	
	@Test
	public void testeLocacao() {
		
		//GIVEN/DADO QUE
		LocacaoService servicoDeLocacao = new LocacaoService();
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Piranhas do caribe",3,30.00);
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, filmeAlugado);
		
		//THEN/ENTÃO
		erro.checkThat(objetoLocacao.getValor(), is(30.00));
		erro.checkThat(isMesmaData(objetoLocacao.getDataLocacao(), new Date()), is(true));
		erro.checkThat(isMesmaData(objetoLocacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		
	}

}
