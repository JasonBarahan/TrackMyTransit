package view;

import app.Main;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import use_case.search.SearchInputBoundary;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class SearchPanelViewTest {
    // Gets the search button
    private JButton getButton() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }

        // check if the window is found
        assertNotNull(app);
        Component root = app.getComponent(0);
        Component cp = ((JRootPane) root).getContentPane();
        JPanel jp = (JPanel) cp;
        JPanel jp2 = (JPanel) jp.getComponent(0);

        // We are checking the SearchPanelView, component number 0
        SearchPanelView spv = (SearchPanelView) jp2.getComponent(0);

        // We are checking the buttons, component number 3
        JPanel buttons = (JPanel) spv.getComponent(3);

        // Return the search button (this should be the only button on there)
        return (JButton) buttons.getComponent(0);
    }

    // sleep
    private void pause(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // generate right/left movement
    private KeyEvent generateMoveRightEvent(Component component) {

        return new KeyEvent(
                component,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_RIGHT,
                KeyEvent.CHAR_UNDEFINED
        );
    }

    private KeyEvent generateMoveLeftEvent(Component component) {

        return new KeyEvent(
                component,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_LEFT,
                KeyEvent.CHAR_UNDEFINED
        );
    }

    // key typed event
    private KeyEvent generateKeyTypedEvent(Component component, char c) {
        return new KeyEvent(
                component, // we are interacting with the searchField
                KeyEvent.KEY_TYPED, //
                System.currentTimeMillis(), // say the event happened right now
                0, // no modifiers
                KeyEvent.VK_UNDEFINED, // for KEY_TYPED, the KeyCode is undefined per documentation
                c);
    }

    // backspace event
    private KeyEvent generateBackspaceEvent(Component component) {
        return new KeyEvent(
                component,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_BACK_SPACE,
                KeyEvent.CHAR_UNDEFINED
        );
    }

    /**
     * This tests if the "Submit" button is present.
     */
    @org.junit.Test
    public void testSubmitButtonPresent() {
        Main.main(null);
        JButton button = getButton();
        assert (button.getText().equals("Submit"));
    }

    /**
     * These tests check whether the user's intended query and the query stored within the state match.
     * The multiple tests handle a variety of typo cases.
     */
    @org.junit.Test
    public void testValidQueryWithoutTypos() {
        /**
         * No typos whatsoever.
         */
       SearchInputBoundary sib = null;
       SearchViewModel searchViewModel = new SearchViewModel();

        // controller
        SearchController searchController = new SearchController(sib);
        JPanel searchPanelView = new SearchPanelView(searchViewModel, searchController);

        // make the frame visible
        JFrame jf = new JFrame();
        jf.setContentPane(searchPanelView);
        jf.pack();
        jf.setVisible(true);

        // obtain quick reference to search panel
        LabelTextPanel panel = (LabelTextPanel) searchPanelView.getComponent(1);
        JTextField searchField = (JTextField)  panel.getComponent(1);

        // write down string
        String testQuery = "Aurora GO";
        for (int i = 0; i < testQuery.length(); i++) {
            // get character from test query at current index
            char c = testQuery.charAt(i);

            // create a new key typed event and dispatch
            panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

            // pause for a bit
            pause(10);

            // move to the right in the field
            panel.dispatchEvent(generateMoveRightEvent(searchField));


            // pause execution for a bit (1/5th of a second) to account for user typing speed
            pause(200);
        }

        // assert state data matches user query test string
        System.out.println();
        System.out.println("Query: " + testQuery);
        System.out.println("Printed query: " + searchField.getText());
        System.out.println("State data: " + searchViewModel.getState().getStateStationName());
        assertEquals(testQuery, searchField.getText());
        assertEquals(testQuery, searchViewModel.getState().getStateStationName());
    }

    @org.junit.Test
    public void testValidQueryFixedTypoAtEnd() {
        /**
         * A typo occurs at the end, which is met by a backspace.
         */
        SearchInputBoundary sib = null;
        SearchViewModel searchViewModel = new SearchViewModel();

        // controller
        SearchController searchController = new SearchController(sib);
        JPanel searchPanelView = new SearchPanelView(searchViewModel, searchController);

        // make the frame visible
        JFrame jf = new JFrame();
        jf.setContentPane(searchPanelView);
        jf.pack();
        jf.setVisible(true);

        // obtain quick reference to search panel
        LabelTextPanel panel = (LabelTextPanel) searchPanelView.getComponent(1);
        JTextField searchField = (JTextField)  panel.getComponent(1);

        // write down string
        String query = "Port Credit GO";
        String[] querySegments = {query, "O"};
        for (String q : querySegments) {
            for (int i = 0; i < q.length(); i++) {
                // get character from test query at current index
                char c = q.charAt(i);

                // create a new key typed event and dispatch
                panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

                // pause for a bit
                pause(10);

                // move to the right in the field
                panel.dispatchEvent(generateMoveRightEvent(searchField));

                // pause execution for a bit (1/5th of a second) to account for user typing speed
                pause(200);

            }
        }

        // user makes typo and needs to backspace
        panel.dispatchEvent(generateBackspaceEvent(searchField));
        pause(10);

        // pause execution for a bit (1/5th of a second) to account for user typing speed
        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // assert state data matches user query test string
        System.out.println();
        System.out.println("Query: " + query);
        System.out.println("Printed query: " + searchField.getText());
        System.out.println("State data: " + searchViewModel.getState().getStateStationName());
        // TODO: searchViewModel.getState().getStateStationName() is still "Port Credit GOO"
        assertEquals(query, searchField.getText());
        assertEquals(query, searchViewModel.getState().getStateStationName());
    }

    @org.junit.Test
    public void testValidQueryFixedTypoAtEnd2() {
        /**
         * A typo occurs at the end, which is met by a backspace.
         */
        SearchInputBoundary sib = null;
        SearchViewModel searchViewModel = new SearchViewModel();

        // controller
        SearchController searchController = new SearchController(sib);
        JPanel searchPanelView = new SearchPanelView(searchViewModel, searchController);

        // make the frame visible
        JFrame jf = new JFrame();
        jf.setContentPane(searchPanelView);
        jf.pack();
        jf.setVisible(true);

        // obtain quick reference to search panel
        LabelTextPanel panel = (LabelTextPanel) searchPanelView.getComponent(1);
        JTextField searchField = (JTextField)  panel.getComponent(1);

        // write down string
        String query = "Port Credit go";
        String[] querySegments = {query, "GO"};
        for (String q : querySegments) {
            for (int i = 0; i < q.length(); i++) {
                // get character from test query at current index
                char c = q.charAt(i);

                // create a new key typed event and dispatch
                panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

                // pause for a bit
                pause(10);

                // move to the right in the field
                panel.dispatchEvent(generateMoveRightEvent(searchField));

                // pause execution for a bit (1/5th of a second) to account for user typing speed
                pause(200);

            }

            // user deletes the last 2 letters with incorrect casing
            if (q.equals(querySegments[0])) {
                panel.dispatchEvent(generateBackspaceEvent(searchField));
                pause(200);
                panel.dispatchEvent(generateBackspaceEvent(searchField));
            }
        }


        // pause execution for a bit (1/5th of a second) to account for user typing speed
        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // assert state data matches user query test string
        System.out.println();
        System.out.println("Query: " + "Port Credit GO");
        System.out.println("Printed query: " + searchField.getText());
        System.out.println("State data: " + searchViewModel.getState().getStateStationName());

        assertEquals("Port Credit GO", searchField.getText());
        assertEquals("Port Credit GO", searchViewModel.getState().getStateStationName());
    }

    @org.junit.Test
    public void testValidQueryFixedTypoInMiddle() {
        /**
         * A typo occurs in the middle, which is met by a backspace, before the rest of
         * the input is typed correctly.
         */
        SearchInputBoundary sib = null;
        SearchViewModel searchViewModel = new SearchViewModel();

        // controller
        SearchController searchController = new SearchController(sib);
        JPanel searchPanelView = new SearchPanelView(searchViewModel, searchController);

        // make the frame visible
        JFrame jf = new JFrame();
        jf.setContentPane(searchPanelView);
        jf.pack();
        jf.setVisible(true);

        // obtain quick reference to search panel
        LabelTextPanel panel = (LabelTextPanel) searchPanelView.getComponent(1);
        JTextField searchField = (JTextField)  panel.getComponent(1);

        // write down string
        String query = "Milton GO";
        String[] querySegments = {"Milta", "on GO"};
        for (String q : querySegments) {
            for (int i = 0; i < q.length(); i++) {
                // get character from test query at current index
                char c = q.charAt(i);

                // create a new key typed event and dispatch
                panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

                // pause for a bit
                pause(10);

                // move to the right in the field
                panel.dispatchEvent(generateMoveRightEvent(searchField));

                // pause execution for a bit (1/5th of a second) to account for user typing speed
                pause(200);

            }

            if (q.equals(querySegments[0])) {
                panel.dispatchEvent(generateBackspaceEvent(searchField));
            }
        }

        // pause execution for a bit (1/5th of a second) to account for user typing speed
        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // assert state data matches user query test string
        System.out.println();
        System.out.println("Query: " + query);
        System.out.println("Printed query: " + searchField.getText());
        System.out.println("State data: " + searchViewModel.getState().getStateStationName());
        assertEquals(query, searchField.getText());
        assertEquals(query, searchViewModel.getState().getStateStationName());
    }

    @org.junit.Test
    public void testValidQueryFixedTypoInMiddle2() {
        /**
         * A typo occurs in the middle, but the user does not notice it until the end.
         * The user then presses the 'left arrow' button until the caret reaches the typo, erases it,
         * then inserts the correct character without modifying anything else.
         */
        SearchInputBoundary sib = null;
        SearchViewModel searchViewModel = new SearchViewModel();

        // controller
        SearchController searchController = new SearchController(sib);
        JPanel searchPanelView = new SearchPanelView(searchViewModel, searchController);

        // make the frame visible
        JFrame jf = new JFrame();
        jf.setContentPane(searchPanelView);
        jf.pack();
        jf.setVisible(true);

        // obtain quick reference to search panel
        LabelTextPanel panel = (LabelTextPanel) searchPanelView.getComponent(1);
        JTextField searchField = (JTextField)  panel.getComponent(1);

        // write down string
        String query = "Union Station";
        String[] querySegments = {"Unian Station"};
        for (String q : querySegments) {
            for (int i = 0; i < q.length(); i++) {
                // get character from test query at current index
                char c = q.charAt(i);

                // create a new key typed event and dispatch
                panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

                // pause for a bit
                pause(20);

                // move to the right in the field
                panel.dispatchEvent(generateMoveRightEvent(searchField));

                // pause execution for a bit (1/5th of a second) to account for user typing speed
                pause(200);

            }


            for (int p = 0; p < 9; p++) {
                panel.dispatchEvent(generateMoveLeftEvent(searchField));
                pause(200);
            }
            panel.dispatchEvent(generateBackspaceEvent(searchField));
            pause(1000);
            panel.dispatchEvent(generateMoveLeftEvent(searchField));
            pause(1000);
            panel.dispatchEvent(generateKeyTypedEvent(searchField, 'o'));
            //TODO: "o" is attached to the end in searchViewModel.getState().getStateStationName()
        }

        // assert state data matches user query test string
        System.out.println();
        System.out.println("Query: " + query);
        System.out.println("Printed query: " + searchField.getText());
        System.out.println("State data: " + searchViewModel.getState().getStateStationName());
        assertEquals(query, searchField.getText());
        assertEquals(query, searchViewModel.getState().getStateStationName());
    }


    @org.junit.Test
    public void testValidQueryFixedTypoInMiddle3() {
        /**
         * A typo occurs in the middle, which is met by a backspace, before the rest of
         * the input is typed correctly.
         */
        SearchInputBoundary sib = null;
        SearchViewModel searchViewModel = new SearchViewModel();

        // controller
        SearchController searchController = new SearchController(sib);
        JPanel searchPanelView = new SearchPanelView(searchViewModel, searchController);

        // make the frame visible
        JFrame jf = new JFrame();
        jf.setContentPane(searchPanelView);
        jf.pack();
        jf.setVisible(true);

        // obtain quick reference to search panel
        LabelTextPanel panel = (LabelTextPanel) searchPanelView.getComponent(1);
        JTextField searchField = (JTextField)  panel.getComponent(1);

        // write down string
        String query = "Milton GO";
        String[] querySegments = {"Miltan", "on GO"};
        for (String q : querySegments) {
            for (int i = 0; i < q.length(); i++) {
                // get character from test query at current index
                char c = q.charAt(i);

                // create a new key typed event and dispatch
                panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

                // pause for a bit
                pause(10);

                // move to the right in the field
                panel.dispatchEvent(generateMoveRightEvent(searchField));

                // pause execution for a bit (1/5th of a second) to account for user typing speed
                pause(200);

            }

            // user removes the last two letters in "Miltan"
            if (q.equals(querySegments[0])) {
                panel.dispatchEvent(generateBackspaceEvent(searchField));
                pause(20);
                panel.dispatchEvent(generateBackspaceEvent(searchField));
            }
        }

        // pause execution for a bit (1/5th of a second) to account for user typing speed
        try {
            sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // assert state data matches user query test string
        System.out.println();
        System.out.println("Query: " + query);
        System.out.println("Printed query: " + searchField.getText());
        System.out.println("State data: " + searchViewModel.getState().getStateStationName());
        assertEquals(query, searchField.getText());
        assertEquals(query, searchViewModel.getState().getStateStationName());
    }

    @org.junit.Test
    public void testValidQueryFixedCasingAtStart() {
        /**
         * A casing typo occurs at the start, but the user does not notice it until the end.
         * The user then presses the 'left arrow' button until the caret reaches the typo, erases it,
         * then inserts the correct character without modifying anything else.
         */
        SearchInputBoundary sib = null;
        SearchViewModel searchViewModel = new SearchViewModel();

        // controller
        SearchController searchController = new SearchController(sib);
        JPanel searchPanelView = new SearchPanelView(searchViewModel, searchController);

        // make the frame visible
        JFrame jf = new JFrame();
        jf.setContentPane(searchPanelView);
        jf.pack();
        jf.setVisible(true);

        // obtain quick reference to search panel
        LabelTextPanel panel = (LabelTextPanel) searchPanelView.getComponent(1);
        JTextField searchField = (JTextField)  panel.getComponent(1);

        // write down string
        String query = "Union Station";
        String[] querySegments = {"union Station"};
        for (String q : querySegments) {
            for (int i = 0; i < q.length(); i++) {
                // get character from test query at current index
                char c = q.charAt(i);

                // create a new key typed event and dispatch
                panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

                // pause for a bit
                pause(20);

                // move to the right in the field
                panel.dispatchEvent(generateMoveRightEvent(searchField));

                // pause execution for a bit (1/5th of a second) to account for user typing speed
                pause(20);

            }

            // move cursor to the correct typo caret
            for (int p = 0; p < 12; p++) {
                panel.dispatchEvent(generateMoveLeftEvent(searchField));
                pause(200);
            }

            // user deletes the typo and move cursor to the correct position
            panel.dispatchEvent(generateBackspaceEvent(searchField));
            pause(200);
            panel.dispatchEvent(generateMoveLeftEvent(searchField));
            pause(1000);
            // user fixes the typo
            panel.dispatchEvent(generateKeyTypedEvent(searchField, 'U'));
            pause(1000);

        }

        // assert state data matches user query test string
        System.out.println();
        System.out.println("Query: " + query);
        System.out.println("Printed query: " + searchField.getText());
        System.out.println("State data: " + searchViewModel.getState().getStateStationName());
        assertEquals(query, searchField.getText());
        assertEquals(query, searchViewModel.getState().getStateStationName());
        // TODO: the corrected typo "U" is attached to the end in searchViewModel.getState().getStateStationName()
        //  instead of the start
    }

    /**
     * These tests check whether, given a valid query is passed into the view, assuming the query is correctly stored in
     * the state, the view returns the associated station details.
     */
    @org.junit.Test
    public void testValidQueryExecution()
    {
        /**
         * Checks whether a query, returns the expected results within the StationInfoView.
         */
        // TODO: Implement me
    }

    /**
     * These tests check whether, given an invalid query is passed into the view, assuming the query is correctly stored
     * in the state, the view returns a popup with an error message stating that the station does not exist.
     */
    @org.junit.Test
    public void testInvalidQueryExecution()
    {
        /**
         * Checks whether an invalid query yields an error popup.
         */
        // TODO: Implement me
    }
}
