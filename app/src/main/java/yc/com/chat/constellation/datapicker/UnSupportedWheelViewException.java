package yc.com.chat.constellation.datapicker;

public class UnSupportedWheelViewException extends Exception {
    private static final long serialVersionUID = 1894662683963152958L;
    String mistake;

    public UnSupportedWheelViewException() {
        this.mistake = "Only support List, Map,String Array,Cursor,SparseArray,SparseBooleanArray,SparseIntArray,Vector, and basic data type";
    }

    public UnSupportedWheelViewException(String err) {
        super(err);
        this.mistake = err;
    }

    public String getError() {
        return this.mistake;
    }
}