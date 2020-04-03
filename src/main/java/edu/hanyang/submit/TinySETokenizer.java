package edu.hanyang.submit;


import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.tartarus.snowball.ext.PorterStemmer;
import edu.hanyang.indexer.Tokenizer;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class TinySETokenizer implements Tokenizer {
	//public Analyzer analyzer = null;	
	SimpleAnalyzer analyzer = null;
	PorterStemmer stemmer = null;
	
	public void setup() {
		//analyzer = new SimpleAnalyzer();
		//==============================
		
		//루신 아날라이저, 스태머 라이브러리 초기화	
		analyzer = new SimpleAnalyzer();
		stemmer = new PorterStemmer();	
	}

	public List<String> split(String text) { 
		/*
		List result = new ArrayList<>();
		try {
			setup();
			TokenStream stream  = analyzer.tokenStream(null, new StringReader(text));
			stream.reset();
		    while (stream.incrementToken()) {
		        result.add(stream.getAttribute(CharTermAttribute.class).toString());
		    }
		} catch (IOException e) {
		    // not thrown b/c we're using a string reader...
		    throw new RuntimeException(e);
		}
		return result;		
		*/
		//======================================
		//초기화해둔 스태머를 이용해 각각을 토크나이징하고 스태밍을 해서 스태밍이 끝난
		//텀들의 리스트를 반환 
		List result = new ArrayList<>();
		
		try {
			this.setup();
			TokenStream stream = analyzer.tokenStream(null, new StringReader(text));
			stream.reset();
			CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
			
			while(stream.incrementToken()) {
				stemmer.setCurrent(term.toString());
				stemmer.stem();
				result.add(stemmer.getCurrent());
			}
			stream.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		analyzer.close();
		return result;
		
	}

	public void clean() {
		//analyzer = null;
		
		//close 하는 코드 
		analyzer = null;
		stemmer = null;
	}

}