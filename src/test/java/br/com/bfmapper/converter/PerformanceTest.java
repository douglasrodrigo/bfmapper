package br.com.bfmapper.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;

import br.com.bfmapper.Mapping;
import br.com.bfmapper.model.CarroCanonico;
import br.com.bfmapper.model.EnderecoCanonico;
import br.com.bfmapper.model.IntBean;
import br.com.bfmapper.model.Pessoa;
import br.com.bfmapper.model.PessoaCanonico;

public class PerformanceTest  extends BaseTest {

	private int numIters = 1000;
	
	private Logger logger = Logger.getLogger(ServiceLoader.class.getName());  
	
	@Test
	public void testPessoa() {
		PessoaCanonico pessoaCanonico = new PessoaCanonico(1L, "Douglas Rodrigo");
		
		List<EnderecoCanonico> enderecos = new ArrayList<EnderecoCanonico>();
		enderecos.add(new EnderecoCanonico("Av Paulista", "344", "Paulista"));
		enderecos.add(new EnderecoCanonico("Alameda Santos", "899", "Paulista"));
		pessoaCanonico.setEnderecos(enderecos);
		
		CarroCanonico carroCanonico = new CarroCanonico("Uno", "EP", "Pirelli");
		logger.log(Level.INFO, "Begin timings for testPessoa");
		
		
		StopWatch timer = new StopWatch();
		timer.start();
		for (int i = 0; i < numIters; i++) {
			new Mapping().apply(pessoaCanonico).apply(carroCanonico).to(Pessoa.class);
		}
		timer.stop();
		logger.log(Level.INFO, "Total time for additional " + numIters + " mappings: " + timer.getTime() + " milliseconds");
		logger.log(Level.INFO, "avg time for " + numIters + " mappings: " + (timer.getTime() / numIters) + " milliseconds");
	}

	@Test
	public void test() {
		final int[] counts = { 10, 100, 1000, 10000, 20000, 50000, 100000 };
		for (final int count : counts) {
			final IntBean intBean1 = new IntBean(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			final IntBean intBean2 = new IntBean();
			long time = System.nanoTime();
			for (int runs = 0; runs < count; runs++) {
			    new Mapping().apply(intBean1).to(intBean2);
			}
			logger.log(Level.INFO, count + "\t" + (System.nanoTime() - time) / (1000 * 1000));
		}
	}
}
