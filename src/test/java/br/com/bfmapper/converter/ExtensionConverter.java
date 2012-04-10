package br.com.bfmapper.converter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import br.com.bfmapper.Mapping;
import br.com.bfmapper.model.canonic.CoolClient;
import br.com.bfmapper.model.xml.Client;

public class ExtensionConverter extends BaseTest {

	@Test
	public void simpleExtensionConverter() {
		final CoolClient fake = new CoolClient("Fake Client", new Date(), "@fake");

		Client xml = new Mapping().apply(fake).to(Client.class);
		
		assertNotNull(xml);
		assertEquals(fake.getName(), xml.getXmlName());
		assertEquals(fake.getBirthday(), xml.getXmlBirthday().toGregorianCalendar().getTime());
		assertEquals(fake.getTwitter(), xml.getXmlTwitter());
		assertNull(xml.getXmlEmail());
		assertNull(xml.getXmlPhone());
	}
	
}
