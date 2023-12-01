package use_case.coodrinatesFunctions;

import use_case.estimated_time.CoordinatesFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class CoordinatesFunctionTest {

    @org.junit.Test
    public void testHaversineFunction() {
        double unionStationGOLat = 43.6454; // N
        double unionStationGOLong = 79.3798; // W
        double danforthStationGOLat = 43.6864; // N
        double danforthStationGOLong = 79.3003; // W

        double haversineFunction = 7.853; // according to movable-type.co.uk
    }

    @org.junit.Test
    public void testTimeFunction() {

    }
}
