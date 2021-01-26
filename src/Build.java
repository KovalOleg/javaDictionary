import java.io.*;
import java.nio.Buffer;
import java.util.Arrays;

public class Build {

    private static Word[] dictionary = new Word[24000];
    private static int countWords = 0;
    public static void main(String[] args) throws IOException {
        File file1 = new File("./src/res/The Truth.txt");
        getArray(file1);
        clearMainDictionary();
        for(int i = 0; i<countWords;i++) {
            System.out.println(dictionary[i]);
            System.out.println(i);
        }
        System.out.println(countWords);
    }

    public static String readFromFile(File file) throws IOException {
        String text = "";
        if (!file.exists()) {
            System.out.println("File not found");
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            char i;
            while (reader.ready()) {
                text += reader.readLine();
//                text += "\n";
            }
            text += " ";
        }
        return text;
    }

    public static void getArray(File file) throws IOException {
        String text = readFromFile(file);
        int i = 0;
        for (; ; ) {
            String word;
            if ((text.charAt(i) >= 65 && text.charAt(i) <= 90) || (text.charAt(i) >= 97 && text.charAt(i) <= 122)) {
                word = getWord(text, i).toLowerCase();
                i += word.length();
                dictionary[countWords++] = new Word(word,file.getName(),1);
                if (dictionary.length == countWords) {
                    Word[] temp = new Word[dictionary.length * 2];
                    for (int j = 0; j < dictionary.length; j++)
                        temp[j] = dictionary[j];
                    dictionary = temp;
                }
            } else {
                i++;
            }
            if (i >= text.length()) {
                break;
            }
        }

        if (dictionary.length == countWords) {
            Word[] temp = new Word[dictionary.length * 2];
            for (int j = 0; j < dictionary.length; j++)
                temp[i] = dictionary[i];
            dictionary = temp;
        }
    }

    private static void clearMainDictionary() {
        Word[] copyDict = new Word[countWords];
        for (int i = 0; i < copyDict.length; i++)
            copyDict[i] = dictionary[i];
        dictionary = copyDict;

        Arrays.sort(dictionary);
        int currentWord = 0;
        Word[] tempDict = new Word[dictionary.length];
        int localCountWords = countWords;
//        tempDict[0] = new Word(dictionary[currentWord].word,dictionary[currentWord].files, dictionary[currentWord].repeat);
        for (int i = 1; i < localCountWords; i++) {
            if (tempDict[i] == null)
                tempDict[0] = new Word(dictionary[currentWord].getWord(), dictionary[currentWord].getFiles(), dictionary[currentWord].getRepeat());
            else {
                if (dictionary[currentWord].getWord().equals(dictionary[i].getWord())) {
                    tempDict[currentWord].setRepeat(dictionary[currentWord].getRepeat() + dictionary[i].getRepeat());
                    tempDict[currentWord].addFile(dictionary[i].files);
                    countWords--;
                } else {
                    if (currentWord < localCountWords)
                        currentWord += tempDict[currentWord].getRepeat();
                    else break;
                }
            }
            dictionary = tempDict;
        }
    }

    public static String getWord(String text, int i) {
        var word = "";
        for (; ; ) {
            if ((text.charAt(i) >= 65 && text.charAt(i) <= 90) || (text.charAt(i) >= 97 && text.charAt(i) <= 122)) {
                word += text.charAt(i);
                i++;
            } else {
                if (text.charAt(i) == 226) {
                    word += "'";
                    i++;
                } else {
                    if (text.charAt(i) == 45) {
                        word += "-";
                        i++;
                    } else {
                        return word;
                    }
                }
            }
        }
    }

}