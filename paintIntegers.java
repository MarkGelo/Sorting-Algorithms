import java.awt.*;

public class paintIntegers {
    public int val;
    public Color col;

    public paintIntegers(){
        val = 0;
        col = Color.black;
    }

    public paintIntegers(int value){
        val = value;
        col = Color.black; // default is black
    }

    public int getVal(){
        return val;
    }
    public Color getColor(){
        return col;
    }
    public void setColor(Color newColor){
        col = newColor;
    }
    public void setVal(int value){
        val = value;
    }
    public paintIntegers[] dup(paintIntegers[] orig){
        paintIntegers[] out = new paintIntegers[orig.length];
        initPaint(out);
        for(int i = 0; i < orig.length; i++){
            out[i].val = orig[i].val;
        }
        return out;
    }
    public static void initPaint(paintIntegers[] arrayNew) {
        for(int i = 0; i < arrayNew.length; i++){
            arrayNew[i] = new paintIntegers();
        }
    }

    public static paintIntegers[] deepCopy(paintIntegers[] other){
        paintIntegers[] out = new paintIntegers[other.length];
        //init array
        for(int i = 0; i < out.length; i++){
            out[i] = new paintIntegers();
        }
        //deep copy this is stupid why tf d i have to di this
        for(int i = 0; i < out.length; i++){
            out[i].val = other[i].val;
            out[i].col = other[i].col;
        }
        return out;
    }

}
