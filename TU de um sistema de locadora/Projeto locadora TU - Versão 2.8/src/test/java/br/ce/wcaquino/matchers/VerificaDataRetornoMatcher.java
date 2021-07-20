package br.ce.wcaquino.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class VerificaDataRetornoMatcher extends TypeSafeMatcher<Date> {
	
	private Date hoje;
	
	public VerificaDataRetornoMatcher(Date hoje) {
		this.hoje = hoje;
		
	}

	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, hoje);
	}

}
