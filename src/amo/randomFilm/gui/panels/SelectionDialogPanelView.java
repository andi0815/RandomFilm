package amo.randomFilm.gui.panels;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelBasicView;
import amo.randomFilm.model.Movie;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 24.11.2011
 */
public class SelectionDialogPanelView {
    
    private SelectableMoviePanelView selectionView;
    private JPanel view;
    private JButton btnOK;
    private JButton btnCancel;
    private SelectionHandler selectionHandler = new SelectionHandler();
    
    public SelectionDialogPanelView() {
        this.selectionView = new SelectableMoviePanelView();
        this.btnOK = new JButton(GuiConstants.LABEL_BTN_SELECT);
        this.btnCancel = new JButton(GuiConstants.LABEL_BTN_CANCEL);
        
        this.view = new JPanel();
        GroupLayout layout = new GroupLayout(this.view);
        this.view.setLayout(layout);
        
        JScrollPane moviepanel = new JScrollPane(this.selectionView.getComponent(),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(moviepanel)
                .addGroup(layout.createSequentialGroup().addComponent(this.btnCancel).addComponent(this.btnOK)));
        
        layout.setVerticalGroup(layout.createSequentialGroup() //
                .addComponent(moviepanel) //
                .addGroup( //
                        layout.createParallelGroup()//
                                .addComponent(this.btnCancel) //
                                .addComponent(this.btnOK)));
        
        this.view.add(moviepanel);
        this.view.add(this.btnCancel);
        this.view.add(this.btnOK);
    }
    
    public void setData(List<? extends Movie> movies) {
        for (Movie movie : movies) {
            MoviePanelBasicView movieView = new MoviePanelBasicView(null);
            movieView.setData(movie);
//            MoviePanelBasicPresenter moviePanelBasicPresenter = new MoviePanelBasicPresenter(movie, movieView);
            this.selectionView.addData(movieView);
            this.selectionHandler.addMovie(movieView);
//            movieView.getComponent().addMouseListener(this.selectionHandler);
        }
    }
    
    public Component getComponent() {
        return this.view;
    }
    
    public void addActionListener(ActionListener listener) {
        this.btnCancel.addActionListener(listener);
        this.btnOK.addActionListener(listener);
    }
    
    public MoviePanelBasicView getSelectedMovie() {
        return this.selectionHandler.getSelectedMovie();
    }
    
}
