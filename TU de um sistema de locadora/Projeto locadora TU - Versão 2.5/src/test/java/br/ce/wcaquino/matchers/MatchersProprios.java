package br.ce.wcaquino.matchers;

import java.util.Calendar;
import java.util.Date;

import br.ce.wcaquino.utils.DataUtils;

public class MatchersProprios {
	
	public static DiaSemanaMatcher caiEm(Integer diaDaSemana) {
		return new DiaSemanaMatcher(diaDaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(Calendar.SUNDAY);
	}
	
	public static VerificaDataRetornoMatcher ehHoje() {
		return new VerificaDataRetornoMatcher(new Date());
	}
	
	public static DataDeDevolucaoMatcher ehHojeComDiferencaDeDias(Integer diasAMais) {
		return new DataDeDevolucaoMatcher(diasAMais);
	}

}
