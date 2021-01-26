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

        for(int i = 0; i<files.length; i++){
            if(!contains(files[i])){
                String[] newFiles = new String[this.files.length+1];
                for(int j = 0; j<this.files.length; j++)
                    newFiles[j] = files[j];
                newFiles[files.length] = files[i];
            }
        }
    }

    private boolean contains(String file){
        for(int i = 0; i<files.length; i++){
            if(file == files[i]) return true;
        }
        return false;
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
