package interface_adapter.search;

import entity.Station;

public class SearchState {
    private String stationName = "";
    private Station station;
    private String stationError = null;


    public SearchState(SearchState copy) {
        stationName = copy.stationName;
        station = copy.station;
        stationError = copy.stationError;
    }

    public SearchState() {}

    public String getStationName() {return stationName;}
    public Station getStation() {return station;}

    public String getStationError() {return stationError;}

    public void setStation(Station station) {this.station = station;}

    public void setStationName(String stationName) {this.stationName = stationName;}

    public void setStationError(String stationError) {this.stationError = stationError;}


}
