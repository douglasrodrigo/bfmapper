package br.com.bfmapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.bfmapper.transformer.Transformer;
import br.com.bfmapper.util.Acceptable;
import br.com.bfmapper.util.CollectionUtils;
import br.com.bfmapper.util.ReflectionUtils;

public class Converter {
	
	private List<Property> properties = new ArrayList<Property>();
	
	private Property currentProperty;
	
	private Class<?> sourceClass;
	
	private Class<?> targetClass;
	
	private Set<DefaultTransformer> defaultPropertiesTransformers;
	
	public Converter(Class<?> sourceClass, Class<?> targetClass, DefaultTransformer ... defaultPropertiesTransformers) {
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
		this.defaultPropertiesTransformers = new HashSet<DefaultTransformer>();
		
		if (defaultPropertiesTransformers != null) {
			this.defaultPropertiesTransformers.addAll(Arrays.asList(defaultPropertiesTransformers));
		}
	}
	
	public Converter add(String sourceProperty, String targetProperty) {
		PropertyMapping propertyMapping = new PropertyMapping(sourceProperty, targetProperty);
		
		Class<?> sourcePropertyClass = ReflectionUtils.invokeRecursiveType(this.sourceClass, sourceProperty);
		Class<?> targetPropertyClass = ReflectionUtils.invokeRecursiveType(this.targetClass, targetProperty);
		
		propertyMapping.setTransformer(this.getDefaultPropertyTransformer(sourcePropertyClass, targetPropertyClass));
		
		this.currentProperty = propertyMapping;
		this.properties.add(propertyMapping);
		
		return this;
	}
	
	public Class<?> getSourceClass() {
		return this.sourceClass;
	}

	public Class<?> getTargetClass() {
		return this.targetClass;
	}

	public List<Property> getProperties() {
		return this.properties;
	}
	
	public void addDefault(Class<?> clazz, String property, Object defaultValue) {
		PropertyDefault propertyDefault = new PropertyDefault(clazz, property, defaultValue);
		this.currentProperty = propertyDefault;
		this.properties.add(propertyDefault);
	}
	
	public void with(Transformer transformer){
		if (this.currentProperty instanceof PropertyMapping) {
			((PropertyMapping) this.currentProperty).setTransformer(transformer);
		} else {
			throw new IllegalArgumentException("It couldn't use with for default values");
		}
	}
	
	public void withShallowCopy() {
        if (this.currentProperty instanceof PropertyMapping) {
            ((PropertyMapping) this.currentProperty).setShallowCopy(true);
        } else {
            throw new IllegalArgumentException("It couldn't use withShallowCopy for default values");
        }
	}

	public Transformer getDefaultPropertyTransformer(final Class<?> propertySourceClass, final Class<?> propertyTargetClass) {
		Transformer transformer = null;
		DefaultTransformer baseTransformer = CollectionUtils.unique(this.defaultPropertiesTransformers, new Acceptable<DefaultTransformer>() {
			@Override
			public boolean accept(DefaultTransformer value) {
				return value.getSourcePropertyClass().equals(propertySourceClass) && value.getTargetPropertyClass().equals(propertyTargetClass);
			}
		});
		
		if (baseTransformer != null) {
			transformer = baseTransformer.getTransformer();
		}
		
		return transformer;
	}
	
	public boolean containsProperty(Class<?> sourceClass, String propertyName) {
		boolean contains = false;
		for (Property property : this.properties) {
			if ((property.getTargetProperty(sourceClass) != null && property.getTargetProperty(sourceClass).equals(propertyName)) || 
					(property instanceof PropertyMapping && ((PropertyMapping) property).getSourceProperty(sourceClass).equals(propertyName))) { 
				contains = true;
				break;
			}
		}
		return contains;
	}

	public interface Property {
		
		Object getSourceValue(Object source);
		
		String getTargetProperty(Class<?> source);
		
	}
	
	public class PropertyDefault implements Property {
		
		private Class<?> targetDefaultClass;
		
		private String property;
		
		private Object defaultValue;

		public PropertyDefault(Class<?> targetDefaultClass, String property, Object defaultValue) {
			this.targetDefaultClass = targetDefaultClass;
			this.property = property;
			this.defaultValue = defaultValue;
		}

		@Override
		public Object getSourceValue(Object source) {
			return this.defaultValue;
		}
		
		public Class<?> getTargetClass() {
			return this.targetDefaultClass;
		}
		
		public String getTargetProperty(Class<?> source) {
			String targetProperty = null;
			if (!source.equals(this.targetDefaultClass)) {
				targetProperty = this.property;
			}
			return targetProperty;
		}
	}
	
	public class PropertyMapping implements Property {
		
		private String sourceProperty;
		
		private String targetProperty;
		
		private Transformer transformer;
		
		private boolean shallowCopy;
		
		public PropertyMapping(String sourceProperty, String targetProperty) {
			this.sourceProperty = sourceProperty;
			this.targetProperty = targetProperty;
		}
		
		public String getSourceProperty(Class<?> source) {
			String property = null;
			if (source.equals(Converter.this.sourceClass)) {
				property = this.sourceProperty;
			} else {
				property = this.targetProperty;
			}
			return property;
		}

		public String getTargetProperty(Class<?> source) {
			String property = null;
			if (source.equals(Converter.this.sourceClass)) {
				property = this.targetProperty;
			} else {
				property = this.sourceProperty;
			}
			return property;
		}

		public Transformer getTransformer() {
			return transformer;
		}

		public void setTransformer(Transformer transformer) {
			this.transformer = transformer;
		}

		public boolean isShallowCopy() {
            return this.shallowCopy;
        }

        public void setShallowCopy(boolean shallowCopy) {
            this.shallowCopy = shallowCopy;
        }

        @Override
		public Object getSourceValue(Object source) {
			return ReflectionUtils.invokeRecursiveGetter(source, this.getSourceProperty(source.getClass()));
		}
	}
}
