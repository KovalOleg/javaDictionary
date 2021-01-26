import java.util.Arrays;

public class Word implements Comparable<Word> {
    String word;
    String[] files;
    int repeat;

    public Word(String word, String file, int repeat) {
        this.word = word;
        this.files = new String[1];
        files[0] = file;
        this.repeat = repeat;
    }

    public Word(String word, String[] files, int repeat) {
        this.word = word;
        this.files = new String[files.length];
        for (int i = 0; i < files.length; i++)
            this.files[i] = files[i];
        this.repeat = repeat;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String[] getFiles() {
        return files;
    }

    public void addFile(String[] files) {
        String[] newFiles = new String[files.length + this.files.length];
        for (int i = 0; i < this.files.length; i++)
            newFiles[i] = this.files[i];
        for (int i = this.files.length; i < this.files.length + files.length; i++)
            if (!checkUniq(this.files,files[i]))
                newFiles[i] = files[i - this.files.length];
        this.files = newFiles;
    }

    private boolean checkUniq(String[] files, String file) {
        for (int i = 0; i < files.length; i++)
            if (files[i].equals(file)) return true;
        return false;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }


    @Override
    public int compareTo(Word o) {
        return word.compareTo(o.getWord());
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", files=" + Arrays.toString(files) +
                ", repeat=" + repeat +
                '}';
    }
}
