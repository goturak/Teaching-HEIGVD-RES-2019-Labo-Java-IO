package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int lineNumber;
  private int charIndex;
  private char prevChar;
  private boolean newLine;
  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineNumber=1;
    charIndex=0;
    newLine=true;
    prevChar=Character.MIN_VALUE;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String result=new String();
    str= str.substring(off,off+len);
    int excessLength=0;

    if(newLine){
      result=result.concat( Integer.toString(lineNumber));
      result=result.concat("\t");
      excessLength+= (1 +Integer.toString(lineNumber).length());
      lineNumber++;
      newLine=false;
    }
    String[] lines= Utils.getNextLine(str);

    if(!lines[0].isEmpty()){
      result=result.concat(lines[0]);
      if(lines[1].isEmpty()){
        result=result.concat( Integer.toString(lineNumber));
        result=result.concat("\t");
        excessLength+= (1 +Integer.toString(lineNumber).length());
        lineNumber++;
        newLine=false;

      }
    }
    super.write(result,0,result.length());
    if(!lines[1].isEmpty()){

      if( lines[0].isEmpty()){
        newLine=false;
        super.write(lines[1],0,lines[1].length());
      }else{
        newLine=true;
        write(lines[1],0,lines[1].length());
      }

    }



  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
      String str= new String(cbuf);
      write(str,off,len);
  }

  @Override
  public void write(int c) throws IOException {
    char currentChar= (char) c;
    boolean tempNewLine= false;
    if (prevChar == '\r') {
      if(currentChar=='\n'){
        tempNewLine=true;
        newLine=false;
      }
    }
    if(currentChar=='\n'||currentChar=='\r'){
      tempNewLine=true;
    }
    if(newLine){
        String lineStr=Integer.toString(lineNumber);
        for (int i = 0; i < lineStr.length();i++) {
          super.write(lineStr.charAt(i));
        }
        super.write('\t');
        lineNumber++;
    }
    newLine=tempNewLine;
    prevChar=currentChar;
    super.write(currentChar);

  }

}
