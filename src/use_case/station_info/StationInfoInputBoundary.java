package use_case.station_info;

import java.text.ParseException;

public interface StationInfoInputBoundary {
    void execute(StationInfoInputData stationInfoInputData) throws ParseException;
}
