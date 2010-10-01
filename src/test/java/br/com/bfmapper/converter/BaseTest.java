package br.com.bfmapper.converter;

import org.junit.BeforeClass;

import br.com.bfmapper.MappingRulesLoader;

public abstract class BaseTest {

    @BeforeClass
    public static void loadMappings() {
        MappingRulesLoader.loader("br.com.bfmapper.mapping");
    }
    
}
