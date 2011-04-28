package br.com.bfmapper.converter.transformer;

import org.junit.Assert;
import org.junit.Test;

import br.com.bfmapper.transformer.NumberTransformer;

public class NumberTransformerTest {

	@Test
	public void transformerStringToLong() {
		String value = "20";
		Object transform = new NumberTransformer().transform(value, Long.class);

		Assert.assertNotNull("Objeto Long não pode ser null", transform);
		Assert.assertEquals("Objeto Long deve ser compativel com outro objeto Long de mesmo valor", transform, new Long(value));
	}

	@Test
	public void transformerLongToString() {
		Long value = 20L;
		Object transform = new NumberTransformer().transform(value, String.class);

		Assert.assertNotNull("Objeto String não pode ser null", transform);
		Assert.assertEquals("Objeto String deve ser compativel com outro objeto String de mesmo valor", transform, value.toString());
	}

	@Test
	public void transformerIntegerToLong() {
		Integer value = 800;
		Object transform = new NumberTransformer().transform(value, Long.class);

		Assert.assertNotNull("Objeto Long não pode ser null", transform);
		Assert.assertEquals("Objeto Long deve ser compativel com outro objeto Long de mesmo valor", transform, value.longValue());
	}

	@Test
	public void transformerLongToInteger() {
		Long value = 800L;
		Object transform = new NumberTransformer().transform(value, Integer.class);

		Assert.assertNotNull("Objeto Integer não pode ser null", transform);
		Assert.assertEquals("Objeto Integer deve ser compativel com outro objeto Integer de mesmo valor", transform, value.intValue());
	}

	@Test
	public void transformerLongToDouble() {
		Long value = 800L;
		Object transform = new NumberTransformer().transform(value, Double.class);

		Assert.assertNotNull("Objeto Double não pode ser null", transform);
		Assert.assertEquals("Objeto Double deve ser compativel com outro objeto Double de mesmo valor", transform, value.doubleValue());
	}

	@Test
	public void transformerDoubleToLong() {
		Double value = 1050.00;
		Object transform = new NumberTransformer().transform(value, Long.class);

		Assert.assertNotNull("Objeto Long não pode ser null", transform);
		Assert.assertEquals("Objeto Long deve ser compativel com outro objeto Long de mesmo valor", transform, value.longValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void transformerDoubleToInvalidLong() {
		new NumberTransformer().transform(1050.50, Long.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void transformerInvalidTypes() {
		new NumberTransformer().transform(Enum.class, Character.class);
	}
	
	@Test
	public void transformerNull() {
		Assert.assertNull("Conversao para valor null deve ser null", new NumberTransformer().transform(null, Long.class));
	}
	
	@Test
	public void transformerEmpty() {
		Assert.assertNull("Conversao para valor null deve ser null", new NumberTransformer().transform("", Long.class));
	}
}
