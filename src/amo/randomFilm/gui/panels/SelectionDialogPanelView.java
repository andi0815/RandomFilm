package amo.randomFilm.gui.panels;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelBasicView;
import amo.randomFilm.model.Movie;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 24.11.2011
 */
public class SelectionDialogPanelView {
    
    private SelectableMoviePanelView selectionView;
    private SelectableMoviePanelPresenter selectionPresenter;
    private JPanel view;
    private JButton btnOK;
    private JButton btnCancel;
    
    public SelectionDialogPanelView() {
        this.selectionView = new SelectableMoviePanelView();
        this.selectionPresenter = new SelectableMoviePanelPresenter(this.selectionView);
        this.btnOK = new JButton(GuiConstants.LABEL_BTN_SELECT);
        this.btnCancel = new JButton(GuiConstants.LABEL_BTN_CANCEL);
        
        // TODO: add Scrollbar !!!
        
        this.view = new JPanel();
        GroupLayout layout = new GroupLayout(this.view);
        this.view.setLayout(layout);
        
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(this.selectionView.getComponent())
                .addGroup(layout.createSequentialGroup().addComponent(this.btnCancel).addComponent(this.btnOK)));
        
        layout.setVerticalGroup(layout.createSequentialGroup() //
                .addComponent(this.selectionView.getComponent()) //
                .addGroup( //
                        layout.createParallelGroup()//
                                .addComponent(this.btnCancel) //
                                .addComponent(this.btnOK)));
        
        this.view.add(this.selectionView.getComponent());
        this.view.add(this.btnCancel);
        this.view.add(this.btnOK);
    }
    
    public void setData(List<? extends Movie> movies) {
        for (Movie movie : movies) {
            MoviePanelBasicView movieView = new MoviePanelBasicView(null);
            movieView.setData(movie);
            this.selectionView.addData(movieView);
        }
    }
    
    public Component getComponent() {
        return this.view;
    }
    
    public void addActionListener(ActionListener listener) {
        this.btnCancel.addActionListener(listener);
        this.btnOK.addActionListener(listener);
    }
    
}
