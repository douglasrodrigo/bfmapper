package br.com.bfmapper.converter.transformer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import br.com.bfmapper.transformer.DateTimeTransformer;

public class DateTimeTransformerTest {

	private static final String TEST_PATTERN_DATE_1 = "dd-MM-yyyy";
	
	private static final String TEST_PATTERN_DATE_2 = "dd/MM/yyyy";
	
	private static final String TEST_PATTERN_DATE_3 = "yyyyMMdd";
	
	@Test
	public void transformerStringToDate() throws ParseException {
		String value = "26-03-2010";
		DateFormat format = new SimpleDateFormat(TEST_PATTERN_DATE_1);
		Date transform = new DateTimeTransformer(TEST_PATTERN_DATE_1).transform(value, Date.class);

		Assert.assertNotNull("Objeto java.util.Date não pode ser null", transform);
		Assert.assertEquals("Objeto java.util.Date deve ser compativel com outro objeto java.util.Date de mesmo valor",
				transform, format.parse(value));
	}

	@Test
	public void transformerDateToString() {
		java.util.Date value = new java.util.Date();
		DateFormat format = new SimpleDateFormat(TEST_PATTERN_DATE_1);
		String transform = new DateTimeTransformer(TEST_PATTERN_DATE_1).transform(value, String.class);

		Assert.assertNotNull("Objeto String não pode ser null", transform);
		Assert.assertEquals("Objeto String deve ser compativel com outro objeto String de mesmo valor",
				transform.toString(), format.format(value));
	}

	@Test
	public void transformerDateToCalendar() {
		java.util.Date value = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		
		Calendar transform = new DateTimeTransformer().transform(value, Calendar.class);

		Assert.assertNotNull("Objeto Calendar não pode ser null", transform);
		Assert.assertEquals("Objeto Calendar deve ser compativel com outro objeto Calendar de mesmo valor", transform, calendar);
	}

	@Test
	public void transformerCalendarToDate() {
		java.util.Date date = new java.util.Date();
		Calendar value = Calendar.getInstance();
		value.setTime(date);
		
		Date transform = new DateTimeTransformer().transform(value, java.util.Date.class);

		Assert.assertNotNull("Objeto java.util.Date não pode ser null", transform);
		Assert.assertEquals("Objeto java.util.Date deve ser compativel com outro objeto java.util.Date de mesmo valor", transform, date);
	}

	@Test
	public void transformerStringToCalendar() throws ParseException {
		String value = "28/03/2010";
		DateFormat format = new SimpleDateFormat(TEST_PATTERN_DATE_2);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(format.parse(value));
		
		Calendar transform = new DateTimeTransformer(TEST_PATTERN_DATE_2).transform(value, Calendar.class);

		Assert.assertNotNull("Objeto Calendar não pode ser null", transform);
		Assert.assertEquals("Objeto Calendar deve ser compativel com outro objeto Calendar de mesmo valor", transform, calendar);
	}
	
	@Test
	public void transformerCalendarToString() throws ParseException {
		String stringDate = "28/03/2010";
		
		DateFormat format = new SimpleDateFormat(TEST_PATTERN_DATE_2);
		Calendar value = Calendar.getInstance();
		value.setTime(format.parse(stringDate));
		
		String transform = new DateTimeTransformer(TEST_PATTERN_DATE_2).transform(value, String.class);

		Assert.assertNotNull("Objeto String não pode ser null", transform);
		Assert.assertEquals("Objeto String deve ser compativel com outro objeto String de mesmo valor", transform.toString(), stringDate);
	}
	
	@Test
	public void transformerLongToDate() {
		Long value = 1269784952229L; // 28-03-2010 11:02:32
		java.util.Date date = new java.util.Date(value);
		
		Date transform = new DateTimeTransformer().transform(value, java.util.Date.class);

		Assert.assertNotNull("Objeto java.util.Date não pode ser null", transform);
		Assert.assertEquals("Objeto java.util.Date deve ser compativel com outro objeto java.util.Date de mesmo valor", transform, date);
	}
	
	@Test
	public void transformerDateToLong() {
		Long timeMillis = 1269784952229L; // 28-03-2010 11:02:32
		java.util.Date value = new java.util.Date(timeMillis);
		
		Long transform = new DateTimeTransformer().transform(value, Long.class);

		Assert.assertNotNull("Objeto Long não pode ser null", transform);
		Assert.assertEquals("Objeto Long deve ser compativel com outro objeto Long de mesmo valor", transform, timeMillis);
	}

	@Test
	public void transformerLongAsDateToDate() throws ParseException {
		Long value = 20100328L;
		DateFormat format = new SimpleDateFormat(TEST_PATTERN_DATE_3);
		java.util.Date date = format.parse(value.toString());
		
		Date transform = new DateTimeTransformer(TEST_PATTERN_DATE_3, true).transform(value, java.util.Date.class);

		Assert.assertNotNull("Objeto java.util.Date não pode ser null", transform);
		Assert.assertEquals("Objeto java.util.Date deve ser compativel com outro objeto java.util.Date de mesmo valor", transform, date);
	}
	
	@Test
	public void transformerDateToLongAsDate() throws ParseException {
		Long longDate = 20100328L;
		DateFormat format = new SimpleDateFormat(TEST_PATTERN_DATE_3);
		java.util.Date value = format.parse(longDate.toString());
		
		Long transform = new DateTimeTransformer(TEST_PATTERN_DATE_3, true).transform(value, Long.class);

		Assert.assertNotNull("Objeto Long não pode ser null", transform);
		Assert.assertEquals("Objeto Long deve ser compativel com outro objeto Long de mesmo valor", transform, longDate);
	}

	@Test
	public void transformerLongToCalendar() {
		Long value = 1269784952229L; // 28-03-2010 11:02:32
		
		java.util.Date date = new java.util.Date(value);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		Calendar transform = new DateTimeTransformer().transform(value, Calendar.class);

		Assert.assertNotNull("Objeto Calendar não pode ser null", transform);
		Assert.assertEquals("Objeto Calendar deve ser compativel com outro objeto Calendar de mesmo valor", transform, calendar);
	}

	@Test
	public void transformerCalendarToLong() {
		Long timeMillis = 1269784952229L; // 28-03-2010 11:02:32
		
		java.util.Date date = new java.util.Date(timeMillis);
		Calendar value = Calendar.getInstance();
		value.setTime(date);
		
		Long transform = new DateTimeTransformer().transform(value, Long.class);

		Assert.assertNotNull("Objeto Long não pode ser null", transform);
		Assert.assertEquals("Objeto Long deve ser compativel com outro objeto Long de mesmo valor", transform, timeMillis);
	}
	
	@Test
	public void transformerLongAsDateToCalendar() throws ParseException {
		Long value = 20100328L; 
		
		DateFormat format = new SimpleDateFormat(TEST_PATTERN_DATE_3);
		java.util.Date date = format.parse(value.toString());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		Calendar transform = new DateTimeTransformer(TEST_PATTERN_DATE_3, true).transform(value, Calendar.class);

		Assert.assertNotNull("Objeto Calendar não pode ser null", transform);
		Assert.assertEquals("Objeto Calendar deve ser compativel com outro objeto Calendar de mesmo valor", transform, calendar);
	}

	@Test
	public void transformerCalendarToLongAsDate() throws ParseException {
		Long longAsDate = 20100328L; 
		
		DateFormat format = new SimpleDateFormat(TEST_PATTERN_DATE_3);
		java.util.Date date = format.parse(longAsDate.toString());
		
		Calendar value = Calendar.getInstance();
		value.setTime(date);
		
		Long transform = new DateTimeTransformer(TEST_PATTERN_DATE_3, true).transform(value, Long.class);

		Assert.assertNotNull("Objeto Long não pode ser null", transform);
		Assert.assertEquals("Objeto Long deve ser compativel com outro objeto Long de mesmo valor", transform, longAsDate);
	}

	@Test
	public void transformerDateToXMLGregorianCalendar() throws ParseException, DatatypeConfigurationException {
		java.util.Date value = new java.util.Date();

		XMLGregorianCalendar transform = new DateTimeTransformer().transform(value, XMLGregorianCalendar.class);
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(value);
		
		Assert.assertNotNull("Objeto XMLGregorianCalendar não pode ser null", transform);
		Assert.assertEquals("Objeto XMLGregorianCalendar deve ser compativel com outro objeto XMLGregorianCalendar de mesmo valor", transform, DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
	}
	
	@Test
	public void transformerXMLGregorianCalendarToDate() throws ParseException, DatatypeConfigurationException {
		java.util.Date date = new java.util.Date();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		XMLGregorianCalendar value = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		
		java.util.Date transform = new DateTimeTransformer().transform(value, java.util.Date.class);
		
		Assert.assertNotNull("Objeto java.util.Date não pode ser null", transform);
		Assert.assertEquals("Objeto java.util.Date deve ser compativel com outro objeto java.util.Date de mesmo valor", transform, date);
	}

	@Test
	public void transformerUtilDateToSqlDate() {
		java.util.Date value = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(value.getTime());

		java.sql.Date transform = new DateTimeTransformer().transform(value, java.sql.Date.class);

		Assert.assertNotNull("Objeto java.sql.Date não pode ser null", transform);
		Assert.assertEquals("Objeto java.sql.Date deve ser compativel com outro objeto java.sql.Date de mesmo valor", transform, sqlDate);
	}

	@Test
	public void transformerSqlDateToUtilDate() {
		java.util.Date utilDate = new java.util.Date();

		java.sql.Date value = new java.sql.Date(utilDate.getTime());
		java.util.Date transform = new DateTimeTransformer().transform(value, java.util.Date.class);

		Assert.assertNotNull("Objeto java.util.Date não pode ser null", transform);
		Assert.assertEquals("Objeto java.util.Date deve ser compativel com outro objeto java.util.Date de mesmo valor", transform, utilDate);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void requiredPatternToParse() {
		new DateTimeTransformer().transform("20100328", Calendar.class);
	}

	@Test(expected=IllegalArgumentException.class)
	public void requiredPatternToFormat() {
		new DateTimeTransformer().transform(new java.util.Date(), String.class);
	}
	
	@Test
	public void transformerNull() {
		Assert.assertNull("Conversao para valor null deve ser null", new DateTimeTransformer().transform(null, Long.class));
	}
	
	@Test
	public void transformerEmpty() {
		Assert.assertNull("Conversao para valor empty deve ser null", new DateTimeTransformer().transform("", Long.class));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void transformerInvalidTypes() {
		new DateTimeTransformer().transform(Enum.class, Character.class);
	}

}
