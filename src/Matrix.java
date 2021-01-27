import java.util.Arrays;

public class Matrix {
    String[] files;
    Word[] words;
    private static byte matrix[][];

    public Matrix(String[] files, Word[] words) {
        this.files = files;
        this.words = words;
        matrix = new byte[words.length][files.length];
    }

    public void buildMatrix() {
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].getFiles().length; j++) {
                String file = words[i].getFiles()[j];
                int index = index(file);
                if(index!=-1)
                    matrix[i][index] = 1;
            }
        }
    }

    public String toString(){
        String text = "";
        for(int i = 0; i<words.length; i++) {
            text += Arrays.toString(matrix[i]);
            text += "\n";
        }
        return text;
    }

    private int index(String file) {
        for (int i = 0; i < files.length; i++)
            if (file.equals(files[i])) return i;
        return -1;
    }
}