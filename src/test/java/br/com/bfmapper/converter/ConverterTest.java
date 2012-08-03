package br.com.bfmapper.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import org.junit.Test;

import br.com.bfmapper.Converter;
import br.com.bfmapper.Mapping;
import br.com.bfmapper.MappingRules;
import br.com.bfmapper.model.Aluno;
import br.com.bfmapper.model.AlunoCanonico;
import br.com.bfmapper.model.Carro;
import br.com.bfmapper.model.CarroCanonico;
import br.com.bfmapper.model.Endereco;
import br.com.bfmapper.model.EnderecoCanonico;
import br.com.bfmapper.model.Livro;
import br.com.bfmapper.model.LivroCanonico;
import br.com.bfmapper.model.ObjectCanonicModel;
import br.com.bfmapper.model.ObjectCanonicRecursiveModel1;
import br.com.bfmapper.model.ObjectCanonicRecursiveModel2;
import br.com.bfmapper.model.ObjectModel;
import br.com.bfmapper.model.ObjectRecursiveModel1;
import br.com.bfmapper.model.Outer;
import br.com.bfmapper.model.Outer.Inner;
import br.com.bfmapper.model.OuterCanonico;
import br.com.bfmapper.model.Pessoa;
import br.com.bfmapper.model.PessoaCanonico;
import br.com.bfmapper.model.Pneu;
import br.com.bfmapper.model.PneuCanonico;
import br.com.bfmapper.model.Produto;
import br.com.bfmapper.model.ProdutoCanonico;
import br.com.bfmapper.model.TipoAluno;
import br.com.bfmapper.model.canonic.ContratoCanonico;
import br.com.bfmapper.model.vo.Contrato;
import br.com.bfmapper.model.vo.Dependente;

public class ConverterTest extends BaseTest {
    
	@Test
	public void simpleConverter() {
		Carro carro = new Carro(1L, "Uno", "EP", new Pneu(1L, "Pirelli"));
		CarroCanonico carroCanonico = new Mapping().apply(carro).to(CarroCanonico.class);
		
		assertNotNull("Objeto carro não poder ser null", carroCanonico);
		assertNotNull("Propriedade marca não poder ser null", carroCanonico.getMarca());
		assertNotNull("Propriedade modelo não poder ser null", carroCanonico.getModelo());
		assertNotNull("Propriedade pneu não poder ser null", carroCanonico.getPneu());
	}
	
	@Test
	public void reverseSimpleConverter() {
		CarroCanonico carroCanonico = new CarroCanonico("Uno", "EP", "Pirelli");
		Carro carro = new Mapping().apply(carroCanonico).to(Carro.class);
		
		assertNotNull("Objeto carro não poder ser null", carro);
		assertNotNull("Propriedade marca não poder ser null", carro.getNome());
		assertNotNull("Propriedade modelo não poder ser null", carro.getTipo());
		assertNotNull("Propriedade pneu não poder ser null", carro.getPneu());
	}

	@Test
	public void multiApplyConverter() {
		PessoaCanonico pessoaCanonico = new PessoaCanonico(1L, "Douglas Rodrigo");
		
		List<EnderecoCanonico> enderecos = new ArrayList<EnderecoCanonico>();
		enderecos.add(new EnderecoCanonico("Av Paulista", "344", "Paulista"));
		enderecos.add(new EnderecoCanonico("Alameda Santos", "899", "Paulista"));
		pessoaCanonico.setEnderecos(enderecos);
		
		CarroCanonico carroCanonico = new CarroCanonico("Uno", "EP", "Pirelli");
		Pessoa pessoa =	new Mapping().apply(pessoaCanonico).apply(carroCanonico).to(Pessoa.class);
		
		assertNotNull("Objeto pessoa não poder ser null", pessoa);
		assertEquals("Objeto pessoa.endereco deve possuir 2 itens", pessoa.getEnderecos().size(), 2);
		assertNotNull("Objeto pessoa.carro não pode ser null", pessoa.getCarro());
		assertEquals("Objeto pessoa.carro.nome deve ser igual 'Uno'", pessoa.getCarro().getNome(), "Uno");
	}

	@Test
	public void multiApplyReverse() {
		Pessoa pessoa = new Pessoa(1L, "Douglas Rodrigo", new Carro(1l, "Fusca", new Pneu(1l, "Toyo")));
		
		List<Endereco> enderecos = new ArrayList<Endereco>();
		enderecos.add(new Endereco("Av Paulista", "344", "Paulista"));
		enderecos.add(new Endereco("Alameda Santos", "899", "Paulista"));
		pessoa.setEnderecos(enderecos);
		
		PessoaCanonico pessoaCanonico =	new Mapping().apply(pessoa).to(PessoaCanonico.class);
		CarroCanonico carroCanonico = new Mapping().apply(pessoa.getCarro()).to(CarroCanonico.class);
		
		assertNotNull("Objeto pessoa não poder ser null", pessoaCanonico);
		assertNotNull("Objeto pessoa não poder ser null", carroCanonico);		
	}
	
	@Test
	public void applyOnAttribute() {
		CarroCanonico carroCanonico = new CarroCanonico("Uno", "EP", "Pirelli");
		Pessoa pessoa =	new Mapping().applyOn(carroCanonico, "carro").to(Pessoa.class);
		
		assertNotNull("Objeto pessoa não poder ser null", pessoa);
		assertNotNull("Objeto pessoa.carro não poder ser null", pessoa.getCarro());
	}

	@Test
	public void applyOnRecursiveAttribute() {
		PneuCanonico pneu = new PneuCanonico(4556L, "Pirelli");
		Pessoa pessoa =	new Mapping().applyOn(pneu, "carro.pneu").to(Pessoa.class);
		
		assertNotNull("Objeto pessoa não poder ser null", pessoa);
		assertNotNull("Atributo pessoa.carro.pneu não poder ser null", pessoa.getCarro().getPneu());
		
		assertTrue(pneu.getCodigo() == pessoa.getCarro().getPneu().getId());
	}
	
	@Test
	public void circularReference() {
		ObjectCanonicRecursiveModel1 objectCanonicRecursiveModel1 = new ObjectCanonicRecursiveModel1();
		ObjectCanonicRecursiveModel2 objectCanonicRecursiveModel2 = new ObjectCanonicRecursiveModel2();
		objectCanonicRecursiveModel2.setObject1(objectCanonicRecursiveModel1);
		objectCanonicRecursiveModel1.setObject2(objectCanonicRecursiveModel2);		
		
		ObjectRecursiveModel1 objectRecursiveModel1 = new Mapping().apply(objectCanonicRecursiveModel1).to(ObjectRecursiveModel1.class);
		
		assertTrue("objectRecursiveModel1.object2.object1 should be same reference as objectRecursiveModel1", 
				objectRecursiveModel1 == objectRecursiveModel1.getObject2().getObject1());
	}
	
	@Test
	public void deepCircularReference() {
		ObjectCanonicRecursiveModel1 objectCanonicRecursiveModel1 = new ObjectCanonicRecursiveModel1();
		ObjectCanonicRecursiveModel2 objectCanonicRecursiveModel2 = new ObjectCanonicRecursiveModel2();
		ObjectCanonicRecursiveModel1 otherObject1 = new ObjectCanonicRecursiveModel1();
		
		objectCanonicRecursiveModel1.setObject2(objectCanonicRecursiveModel2);
		objectCanonicRecursiveModel2.setObject1(otherObject1);
		otherObject1.setObject2(objectCanonicRecursiveModel2);
		
		ObjectRecursiveModel1 objectRecursiveModel1 = new Mapping().apply(objectCanonicRecursiveModel1).to(ObjectRecursiveModel1.class);
		
		assertTrue("objectRecursiveModel1.object2.object1.object2 should be same reference as objectRecursiveModel1.object2", 
				objectRecursiveModel1.getObject2().getObject1().getObject2() == objectRecursiveModel1.getObject2());
	}
	
	@Test
	public void converterWithTransformerValues() {
		Aluno aluno = new Aluno(1L, "Aline Alves", "20", "07111989", "REGULAR");
		AlunoCanonico alunoCanonico = new Mapping().apply(aluno).to(AlunoCanonico.class);

		assertNotNull("Objeto aluno não poder ser null", alunoCanonico);
		assertNotNull("Propriedade id não poder ser null", alunoCanonico.getId());
		assertNotNull("Propriedade nome não poder ser null", alunoCanonico.getNome());
		assertNotNull("Propriedade idade não poder ser null", alunoCanonico.getIdade());		
		assertNotNull("Propriedade dataAniversario não poder ser null", alunoCanonico.getDataAniversario());
		assertNotNull("Propriedade tipoAluno não poder ser null", alunoCanonico.getTipoAluno());
		assertEquals("Propriedade sexo deve receber o vsalor default 'F'", alunoCanonico.getSexo(), "F");
	}

	@Test
	public void converterListSimpleType() {
		Aluno aluno = new Aluno(1L, "Aline Alves", "20", "07111989", "REGULAR");
		aluno.setNotas(Arrays.asList("0.1", "0.2", "10.0"));
		
		AlunoCanonico alunoCanonico = new Mapping().apply(aluno).to(AlunoCanonico.class);

		assertNotNull("Propriedade notas não poder ser null", alunoCanonico.getNotas());
		assertNotNull("Propriedade notas não poder ser vazia", !alunoCanonico.getNotas().isEmpty());
	}

	@Test
	public void converterListEnumType() {
		ObjectModel model = new ObjectModel(Arrays.asList(TipoAluno.ESPECIAL, TipoAluno.REGULAR));
		
		ObjectCanonicModel canonicModel = new Mapping().apply(model).to(ObjectCanonicModel.class);

		assertNotNull("Propriedade tipoAlunos não poder ser null", canonicModel.getTipoAlunos());
		assertNotNull("Propriedade tipoAlunos não poder ser vazia", !canonicModel.getTipoAlunos().isEmpty());
	}
	
	@Test
	public void deepClone() {
	    Pneu pneuOrigem = new Pneu(1L, "Pirelli");
	    Carro carroOrigem = new Carro(1L, "Uno", "EP", pneuOrigem);
	    
	    Carro carro = new Mapping().apply(carroOrigem).to(Carro.class);
	    
	    assertNotNull("O pneu não pode ser null", carro.getPneu());
	    assertFalse("Os carros não deveriam ter a mesma referencia", carro == carroOrigem);
	    assertFalse("Os pneus não deveriam ter a mesma referencia", carro.getPneu() == carroOrigem.getPneu());
	}
	
	@Test
	public void deepCloneList() {
	    Pneu pneuOrigem = new Pneu(1L, "Pirelli");
        Carro carroOrigem = new Carro(1L, "Corsa", "Millenium", pneuOrigem);
        Pessoa dono1 = new Pessoa(1L, "fulano de tal", null);
        Pessoa dono2 = new Pessoa(2L, "beltrano de tal", null);
        carroOrigem.setDonos(Arrays.asList(dono1, dono2));
        Carro carro = new Mapping().apply(carroOrigem).to(Carro.class);
        
        assertNotNull("O pneu não pode ser null", carro.getPneu());
        assertFalse("Os carros não deveriam ter a mesma referencia", carro == carroOrigem);
        assertFalse("Os pneus não deveriam ter a mesma referencia", carro.getPneu() == carroOrigem.getPneu());
        assertFalse("As listas de donos não deveriam ter a mesma referencia", carro.getDonos() == carroOrigem.getDonos());
        assertFalse("O dono1 não deveriam ter a mesma referencia", carro.getDonos().get(0) == carroOrigem.getDonos().get(0));
        assertFalse("O dono2 não deveriam ter a mesma referencia", carro.getDonos().get(1) == carroOrigem.getDonos().get(1));
	}

	@Test
	public void automaticEqualsProperties() {
	    Date dataValidade = new Date();
	    Produto produto = new Produto("marca A", new Float("3700.00"), "fabricante B");
	    produto.setDataValidade(dataValidade);
	    ProdutoCanonico produtoCanonico = new Mapping().apply(produto).to(ProdutoCanonico.class);
	    
	    assertNotNull("A marcao nao deveria ser null", produtoCanonico.getMarca());
	    assertNotNull("O fabricante nao deveria ser null", produtoCanonico.getFabricante());
	    assertNotNull("O preco nao deveria ser null", produtoCanonico.getPreco());
	    assertEquals("A data de validade deveria igual", produtoCanonico.getDataValidade(), dataValidade);
	}
	
    @Test
    public void excludeEqualsProperties() {
        Livro livro = new Livro("O programador pragmático", "BOOKMAN COMPANHIA ED", "Andrew Hunt");
        livro.setAnoPublicacao(2011L);
        
        LivroCanonico livroCanonico = new Mapping().apply(livro).to(LivroCanonico.class);
        
        assertNotNull("O autor do livro nao deveria ser null", livroCanonico.getAutor());
        assertNotNull("A editora do livro nao deveria ser null", livroCanonico.getEditora());
        assertNotNull("O nome do livro nao deveria ser null", livroCanonico.getNome());
        assertNull("A data de publicacao deveria ser null", livroCanonico.getAnoPublicacao());
    }
    
    @Test
    public void concerterProxiedClasses() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Carro.class);
        enhancer.setCallback(NoOp.INSTANCE);
        Object proxiedCarroObject = enhancer.create();
        
        ((Carro) proxiedCarroObject).setNome("Car one");
        
        CarroCanonico carroCanonico = new Mapping().apply(proxiedCarroObject).to(CarroCanonico.class);
        assertEquals("car and canonic car should have same name", ((Carro) proxiedCarroObject).getNome(), carroCanonico.getMarca());
    }
    
    @Test
    public void shouldNotAddMultipleConvertersForTheSamePairOfClasses() {
        MappingRules.addRule(new Converter(ObjectCanonicModel.class, ObjectModel.class));
        MappingRules.addRule(new Converter(ObjectCanonicModel.class, ObjectModel.class));
    }
    
    @Test
    public void innerClassMapping() {
    	Outer outer = new Outer();
    	outer.setName("outer name");
    	Inner inner = outer.new Inner();
    	inner.setName("inner name");
    	outer.setInner(inner);
    	
    	OuterCanonico outerCanonico = new Mapping().apply(outer).to(OuterCanonico.class);
    	
    	assertEquals(outer.getName(), outerCanonico.getName());
    	assertEquals(outer.getInner().getName(), outerCanonico.getInnerCanonico().getName());
    }
    
    @Test
    public void innerClassToExistingInstanceMapping() {
    	Outer outer = new Outer();
    	outer.setName("outer name");
    	Inner inner = outer.new Inner();
    	inner.setName("inner name");
    	outer.setInner(inner);
    	OuterCanonico outerCanonico = new OuterCanonico();
    	OuterCanonico returningObject = new Mapping().apply(outer).to(outerCanonico);
    	
    	assertTrue(outerCanonico == returningObject);
    	assertEquals(outer.getName(), outerCanonico.getName());
    	assertEquals(outer.getInner().getName(), outerCanonico.getInnerCanonico().getName());
    }
    
    @Test
    public void innerClassWithList() {
    	Contrato contrato = new Contrato();
    	contrato.setCodigo("666");
    	contrato.adicionar(new Dependente("Tia")).adicionar(new Dependente("Andressa"));
    	
    	ContratoCanonico contratoCanonico = new Mapping().apply(contrato).to(ContratoCanonico.class);
    	assertNotNull(contratoCanonico);
    	assertFalse(contratoCanonico.getDependentes().isEmpty());

    	for (br.com.bfmapper.model.canonic.ContratoCanonico.Dependente dependente : contratoCanonico.getDependentes()) {
    		assertNotNull(dependente);
		}
    }
}
