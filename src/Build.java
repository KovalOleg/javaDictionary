import java.io.*;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Scanner;

public class Build {

    private static Word[] dictionary = new Word[24000];
    private static int countWords = 0;
    private static int[][] matrix;
    private static String[] files = new String[10];


    public static void main(String[] args) throws IOException {
        File file1 = new File("./src/res/Going Postal.txt");
        File file2 = new File("./src/res/Interesting Times.txt");
        File file3 = new File("./src/res/Making Money.txt");
        File file4 = new File("./src/res/Monstrous Regiment.txt");
        File file5 = new File("./src/res/Moving Pictures.txt");
        File file6 = new File("./src/res/Sourcery.txt");
        File file7 = new File("./src/res/The Color of Magic.txt");
        File file8 = new File("./src/res/The Last Continent.txt");
        File file9 = new File("./src/res/The Light Fantastic.txt");
        File file10 = new File("./src/res/The Truth.txt");

        getArray(file1);
        getArray(file2);
        getArray(file3);
        getArray(file4);
        getArray(file5);
        getArray(file6);
        getArray(file7);
        getArray(file8);
        getArray(file9);
        getArray(file10);

        clearMainDictionary();

        files[0] = file1.getName();
        files[1] = file2.getName();
        files[2] = file3.getName();
        files[3] = file4.getName();
        files[4] = file5.getName();
        files[5] = file6.getName();
        files[6] = file7.getName();
        files[7] = file8.getName();
        files[8] = file9.getName();
        files[9] = file10.getName();


        FileWriter wr = new FileWriter(new File("Dictionary.txt"));
        for (int i = 0; i<dictionary.length; i++)
            wr.write(dictionary[i].toString()+"\n");
//        for (int i = 0; i < dictionary.length; i++) {
//            System.out.println(dictionary[i]);
//        }
//        System.out.println(countWords);

//        Matrix matrix = new Matrix(files,dictionary);
//        matrix.buildMatrix();
//        System.out.println(Arrays.toString(files));
//        System.out.println(matrix.toString());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        String[] commands = line.split(" ");
        String[] searchWords = new String[commands.length / 2 + 1];
        int count = 0;
        String writeOnScreen = "";
        for (String command : commands)
            if (!command.equals("AND") && !command.equals("OR")) searchWords[count++] = command;

        if (commands[1].equals("AND")) writeOnScreen = search(searchWords, "AND");
        else writeOnScreen = search(searchWords, "OR");
        System.out.println(writeOnScreen);
    }

    private static Word binarySearch(String word) {
        int index = Arrays.binarySearch(dictionary, new Word(word,"nn.txt",1));
        System.out.println(index);
        System.out.println(dictionary[index]);
        if (dictionary[index].getWord().equals(word)) return dictionary[index];
        else return null;
    }

    private static String search(String[] searchWords, String sign) {
        byte[][] matrix = new byte[files.length][searchWords.length];
        for (int i = 0; i < searchWords.length; i++) {
            Word tempWord = binarySearch(searchWords[i]);
            if (tempWord == null) return "Word not found";
            for (int j = 0; j < tempWord.getFiles().length; j++) {
                matrix[index(tempWord.getFiles()[j])][i] = 1;
            }
        }
        String[] finalFiles = new String[10];
        boolean result = true;
        int counter = 0;
        for (int i = 0; i < files.length; i++) {
            result = true;
            if (sign.equals("AND")) {
                for (int j = 0; j < searchWords.length; j++) {
                    if (matrix[i][j] == 0) {
                        result = false;
                        break;
                    }
                }
            } else {
                for (int j = 0; j < searchWords.length; j++) {
                    if (matrix[i][j] == 1) {
                        result = true;
                        break;
                    }
                }
            }
            if (result) finalFiles[counter++] = files[i];
        }
        String text = "";
        for (String finalFile : finalFiles) if (finalFile != null) text += finalFile + " ";
        return text;
    }

    private static int index(String file) {
        for (int i = 0; i < files.length; i++)
            if (file.equals(files[i])) return i;
        return -1;
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
        do {
            String word;
            if ((text.charAt(i) >= 65 && text.charAt(i) <= 90) || (text.charAt(i) >= 97 && text.charAt(i) <= 122)) {
                word = getWord(text, i).toLowerCase();
                i += word.length();
                dictionary[countWords++] = new Word(word, file.getName(), 1);
                if (dictionary.length == countWords) {
                    Word[] temp = new Word[dictionary.length * 2];
                    System.arraycopy(dictionary, 0, temp, 0, dictionary.length);
                    dictionary = temp;
                }
            } else {
                i++;
            }
        } while (i < text.length());

        if (dictionary.length == countWords) {
            Word[] temp = new Word[dictionary.length * 2];
            for (int j = 0; j < dictionary.length; j++)
                temp[i] = dictionary[i];
            dictionary = temp;
        }
    }

    private static void clearMainDictionary() {
        Word[] copyDict = new Word[countWords];
        if (copyDict.length >= 0) System.arraycopy(dictionary, 0, copyDict, 0, copyDict.length);
        dictionary = copyDict;
        Arrays.sort(dictionary);

        int currentWord = 0;
        Word[] tempDict = new Word[dictionary.length];
        int localCountWords = countWords;
//        tempDict[0] = new Word(dictionary[currentWord].word,dictionary[currentWord].files, dictionary[currentWord].repeat);

        for (int i = 0; i < countWords; i++) {

            if (!exist(tempDict, dictionary[i], currentWord)) {
                tempDict[currentWord++] = dictionary[i];
            } else {
                tempDict[currentWord - 1].setRepeat(tempDict[currentWord - 1].getRepeat() + dictionary[i].getRepeat());
                tempDict[currentWord - 1].addFile(dictionary[i].getFiles());
            }
        }

        dictionary = new Word[currentWord];
        if (currentWord >= 0) System.arraycopy(tempDict, 0, dictionary, 0, currentWord);
    }

    private static boolean exist(Word[] tempDict, Word word, int start) {
        if (start == 0)
            return false;
        else return tempDict[start - 1].getWord().equals(word.getWord());
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
