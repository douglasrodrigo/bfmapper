package br.com.bfmapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.bfmapper.transformer.Transformer;
import br.com.bfmapper.util.ReflectionUtils;

public class Converter {
	
	private List<Property> properties = new ArrayList<Property>();
	
	private List<String> excludedProperties = new ArrayList<String>();
	
	private Property currentProperty;
	
	private Class<?> sourceClass;
	
	private Class<?> targetClass;
	
	private List<CustomTransformer> customTransformers = new ArrayList<CustomTransformer>();
	
	public Converter(Class<?> sourceClass, Class<?> targetClass) {
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
	}
	
	public Converter add(String sourceProperty, String targetProperty) {
		PropertyMapping propertyMapping = new PropertyMapping(sourceProperty, targetProperty);
		this.currentProperty = propertyMapping;
		properties.add(propertyMapping);
		return this;
	}
	
	public void addDefault(Class<?> clazz, String property, Object defaultValue) {
		PropertyDefault propertyDefault = new PropertyDefault(clazz, property, defaultValue);
		this.currentProperty = propertyDefault;
		properties.add(propertyDefault);
	}
	
	public void excludeEqualsProperties(String... properties) {
	    excludedProperties.addAll(Arrays.asList(properties));
	}
	
	public void with(Transformer transformer){
		if (currentProperty instanceof PropertyMapping) {
			((PropertyMapping) this.currentProperty).setTransformer(transformer);
		} else {
			throw new IllegalArgumentException("It couldn't use with for default values");
		}
	}
	
	public void withShallowCopy() {
        if (currentProperty instanceof PropertyMapping) {
            ((PropertyMapping) this.currentProperty).setShallowCopy(true);
        } else {
            throw new IllegalArgumentException("It couldn't use withShallowCopy for default values");
        }
	}

    public void addCustomTransformer(CustomTransformer customTransformer) {
        this.customTransformers.add(customTransformer);
    }
    
    public List<CustomTransformer> getCustomTransfomers() {
        return customTransformers;
    }
    
	public Class<?> getSourceClass() {
		return sourceClass;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public List<Property> getProperties() {
		return properties;
	}
	
	public List<String> getExcludedProperties() {
	    return excludedProperties;
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

		public PropertyDefault(Class<?> targetDefaultClass, String property,
				Object defaultValue) {
			this.targetDefaultClass = targetDefaultClass;
			this.property = property;
			this.defaultValue = defaultValue;
		}


		@Override
		public Object getSourceValue(Object source) {
			return defaultValue;
		}
		
		public Class<?> getTargetClass() {
			return this.targetDefaultClass;
		}
		
		public String getTargetProperty(Class<?> source) {
			String targetProperty = null;
			
			if (!source.equals(targetDefaultClass)) {
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
			String property;
			if (source.equals(Converter.this.sourceClass)) {
				property = this.sourceProperty;
			} else {
				property = this.targetProperty;
			}
			return property;
		}

		public String getTargetProperty(Class<?> source) {
			String property;
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
            return shallowCopy;
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
