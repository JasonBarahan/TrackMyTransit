package use_case.station_general_info;

import java.text.ParseException;

public interface StationGeneralInfoInputBoundary {
    /**
     * Purpose: Contains the "blueprint" for the execute method used by the interactor class
     * */
    void execute(StationGeneralInfoInputData searchInputData) throws ParseException;
}
