package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException  {
		
		
		if(filmes == null) {
			throw new LocadoraException("Lista de filmes está vazia");
		}	
		
		for(Filme filme : filmes) {
			if(filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}
		
		if(usuario == null) {
			throw new LocadoraException("Usuario está vazio");
		}
		
		double valorTotal= 0;
		
		Locacao locacao = new Locacao();
		
		for(int indice = 0; indice < filmes.size(); indice ++) {
			
			Filme filme = filmes.get(indice);
			Double valorFilme = filme.getPrecoLocacao();
			
			switch(indice) {
				case 2: valorFilme = valorFilme - (valorFilme * 0.25); break;
				case 3: valorFilme = valorFilme -(valorFilme * 0.50); break;
				case 4: valorFilme = valorFilme -(valorFilme * 0.75); break;	
				case 5: valorFilme = 0d;
			}
			valorTotal += valorFilme;
		}
		
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(valorTotal);
		
		

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
	
}