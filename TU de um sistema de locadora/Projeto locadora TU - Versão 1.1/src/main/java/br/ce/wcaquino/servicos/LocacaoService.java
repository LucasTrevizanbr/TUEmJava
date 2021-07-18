package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	@Test
	public void teste() {
		
		//GIVEN/DADO QUE
		LocacaoService servicoDeLocacao = new LocacaoService();
		Usuario caraQueAlugou = new Usuario("Lucas");
		Filme filmeAlugado = new Filme("Piranhas do caribe",3,30.00);
		
		//WHEN/QUANDO
		Locacao objetoLocacao = servicoDeLocacao.alugarFilme(caraQueAlugou, filmeAlugado);
		
		//THEN/ENTÃO
		Assert.assertTrue(objetoLocacao.getValor() == 30.00);
		Assert.assertTrue(DataUtils.isMesmaData(objetoLocacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(objetoLocacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		
		
	}
}