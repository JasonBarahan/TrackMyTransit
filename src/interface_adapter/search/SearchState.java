package interface_adapter.search;

import entity.Station;

public class SearchState {
    private String stationName = "";
    private Station station;

    public SearchState(SearchState copy) {
        stationName = copy.stationName;
        station = copy.station;
    }

    public SearchState() {

    }

    public String getStationName() {return stationName;}
    public Station getStation() {return station;}

    public void setStation(Station station) {
        this.station = station;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
