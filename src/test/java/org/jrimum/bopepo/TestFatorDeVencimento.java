/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:15:56
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:15:56
 * 
 */
package org.jrimum.bopepo;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;


public class TestFatorDeVencimento {

	private GregorianCalendar data = new GregorianCalendar();

	@Test(expected = IllegalArgumentException.class)
	public void testToFatorComDataNula() {

		FatorDeVencimento.toFator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToFatorComDataMenorQueDataBase() {

		data.set(1997, Calendar.JANUARY, 1);

		FatorDeVencimento.toFator(data.getTime());
	}

	@Test
	public final void testToFator() {
        // obs: novo cálculo opera com módulo, mais de uma data pode cair no mesmo fator
		data.set(2000, Calendar.JULY, 3);
		assertEquals(1000, FatorDeVencimento.toFator(data.getTime()));

		data.set(2000, Calendar.JULY, 5);
		assertEquals(1002, FatorDeVencimento.toFator(data.getTime()));

		data.set(2025, Calendar.FEBRUARY, 21);
		assertEquals(9999, FatorDeVencimento.toFator(data.getTime()));

 		// exemplos do manual safras/banco do brasil
        data.set(2025, Calendar.FEBRUARY, 22);
		assertEquals(1000, FatorDeVencimento.toFator(data.getTime()));

 		data.set(2025, Calendar.FEBRUARY, 23);
		assertEquals(1001, FatorDeVencimento.toFator(data.getTime()));

 		// exemplos do manula operacional da AABC - Associação Brasileira de Bancos
        data.set(2029, Calendar.APRIL, 02);
		assertEquals(2500, FatorDeVencimento.toFator(data.getTime()));

        data.set(2012, Calendar.APRIL, 27);
		assertEquals(5316, FatorDeVencimento.toFator(data.getTime()));

        data.set(2020, Calendar.JULY, 14);
		assertEquals(8316, FatorDeVencimento.toFator(data.getTime()));

        data.set(2035, Calendar.AUGUST, 06);
		assertEquals(4817, FatorDeVencimento.toFator(data.getTime()));

        // simulação pelo ACBr
        data.set(2026, Calendar.MAY, 29);
		assertEquals(1461, FatorDeVencimento.toFator(data.getTime()));
    }
	
	@Test(expected = IllegalArgumentException.class)
	public void testToDateComFatorMenorQueLimiteBase() {

		FatorDeVencimento.toDate(-1);
	}
	
	@Test
	public final void testToDate() {
		
		data.set(2000, Calendar.JULY, 3);
		Date date = DateUtils.truncate(data.getTime() ,Calendar.DATE);
		//assertEquals(date, FatorDeVencimento.toDate(1000));

		data.set(2000, Calendar.JULY, 5);
		date = DateUtils.truncate(data.getTime() ,Calendar.DATE);
		//assertEquals(date, FatorDeVencimento.toDate(1002));
		
		data.set(2025, Calendar.FEBRUARY, 21);
		date = DateUtils.truncate(data.getTime() ,Calendar.DATE);
		assertEquals(date, FatorDeVencimento.toDate(9999));
	}
}
