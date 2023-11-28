package use_case.visualize;

import entity.Train;

import java.util.List;

public class VisualizeOutputData {
    private final List<List<String>> data;

    /*
        There's no changes to the data passed into this use case. Why bother with input/output data objects?

        In case the implementation of the map visualization changes, particularly with regard to the data required,
        we can implement these changes in their own use case files.

        Single Responsibility Principle
     */

    /**
     * Constructor.
     *
     * @param data: an array of Train objects
     */
    public VisualizeOutputData(List<List<String>> data) {
        this.data = data;
    }

    /**
     * Getter for the array of train objects.
     *
     * @return the train array Train[]
     */
    public List<List<String>> getData() {
        return data;
    }
}
