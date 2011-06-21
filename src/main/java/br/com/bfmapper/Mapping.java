package br.com.bfmapper;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;
import org.springframework.beans.BeanUtils;

import br.com.bfmapper.transformer.ComplexTransformer;
import br.com.bfmapper.transformer.EnumTransformer;
import br.com.bfmapper.transformer.NumberTransformer;
import br.com.bfmapper.transformer.SimpleTransformer;
import br.com.bfmapper.transformer.Transformer;
import br.com.bfmapper.util.ReflectionUtils;

public class Mapping implements Serializable {

	private static final long serialVersionUID = 4371444630053025368L;
	
	private MappingContext mappingContext = new MappingContext();
	
	public synchronized  Mapping apply(Object o) {
		this.setAppliedObjectItem(new AppliedObject(o));
		return this;
	}
	
	public synchronized Mapping applyOn(Object o, String attribute) {
		this.setAppliedObjectItem(new AppliedObject(AppliedType.APPLY_ON, o, attribute));
		return this;
	}
	
	private synchronized void setAppliedObjectItem(AppliedObject appliedObject) {
		this.mappingContext.getAppliedObjects().add(appliedObject);
	}
	
	public synchronized <T> T to(T target) {
		if (target == null) {
			return null;
		}
		
		for (AppliedObject appliedObject : this.mappingContext.getAppliedObjects()) {
			Object appliedObjectSource = appliedObject.getSourceObject();
			
			if (appliedObjectSource == null) {
				continue;
			}
			
			this.addToCache(appliedObjectSource, target);
			
			if (appliedObject.getAppliedType().equals(AppliedType.APPLY)) {
				if (appliedObjectSource.getClass().equals(target.getClass())) {
					this.evalEqualsProperties(appliedObjectSource, target, null);										
				} else {
					Converter converter = this.getConverter(appliedObjectSource.getClass(), target.getClass());
					if (converter != null) {
						this.applyConverter(converter, appliedObjectSource, target);
					} else { 
						this.applyConverterOnField(appliedObjectSource, target);
					}
				}				
			}	

			if (appliedObject.getAppliedType().equals(AppliedType.APPLY_ON)) {
				this.applyConverterOnFieldName(appliedObjectSource, target, appliedObject.getAttribute());
			}	
		}
		this.mappingContext.clear();
		return target;
	}

	private void addToCache(Object source, Object target) {
		this.mappingContext.getCachedObjects().put(source, target);
	}
	
	public <T> T to(Class<T> target) {
		T returnValue = null;
		boolean validateApply = false;
		
		for (AppliedObject appliedObject : this.mappingContext.getAppliedObjects()) {
			Object appliedObjectSource = appliedObject.getSourceObject();
			/* se pelo menos um appliedObjectSource for diferente de null, valida a chamada da conversão */
			if (appliedObjectSource != null) {
				validateApply = true;
				break;
			}
		}	

		if (validateApply) {
			returnValue = target.cast(this.to(this.instanceFactory(target)));
		}
		
		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
    public <T> T deepClone(T value) {
	    return (T) this.apply(value).to(value.getClass());
	}
	
	public <T> T toCollection(Class<?> target, Class<? extends Collection<?>> collectionClass) {
	    return null;
	}
	
    public <T> T toCollection(Class<?> target) {
	    return null;
	}
	
	private Object instanceFactory(Class<?> target) {
		Object object = null;
		try {
			object = target.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} 
		return object;
	}

	private Object applyConverter(Converter converter, Object source, Object target) {
		for (Converter.Property property : converter.getProperties()) {
			this.resolveProperty(converter, source, target, property);
		}

		this.evalEqualsProperties(source, target, converter);
		
		for (CustomTransformer customTransformer: converter.getCustomTransfomers()) {
		    customTransformer.apply(source, target);
		}
		
		return target;
	}
	
	private void resolveProperty(Converter converter, Object source, Object target, Converter.Property property) {
		String targetProperty = property.getTargetProperty(source.getClass());
		
		if (targetProperty != null) {
			Object value = property.getSourceValue(source);
			
			if (property instanceof Converter.PropertyMapping) {
				Converter.PropertyMapping propertyMapping = (Converter.PropertyMapping) property;
				if (!propertyMapping.isShallowCopy()) {
				    value = this.resolveValue(source, target, propertyMapping.getSourceProperty(source.getClass()), targetProperty, value, propertyMapping.getTransformer());    
				}
			} 

			try {
				if (value != null) {
					PropertyDescriptor targetPropertyDescriptor = BeanUtils.getPropertyDescriptor(target.getClass(), targetProperty);
					
					if (targetPropertyDescriptor == null || targetPropertyDescriptor.getWriteMethod() != null) {
						try {
						    ReflectionUtils.invokeRecursiveSetter(target, targetProperty, value);    
                        } catch (Exception e) {
                            //hack to set collections without set method in chained properties
                            if (ClassUtils.isAssignable(value.getClass(), Collection.class) && targetProperty.contains(".")) {
                                Object chainedObjectTarget = ReflectionUtils.prepareInvokeRecursiveSetter(target, targetProperty, value);
                                String lastProperty = targetProperty.substring(targetProperty.lastIndexOf(".") + 1);
                                setCollectionValueFromReadMethod(BeanUtils.getPropertyDescriptor(chainedObjectTarget.getClass(), lastProperty), chainedObjectTarget, value);      
                            }
                        }
					} else if (ClassUtils.isAssignable(value.getClass(), Collection.class)) {
						setCollectionValueFromReadMethod(targetPropertyDescriptor, target, value);
						
					}
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	private Collection<?> getInstanceFromCollectionType(Class<?> collectionClass) {
		Class<?> collectionClassResult = null;
		if (ClassUtils.isAssignable(collectionClass, Set.class)) {
			collectionClassResult = HashSet.class;
		} else {
			collectionClassResult = ArrayList.class;
		}
		
		return ReflectionUtils.newInstance(collectionClassResult);
	}

	private void applyConverterOnField(Object source, Object target) {
		Converter converter = null;

		PropertyDescriptor targetProperty = null;
		
		for (PropertyDescriptor property: BeanUtils.getPropertyDescriptors(target.getClass())) {
			converter = this.getConverter(source.getClass(), property.getPropertyType());
			
			if (converter != null) {
				targetProperty = property;
				break;
			}
		}
		
		if (converter != null) {
			try {
				ReflectionUtils.invokeRecursiveSetter(target, targetProperty.getName(), this.applyConverter(converter, source, targetProperty.getPropertyType().newInstance()));
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			} 
		}
	}
	
	private void applyConverterOnFieldName(Object source, Object target, String fieldName) {
		try {
			Class<?> fieldType = ReflectionUtils.invokeRecursiveType(target, fieldName);
			Converter converter = this.getConverter(source.getClass(), fieldType);
			
			if (converter != null) {
			    Object fieldValue = ReflectionUtils.invokeRecursiveGetter(target, fieldName);
			    if (fieldValue == null) {
			        fieldValue = fieldType.newInstance();
			    }
			    
				ReflectionUtils.invokeRecursiveSetter(target, fieldName, this.applyConverter(converter, source, fieldValue));
			} 
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} 
	}
	
	private Converter getConverter(Class<?> sourceClass, Class<?> targetClass) {
		Converter converterResult = null;
		
		for (Converter converter : MappingRules.getConverters()) {
			if (isValidConverter(converter, sourceClass, targetClass)) {
				converterResult = converter;
				break;
			}
		}
		return converterResult;
	}
	
	private boolean isValidConverter(Converter converter, Class<?> sourceClass, Class<?> targetClass) {
		sourceClass = ReflectionUtils.getTargetClass(sourceClass);
		targetClass = ReflectionUtils.getTargetClass(targetClass);
		
		Class<?> sourceConverter = converter.getSourceClass();
		Class<?> targetConverter = converter.getTargetClass();
		return (sourceClass.equals(sourceConverter) && targetClass.equals(targetConverter)) || (targetClass.equals(sourceConverter) && sourceClass.equals(targetConverter));		
	}
	
	private void evalEqualsProperties(Object source, Object target, Converter converter) {
		String targetPropertyName = null;
		Object value = null;
		try {
	
			for(PropertyDescriptor targetPropertyDescriptor : BeanUtils.getPropertyDescriptors(target.getClass())) {
				targetPropertyName = targetPropertyDescriptor.getName();

				if (targetPropertyName.equals("class") || (converter != null && converter.containsProperty(source.getClass(), targetPropertyName))) {
					continue;
				}

				PropertyDescriptor sourcePropertyDescriptor = BeanUtils.getPropertyDescriptor(source.getClass(), targetPropertyName);

				if (sourcePropertyDescriptor == null) {
					continue;
				}

				Class<?> sourcePropertyClass = sourcePropertyDescriptor.getPropertyType();
				Class<?> targetPropertyClass = targetPropertyDescriptor.getPropertyType();
				value = sourcePropertyDescriptor.getReadMethod().invoke(source);

                if (!this.isSimpleType(sourcePropertyClass, targetPropertyClass)) {
					value = this.resolveValue(source, target, targetPropertyName, targetPropertyName, value, null); 
				}

				/* the value of the targetProperty won't be replaced by the value of the sourceProperty in case it's null */
				if (value != null) {
					if (targetPropertyDescriptor.getWriteMethod() != null) {
						targetPropertyDescriptor.getWriteMethod().invoke(target, value);
					} else if (ClassUtils.isAssignable(targetPropertyClass, Collection.class)) {
					    setCollectionValueFromReadMethod(targetPropertyDescriptor, target, value);
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Converting error. \nSource: " + source + "\n" +
					"Target: " + target + "\nTarget Property: " + targetPropertyName + "\nConverter: " + converter + "\nValue: " + value, e); 
		}
	}

	@SuppressWarnings("unchecked")
    private void setCollectionValueFromReadMethod(PropertyDescriptor targetPropertyDescriptor, Object target, Object value) {
	    /* generated beans by webservices don't have set for collections */
	    try {
            ((Collection<?>)targetPropertyDescriptor.getReadMethod().invoke(target)).addAll((Collection)value);
        } catch (Exception e) {
            throw new IllegalArgumentException("illegal attribute from " + target.getClass().getSimpleName());
        }	    
	}
	
	private boolean isSimpleType(Class<?> sourceClass, Class<?> targetClass) {
		return ReflectionUtils.isSimpleType(sourceClass) && ReflectionUtils.isPrimitiveWrapperAssignable(sourceClass, targetClass);
	}
	
	private Object resolveValue(Object source, Object target, String sourceProperty, String targetProperty, Object value, Transformer transformer) {
		
        Class<?> sourceClassAttribute = ReflectionUtils.invokeRecursiveType(source, sourceProperty);
	    Class<?> targetClassAttribute = ReflectionUtils.invokeRecursiveType(target, targetProperty);
		
		if (ClassUtils.isAssignable(targetClassAttribute, Collection.class) && value instanceof Collection<?>) {
			value = this.resolveCollectionValue(source, target, sourceProperty, targetProperty, targetClassAttribute, value, transformer);
		
		} else if (transformer != null || ReflectionUtils.isSimpleType(sourceClassAttribute) || ReflectionUtils.isSimpleType(targetClassAttribute)) {
			value = this.resolveSimpleValue(source, target, targetClassAttribute, value, transformer);
			
		} else if (value != null) {
			Converter converter = this.getConverter(sourceClassAttribute, targetClassAttribute);
		    if (converter != null) {
		        try {
		            Object targetInstance = this.mappingContext.getCachedObjects().get(value);
		            if (targetInstance == null) {
		                targetInstance = targetClassAttribute.newInstance();
		                this.addToCache(value, targetInstance);
		                value = this.applyConverter(converter, value, targetInstance);
		            } else {
		                value = targetInstance;
		            }
		        } catch (Exception e) {
		            throw new IllegalArgumentException(e);
		        } 
		    } else if (targetClassAttribute.equals(sourceClassAttribute)) {
		        Object returnValue = instanceFactory(targetClassAttribute);
		        evalEqualsProperties(value, returnValue, null);
		        
		        value = returnValue;
		    } else {
		        value = null;
		    }
		}

		return value;
	}

	@SuppressWarnings("unchecked")
	private Object resolveSimpleValue(Object source, Object target, Class<?> targetClassAttribute, Object value, Transformer transformer) {

		if (transformer != null) {
			/* use custom transformer user */
			if (transformer instanceof SimpleTransformer) {
				value = ((SimpleTransformer) transformer).transform(value, targetClassAttribute);
				
			} else if (transformer instanceof ComplexTransformer) {
				value = ((ComplexTransformer) transformer).transform(source, target, value, targetClassAttribute);
				
			} else {
				throw new IllegalArgumentException("Incorrect transformer instance");
			}
		} else if (ClassUtils.isAssignable(targetClassAttribute, Number.class) || value instanceof Number || ClassUtils.isAssignable(ClassUtils.primitiveToWrapper(targetClassAttribute), Number.class)) {
			/* use automatic transformer number */
			value = new NumberTransformer().transform(value, targetClassAttribute);
		
		} else if (ClassUtils.isAssignable(targetClassAttribute, Enum.class) || value instanceof Enum) { 
			/* use automatic transformer enumeration */
			value = new EnumTransformer().transform(value, targetClassAttribute);
		}
		
		return value;
	}
	
	@SuppressWarnings("unchecked")
	private Object resolveCollectionValue(Object source, Object target, String sourceProperty, String targetProperty, Class<?> targetClassAttribute, Object value, Transformer transformer) {
		if (ClassUtils.isAssignable(targetClassAttribute, Collection.class) && value instanceof Collection<?>) {
			/* use automatic transformer collection */
			Class<?> sourceCollectionItemClass = ReflectionUtils.getGenericClassFromRecursiveField(source, sourceProperty, 0);
			Class<?> targetCollectionItemClass = ReflectionUtils.getGenericClassFromRecursiveField(target, targetProperty, 0);
			Converter converter = this.getConverter(sourceCollectionItemClass, targetCollectionItemClass);
			
			try {
				Object sourceValue = value;
				value = this.getInstanceFromCollectionType(targetClassAttribute);
				Collection<Object> targetCollection = (Collection<Object>) value;
				
				if (converter != null) {
					for (Object sourceItem : (Collection<Object>) sourceValue) {
						targetCollection.add(this.applyConverter(converter, sourceItem, targetCollectionItemClass.newInstance()));
					}
					
				} else if (ReflectionUtils.isSimpleType(targetCollectionItemClass)) {
					for (Object sourceItem : (Collection<Object>) sourceValue) {
						targetCollection.add(this.resolveSimpleValue(source, target, targetCollectionItemClass, sourceItem, transformer));
					}
					
				} else if (sourceCollectionItemClass.equals(targetCollectionItemClass)) {
                    for (Object sourceItem : (Collection<Object>) sourceValue) {
                        Object returnValue = instanceFactory(targetCollectionItemClass);
                        
                        this.evalEqualsProperties(sourceItem, returnValue, null);
                        targetCollection.add(returnValue);
                        
                    }
                    value = targetCollection;
				    
					// TODO - implementar conversao de List dentro de List (recursivo)
//					value = null;
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			} 
		} else if (ClassUtils.isAssignable(targetClassAttribute, Map.class) || value instanceof Map<?, ?>) {
		    // TODO - implementar conversao de Map
		    value = null;
		} 
		
		return value;
	}
	
}
