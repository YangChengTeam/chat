package yc.com.chat.constellation.datapicker;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

public class WheelGeneralAdapter extends AbstractWheelAdapter {
    private Context context;
    private Object data;
    private DataType dataType = DataType.OTHERS;
    private GenWheelView generator;

    public enum DataType {
        ARRAYLIST,
        LINKEDHASHMAP,
        CURSOR,
        OBJECT_ARRAY,
        SPARSE_ARRAY,
        SPARSE_BOOLEAN_ARRAY,
        SPARSE_INT_ARRAY,
        VECTOR,
        LINKEDLIST,
        OTHERS
    }

    public WheelGeneralAdapter(Context context, GenWheelView generator) {
        this.generator = generator;
        this.context = context;
    }

    public int getCountWithoutHeader() {
        int i = 0;
        switch (this.dataType) {
            case ARRAYLIST:
                return ((ArrayList) this.data).size();
            case LINKEDHASHMAP:

                LinkedHashMap<Object, Object> result = ((LinkedHashMap) this.data);
                for (Entry<Object, Object> entry : result.entrySet()) {
                    if (entry.getValue() instanceof List) {
                        i += ((List) entry.getValue()).size();
                    }
                }
                return i;
            case CURSOR:
                return ((Cursor) this.data).getCount();
            case OBJECT_ARRAY:
                return ((Object[]) this.data).length;
            case SPARSE_ARRAY:
                return ((SparseArray) this.data).size();
            case SPARSE_BOOLEAN_ARRAY:
                return ((SparseBooleanArray) this.data).size();
            case SPARSE_INT_ARRAY:
                return ((SparseIntArray) this.data).size();
            case VECTOR:
                return ((Vector) this.data).size();
            case LINKEDLIST:
                return ((LinkedList) this.data).size();
            default:
                return 0;
        }
    }

    public Object getData() {
        return this.data;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public View getItem(int position, View convertView, ViewGroup parent) {
        return this.generator.setup(this.context, position, convertView, parent, this.data);
    }

    public int getItemsCount() {
        switch (this.dataType) {
            case ARRAYLIST:
                return ((ArrayList) this.data).size();
            case LINKEDHASHMAP:
                int i = 0;
                LinkedHashMap<Object, Object> result = ((LinkedHashMap) this.data);

                for (Entry entry : result.entrySet()) {
                    if (entry.getValue() instanceof List) {
                        i = (i + 1) + ((List) entry.getValue()).size();
                    }
                }
                return i;
            case CURSOR:
                return ((Cursor) this.data).getCount();
            case OBJECT_ARRAY:
                return ((Object[]) this.data).length;
            case SPARSE_ARRAY:
                return ((SparseArray) this.data).size();
            case SPARSE_BOOLEAN_ARRAY:
                return ((SparseBooleanArray) this.data).size();
            case SPARSE_INT_ARRAY:
                return ((SparseIntArray) this.data).size();
            case VECTOR:
                return ((Vector) this.data).size();
            case LINKEDLIST:
                return ((LinkedList) this.data).size();
            default:
                return 0;
        }
    }

    public void setData(SparseArray<?> sparseArray) {
        this.dataType = DataType.SPARSE_ARRAY;
        this.data = sparseArray;
    }

    public void setData(Object object) throws UnSupportedWheelViewException {
        if (object instanceof ArrayList) {
            this.dataType = DataType.ARRAYLIST;
        } else if (object instanceof LinkedHashMap) {
            this.dataType = DataType.LINKEDHASHMAP;
        } else if (object instanceof Cursor) {
            this.dataType = DataType.CURSOR;
        } else if (object instanceof Object[]) {
            this.dataType = DataType.OBJECT_ARRAY;
        } else if (object instanceof SparseArray) {
            this.dataType = DataType.SPARSE_ARRAY;
        } else if (object instanceof SparseBooleanArray) {
            this.dataType = DataType.SPARSE_BOOLEAN_ARRAY;
        } else if (object instanceof SparseIntArray) {
            this.dataType = DataType.SPARSE_INT_ARRAY;
        } else if (object instanceof Vector) {
            this.dataType = DataType.VECTOR;
        } else if (object instanceof LinkedList) {
            this.dataType = DataType.LINKEDLIST;
        } else {
            throw new UnSupportedWheelViewException();
        }
        this.data = object;
    }

    public void setData(ArrayList<?> arrayList) {
        this.dataType = DataType.ARRAYLIST;
        this.data = arrayList;
    }

    public void setData(LinkedList<?> linkedList) {
        this.dataType = DataType.LINKEDLIST;
        this.data = linkedList;
    }

    public void setData(Vector<?> vector) {
        this.dataType = DataType.VECTOR;
        this.data = vector;
    }

    public void setData(Object[] object) {
        this.dataType = DataType.OBJECT_ARRAY;
        this.data = object;
    }
}