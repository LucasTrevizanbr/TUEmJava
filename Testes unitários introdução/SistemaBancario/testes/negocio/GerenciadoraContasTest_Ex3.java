package negocio;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GerenciadoraContasTest_Ex3 {

	private GerenciadoraContas gerContas;
	
	@Test
	public void testTransfereValor() {

		/*DADO QUE eu tenha conta correntes cadastradas no banco, que elas tenham ou não saldo e 
		 estejam ativas*/
		ContaCorrente conta01 = new ContaCorrente(1, 200, true);
		ContaCorrente conta02 = new ContaCorrente(2, 0, true);
		
		List<ContaCorrente> contasDoBanco = new ArrayList<>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		/*QUANDO eu chamar meu programa gerenciador de contas passando minha lista de contas e
		executar seu método de transferência de valor com id da conta de origem, o valor a ser
		transferido e o id da conta de destino*/
		gerContas = new GerenciadoraContas(contasDoBanco);
		gerContas.transfereValor(1, 100, 2);
		
		/*ENTÃO vou ter o saldo da minha conta que transferiu com 100 e a conta que recebeu
		 com 100 */
		assertThat(conta02.getSaldo(), is(100.0));
		assertThat(conta01.getSaldo(), is(100.0));
	}

}
