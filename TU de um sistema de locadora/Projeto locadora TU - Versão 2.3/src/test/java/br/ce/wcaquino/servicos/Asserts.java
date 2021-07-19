package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class Asserts {
	
	@Test
	public void teste() {
		
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals(1, 1);
		Assert.assertEquals(0.51234,0.512,0.001);
		
		int tipoPrimitivo = 3;
		Integer tipoObjeto = 3;
		Assert.assertEquals(Integer.valueOf(tipoPrimitivo), tipoObjeto);
		Assert.assertEquals(tipoPrimitivo, tipoObjeto.intValue());
		
		Assert.assertEquals("bola", "bola");
		Assert.assertTrue("bola".equalsIgnoreCase("BOLA"));
		Assert.assertTrue("bola".startsWith("bo"));
		
		
		Usuario usuarioUm = new Usuario("Jorge");
		Usuario usuarioDois = new Usuario("Jorge");
		Usuario usuarioTresApontandoParaUsuarioUm = usuarioUm;
		Usuario usuarioNulo = null;
		
		Assert.assertEquals(usuarioUm, usuarioDois);
		Assert.assertSame(usuarioUm, usuarioTresApontandoParaUsuarioUm);
		Assert.assertNull(usuarioNulo);
		
		
	}

}
