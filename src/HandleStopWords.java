import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandleStopWords {

	static File f = null;

	public static List<String> readStopWords(String stopWordsFilename)
			throws HandleUserException {
		List<String> stopWordsList = new ArrayList<String>();
		f = new File(stopWordsFilename);
		if (!f.exists()) {
			throw new HandleUserException("No Stopword file is found");
		}
		Scanner stopWordsFile;
		try {
			stopWordsFile = new Scanner(f);
		} catch (FileNotFoundException e) {
			throw new HandleUserException("No Stopword file is found");
		}
		while (stopWordsFile.hasNext())
			stopWordsList.add(stopWordsFile.next());
		stopWordsFile.close();
		return stopWordsList;
	}

	public static List< String> removeStopWords(
			String[] docWords, String id)
			throws HandleUserException
	{
		List<String> docWordList = new ArrayList<String>();
		List<String> stopWordList = readStopWords("../src/Stopword");
		for (String temp : docWords) {
			if (!stopWordList.contains(temp)) {
				docWordList.add(temp);
			}
		}
		return docWordList;
	}

}
