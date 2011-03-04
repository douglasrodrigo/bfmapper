package br.com.bfmapper.converter.transformer;

import org.junit.Assert;
import org.junit.Test;

import br.com.bfmapper.transformer.EnumTransformer;

public class EnumTransformerTest {

	@Test
	public void transformerOrdinalToEnum() {
		Object transform = new EnumTransformer().transform(0, Status.class);
		
		Assert.assertNotNull("Enum Status não pode ser null", transform);
		Assert.assertEquals("Enum Status deve ser compativel com outro objeto Long de mesmo valor", transform, Status.CONCLUIDO);
	}

	@Test(expected=IllegalArgumentException.class)
	public void transformerInvalidOrdinalToEnum() {
		new EnumTransformer().transform(50, Status.class);
	}
	
	@Test
	public void transformerStringToEnum() {
		Object transform = new EnumTransformer().transform("CONCLUIDO", Status.class);
		
		Assert.assertNotNull("Enum Status não pode ser null", transform);
		Assert.assertEquals("Enum Status deve ser compativel com outro objeto Long de mesmo valor", transform, Status.CONCLUIDO);
	}

	@Test(expected=IllegalArgumentException.class)
	public void transformerInvalidStringToEnum() {
		new EnumTransformer().transform("CONSTANTE_INEXISTENTE", Status.class);
	}
	
	@Test
	public void transformerEnumToString() {
		String transform = new EnumTransformer().transform(Status.CONCLUIDO, String.class).toString();
		
		Assert.assertNotNull("Objeto String não pode ser null", transform);
		Assert.assertEquals("Objeto String deve ser compativel com outro objeto String de mesmo valor", transform, "CONCLUIDO");
	}

	@Test(expected=IllegalArgumentException.class)
	public void transformerInvalidEnumToString() {
		new EnumTransformer().transform(Status.class, String.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void transformerInvalidTypes() {
		new EnumTransformer().transform(Long.class, Double.class);
	}
	
	@Test
	public void transformerNull() {
		Assert.assertNull("Conversao para valor null deve ser null", new EnumTransformer().transform(null, Status.class));
	}

	@Test
	public void transformerEmpty() {
		Assert.assertNull("Conversao para valor null deve ser null", new EnumTransformer().transform("", Status.class));
	}
	
}

	enum Status {
		CONCLUIDO, CANCELADO, PROCESSANDO
	}