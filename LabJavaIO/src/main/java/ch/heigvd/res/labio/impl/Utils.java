package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {

      String[] r= new String[2];

      int splitAt=-1;
      boolean rn=false;
      for(int i =0; i <lines.length();i++){
          if(splitAt==-1){
              char c = lines.charAt(i);

              if(c=='\r'){
                  splitAt=i;
                  if(i<lines.length()-1 && lines.charAt(i+1)=='\n'){
                      rn=true;
                  }
              }

              if(c=='\n'){
                    splitAt=i;
              }
          }
      }
      String firstLine=  new String();
      String secondLine;
      if(rn){
          splitAt++;
      }
      if(splitAt!=-1){
          firstLine= lines.substring(0,splitAt+1);
          secondLine= lines.substring(splitAt+1);
      }else{
          secondLine= lines;
      }
      String[] result = {firstLine,secondLine};
   return result;
  }

}
