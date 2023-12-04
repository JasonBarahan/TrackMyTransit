package use_case.station_general_info;

import java.text.ParseException;

public interface StationGeneralInfoInputBoundary {
    void execute(StationGeneralInfoInputData searchInputData) throws ParseException;
}
