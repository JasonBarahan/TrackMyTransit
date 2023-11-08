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
        JFrame application = new JFrame("Search Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        SearchViewModel searchViewModel = new SearchViewModel();
        FileStationDataAccessObject fileStationDataAccessObject;

        fileStationDataAccessObject = new FileStationDataAccessObject("./stations.txt", new StationFactory());

        SearchPanelView searchPanelView = SearchUseCaseFactory.create(viewManagerModel, searchViewModel, fileStationDataAccessObject);
        views.add(searchPanelView, searchPanelView.viewName);

        viewManagerModel.setActiveView(searchPanelView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}
