package br.com.blackfoot.bfmapper.transformer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.ClassUtils;

public class DateTimeTransformer implements SimpleTransformer {

	private DateFormat format;

	private boolean longAsDate;
	
	public DateTimeTransformer() {}
	
	public DateTimeTransformer(String patternFormat) {
		this.format = new SimpleDateFormat(patternFormat);	
	}

	public DateTimeTransformer(String patternFormat, boolean longAsDate) {
		this(patternFormat);
		this.longAsDate = longAsDate;
	}
	
	@Override
	public <T> T transform(Object value, Class<T> type)  {
		Object returnValue = null;
		
		if (value == null || value.equals("")) {
			return null;
		}
		
		if (ClassUtils.isAssignable(type, java.util.Date.class) && value instanceof String) {
			returnValue = this.transformStringToDate((String) value);
		
		} else if (ClassUtils.isAssignable(type, String.class) && value instanceof java.util.Date) {
			returnValue = this.transformDateToString((java.util.Date) value);
		
		} else if (ClassUtils.isAssignable(type, Calendar.class) && value instanceof java.util.Date) {
			returnValue = this.transformDateToCalendar((java.util.Date) value);
		
		} else if (ClassUtils.isAssignable(type, java.util.Date.class) && value instanceof Calendar) {
			returnValue = this.transformCalendarToDate((Calendar) value);
		
		} else if (ClassUtils.isAssignable(type, Calendar.class) && value instanceof String) {
			returnValue = this.transformDateToCalendar(this.transformStringToDate((String) value));
		
		} else if (ClassUtils.isAssignable(type, String.class) && value instanceof Calendar) {
			returnValue = this.transformDateToString(this.transformCalendarToDate((Calendar) value));
		
		} else if (ClassUtils.isAssignable(type, java.util.Date.class) && value instanceof Long) {
			if (!this.longAsDate) {
				returnValue = this.transformLongToDate((Long) value);
			} else {
				returnValue = this.transformStringToDate(value.toString());
			}
		} else if (ClassUtils.isAssignable(type, Long.class) && value instanceof java.util.Date) {
			if (!this.longAsDate) {
				returnValue = this.transformDateToLong((java.util.Date) value);
			} else {
				returnValue = new Long(this.transformDateToString((java.util.Date) value));
			}
		} else if (ClassUtils.isAssignable(type, Calendar.class) && value instanceof Long) {
			if (!this.longAsDate) {
				returnValue = this.transformDateToCalendar(this.transformLongToDate((Long) value));
			} else {
				returnValue = this.transformDateToCalendar(this.transformStringToDate(value.toString()));
			}
		} else if (ClassUtils.isAssignable(type, Long.class) && value instanceof Calendar) {
			if (!this.longAsDate) {
				returnValue = this.transformDateToLong(this.transformCalendarToDate((Calendar) value));
			} else {
				returnValue = new Long(this.transformDateToString(this.transformCalendarToDate((Calendar) value)));
			}
		} else if (ClassUtils.isAssignable(type, XMLGregorianCalendar.class) && value instanceof java.util.Date) {
			try {
				returnValue = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) this.transformDateToCalendar((java.util.Date) value));
			} catch (DatatypeConfigurationException e) {
				throw new IllegalArgumentException("Error parser java.util.Date value to XMLGregorianCalendar");
			}
			
		} else if (ClassUtils.isAssignable(type, java.util.Date.class) && value instanceof XMLGregorianCalendar) {
			returnValue = this.transformCalendarToDate(((XMLGregorianCalendar) value).toGregorianCalendar());			
			
		} else {
			throw new IllegalArgumentException("Incorrect type for transformer class");
		}
		
		return type.cast(returnValue);
	}

	private java.util.Date transformStringToDate(String value) {
		this.validateFormat();
		try {
			return this.format.parse(value);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Error parser String value to java.util.Date");
		}
	}

	private String transformDateToString(java.util.Date value) {
		this.validateFormat();
		return this.format.format(value);
	}

	private java.util.Date transformCalendarToDate(Calendar value) {
		return value.getTime();
	}

	private Calendar transformDateToCalendar(java.util.Date value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		return calendar;
	}
	
	private java.util.Date transformLongToDate(Long value) {
		return new java.util.Date(value);
	}

	private Long transformDateToLong(java.util.Date value) {
		return value.getTime();
	}

	private void validateFormat() {
		if (this.format == null) {
			throw new IllegalArgumentException("Format is required");
		}
	}
	
}
