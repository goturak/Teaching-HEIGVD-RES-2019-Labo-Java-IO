package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    str=str.toUpperCase();
    super.write(str,off,len);
  //  throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i= 0; i<cbuf.length;i++) {
        cbuf[i]=Character.toUpperCase(cbuf[i]);
    }
   super.write(cbuf,off,len);
  }

  @Override
  public void write(int c) throws IOException {
    c=Character.toUpperCase(c);
    super.write(c);
  }

}
