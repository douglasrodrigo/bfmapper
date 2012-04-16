package br.com.bfmapper.converter;

import org.junit.BeforeClass;

import br.com.bfmapper.MappingRulesLoader;

public abstract class BaseTest {

    private static boolean mappingRulesLoaded = false;
    
    @BeforeClass
    public static void loadMappings() {
        if (!mappingRulesLoaded) {
            MappingRulesLoader.loader("br.com.bfmapper.mapping");
            mappingRulesLoaded = true;
        }
    }
    
}
