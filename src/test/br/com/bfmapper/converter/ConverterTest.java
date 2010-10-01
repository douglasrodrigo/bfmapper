package br.com.bfmapper.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.bfmapper.Mapping;
import br.com.bfmapper.model.Aluno;
import br.com.bfmapper.model.AlunoCanonico;
import br.com.bfmapper.model.Carro;
import br.com.bfmapper.model.CarroCanonico;
import br.com.bfmapper.model.Endereco;
import br.com.bfmapper.model.EnderecoCanonico;
import br.com.bfmapper.model.ObjectCanonicModel;
import br.com.bfmapper.model.ObjectCanonicRecursiveModel1;
import br.com.bfmapper.model.ObjectCanonicRecursiveModel2;
import br.com.bfmapper.model.ObjectModel;
import br.com.bfmapper.model.ObjectRecursiveModel1;
import br.com.bfmapper.model.Pessoa;
import br.com.bfmapper.model.PessoaCanonico;
import br.com.bfmapper.model.Pneu;
import br.com.bfmapper.model.PneuCanonico;
import br.com.bfmapper.model.TipoAluno;

public class ConverterTest extends BaseTest {
    
	@Test
	public void testConverterSimple() {
		Carro carro = new Carro(1L, "Uno", "EP", new Pneu(1L, "Pirelli"));
		CarroCanonico carroCanonico = new Mapping().apply(carro).to(CarroCanonico.class);
		
		Assert.assertNotNull("Objeto carro não poder ser null", carroCanonico);
		Assert.assertNotNull("Propriedade marca não poder ser null", carroCanonico.getMarca());
		Assert.assertNotNull("Propriedade modelo não poder ser null", carroCanonico.getModelo());
		Assert.assertNotNull("Propriedade pneu não poder ser null", carroCanonico.getPneu());
	}
	
	@Test
	public void testConverterSimpleReverse() {
		CarroCanonico carroCanonico = new CarroCanonico("Uno", "EP", "Pirelli");
		Carro carro = new Mapping().apply(carroCanonico).to(Carro.class);
		
		Assert.assertNotNull("Objeto carro não poder ser null", carro);
		Assert.assertNotNull("Propriedade marca não poder ser null", carro.getNome());
		Assert.assertNotNull("Propriedade modelo não poder ser null", carro.getTipo());
		Assert.assertNotNull("Propriedade pneu não poder ser null", carro.getPneu());
	}

	@Test
	public void testConverterMultiApply() {
		PessoaCanonico pessoaCanonico = new PessoaCanonico(1L, "Douglas Rodrigo");
		
		List<EnderecoCanonico> enderecos = new ArrayList<EnderecoCanonico>();
		enderecos.add(new EnderecoCanonico("Av Paulista", "344", "Paulista"));
		enderecos.add(new EnderecoCanonico("Alameda Santos", "899", "Paulista"));
		pessoaCanonico.setEnderecos(enderecos);
		
		CarroCanonico carroCanonico = new CarroCanonico("Uno", "EP", "Pirelli");
		Pessoa pessoa =	new Mapping().apply(pessoaCanonico).apply(carroCanonico).to(Pessoa.class);
		
		Assert.assertNotNull("Objeto pessoa não poder ser null", pessoa);
		Assert.assertEquals("Objeto pessoa.endereco deve possuir 2 itens", pessoa.getEnderecos().size(), 2);
		Assert.assertNotNull("Objeto pessoa.carro não pode ser null", pessoa.getCarro());
		Assert.assertEquals("Objeto pessoa.carro.nome deve ser igual 'Uno'", pessoa.getCarro().getNome(), "Uno");
	}

	@Test
	public void testConverterMultiApplyReverse() {
		Pessoa pessoa = new Pessoa(1L, "Douglas Rodrigo", new Carro(1l, "Fusca", new Pneu(1l, "Toyo")));
		
		List<Endereco> enderecos = new ArrayList<Endereco>();
		enderecos.add(new Endereco("Av Paulista", "344", "Paulista"));
		enderecos.add(new Endereco("Alameda Santos", "899", "Paulista"));
		pessoa.setEnderecos(enderecos);
		
		PessoaCanonico pessoaCanonico =	new Mapping().apply(pessoa).to(PessoaCanonico.class);
		CarroCanonico carroCanonico = new Mapping().apply(pessoa.getCarro()).to(CarroCanonico.class);
		
		Assert.assertNotNull("Objeto pessoa não poder ser null", pessoaCanonico);
		Assert.assertNotNull("Objeto pessoa não poder ser null", carroCanonico);		
	}
	
	@Test
	public void testConverterApplyOnAttribute() {
		CarroCanonico carroCanonico = new CarroCanonico("Uno", "EP", "Pirelli");
		Pessoa pessoa =	new Mapping().applyOn(carroCanonico, "carro").to(Pessoa.class);
		
		Assert.assertNotNull("Objeto pessoa não poder ser null", pessoa);
		Assert.assertNotNull("Objeto pessoa.carro não poder ser null", pessoa.getCarro());
	}

	@Test
	public void testConverterApplyOnRecursiveAttribute() {
		PneuCanonico pneu = new PneuCanonico(4556L, "Pirelli");
		Pessoa pessoa =	new Mapping().applyOn(pneu, "carro.pneu").to(Pessoa.class);
		
		Assert.assertNotNull("Objeto pessoa não poder ser null", pessoa);
		Assert.assertNotNull("Atributo pessoa.carro.pneu não poder ser null", pessoa.getCarro().getPneu());
		
		Assert.assertTrue(pneu.getCodigo() == pessoa.getCarro().getPneu().getId());
	}
	
	@Test
	public void testCircularReference() {
		ObjectCanonicRecursiveModel1 objectCanonicRecursiveModel1 = new ObjectCanonicRecursiveModel1();
		ObjectCanonicRecursiveModel2 objectCanonicRecursiveModel2 = new ObjectCanonicRecursiveModel2();
		objectCanonicRecursiveModel2.setObject1(objectCanonicRecursiveModel1);
		objectCanonicRecursiveModel1.setObject2(objectCanonicRecursiveModel2);		
		
		
		ObjectRecursiveModel1 objectRecursiveModel1 = new Mapping().apply(objectCanonicRecursiveModel1).to(ObjectRecursiveModel1.class);
		
		Assert.assertTrue("objectRecursiveModel1.object2.object1 should be same reference as objectRecursiveModel1", 
				objectRecursiveModel1 == objectRecursiveModel1.getObject2().getObject1());
	}
	
	@Test
	public void testDeepCircularReference() {
		ObjectCanonicRecursiveModel1 objectCanonicRecursiveModel1 = new ObjectCanonicRecursiveModel1();
		ObjectCanonicRecursiveModel2 objectCanonicRecursiveModel2 = new ObjectCanonicRecursiveModel2();
		ObjectCanonicRecursiveModel1 otherObject1 = new ObjectCanonicRecursiveModel1();
		
		objectCanonicRecursiveModel1.setObject2(objectCanonicRecursiveModel2);
		objectCanonicRecursiveModel2.setObject1(otherObject1);
		otherObject1.setObject2(objectCanonicRecursiveModel2);
		
		ObjectRecursiveModel1 objectRecursiveModel1 = new Mapping().apply(objectCanonicRecursiveModel1).to(ObjectRecursiveModel1.class);
		
		Assert.assertTrue("objectRecursiveModel1.object2.object1.object2 should be same reference as objectRecursiveModel1.object2", 
				objectRecursiveModel1.getObject2().getObject1().getObject2() == objectRecursiveModel1.getObject2());
	}
	
	@Test
	public void testConverterTransformerValues() {
		Aluno aluno = new Aluno(1L, "Aline Alves", "20", "07111989", "REGULAR");
		AlunoCanonico alunoCanonico = new Mapping().apply(aluno).to(AlunoCanonico.class);

		Assert.assertNotNull("Objeto aluno não poder ser null", alunoCanonico);
		Assert.assertNotNull("Propriedade id não poder ser null", alunoCanonico.getId());
		Assert.assertNotNull("Propriedade nome não poder ser null", alunoCanonico.getNome());
		Assert.assertNotNull("Propriedade idade não poder ser null", alunoCanonico.getIdade());		
		Assert.assertNotNull("Propriedade dataAniversario não poder ser null", alunoCanonico.getDataAniversario());
		Assert.assertNotNull("Propriedade tipoAluno não poder ser null", alunoCanonico.getTipoAluno());
		Assert.assertEquals("Propriedade sexo deve receber o vsalor default 'F'", alunoCanonico.getSexo(), "F");
	}

	@Test
	public void testConverterListSimpleType() {
		Aluno aluno = new Aluno(1L, "Aline Alves", "20", "07111989", "REGULAR");
		aluno.setNotas(Arrays.asList("0.1", "0.2", "10.0"));
		
		AlunoCanonico alunoCanonico = new Mapping().apply(aluno).to(AlunoCanonico.class);

		Assert.assertNotNull("Propriedade notas não poder ser null", alunoCanonico.getNotas());
		Assert.assertNotNull("Propriedade notas não poder ser vazia", !alunoCanonico.getNotas().isEmpty());
	}

	@Test
	public void testConverterListEnumType() {
		ObjectModel model = new ObjectModel(Arrays.asList(TipoAluno.ESPECIAL, TipoAluno.REGULAR));
		
		ObjectCanonicModel canonicModel = new Mapping().apply(model).to(ObjectCanonicModel.class);

		Assert.assertNotNull("Propriedade tipoAlunos não poder ser null", canonicModel.getTipoAlunos());
		Assert.assertNotNull("Propriedade tipoAlunos não poder ser vazia", !canonicModel.getTipoAlunos().isEmpty());
	}
	
	@Test
	public void testDeepClone() {
	    Pneu pneuOrigem = new Pneu(1L, "Pirelli");
	    Carro carroOrigem = new Carro(1L, "Uno", "EP", pneuOrigem);
	    
	    Carro carro = new Mapping().apply(carroOrigem).to(Carro.class);
	    
	    Assert.assertNotNull("o pneu não pode ser null", carro.getPneu());
	    Assert.assertFalse("Os carros não deveriam ter a mesma referencia", carro == carroOrigem);
	    Assert.assertFalse("Os pneus Não deveriam ter a mesma referencia", carro.getPneu() == carroOrigem.getPneu());
	}
	
	@Test
	public void testDeepCloneList() {
	    Pneu pneuOrigem = new Pneu(1L, "Pirelli");
        Carro carroOrigem = new Carro(1L, "Corsa", "Millenium", pneuOrigem);
        Pessoa dono1 = new Pessoa(1L, "fulano de tal", null);
        Pessoa dono2 = new Pessoa(2L, "beltrano de tal", null);
        carroOrigem.setDonos(Arrays.asList(dono1, dono2));
        Carro carro = new Mapping().apply(carroOrigem).to(Carro.class);
        
        Assert.assertNotNull("o pneu não pode ser null", carro.getPneu());
        Assert.assertFalse("Os carros não deveriam ter a mesma referencia", carro == carroOrigem);
        Assert.assertFalse("Os pneus Não deveriam ter a mesma referencia", carro.getPneu() == carroOrigem.getPneu());
        Assert.assertFalse("As listas de donos não deveriam ter a mesma referencia", carro.getDonos() == carroOrigem.getDonos());
        Assert.assertFalse("O dono1 não deveriam ter a mesma referencia", carro.getDonos().get(0) == carroOrigem.getDonos().get(0));
        Assert.assertFalse("O dono2 não deveriam ter a mesma referencia", carro.getDonos().get(1) == carroOrigem.getDonos().get(1));
        
	}
}
