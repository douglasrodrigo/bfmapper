package br.com.bfmapper.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

public class StringUtil extends StringUtils {

	private static final String strAcentos = "àáâãäåæÀÁÂÃÄÅÆéêèëÈÉÊËíìïîÍÏÌÎóõôòöÓÔÕÒÖúüùûÚÜÙÜÛçÇabcdefghijklmnñÑopqrstuvxwyýÿÝz^~'¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿§²³¹·°¸÷ø£Ø×ªº¿®¬½¼¡«»©®½¼¡©¥¤ðÐ¦ßµþÞ¯´±¾¶§¸°¨·¹³²`\"";
	private static final String strTraducao = "AAAAAAAAAAAAAAEEEEEEEEIIIIIIIIOOOOOOOOOOUUUUUUUUUCCABCDEFGHIJKLMNNNOPQRSTUVXWYYYYZ---------------------------------------------------------------------------------------";

	public static String defaultNullString(String str) {
		if (!defaultString(str).trim().equals("")) {
			return str;
		}
		return null;
	}

	public static String retiraAcentosEMinusculas(String param) {
		return retiraAcentosEMinusculas(param, true);
	}

	public static String retiraAcentosEMinusculas(String param, boolean retirarEspacoesFinais) {
		if (param == null) {
			return null;
		}	
		
		String param2 = param.trim();

		if (retirarEspacoesFinais) {
			param2 = param.trim();
		} else {
			param2 = param;
		}
		
		int intPosic, intCarac;
		StringBuilder strTempLog = new StringBuilder(param.length());

		for (int i = 0; i < param2.length(); i++) {
			intCarac = param2.charAt(i);
			intPosic = strAcentos.indexOf(intCarac);
			if (intPosic > -1) {
				strTempLog.append(strTraducao.charAt(intPosic));
			} else {
				strTempLog.append(param2.charAt(i));
			}
		}
		
		return (strTempLog.toString());
	}

	public static String[] parseStr(int[][] posicoes, String str) {
		String[] strArray = new String[posicoes.length];
		
		for (int i = 0; i < posicoes.length; i++) {
			if (posicoes[i].length == 1) {
				strArray[i] = vazioParaNull(str.substring(posicoes[i][0] - 1, posicoes[i][0]).trim());
			} else {
				strArray[i] = vazioParaNull(str.substring(posicoes[i][0] - 1, posicoes[i][1]).trim());
			}

		}
		return strArray;
	}

	private static String vazioParaNull(String str) {
		if (str.length() == 0) {
			return null;
		} else {
			return str;
		}
	}

	public static List<?> geraCombinacoes(char[] letras) {
		return geraCombinacoes(letras, 0, letras.length);
	}

	private static List<String> geraCombinacoes(char[] letras, int posicao, int tamanho) {
		List<String> combinacoes = new ArrayList<String>();
		
		if (posicao == tamanho - 1) {
			StringBuilder combinacao = new StringBuilder();
			for (int j = 0; j < tamanho; j++) {
				combinacao.append(letras[j]);
			}
			combinacoes.add(combinacao.toString());
		} else {
			combinacoes.addAll(geraCombinacoes(letras, posicao + 1, tamanho));
			int i = posicao + 1;
			while (i < tamanho) {
				char aux = letras[posicao]; // troca
				letras[posicao] = letras[i];
				letras[i] = aux;
				combinacoes.addAll(geraCombinacoes(letras, posicao + 1, tamanho));
				aux = letras[posicao]; // restaura
				letras[posicao] = letras[i];
				letras[i] = aux;
				i++;
			}
		}
		return combinacoes;
	}

	public static String convertLineBreaksToHtml(String value) {
		if (value == null) {
			return null;
		}
		return value.replaceAll("\\r\\n", "<br>");
	}

	public static String convertLineBreaksFromHtml(String value) {
		if (value == null) {
			return null;
		}
		return value.replaceAll("<br>", "\\r\\n");
	}

	public static boolean isSequence(String value) {
		boolean isSequence = true;
		char baseChar = value.charAt(0);

		for (int i = 1; i < value.length(); i++) {
			baseChar++;
			char[] newBaseChar = { baseChar };
			String stringChar = new String(newBaseChar);
			if (!stringChar.equalsIgnoreCase(value.substring(i, i + 1))) {
				isSequence = false;
				break;
			}
		}
		
		return isSequence;
	}

	public static String capitalizeAllWords(String string) {
		if (StringUtil.isBlank(string)) {
			return string;
		}
		return WordUtils.capitalize(string.toLowerCase(), null);
	}

	public static String truncar(String value, int maxLength) {
		return !StringUtil.isBlank(value) && value.length() > maxLength ? value.substring(0, maxLength) : value;
	}

	public static <T> T retiraAcentosEMinusculas(T target, String propertyNames, boolean usingBlackList, boolean retirarEspacosFinais) {
		try {
			boolean usingWhiteList = !usingBlackList;
			Field[] fields = target.getClass().getDeclaredFields();

			List<String> propertyNamesList = null;

			if (propertyNames != null && !propertyNames.equals("")) {
				String[] arrayProperties = propertyNames.split(";");
				propertyNamesList = Arrays.asList(arrayProperties);
			}

			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getType().equals(String.class) && field.get(target) != null) {
					// se a propriedade estiver na black list
					if (propertyNamesList != null && usingBlackList
							&& propertyNamesList.contains(field.getName())) {
						continue;
					}
					// se a propriedade não estiver white list
					if (propertyNamesList != null && usingWhiteList && !propertyNamesList.contains(field.getName())) {
						continue;
					}

					String value = (String) field.get(target);
					field.set(target, StringUtil.retiraAcentosEMinusculas(value, retirarEspacosFinais));

				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		return target;
	}

	public static Character stringToChar(String value) {
		Character result = null;
		if (value != null) {
			result = value.charAt(0);
		}
		return result;
	}

	public static char convertStringToChar(String value) {
		Character c = stringToChar(value);
		if (c != null) {
			return c.charValue();
		} else {
			return '0';
		}

	}

	public static String CharToString(Character value) {
		if (value != null) {
			return value.toString();
		} else {
			return "";
		}	
	}

}