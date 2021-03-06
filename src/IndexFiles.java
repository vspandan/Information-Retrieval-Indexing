import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class IndexFiles {
	public static void main(String[] args) {
		String fileName = ""; 
		String indexFolder="";
		File file = null;
		File conf = null;
		File postingIndexFile = null;
		Map<String,List<String>> indextokens=null;
		try {
			if (args.length == 2) {
				fileName = args[0];
				indexFolder = args[1];
				if(!fileName.substring(fileName.length()-3, fileName.length()).equals("xml"))
					throw new HandleUserException("Invalid File. Data file must be XML");					
				file = new File(fileName);
				if (!file.exists()) {
					throw new HandleUserException("No Such file exists");
				}
				String fileName2 = fileName.substring(0, fileName.length() - 4)	+ "1";
				Parser parser = new Parser();
				StemWord stemWrd = new StemWord();
				parser.HandleSpclCHar(fileName, fileName2);
				String[] docWords = null;
				List<ParsedData> list = parser.parseData(new File(fileName2));
				Map<String, String> dataMap = null;
				int pageCount = 0;
				File dir = new File(indexFolder);
				if(!dir.isDirectory()||!dir.exists())
				{
					dir.mkdirs();
				}
				postingIndexFile = new File(indexFolder+"/IndexFile");
				if (postingIndexFile.exists())
					postingIndexFile.delete();
				conf = new File("index.conf");
				if(conf.exists())
					conf.delete();
				conf.createNewFile();
				FileOutputStream fos = new FileOutputStream(conf);
				String absoluteIndexFilePath= "IndexPath:"+postingIndexFile.getAbsolutePath();
				char[] charPath =  absoluteIndexFilePath.toCharArray();
				for(int i=0;i<charPath.length;i++)
					fos.write(charPath[i]);
				fos.close(); 
				for (ParsedData pData : list) {
					pData.setDocText(TokenizeDoc.removeUrl(pData.getDocText()));
					dataMap = new HashMap<String, String>();
					docWords = TokenizeDoc.splitWords(pData.getDocText(), "DocText");
					List<String> docWordList = HandleStopWords.removeStopWords(docWords,pData.getId());
					for (String temp:docWordList) {
						if (temp.length() >= 3) {
							temp = stemWrd.stem(temp);
							if (!dataMap.containsKey(temp)) {
								dataMap.put(temp,	pData.getId());
							}
						}
					}
					indextokens=ReadWriteData.tokenizeDump(dataMap, pageCount++,indextokens);
				}
			}
			
			else
				if(args.length!=2)
					throw new HandleUserException("Invalied Number of arguments");
			ReadWriteData.writeData(indextokens,postingIndexFile);
		} catch (HandleUserException | ParserConfigurationException | SAXException | IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
