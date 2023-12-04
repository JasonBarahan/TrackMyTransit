package view;

import app.Main;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import use_case.station_general_info.StationGeneralInfoInputBoundary;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class SearchPanelViewTest {
    static String message;
    static boolean popUpDiscovered = false;

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

    // Gets the SearchPanelView and related elements
    private LabelTextPanel getPanel() {
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

        // We are checking the LabelTextPanel, component number 1
        // Return the search panel (this should be the only panel on there)
        return (LabelTextPanel) spv.getComponent(1);
    }

    // Gets the search bar from the SearchPanelView
    private JTextField getInputField(LabelTextPanel searchPanel) {
        // return the input field, component number 1
        return (JTextField) searchPanel.getComponent(1);
    }

    // Gets the station name from the StationInfoView
    private JLabel getStationName() {
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

        // We are checking the StationInfoView, component number 1
        StationGeneralInfoView siv = (StationGeneralInfoView) jp2.getComponent(1);

        // We are checking the station name JLabel, component number 2
        // Return the search panel (this should be the only panel on there)
        return (JLabel) siv.getComponent(2);
    }

    // Pauses execution for a set number of milliseconds
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

    // Dialog checking mechanism. Courtesy Paul Gries
    private Timer createCloseTimer() {
        ActionListener close = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Window[] windows = Window.getWindows();
                for (Window window : windows) {

                    if (window instanceof JDialog) {

                        JDialog dialog = (JDialog)window;

                        // this ignores old dialogs
                        if (dialog.isVisible()) {
                            String s = ((JOptionPane) ((BorderLayout) dialog.getRootPane()
                                    .getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER)).getMessage().toString();
                            System.out.println("A dialog box was opened. Message: " + s);

                            // store the information we got from the JDialog
                            SearchPanelViewTest.message = s;
                            SearchPanelViewTest.popUpDiscovered = true;

                            System.out.println("disposing of " + window.getClass());
                            window.dispose();
                        }
                    }
                }
            }

        };

        Timer t = new Timer(1000, close);
        t.setRepeats(false);
        return t;
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
     * This test checks whether the user's intended query and the query stored within the state match.
     */
    @org.junit.Test
    public void testValidQueryWithoutTypos() {
        StationGeneralInfoInputBoundary sib = null;
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
            pause(100);

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

    /**
     * This test checks whether a query with a typo at the end, fixed with a backspace, remains a valid query.
     */
    @org.junit.Test
    public void testValidQueryFixedTypoAtEnd() {

        Main.main(null);
        LabelTextPanel panel = getPanel();
        JTextField searchField = getInputField(panel);

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
                pause(100);

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
        assertEquals(query, searchField.getText());

        // obtain final submitted query for later comparison
        String finalQuery = searchField.getText();

        // initiate timer for dialog checking
        createCloseTimer().start();

        // click the button (empty string is invalid query)
        JButton button = getButton();
        button.doClick();

        // check that a popup did not occur
        //assert !(popUpDiscovered);

        // check that the view is returned and data matches
        // TODO: This only checks the station name. May need to change this in the future
        String stationName = getStationName().getText();
        assertEquals(stationName, finalQuery);
    }

    /**
     * This test checks whether a typo at the end of the string, fixed by a backspace, is accurately passed
     * to the state.
     */
    @org.junit.Test
    public void testValidQueryFixedTypoAtEnd2() {

        StationGeneralInfoInputBoundary sib = null;
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
                pause(100);

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

    /**
     * This test checks whether a typo at the middle of the string, fixed by a backspace, is accurately passed
     * to the state.
     */
    @org.junit.Test
    public void testValidQueryFixedTypoInMiddle() {
        StationGeneralInfoInputBoundary sib = null;
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
                pause(100);

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

    /**
     * A typo occurs in the middle, but the user does not notice it until the end.
     * The user then presses the 'left arrow' button until the caret reaches the typo, erases it,
     * then inserts the correct character without modifying anything else.
     * Checks if this query is considered 'valid'.
     */
    @org.junit.Test
    public void testValidQueryFixedTypoInMiddle2() {
        Main.main(null);
        LabelTextPanel panel = getPanel();
        JTextField searchField = getInputField(panel);

        // write down string
        String finalQuery = "Union Station";
        String[] querySegments = {"Unian Station"};
        for (String q : querySegments) {
            for (int i = 0; i < q.length(); i++) {
                // get character from test query at current index
                char c = q.charAt(i);

                // create a new key typed event and dispatch
                panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

                // pause for a bit
                pause(200);

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

        // click the button (empty string is invalid query)
        JButton button = getButton();
        button.doClick();

        // check that a popup did not occur
        assert !(popUpDiscovered);

        // check that the view is returned and data matches
        // TODO: This only checks the station name. May need to change this in the future
        String stationName = getStationName().getText();
        assertEquals(stationName, finalQuery);
    }

    /**
     * A typo occurs in the middle, which is met by a backspace, before the rest of
     * the input is typed correctly.
     *
     * Checks if the state matches the input data in this case.
     */
    @org.junit.Test
    public void testValidQueryFixedTypoInMiddle3() {
        StationGeneralInfoInputBoundary sib = null;
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
                pause(200);

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

    /**
     * A casing typo occurs at the start, but the user does not notice it until the end.
     * The user then presses the 'left arrow' button until the caret reaches the typo, erases it,
     * then inserts the correct character without modifying anything else.
     * Checks if this query is considered 'valid'.
     */
    @org.junit.Test
    public void testValidQueryFixedCasingAtStart() {
        Main.main(null);
        LabelTextPanel panel = getPanel();
        JTextField searchField = getInputField(panel);

        // write down string
        String finalQuery = "Union Station";
        String[] querySegments = {"union Station"};
        for (String q : querySegments) {
            for (int i = 0; i < q.length(); i++) {
                // get character from test query at current index
                char c = q.charAt(i);

                // create a new key typed event and dispatch
                panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

                // pause for a bit
                pause(200);

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

        // click the button (empty string is invalid query)
        JButton button = getButton();
        button.doClick();

        // check that a popup did not occur
        assert !(popUpDiscovered);

        // check that the view is returned and data matches
        // TODO: This only checks the station name. May need to change this in the future
        String stationName = getStationName().getText();
        assertEquals(stationName, finalQuery);
    }

    /**
     * This test checks whether, given a valid query is passed into the view,
     * the view returns the associated station details without error.
     */
    @org.junit.Test
    public void testValidQueryExecution() {
        Main.main(null);
        JButton button = getButton();
        LabelTextPanel panel = getPanel();
        JTextField searchField = getInputField(panel);

        // generate valid query
        String[] querySegments = {"King City GO"};

        // emulate typing the query
        for (String q : querySegments) {
            for (int i = 0; i < q.length(); i++) {
                // get character from test query at current index
                char c = q.charAt(i);

                // create a new key typed event and dispatch
                panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

                // pause for a bit
                pause(100);

                // move to the right in the field
                panel.dispatchEvent(generateMoveRightEvent(searchField));

                // pause execution for a bit (1/5th of a second) to account for user typing speed
                pause(200);
            }
        }
        // obtain final submitted query for later comparison
        String finalQuery = searchField.getText();

        // initiate timer for dialog checking
        createCloseTimer().start();

        // click the button (empty string is invalid query)
        button.doClick();

        // check that a popup did not occur
        assert (!(popUpDiscovered));

        // check that the view is returned and data matches
        // TODO: This only checks the station name. May need to change this in the future
        String stationName = getStationName().getText();
        assertEquals(stationName, finalQuery);
    }

    /**
     * This test checks whether, given an invalid query is passed into the view, assuming the query is correctly stored
     * in the state, the view returns a popup with an error message stating that the station does not exist.
     * The following queries are tested:
     *  - blank query
     *  - invalid name
     *  - Capitalization error
     */
    @org.junit.Test
    public void testInvalidQueryExecution() {
        Main.main(null);
        JButton button = getButton();
        LabelTextPanel panel = getPanel();
        JTextField searchField = getInputField(panel);

        // generate invalid queries
        String[] queries = {"", "Hamilton GO", " Center"};
        for (String q : queries) {
            for (int i = 0; i < q.length(); i++) {
                // get character from test query at current index
                char c = q.charAt(i);

                // create a new key typed event and dispatch
                panel.dispatchEvent(generateKeyTypedEvent(searchField, c));

                // pause for a bit
                pause(100);

                // move to the right in the field
                panel.dispatchEvent(generateMoveRightEvent(searchField));

                // pause execution for a bit (1/5th of a second) to account for user typing speed
                pause(200);

            }
            System.out.println("Query: " + q);
            // These are nested inside the for loop as we're checking that all queries trigger invalid case handling
            // timer check
            createCloseTimer().start();

            // click the button (empty string is invalid query)
            button.doClick();

            // check for popup
            assert (popUpDiscovered);
        }
    }
}
