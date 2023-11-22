package use_case.visualize;

import entity.Train;

public class VisualizeOutputData {
    private final Train[] trains;

    /*
        There's no changes to the data passed into this use case. Why bother with input/output data objects?

        In case the implementation of the map visualization changes, particularly with regard to the data required,
        we can implement these changes in their own use case files.

        Single Responsibility Principle
     */

    /**
     * Constructor.
     *
     * @param trains: an array of Train objects
     */
    public VisualizeOutputData(Train[] trains) {
        this.trains = trains;
    }

    /**
     * Getter for the array of train objects.
     *
     * @return the train array Train[]
     */
    public Train[] getTrains() {
        return trains;
    }
}
