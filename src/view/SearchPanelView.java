package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

public class SearchPanelView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "search";
    private final SearchViewModel searchViewModel;

    final JTextField stationInputField = new JTextField(20);
    private final JLabel stationErrorField = new JLabel();

    final JButton submit;
    private final SearchController searchController;

    public SearchPanelView(SearchViewModel searchViewModel, SearchController searchController) {

        this.searchViewModel = searchViewModel;
        this.searchController = searchController;
        this.searchViewModel.addPropertyChangeListener(this); // Listening to changes in SearchViewModel.java

        JLabel title = new JLabel(searchViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel stationInfo = new LabelTextPanel(
                new JLabel(searchViewModel.STATION_INPUT_LABEL), stationInputField);

        JPanel buttons = new JPanel();
        submit = new JButton(searchViewModel.SUBMIT_BUTTON_LABEL);
        buttons.add(submit);

        submit.addActionListener( // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(submit)) {
                            //TODO: REVIEW PROPOSED CHANGE. Liens 46 -47 fetches the current CORRECT user input from the text box, so that the correct, up-to-date user input is fetched from the search bar for processing in the controller.
                           SearchState currentStateOfInput = searchViewModel.getState();
                           currentStateOfInput.setStateStationName(stationInputField.getText());

                            SearchState currentState = searchViewModel.getState();
                            try {
                                searchController.execute(
                                        currentState.getStateStationName()
                                );
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        );

        stationInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                SearchState currentState = searchViewModel.getState();
                currentState.setStateStationName(stationInputField.getText() + e.getKeyChar());
                searchViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(stationInfo);
        this.add(stationErrorField);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SearchState state = (SearchState) evt.getNewValue();
        if (state.getStateStationError() != null){
            JOptionPane.showMessageDialog(this, state.getStateStationError());
        }
        else if (state.getStateStationName() != null) {
            JOptionPane.showMessageDialog(this, state.getStateStationName());

        }
    }

}


