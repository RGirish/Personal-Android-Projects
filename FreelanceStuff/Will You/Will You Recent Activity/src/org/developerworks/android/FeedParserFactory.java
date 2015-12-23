package org.developerworks.android;
public abstract class FeedParserFactory {
	static String feedUrl = "http://fbrss.com/f/85518f0417a99f19f5bf30b7e3d71b66_779Vg7MJIGKLKSa1o2nH.xml";
	
	public static FeedParser getParser(){
		return getParser(ParserType.ANDROID_SAX);
	}
	
	public static FeedParser getParser(ParserType type){
		switch (type){
			
			case ANDROID_SAX:
				return new AndroidSaxFeedParser(feedUrl);
			
			default: return null;
		}
	}
}
