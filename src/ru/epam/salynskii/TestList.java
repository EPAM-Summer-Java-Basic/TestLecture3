package ru.epam.salynskii;

/**
 * Created by Alimantu on 05/07/16.
 */
public class TestList {
    private int size[];
    private int currPart;
    private int currElem;
//    public static final int DEFAULT_SIZE = 115249;
    public static final int MAX_SIZE = Integer.MAX_VALUE;
    private int elems[][];
//    private boolean used[][];

    public TestList(){
        this(MAX_SIZE);
    }

    private TestList(int size){
        checkSize(size);
        this.size = new int[MAX_SIZE];
        for(int i = 0; i < MAX_SIZE; i++) {
            this.size[i] = -1;
        }
        this.size[0] = size;
        this.currPart = 0;
        this.currElem = -1;
        this.elems = new int[MAX_SIZE][];
        this.elems[0] = new int[size];
//        this.used = new boolean[MAX_SIZE][];
//        this.used[0] = new boolean[size];
    }

    private void increaseCapacity() {
        if (currElem == MAX_SIZE) {
            size[++currPart] = MAX_SIZE;
            elems[currPart] = new int[MAX_SIZE];
            currElem = 0;
//            used[]
        }
    }

    //TODO
//    private void increaseCapacity(int part){
//
//    }

    public int get(long index){
        checkIndex(index);
        int part = (int) index / MAX_SIZE;
        int insideIndex = (int) index % MAX_SIZE;
        if(currPart < part
                || (currPart == part && currElem < insideIndex)){
            throw new ArrayIndexOutOfBoundsException("Wrong index value, found: " + index + ", but current last index is " + getLastIndex());
        }
        return elems[part][insideIndex];
    }

    public void add(int value){
        increaseCapacity();
        elems[currPart][currElem++] = value;
    }

    public void add(long index, int value){
        int part = (int) index / MAX_SIZE;
        int insideIndex = (int) index % MAX_SIZE;
        if (currPart < part
                || (currPart == part && currElem < insideIndex)) {
            System.err.println("Index is too big, the element would be added to the end to of the list");
            add(value);
            return;
        }
        if (currPart != part || currElem < insideIndex) {
            increaseCapacity();
            for(int i = currPart; i > part; i--){
                System.arraycopy(elems[i], 0, elems[i], 1, ((i == currPart) ? currElem : MAX_SIZE));
                elems[i][0] = elems[i - 1][MAX_SIZE];
            }
            System.arraycopy(elems[part], insideIndex, elems[part], insideIndex + 1, MAX_SIZE - insideIndex);
        }
        elems[part][insideIndex] = value;
    }

//    private boolean checkCapacity() {
//        return checkCapacity(currPart, currElem);
//    }
//
//    private boolean checkCapacity(int currPart, int currElem) {
//        return size[currPart] > currElem;
//    }

    //TODO
    public long getLastIndex(){
        return currPart * MAX_SIZE + currElem;
    }

    private void checkIndex(long index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Bad index, expected long non negative value, but found: " + index);
        } else if (index > MAX_SIZE * MAX_SIZE) {
            throw new IndexOutOfBoundsException("Value is too big! Found Value: " + index);
        }
    }

    private void checkSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Bad list size, expected positive value, but found: " + size);
        }
    }

    

}
