package view;

import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SearchPanelView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Search";
    private final SearchViewModel searchViewModel;
    private final JTextField stationInputField = new JTextField(25);
    private final JLabel stationErrorField = new JLabel();
    private final SearchController searchController;
    private final JButton search;



    public SearchPanelView(SearchViewModel searchViewModel, SearchController searchController) {
        this.searchViewModel = searchViewModel;
        this.searchController = searchController;
        searchViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(SearchViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel stationInfo = new LabelTextPanel(
                new JLabel(SearchViewModel.STATION_LABEL), stationInputField);

        JPanel buttons = new JPanel();
        search = new JButton(SearchViewModel.SEARCH_BUTTON_LABEL);
        buttons.add(search);

        search.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(search)) {
                            SearchState currentState = searchViewModel.getState();
                            searchController.execute(currentState.getStationName());
                        }
                    }
                }
        );


        stationInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SearchState currentState = searchViewModel.getState();
                        currentState.setStationName(stationInputField.getText() + e.getKeyChar());
                        searchViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(stationInfo);
        this.add(stationErrorField);
        this.add(buttons);
    }


    @Override
    // Need implementation
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SearchState state = (SearchState) evt.getNewValue();
        if (state.getStationName() != null) {
            JOptionPane.showMessageDialog(this, state.getStationName());
        }
        else if (state.getStationError() != null) {
            JOptionPane.showMessageDialog(this, state.getStationError());
        }
    }
}
