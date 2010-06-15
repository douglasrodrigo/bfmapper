package br.com.blackfoot.bfmapper.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateTimeUtil {

	public static final String PATTERN_DATE_PT = "dd/MM/yyyy";

	public static final String PATTERN_TIME_PT = "HH:mm";

	public static final String PATTERN_DATE_TIME_PT = PATTERN_DATE_PT + " HH:mm:ss";

	public static final String PATTERN_DATE_TIME_STR_PT = PATTERN_DATE_PT + " 'às' HH:mm";

	public static final String PATTERN_DATE_MES_EXTENSO_ANO = "MMM/yyyy";

	public static final String PATTERN_DATE_MES_EXTENSO_DE_ANO = "MMMM 'de' yyyy";

	public static final String PATTERN_DATE_YYYYMM = "yyyyMM";

	public static final String PATTERN_DATE_DDMMYYYY = "ddMMyyyy";

	public static final String PATTERN_DATE_YYYYMMDD = "yyyyMMdd";

	public static final String PATTERN_DATE_DD = "dd";

	public static final String PATTERN_DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String PATTERN_DATE_YYYY_MM_DD = "yyyy-MM-dd";

	public static final String PATTERN_TIME = "HH:mm:ss";

	public static final String PATTERN_DATE_MMYYYY = "MMyyyy";
	
	public static final String PATTERN_DATE_TIME = PATTERN_DATE_YYYY_MM_DD + " " + PATTERN_TIME;


	public static String format(java.util.Date dateRep, String pattern) {
		try {
			DateFormat df = new SimpleDateFormat(pattern);
			return df.format(dateRep);
		}
		catch (Exception e) {
			return "";
		}
	}
	
	public static Date parse(String dateRep, String pattern, boolean lenient) {
		Date date = null;
		if (dateRep != null) {
			DateFormat df = null;
			if (pattern != null) {
				df = new SimpleDateFormat(pattern);
			} else {
				df = DateFormat.getInstance();
			}
			try {
				df.setLenient(lenient);
				date = new Date(df.parse(dateRep).getTime());
			} catch (Exception e) { /* ignoring exception */ }
		}
		return date;
	}
	
	public static Date parse(String dateRep, String pattern) {
		return DateTimeUtil.parse(dateRep, pattern, false);
	}
	
	public static String getJulianDateComplete() {
		GregorianCalendar calendarZero = new GregorianCalendar(1840, 11, 31, 0, 0, 0);
		GregorianCalendar calendar = new GregorianCalendar();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int minutos = calendar.get(Calendar.MINUTE);
		int segundos = calendar.get(Calendar.SECOND);
		int totalSegundos = (((hora * 60) + minutos) * 60) + segundos;
		long agora = calendar.getTime().getTime() - calendarZero.getTime().getTime();
		agora = ((agora / 1000) / 3600) / 24;
		return agora + "," + totalSegundos;
	}
	
	public static XMLGregorianCalendar getXMLGregoriaCalendar(java.util.Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			return null;
		}
	}
	
	public static Date getFirstDayOfMonth(java.util.Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getLastDayOfMonth(java.util.Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
	
	  /**
	   * Realiza o cálculo da idade
	   * 
	   * @param dataNascimento data de nascimento
	   * @return idade calculada
	   * @since 1.0
	   */
	  public static int calculaIdade(Date dataNascimento) {
	    return calculaIdade(dataNascimento, new Date());
	  }

	  /**
	   * Realiza o cálculo da idade
	   * 
	   * @param dataNascimento data de nascimento
	   * @param dataReferencia data de referência
	   * @return idade calculada
	   * @since 1.0
	   */
	  public static int calculaIdade(Date dataNascimento, Date dataReferencia) {
	    Calendar calendar = GregorianCalendar.getInstance();
	    calendar.setTime(dataReferencia);
	    int dia2 = calendar.get(GregorianCalendar.DAY_OF_MONTH);
	    int mes2 = calendar.get(GregorianCalendar.MONTH) + 1;
	    int ano2 = calendar.get(GregorianCalendar.YEAR);

	    Calendar nascCal = GregorianCalendar.getInstance();
	    nascCal.setTime(dataNascimento);
	    int dia = nascCal.get(GregorianCalendar.DAY_OF_MONTH);
	    int mes = nascCal.get(GregorianCalendar.MONTH) + 1;
	    int ano = nascCal.get(GregorianCalendar.YEAR);

	    if (ano2 < 100) {
	      ano2 += 1900;
	    }
	    int idade = ano2 - ano;
	    if (mes2 < mes) {
	      idade--;
	    }
	    if (mes2 == mes) {
	      if (dia2 < dia) {
	        idade--;
	      }
	    }
	    return idade;
	  }
	
	
}
