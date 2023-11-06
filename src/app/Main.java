package app;

import data_access.FileStationDataAccessObject;
import entity.StationFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchViewModel;
import view.SearchPanelView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("TrackMyTransit");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // Create an instance of the searchViewModel.
        SearchViewModel searchViewModel = new SearchViewModel();

        FileStationDataAccessObject fileStationDataAccessObject;
        // TODO: this call will need to be modified once we read from the station data file
        // TODO: will there also be an exception thrown in case we can't read from the file?
        fileStationDataAccessObject = new FileStationDataAccessObject(new StationFactory());

        SearchPanelView searchView = SearchUseCaseFactory.create
                (viewManagerModel, searchViewModel, fileStationDataAccessObject);
        views.add(searchView, searchView.viewName);

        // TODO: once we have a view for station details we should place it here

        viewManagerModel.setActiveView(searchView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}