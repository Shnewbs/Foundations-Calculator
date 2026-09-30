package com.foundations.guide.api;
import java.util.*;
import java.math.BigDecimal;

/** Strict bounded JSON reader used by the contract. Duplicate keys and trailing data are errors. */
public final class GuideJson {
    public static Object parse(String json){return new Parser(json).parse();}
    private static final class Parser {
        final String input;int at,nodes;
        Parser(String input){if(input==null||input.length()>98304)throw new IllegalArgumentException("Guide JSON over 96 Ki characters");this.input=input;}
        IllegalArgumentException error(String why){return new IllegalArgumentException(why+" at character "+at);}
        void space(){while(at<input.length()&&" \r\n\t".indexOf(input.charAt(at))>=0)at++;}
        boolean take(char c){space();if(at<input.length()&&input.charAt(at)==c){at++;return true;}return false;}
        void need(char c){if(!take(c))throw error("Expected "+c);}
        Object parse(){Object v=value(0);space();if(at!=input.length())throw error("Trailing JSON");return v;}
        Object value(int depth){
            space();if(depth>24||++nodes>16384||at>=input.length())throw error("JSON limits or missing value");
            char c=input.charAt(at);
            if(c=='"')return string();
            if(c=='{'){at++;Map<String,Object> m=new LinkedHashMap<>();if(take('}'))return m;do{space();if(at>=input.length()||input.charAt(at)!='"')throw error("Expected object key");String k=string();if(m.containsKey(k))throw error("Duplicate key "+k);need(':');m.put(k,value(depth+1));}while(take(','));need('}');return m;}
            if(c=='['){at++;List<Object> a=new ArrayList<>();if(take(']'))return a;do{a.add(value(depth+1));}while(take(','));need(']');return a;}
            if(input.startsWith("true",at)){at+=4;return true;}if(input.startsWith("false",at)){at+=5;return false;}if(input.startsWith("null",at)){at+=4;return null;}
            int start=at;if(c=='-')at++;
            if(at>=input.length())throw error("Invalid number");
            if(input.charAt(at)=='0')at++;else{if(input.charAt(at)<'1'||input.charAt(at)>'9')throw error("Invalid value");while(at<input.length()&&Character.isDigit(input.charAt(at)))at++;}
            if(at<input.length()&&input.charAt(at)=='.'){at++;int digits=at;while(at<input.length()&&Character.isDigit(input.charAt(at)))at++;if(at==digits)throw error("Missing fraction");}
            if(at<input.length()&&(input.charAt(at)=='e'||input.charAt(at)=='E')){at++;if(at<input.length()&&(input.charAt(at)=='+'||input.charAt(at)=='-'))at++;int digits=at;while(at<input.length()&&Character.isDigit(input.charAt(at)))at++;if(at==digits)throw error("Missing exponent");}
            if(at-start>64)throw error("Number too large");try{return new BigDecimal(input.substring(start,at));}catch(NumberFormatException e){throw error("Invalid number");}
        }
        String string(){
            at++;StringBuilder s=new StringBuilder();boolean ended=false;
            while(at<input.length()){
                char c=input.charAt(at++);if(c=='"'){ended=true;break;}if(c<32)throw error("Control in string");
                if(c=='\\'){
                    if(at>=input.length())throw error("Missing escape");c=input.charAt(at++);
                    switch(c){case '"','\\','/'->s.append(c);case 'b'->s.append('\b');case 'f'->s.append('\f');case 'n'->s.append('\n');case 'r'->s.append('\r');case 't'->s.append('\t');case 'u'->{if(at+4>input.length())throw error("Short unicode escape");try{s.append((char)Integer.parseInt(input.substring(at,at+4),16));}catch(NumberFormatException e){throw error("Invalid unicode escape");}at+=4;}default->throw error("Unknown escape");}
                }else s.append(c);
                if(s.length()>16384)throw error("String too long");
            }
            if(!ended)throw error("Unterminated string");
            for(int i=0;i<s.length();i++){char c=s.charAt(i);if(Character.isHighSurrogate(c)){if(++i>=s.length()||!Character.isLowSurrogate(s.charAt(i)))throw error("Unpaired surrogate");}else if(Character.isLowSurrogate(c))throw error("Unpaired surrogate");}
            return s.toString();
        }
    }
    private GuideJson(){}
}
