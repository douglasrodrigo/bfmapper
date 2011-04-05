package br.com.bfmapper;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.bfmapper.util.ClassLoaderUtils;

public class MappingRulesLoader {

    public static void loader(String basePackage) {
        for (Class<?> clazz : ClassLoaderUtils.getClassesForPackage(basePackage)) {
            if (!clazz.isInterface() && RulesMapper.class.isAssignableFrom(clazz)) {
                try {
                    ((RulesMapper)clazz.newInstance()).loadRules();
                } catch (Exception e) {
                    Logger.getLogger(MappingRulesLoader.class.getName()).log(Level.WARNING, "class " + clazz.getName() + " not loaded");
                }
            }
        } 
    }
}
