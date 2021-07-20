package br.ce.wcaquino.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class DataDeDevolucaoMatcher extends TypeSafeMatcher<Date> {
	
	private Integer diasAMais;
	
	public DataDeDevolucaoMatcher(Integer diasAMais) {
		this.diasAMais = diasAMais;
	}

	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(diasAMais));
	}

}
