package com.hanweb.jmp.util;


import java.util.HashMap;   
  
/**
 * 将包含有html特殊字符转义字符的字符串反转译
 * 
 * @param str
 *            
 * @return str
 */
public class HtmlDecoder {   
  
  /**
   * CHARTABLE
   */
  public static final HashMap<String, Character> CHARTABLE;
  
  /**
 * decode:(这里用一句话描述这个方法的作用).
 *
 * @param s s
 * @return    设定参数 .
*/
public static String decode(String s) {
    if(s == null || "".equals(s.trim())){
	  return "";
    }
    String t;   
    Character ch;   
    int tmpPos, i;   
  
    int maxPos = s.length();   
    StringBuffer sb = new StringBuffer(maxPos);   
    int curPos = 0;   
    while (curPos < maxPos) {   
      char c = s.charAt(curPos++);   
      if (c == '&') {   
        tmpPos = curPos;   
        if (tmpPos < maxPos) {   
          char d = s.charAt(tmpPos++);   
          if (d == '#') {   
            if (tmpPos < maxPos) {   
              d = s.charAt(tmpPos++);   
              if ((d == 'x') || (d == 'X')) {   
                if (tmpPos < maxPos) {   
                  d = s.charAt(tmpPos++);   
                  if (isHexDigit(d)) {   
                    while (tmpPos < maxPos) {   
                      d = s.charAt(tmpPos++);   
                      if (!isHexDigit(d)) {   
                        if (d == ';') {   
                          t = s.substring(curPos + 2, tmpPos - 1);   
                          try {   
                            i = Integer.parseInt(t, 16);   
                            if ((i >= 0) && (i < 65536)) {   
                              c = (char) i;   
                              curPos = tmpPos;   
                            }   
                          } catch (NumberFormatException e){
                        	  e.printStackTrace();
                          }   
                        }   
                        break;   
                      }   
                    }   
                  }   
                }   
              } else if (isDigit(d)) {   
                while (tmpPos < maxPos) {   
                  d = s.charAt(tmpPos++);   
                  if (!isDigit(d)) {   
                    if (d == ';') {   
                      t = s.substring(curPos + 1, tmpPos - 1);   
                      try {   
                        i = Integer.parseInt(t);   
                        if ((i >= 0) && (i < 65536)) {   
                          c = (char) i;   
                          curPos = tmpPos;   
                        }   
                      } catch (NumberFormatException e){
                    	  e.printStackTrace();
                      }   
                    }   
                    break;   
                  }   
                }   
              }   
            }   
          } else if (isLetter(d)) {   
            while (tmpPos < maxPos) {   
              d = s.charAt(tmpPos++);   
              if (!isLetterOrDigit(d)) {   
                if (d == ';') {   
                  t = s.substring(curPos, tmpPos - 1);   
                  ch = (Character) CHARTABLE.get(t);   
                  if (ch != null) {   
                    c = ch.charValue();   
                    curPos = tmpPos;   
                  }   
                }   
                break;   
              }   
            }   
          }   
        }   
      }   
      sb.append(c);   
    }   
    return sb.toString();   
  }   
  
  /**
 * isLetterOrDigit:(这里用一句话描述这个方法的作用).
 *
 * @param c c
 * @return    设定参数 .
*/
private static boolean isLetterOrDigit(char c) {   
    return isLetter(c) || isDigit(c);   
  }   
  
  /**
 * isHexDigit:(这里用一句话描述这个方法的作用).
 *
 * @param c c
 * @return    设定参数 .
*/
private static boolean isHexDigit(char c) {   
    return isHexLetter(c) || isDigit(c);   
  }   
  
  /**
 * isLetter:(这里用一句话描述这个方法的作用).
 *
 * @param c c
 * @return    设定参数 .
*/
private static boolean isLetter(char c) {   
    return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'));   
  }   
  
  /**
 * isHexLetter:(这里用一句话描述这个方法的作用).
 *
 * @param c c
 * @return    设定参数 .
*/
private static boolean isHexLetter(char c) {   
    return ((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F'));   
  }   
  
  /**
 * isDigit:(这里用一句话描述这个方法的作用).
 *
 * @param c c
 * @return    设定参数 .
*/
private static boolean isDigit(char c) {   
    return (c >= '0') && (c <= '9');   
  }   
  
  /**
 * compact:(这里用一句话描述这个方法的作用).
 *
 * @param s s
 * @return    设定参数 .
*/
public static String compact(String s){
    int maxPos = s.length();   
    StringBuffer sb = new StringBuffer(maxPos);   
    int curPos = 0;   
    while (curPos < maxPos) {   
      char c = s.charAt(curPos++);   
      if (isWhitespace(c)) {   
        while ((curPos < maxPos) && isWhitespace(s.charAt(curPos))) {   
          curPos++;   
        }   
        c = '\u0020';   
      }   
      sb.append(c);   
    }   
    return sb.toString();   
  }   
    
  /**
	 * isWhitespace:(这里用一句话描述这个方法的作用).
	 *
	 * @param ch ch
	 * @return    设定参数 .
	*/
	public static boolean isWhitespace(char ch) {   
	    return (ch == '\u0020') || (ch == '\r') || (ch == '\n') 
	    	|| (ch == '\u0009') || (ch == '\u000c') || (ch == '\u200b');   
	  }   
  
  static {   
	  CHARTABLE = new HashMap<String, Character>();   
	  CHARTABLE.put("quot", new Character((char) 34));   
	  CHARTABLE.put("amp", new Character((char) 38));   
	  CHARTABLE.put("apos", new Character((char) 39));   
	  CHARTABLE.put("lt", new Character((char) 60));   
	  CHARTABLE.put("gt", new Character((char) 62));   
	  CHARTABLE.put("nbsp", new Character((char) 160));   
	  CHARTABLE.put("iexcl", new Character((char) 161));   
	  CHARTABLE.put("cent", new Character((char) 162));   
	  CHARTABLE.put("pound", new Character((char) 163));   
	  CHARTABLE.put("curren", new Character((char) 164));   
	  CHARTABLE.put("yen", new Character((char) 165));   
	  CHARTABLE.put("brvbar", new Character((char) 166));   
	  CHARTABLE.put("sect", new Character((char) 167));   
	  CHARTABLE.put("uml", new Character((char) 168));   
	  CHARTABLE.put("copy", new Character((char) 169));   
	  CHARTABLE.put("ordf", new Character((char) 170));   
	  CHARTABLE.put("laquo", new Character((char) 171));   
	  CHARTABLE.put("not", new Character((char) 172));   
	  CHARTABLE.put("shy", new Character((char) 173));   
	  CHARTABLE.put("reg", new Character((char) 174));   
	  CHARTABLE.put("macr", new Character((char) 175));   
	  CHARTABLE.put("deg", new Character((char) 176));   
	  CHARTABLE.put("plusmn", new Character((char) 177));   
	  CHARTABLE.put("sup2", new Character((char) 178));   
	  CHARTABLE.put("sup3", new Character((char) 179));   
	  CHARTABLE.put("acute", new Character((char) 180));   
	  CHARTABLE.put("micro", new Character((char) 181));   
	  CHARTABLE.put("para", new Character((char) 182));   
	  CHARTABLE.put("middot", new Character((char) 183));   
	  CHARTABLE.put("cedil", new Character((char) 184));   
	  CHARTABLE.put("sup1", new Character((char) 185));   
	  CHARTABLE.put("ordm", new Character((char) 186));   
	  CHARTABLE.put("raquo", new Character((char) 187));   
	  CHARTABLE.put("frac14", new Character((char) 188));   
	  CHARTABLE.put("frac12", new Character((char) 189));   
	  CHARTABLE.put("frac34", new Character((char) 190));   
	  CHARTABLE.put("iquest", new Character((char) 191));   
	  CHARTABLE.put("Agrave", new Character((char) 192));   
	  CHARTABLE.put("Aacute", new Character((char) 193));   
	  CHARTABLE.put("Acirc", new Character((char) 194));   
	  CHARTABLE.put("Atilde", new Character((char) 195));   
	  CHARTABLE.put("Auml", new Character((char) 196));   
	  CHARTABLE.put("Aring", new Character((char) 197));   
	  CHARTABLE.put("AElig", new Character((char) 198));   
	  CHARTABLE.put("Ccedil", new Character((char) 199));   
    CHARTABLE.put("Egrave", new Character((char) 200));   
    CHARTABLE.put("Eacute", new Character((char) 201));   
    CHARTABLE.put("Ecirc", new Character((char) 202));   
    CHARTABLE.put("Euml", new Character((char) 203));   
    CHARTABLE.put("Igrave", new Character((char) 204));   
    CHARTABLE.put("Iacute", new Character((char) 205));   
    CHARTABLE.put("Icirc", new Character((char) 206));   
    CHARTABLE.put("Iuml", new Character((char) 207));   
    CHARTABLE.put("ETH", new Character((char) 208));   
    CHARTABLE.put("Ntilde", new Character((char) 209));   
    CHARTABLE.put("Ograve", new Character((char) 210));   
    CHARTABLE.put("Oacute", new Character((char) 211));   
    CHARTABLE.put("Ocirc", new Character((char) 212));   
    CHARTABLE.put("Otilde", new Character((char) 213));   
    CHARTABLE.put("Ouml", new Character((char) 214));   
    CHARTABLE.put("times", new Character((char) 215));   
    CHARTABLE.put("Oslash", new Character((char) 216));   
    CHARTABLE.put("Ugrave", new Character((char) 217));   
    CHARTABLE.put("Uacute", new Character((char) 218));   
    CHARTABLE.put("Ucirc", new Character((char) 219));   
    CHARTABLE.put("Uuml", new Character((char) 220));   
    CHARTABLE.put("Yacute", new Character((char) 221));   
    CHARTABLE.put("THORN", new Character((char) 222));   
    CHARTABLE.put("szlig", new Character((char) 223));   
    CHARTABLE.put("agrave", new Character((char) 224));   
    CHARTABLE.put("aacute", new Character((char) 225));   
    CHARTABLE.put("acirc", new Character((char) 226));   
    CHARTABLE.put("atilde", new Character((char) 227));   
    CHARTABLE.put("auml", new Character((char) 228));   
    CHARTABLE.put("aring", new Character((char) 229));   
    CHARTABLE.put("aelig", new Character((char) 230));   
    CHARTABLE.put("ccedil", new Character((char) 231));   
    CHARTABLE.put("egrave", new Character((char) 232));   
    CHARTABLE.put("eacute", new Character((char) 233));   
    CHARTABLE.put("ecirc", new Character((char) 234));   
    CHARTABLE.put("euml", new Character((char) 235));   
    CHARTABLE.put("igrave", new Character((char) 236));   
    CHARTABLE.put("iacute", new Character((char) 237));   
    CHARTABLE.put("icirc", new Character((char) 238));   
    CHARTABLE.put("iuml", new Character((char) 239));   
    CHARTABLE.put("eth", new Character((char) 240));   
    CHARTABLE.put("ntilde", new Character((char) 241));   
    CHARTABLE.put("ograve", new Character((char) 242));   
    CHARTABLE.put("oacute", new Character((char) 243));   
    CHARTABLE.put("ocirc", new Character((char) 244));   
    CHARTABLE.put("otilde", new Character((char) 245));   
    CHARTABLE.put("ouml", new Character((char) 246));   
    CHARTABLE.put("divide", new Character((char) 247));   
    CHARTABLE.put("oslash", new Character((char) 248));   
    CHARTABLE.put("ugrave", new Character((char) 249));   
    CHARTABLE.put("uacute", new Character((char) 250));   
    CHARTABLE.put("ucirc", new Character((char) 251));   
    CHARTABLE.put("uuml", new Character((char) 252));   
    CHARTABLE.put("yacute", new Character((char) 253));   
    CHARTABLE.put("thorn", new Character((char) 254));   
    CHARTABLE.put("yuml", new Character((char) 255));   
    CHARTABLE.put("OElig", new Character((char) 338));   
    CHARTABLE.put("oelig", new Character((char) 339));   
    CHARTABLE.put("Scaron", new Character((char) 352));   
    CHARTABLE.put("scaron", new Character((char) 353));   
    CHARTABLE.put("fnof", new Character((char) 402));   
    CHARTABLE.put("circ", new Character((char) 710));   
    CHARTABLE.put("tilde", new Character((char) 732));   
    CHARTABLE.put("Alpha", new Character((char) 913));   
    CHARTABLE.put("Beta", new Character((char) 914));   
    CHARTABLE.put("Gamma", new Character((char) 915));   
    CHARTABLE.put("Delta", new Character((char) 916));   
    CHARTABLE.put("Epsilon", new Character((char) 917));   
    CHARTABLE.put("Zeta", new Character((char) 918));   
    CHARTABLE.put("Eta", new Character((char) 919));   
    CHARTABLE.put("Theta", new Character((char) 920));   
    CHARTABLE.put("Iota", new Character((char) 921));   
    CHARTABLE.put("Kappa", new Character((char) 922));   
    CHARTABLE.put("Lambda", new Character((char) 923));   
    CHARTABLE.put("Mu", new Character((char) 924));   
    CHARTABLE.put("Nu", new Character((char) 925));   
    CHARTABLE.put("Xi", new Character((char) 926));   
    CHARTABLE.put("Omicron", new Character((char) 927));   
    CHARTABLE.put("Pi", new Character((char) 928));   
    CHARTABLE.put("Rho", new Character((char) 929));   
    CHARTABLE.put("Sigma", new Character((char) 931));   
    CHARTABLE.put("Tau", new Character((char) 932));   
    CHARTABLE.put("Upsilon", new Character((char) 933));   
    CHARTABLE.put("Phi", new Character((char) 934));   
    CHARTABLE.put("Chi", new Character((char) 935));   
    CHARTABLE.put("Psi", new Character((char) 936));   
    CHARTABLE.put("Omega", new Character((char) 937));   
    CHARTABLE.put("alpha", new Character((char) 945));   
    CHARTABLE.put("beta", new Character((char) 946));   
    CHARTABLE.put("gamma", new Character((char) 947));   
    CHARTABLE.put("delta", new Character((char) 948));   
    CHARTABLE.put("epsilon", new Character((char) 949));   
    CHARTABLE.put("zeta", new Character((char) 950));   
    CHARTABLE.put("eta", new Character((char) 951));   
    CHARTABLE.put("theta", new Character((char) 952));   
    CHARTABLE.put("iota", new Character((char) 953));   
    CHARTABLE.put("kappa", new Character((char) 954));   
    CHARTABLE.put("lambda", new Character((char) 955));   
    CHARTABLE.put("mu", new Character((char) 956));   
    CHARTABLE.put("nu", new Character((char) 957));   
    CHARTABLE.put("xi", new Character((char) 958));   
    CHARTABLE.put("omicron", new Character((char) 959));   
    CHARTABLE.put("pi", new Character((char) 960));   
    CHARTABLE.put("rho", new Character((char) 961));   
    CHARTABLE.put("sigmaf", new Character((char) 962));   
    CHARTABLE.put("sigma", new Character((char) 963));   
    CHARTABLE.put("tau", new Character((char) 964));   
    CHARTABLE.put("upsilon", new Character((char) 965));   
    CHARTABLE.put("phi", new Character((char) 966));   
    CHARTABLE.put("chi", new Character((char) 967));   
    CHARTABLE.put("psi", new Character((char) 968));   
    CHARTABLE.put("omega", new Character((char) 969));   
    CHARTABLE.put("thetasym", new Character((char) 977));   
    CHARTABLE.put("upsih", new Character((char) 978));   
    CHARTABLE.put("piv", new Character((char) 982));   
    CHARTABLE.put("ensp", new Character((char) 8194));   
    CHARTABLE.put("emsp", new Character((char) 8195));   
    CHARTABLE.put("thinsp", new Character((char) 8201));   
    CHARTABLE.put("zwnj", new Character((char) 8204));   
    CHARTABLE.put("zwj", new Character((char) 8205));   
    CHARTABLE.put("lrm", new Character((char) 8206));   
    CHARTABLE.put("rlm", new Character((char) 8207));   
    CHARTABLE.put("ndash", new Character((char) 8211));   
    CHARTABLE.put("mdash", new Character((char) 8212));   
    CHARTABLE.put("lsquo", new Character((char) 8216));   
    CHARTABLE.put("rsquo", new Character((char) 8217));   
    CHARTABLE.put("sbquo", new Character((char) 8218));   
    CHARTABLE.put("ldquo", new Character((char) 8220));   
    CHARTABLE.put("rdquo", new Character((char) 8221));   
    CHARTABLE.put("bdquo", new Character((char) 8222));   
    CHARTABLE.put("dagger", new Character((char) 8224));   
    CHARTABLE.put("Dagger", new Character((char) 8225));   
    CHARTABLE.put("bull", new Character((char) 8226));   
    CHARTABLE.put("hellip", new Character((char) 8230));   
    CHARTABLE.put("permil", new Character((char) 8240));   
    CHARTABLE.put("prime", new Character((char) 8242));   
    CHARTABLE.put("Prime", new Character((char) 8243));   
    CHARTABLE.put("lsaquo", new Character((char) 8249));   
    CHARTABLE.put("rsaquo", new Character((char) 8250));   
    CHARTABLE.put("oline", new Character((char) 8254));   
    CHARTABLE.put("frasl", new Character((char) 8260));   
    CHARTABLE.put("euro", new Character((char) 8364));   
    CHARTABLE.put("image", new Character((char) 8465));   
    CHARTABLE.put("weierp", new Character((char) 8472));   
    CHARTABLE.put("real", new Character((char) 8476));   
    CHARTABLE.put("trade", new Character((char) 8482));   
    CHARTABLE.put("alefsym", new Character((char) 8501));   
    CHARTABLE.put("larr", new Character((char) 8592));   
    CHARTABLE.put("uarr", new Character((char) 8593));   
    CHARTABLE.put("rarr", new Character((char) 8594));   
    CHARTABLE.put("darr", new Character((char) 8595));   
    CHARTABLE.put("harr", new Character((char) 8596));   
    CHARTABLE.put("crarr", new Character((char) 8629));   
    CHARTABLE.put("lArr", new Character((char) 8656));   
    CHARTABLE.put("uArr", new Character((char) 8657));   
    CHARTABLE.put("rArr", new Character((char) 8658));   
    CHARTABLE.put("dArr", new Character((char) 8659));   
    CHARTABLE.put("hArr", new Character((char) 8660));   
    CHARTABLE.put("forall", new Character((char) 8704));   
    CHARTABLE.put("part", new Character((char) 8706));   
    CHARTABLE.put("exist", new Character((char) 8707));   
    CHARTABLE.put("empty", new Character((char) 8709));   
    CHARTABLE.put("nabla", new Character((char) 8711));   
    CHARTABLE.put("isin", new Character((char) 8712));   
    CHARTABLE.put("notin", new Character((char) 8713));   
    CHARTABLE.put("ni", new Character((char) 8715));   
    CHARTABLE.put("prod", new Character((char) 8719));   
    CHARTABLE.put("sum", new Character((char) 8721));   
    CHARTABLE.put("minus", new Character((char) 8722));   
    CHARTABLE.put("lowast", new Character((char) 8727));   
    CHARTABLE.put("radic", new Character((char) 8730));   
    CHARTABLE.put("prop", new Character((char) 8733));   
    CHARTABLE.put("infin", new Character((char) 8734));   
    CHARTABLE.put("ang", new Character((char) 8736));   
    CHARTABLE.put("and", new Character((char) 8743));   
    CHARTABLE.put("or", new Character((char) 8744));   
    CHARTABLE.put("cap", new Character((char) 8745));   
    CHARTABLE.put("cup", new Character((char) 8746));   
    CHARTABLE.put("int", new Character((char) 8747));   
    CHARTABLE.put("there4", new Character((char) 8756));   
    CHARTABLE.put("sim", new Character((char) 8764));   
    CHARTABLE.put("cong", new Character((char) 8773));   
    CHARTABLE.put("asymp", new Character((char) 8776));   
    CHARTABLE.put("ne", new Character((char) 8800));   
    CHARTABLE.put("equiv", new Character((char) 8801));   
    CHARTABLE.put("le", new Character((char) 8804));   
    CHARTABLE.put("ge", new Character((char) 8805));   
    CHARTABLE.put("sub", new Character((char) 8834));   
    CHARTABLE.put("sup", new Character((char) 8835));   
    CHARTABLE.put("nsub", new Character((char) 8836));   
    CHARTABLE.put("sube", new Character((char) 8838));   
    CHARTABLE.put("supe", new Character((char) 8839));   
    CHARTABLE.put("oplus", new Character((char) 8853));   
    CHARTABLE.put("otimes", new Character((char) 8855));   
    CHARTABLE.put("perp", new Character((char) 8869));   
    CHARTABLE.put("sdot", new Character((char) 8901));   
    CHARTABLE.put("lceil", new Character((char) 8968));   
    CHARTABLE.put("rceil", new Character((char) 8969));   
    CHARTABLE.put("lfloor", new Character((char) 8970));   
    CHARTABLE.put("rfloor", new Character((char) 8971));   
    CHARTABLE.put("lang", new Character((char) 9001));   
    CHARTABLE.put("rang", new Character((char) 9002));   
    CHARTABLE.put("loz", new Character((char) 9674));   
    CHARTABLE.put("spades", new Character((char) 9824));   
    CHARTABLE.put("clubs", new Character((char) 9827));   
    CHARTABLE.put("hearts", new Character((char) 9829));   
    CHARTABLE.put("diams", new Character((char) 9830));   
  }   
} 
