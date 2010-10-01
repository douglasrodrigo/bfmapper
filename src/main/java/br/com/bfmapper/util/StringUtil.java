package br.com.bfmapper.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/** 
 * <strong>Descrição</strong>
 * <br>
 * Classe com funções úteis ao tratamento de strings.<br>
 * Esta classe estende a classe utilitária do Apache Common Utils (StringUtils)
 * <br>
 * <strong>Histórico de Versões</strong>
 * <br>
 * 1.0 - Criação da classe
 * 
 * @version 1.0 20/07/2004 lpinho
 * @author lpinho
 */
public class StringUtil extends StringUtils {

    private static final String strAcentos  = "àáâãäåæÀÁÂÃÄÅÆéêèëÈÉÊËíìïîÍÏÌÎóõôòöÓÔÕÒÖúüùûÚÜÙÜÛçÇabcdefghijklmnñÑopqrstuvxwyýÿÝz^~'¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿§²³¹·°¸÷ø£Ø×ªº¿®¬½¼¡«»©®½¼¡©¥¤ðÐ¦ßµþÞ¯´±¾¶§¸°¨·¹³²`\"";
    private static final String strTraducao = "AAAAAAAAAAAAAAEEEEEEEEIIIIIIIIOOOOOOOOOOUUUUUUUUUCCABCDEFGHIJKLMNNNOPQRSTUVXWYYYYZ---------------------------------------------------------------------------------------";
  /**
   * Retorna <code>null</code> caso a string seja nula ou com conteúdo vazio ("")
   * 
   * @param str string a ser analizada
   * @return string null ou seu próprio valor
   * @since 1.0
   */
  public static String defaultNullString(String str) {
    if (!defaultString(str).trim().equals("")) {
      return str;
    }
    return null;
  }
  
  /** Formata uma string retirando os acentos e tornando tudo maiusculas.
   * @param param String a ser formatada.
   * @return A String formatada ou null se param for nulo
   */ 
  public static String retiraAcentosEMinusculas(String param){
      return retiraAcentosEMinusculas(param, true);
  }
  public static String retiraAcentosEMinusculas(String param, boolean retirarEspacoesFinais){
      if(param==null)
          return null;
      String param2 = param.trim();
      
      if(retirarEspacoesFinais) {
          param2 = param.trim();
      } else {
          param2 = param;
      }
      int intPosic, intCarac;
      StringBuilder strTempLog = new StringBuilder(param.length());
      
      for (int i=0; i < param2.length(); i++){
          intCarac = param2.charAt(i);
          intPosic  = strAcentos.indexOf(intCarac);
          if (intPosic > -1){
              strTempLog.append(strTraducao.charAt(intPosic));
          } else {
              strTempLog.append(param2.charAt(i));
          }
      }
      return (strTempLog.toString());
  }
  
  /**
   * 'Quebra' a string de acordo com as posições indicadas.
   * Ex:
   * <code>
   * String str = "string de teste";
   * int[][] posicoes = { {1,3},
   *                                            {4},
   *                                            {3,7}}
   * String[] resultado = StringUtil.parseStr(posicoes, str);
   * </code>
   * No array de string retornado teremos :
   * 1-"str"
   * 2-"i"
   * 3-"ring"
   * A função utiliza String.trim() e cada substring do resultado
   * 
   * @param posicoes posicoes a serem pegas 
   * @param str string para fazer o parser
   * @return array com as substrings
   */
  public static String[] parseStr(int[][] posicoes, String str){
      String[] strArray = new String[posicoes.length];
      for (int i = 0; i < posicoes.length; i++) {
          if(posicoes[i].length == 1) {
              strArray[i] = vazioParaNull(str.substring(posicoes[i][0]-1, posicoes[i][0]).trim());
          } else {
              strArray[i] = vazioParaNull(str.substring(posicoes[i][0]-1, posicoes[i][1]).trim());
          }
          
      }
      return strArray;
  }


  private static String vazioParaNull(String str) {
      if(str.length() == 0) {
          return null;
      } else {
          return str;
      }
  }
  
  /**
   * Gera todas as combinações possíveis (análise combinatória) entre os caracteres informados
   *  
   * @param letras carcateres informados
   * @return lista de strings com todas as combinações possíveis entre os caracteres informados
   * @since 1.0
   */
  public static List<?> geraCombinacoes(char[] letras) {
    return geraCombinacoes(letras, 0, letras.length);  
  }
  
  /**
   * Gera todas as combinações possíveis (análise combinatória) entre os caracteres informados
   *  
   * @param letras carcateres informados
   * @param posicao inicio da combinacao
   * @param tamanho tamanho da série de caracteres
   * @return lista de strings com todas as combinações possíveis entre os caracteres informados
   * @since 1.0
   */
  private static List<String> geraCombinacoes(char[] letras, int posicao, int tamanho) {    
    List<String> combinacoes = new ArrayList<String>();
    if (posicao == tamanho - 1) {
      StringBuilder combinacao = new StringBuilder();
      for (int j = 0; j < tamanho; j++) {
        combinacao.append(letras[j]);
      }
      combinacoes.add(combinacao.toString());
    }
    else
    {
      combinacoes.addAll(geraCombinacoes(letras, posicao + 1, tamanho));
      int i = posicao + 1;
      while (i < tamanho)
      {
        char aux = letras[posicao];  // troca
        letras[posicao] = letras[i];
        letras[i] = aux;
        combinacoes.addAll(geraCombinacoes(letras, posicao + 1, tamanho));
        aux = letras[posicao];      // restaura
        letras[posicao] = letras[i];
        letras[i] = aux;
        i++;
      }
    }    
    return combinacoes;        
  }
  
  /**
   * Substitui todos os caracteres linebreaks nativos (\r\n) por linebreaks
   * HTML (<br>)
   * 
   * @param value é a string à ser convertida
   * @return a string já convertida
   * @throws NullPointerException se o atributo for null
   */
  public static String convertLineBreaksToHtml(String value) {
    if (value == null) {
      throw new NullPointerException();
    }
    return value.replaceAll("\\r\\n", "<br>");
  }
  
  
  /**
   * Substitui todos os caracteres linebreaks HTML (<br>) por linebreaks
   * nativos (\r\n)
   * 
   * @param value é a string à ser convertida
   * @return a string já convertida
   * @throws NullPointerException se o atributo for null
   */
  public static String convertLineBreaksFromHtml(String value) {
    if (value == null) {
      throw new NullPointerException();
    }    
    return value.replaceAll("<br>", "\\r\\n");    
  }
  
  /**
   * Verifica se uma string é uma seqüência de letras ou números. 
   * (Ex.: abcdef, 123456)
   *  
   * @param value
   * @return boolean true caso a string seja uma seqüência, senão false.
   * @since 1.0
   */
  public static boolean isSequence (String value) {
    boolean isSequence = true;
    char baseChar = value.charAt(0);
    for (int i = 1; i < value.length(); i++) {
      baseChar++;
      char[] newBaseChar = {baseChar};
      String stringChar = new String(newBaseChar);
      if (!stringChar.equalsIgnoreCase(value.substring(i,i+1))) {
        isSequence = false;
        break;
      }
    }
    return isSequence;
  }

  /**
   * Converte a primeira letra de cada palavra em uma string para maiúsculo.
   * StringUtil.capitalizeAllWords("i am fine") = "I Am Fine"
   * StringUtil.capitalizeAllWords("i aM.fine") = "I Am.fine"
   * StringUtil.capitalizeAllWords("i aM.Fine") = "I Am.fine"
   * @param string
   * @return String
   * @since 1.0
   * 
   */
  public static String capitalizeAllWords(String string) {
    if (StringUtil.isBlank(string)) {
      return string;
    }
    return WordUtils.capitalize(string.toLowerCase(),null);
  }

  
  /**
   * Força uma determinada string para ter um tamanho máximo de caracteres.
   * Se a string for nula, "" ou menor que o tamanho máximo desejado retorna a própria string.
   * StringUtil.truncar(null)      = null
   * StringUtil.truncar("abcde",3) = "abc"
   * StringUtil.truncar(" ")       = " "
   * StringUtil.truncar("abcde", 8)= "abcde"
   *
   * @param value String que se deseja truncar
   * @param maxLength int com o tamanho máximo da String
   * @return String 
   * @since 1.0
   */
  public static String truncar (String value, int maxLength) {
    return !StringUtil.isBlank(value) && value.length() > maxLength ? value.substring(0, maxLength) : value;
  }
  
  /**
   * Funciona de forma análoga aos outros métodos de mesmo nome, mas atua em todas as propriedades do tipo String de um determinado objeto. 
   * @param target O objeto que terá as propriedades do tipo String transformadas em maiúsculas.
   * @param propertyNames Nome das propriedades, separados por ";", que serão incluídas ou excluídas durante o processo de conversão. Pode ser null ou vazio.
   * @param usingBlackList Indica se as propriedades informadas em "propertyName" formam uma "white list" ou uma "black list".
   * Se true, as propriedades informadas serão excluídas da conversão. Se false apenas as propriedades informadas serão incluídas na conversão.
   * @param retirarEspacosFinais indica se os espaços no fim da palavra devem ser retirados ou não.
   */
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
                  if (propertyNamesList != null && usingBlackList && propertyNamesList.contains(field.getName())) {
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
          
      } catch (IllegalAccessException iae) {
          //TODO: lancar exception
      }
      return target;
  }
  
  public static Character stringToChar(String value) {
      Character result = null;
      if(value != null) {
          result = value.charAt(0);
      }
      return result;
  }
  
  public static char convertStringToChar(String value) {
      Character c = stringToChar(value);
      if(c != null){
          return c.charValue();
      }else{
          return '0';
      }
      
  }

  public static String CharToString(Character value) {
      if(value != null)
          return value.toString();
      else
          return "";
      
  }

}