package br.com.bfmapper.converter.transformer;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanUtils;

import br.com.bfmapper.CustomTransformer;

public class UpperCaseCustomTransformer implements CustomTransformer {
    
    private Class<?> targetClass;
    
    public UpperCaseCustomTransformer(Class<?> targetClass) {
        this.targetClass = targetClass;
    }
    
    
    @Override
    public void apply(Object source, Object target) {
        if (target.getClass().equals(targetClass)) {
            for(PropertyDescriptor targetPropertyDescriptor : BeanUtils.getPropertyDescriptors(target.getClass())) {
                try {
                    if (targetPropertyDescriptor.getPropertyType().equals(String.class)) {
                        String formatedValue = ((String) targetPropertyDescriptor.getReadMethod().invoke(target));
                        if (formatedValue != null) {
                            formatedValue = formatedValue.toUpperCase();
                        }
                        targetPropertyDescriptor.getWriteMethod().invoke(target, formatedValue); 
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("invalid properties");
                } 
            }
        }
    }

}
