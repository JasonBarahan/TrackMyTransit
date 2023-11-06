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

    public final String viewName = "search";
    private final SearchViewModel searchViewModel;

    final JTextField searchInputField = new JTextField(15);
    private final JLabel searchErrorField = new JLabel();

    final JButton search;
    private final SearchController searchController;

    public SearchPanelView(SearchViewModel searchViewModel, SearchController searchController) {

        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);

        // Title header
        JLabel title = new JLabel("Search a train station");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // search station
        LabelTextPanel searchInfo = new LabelTextPanel(
                new JLabel("Station name"), searchInputField);

        // Buttons handling
        JPanel buttons = new JPanel();
        search = new JButton(SearchViewModel.SEARCH_LABEL);
        buttons.add(search);

        // This makes the search button sensitive to clicks.
        search.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(search)) {
                            // TODO: remove this debug string
                            System.out.println(searchViewModel.getState().getSearch());

                            SearchState currentState = searchViewModel.getState();

                            searchController.execute(currentState.getSearch());
                        }
                    }
                }
        );

        search.addActionListener(this);

        // This makes the search bar sensitive to changes in input.
        searchInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                SearchState currentState = searchViewModel.getState();
                // TODO: Critical bug(?)
                // What happens if a user corrects a typo which occurs in the middle of the query?
                // Example: user types in "Arora" -> "Station does not exist" modal occurs
                //          user inserts 'u'
                // now query is "Aurora" -> interpreted search query is "Arorau"
                //                       -> "Station does not exist" modal occurs when unintended
                //
                // Temporary solution: force the user to retype the entire query in
                currentState.setSearch(searchInputField.getText() + e.getKeyChar());

                searchViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // do nothing
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // do nothing
            }
        });
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add the elements to the panel
        this.add(title);
        this.add(searchInfo);
        this.add(searchErrorField);
        this.add(buttons);
    }

    // These allow for reactions to onclick events.
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    // based on Carmen's mock data the only entries which will trigger this are
    // "Aurora" and "Union" (case sensitive)
    // TODO: make it so all queries take lowercase capitalization, but are still sensitive to typos
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            SearchState state = (SearchState) evt.getNewValue();
            if (state.getSearchError() != null) {
                // TODO: temporarily hard coded message, will need to fix this
                JOptionPane.showMessageDialog(this, state.getSearchError());
            }
            else {
                JOptionPane.showMessageDialog(this, state.getSearch());

            }
        }
    }

    private void setFields(SearchState state) {
        searchInputField.setText(state.getSearch());
    }

}