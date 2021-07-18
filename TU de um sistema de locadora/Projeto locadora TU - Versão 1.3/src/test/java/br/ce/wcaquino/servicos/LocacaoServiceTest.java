package br.ce.wcaquino.servicos;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void teste() {
		
		//GIVEN/DADO QUE
		LocacaoService servicoDeLocacao = new LocacaoService();
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Piranhas do caribe",3,30.00);
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, filmeAlugado);
		
		//THEN/ENTÃO
		Assert.assertEquals(30.00,objetoLocacao.getValor(), 0.01);
		Assert.assertTrue(DataUtils.isMesmaData(objetoLocacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(objetoLocacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		
	}

}
