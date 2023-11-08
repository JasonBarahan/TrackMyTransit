package interface_adapter.search;
import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SearchViewModel extends ViewModel{
    private static SearchState state = new SearchState();
    public static final String TITLE_LABEL = "Search View";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String STATION_LABEL = "Choose Station";

    public SearchViewModel() {super("Search");}

    public void setState(SearchState state) {
        this.state = state;
    }

    public static SearchState getState() {
        return state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
