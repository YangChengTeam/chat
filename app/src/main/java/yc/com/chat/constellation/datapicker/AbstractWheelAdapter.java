package yc.com.chat.constellation.datapicker;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractWheelAdapter implements WheelViewAdapter {
    private List<DataSetObserver> datasetObservers;

    public View getEmptyItem(View convertView, ViewGroup parent) {
        return null;
    }

    protected void notifyDataChangedEvent() {
        if (this.datasetObservers != null) {
            for (DataSetObserver onChanged : this.datasetObservers) {
                onChanged.onChanged();
            }
        }
    }

    protected void notifyDataInvalidatedEvent() {
        if (this.datasetObservers != null) {
            for (DataSetObserver onInvalidated : this.datasetObservers) {
                onInvalidated.onInvalidated();
            }
        }
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        if (this.datasetObservers == null) {
            this.datasetObservers = new LinkedList();
        }
        this.datasetObservers.add(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (this.datasetObservers != null) {
            this.datasetObservers.remove(observer);
        }
    }
}