package app;

import data_access.API.GOVehicleApiClass;
import data_access.text_file.FileStationDataAccessObject;
import data_access.API.GOStationApiClass;
import entity.StationFactory;
import entity.TrainFactory;
import interface_adapter.search.SearchViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesViewModel;
import interface_adapter.station_general_info.StationGeneralInfoViewModel;
import interface_adapter.visualize.VisualizeViewModel;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("TrackMyTransit");
        application.setPreferredSize(new Dimension(325, 600));
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // The data for the views are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        SearchViewModel searchViewModel = new SearchViewModel();
        StationGeneralInfoViewModel stationInfoViewModel = new StationGeneralInfoViewModel();
        ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel = new ShowIncomingVehiclesViewModel();
        VisualizeViewModel visualizeViewModel = new VisualizeViewModel();


        // Creating a DAO called stationDataAccessObject by reading from file revisedStopData.txt, with the creation of the object being done by StationFactory()
        // Note: This process is wrapped in a try-catch block since it is possible that the code throws out an IOException (occurs when the txt file being read does not exist)
        
      
        // Note #2: There is no argument passed in to StationFactory(), since we are creating new Stations from the text file
        FileStationDataAccessObject stationDataAccessObject;
        try {
            stationDataAccessObject = new FileStationDataAccessObject("./revisedStopData.txt", new StationFactory(),
                    new TrainFactory(), new GOStationApiClass(), new GOVehicleApiClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Creating an instance of SearchPanelView, which is linked to the Search Use case (the use case is created by class SearchUseCaseFactory)
        SearchPanelView stationPanelView = SearchStationGeneralInfoUseCaseFactory.create(viewManagerModel, searchViewModel, stationDataAccessObject, stationInfoViewModel);
        views.add(stationPanelView, stationPanelView.viewName);

        // Creating an instance of StationInfoView. Note: Although this view should have its own use case, for now, since we are NOT displaying other data besides the station name, there is no useCaseFactory for this case
        // This View is only linked to transition from the SearchPanelView (once the other use case are integrated into this view, this will NO LONGER be the case)
        StationGeneralInfoView stationInfoView = ShowIncomingVehiclesUseCaseFactory.create(viewManagerModel, stationInfoViewModel, stationDataAccessObject, showIncomingVehiclesViewModel);
        views.add(stationInfoView, stationInfoView.viewName);

        ShowIncomingVehiclesView showIncomingVehiclesView = new ShowIncomingVehiclesView(showIncomingVehiclesViewModel,
                VisualizeUseCaseFactory.create(viewManagerModel, visualizeViewModel));
        views.add(showIncomingVehiclesView, showIncomingVehiclesView.viewName);

        new MapVisualizationView(visualizeViewModel);

        // When initially booting up the application, the stationPanel is the 1st panel displayed to viewers.
        viewManagerModel.setActiveView(stationPanelView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}