package com.seekon.mars.sqlscript.parser;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;

public class SQLTokenStream extends CommonTokenStream {

  public SQLTokenStream() {
    super();
  }

  public SQLTokenStream(TokenSource tokenSource, int channel) {
    super(tokenSource, channel);
  }

  public SQLTokenStream(TokenSource tokenSource) {
    super(tokenSource);
  }

  @Override
  protected void fetch(int n) {
    for (int i = 1; i <= n; ++i) {
      Token t = this.tokenSource.nextToken();
      t.setTokenIndex(this.tokens.size());

      this.tokens.add(new SQLToken((CommonToken) t));
      if (t.getType() == -1)
        return;
    }
  }
}
