package amo.randomFilm.gui.panels;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelBasicView;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 24.11.2011
 */
public class SelectAlternativeDialogView {
    
    private ListOfMoviesView selectionView;
    
    private JPanel view;
    
    private JButton btnOK;
    
    private JButton btnCancel;
    
    private SelectionHandler selectionHandler = new SelectionHandler();
    
    public SelectAlternativeDialogView() {
        this.selectionView = new ListOfMoviesView();
        JScrollPane moviepanel = new JScrollPane(this.selectionView.getComponent(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // at least three items should be visible
        // moviepanel.setMinimumSize(new Dimension(0, GuiConstants.HEIGHT_MOVIE_PANEL * 3));
        
        this.btnOK = new JButton(GuiConstants.LABEL_BTN_SELECT);
        this.btnCancel = new JButton(GuiConstants.LABEL_BTN_CANCEL);
        
        this.view = new JPanel();
        GroupLayout layout = new GroupLayout(this.view);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(//
        layout.createParallelGroup(//
                GroupLayout.Alignment.TRAILING).addComponent(moviepanel)//
                .addGroup(layout.createSequentialGroup() //
                        .addComponent(this.btnCancel)//
                        .addComponent(this.btnOK)));
        layout.setVerticalGroup(layout.createSequentialGroup() //
                .addComponent(moviepanel) //
                .addGroup( //
                        layout.createParallelGroup()//
                                .addComponent(this.btnCancel) //
                                .addComponent(this.btnOK)));
        
        this.view.setLayout(layout);
        
        this.view.add(moviepanel);
        this.view.add(this.btnCancel);
        this.view.add(this.btnOK);
        this.view.setBackground(GuiConstants.BG_COLOR);
        
        this.view.doLayout();
        System.out.println(" ============== SIZE : " + this.view.getPreferredSize().getWidth() + " / " + this.view.getPreferredSize().getHeight());
    }
    
    // public void setData(List<? extends Movie> movies) {
    // for (Movie movie : movies) {
    // MoviePanelBasicView movieView = new MoviePanelBasicView(null);
    // movieView.setData(movie);
    // // MoviePanelBasicPresenter moviePanelBasicPresenter = new MoviePanelBasicPresenter(movie,
    // movieView);
    // this.selectionView.addData(movieView);
    // this.selectionHandler.addMovie(movieView);
    // // movieView.getComponent().addMouseListener(this.selectionHandler);
    // }
    // }
    
    public void addMovieView(MoviePanelBasicView movieView) {
        this.selectionView.addData(movieView);
        this.selectionHandler.addMovie(movieView);
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
