package amo.randomFilm.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.GuiConstants;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 21.11.2011
 */
public class ButtonPanelPresenter implements ActionListener {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(ButtonPanelPresenter.class);
    
    public ButtonPanelPresenter() {
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_SELECT_ALL)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_SELECT_ALL + " -> " + e + " Source: "
                    + e.getSource());
            
//          Iterator<MoviePanelViewNoButtons> iter = this.getMovieIterator();
//          while (iter.hasNext()) {
//              iter.next().setSelected(true);
//          }
//          this.repaint();
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_SELECT_NOTHING)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_SELECT_NOTHING + " -> " + e + " Source: "
                    + e.getSource());
//          Iterator<MoviePanelViewNoButtons> iter = this.getMovieIterator();
//          while (iter.hasNext()) {
//              iter.next().setSelected(false);
//          }
//          this.repaint();
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_REMOVE_SELECTED)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_REMOVE_SELECTED + " -> " + e + " Source: "
                    + e.getSource());
//          this.removeAll();
//          
//          MoviePanelViewNoButtons item;
//          FileListHandler fileListHandler = new FileListHandler();
//          Iterator<MoviePanelViewNoButtons> iter = this.getMovieIterator();
//          
//          while (iter.hasNext()) {
//              item = iter.next();
//              if (item.isSelected())
//                  iter.remove();
////              if (!item.isSelected()) {
////                  if (fileListHandler.insertItem(item))
////                      this.add(item);
////              }
//          }
//          
////          this.listHandler = fileListHandler;
//          this.resetComponentBounds();
//          this.resizePanel();
//          this.repaint();
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_REMOVE_ALL)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_REMOVE_ALL + " -> " + e + " Source: "
                    + e.getSource());
            
//          this.removeAllMovies();
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_GO)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_GO + " -> " + e + " Source: " + e.getSource());
            
//          MoviePanelViewNoButtons listItem = this.getRandomMovie();
//          if (listItem != null) {
//              
//              String filmName = listItem.getFilmName();
//              String filmPath = listItem.getFile().getPath();
//              Image filmIcon = listItem.getIconImage();
//              
//              if (Dialogs.showStartFilmDialog(filmName, filmPath, filmIcon, this)) {
//                  String executableName = listItem.getExecutableName();
//                  this.parent.setAlwaysOnTop(false);
//                  
//                  if (executableName != null && !executableName.equals("")) {
//                      
//                      String command = executableName + " \"" + filmPath + "\"";
//                      try {
//                          Process child = Runtime.getRuntime().exec(command);
//                      } catch (IOException e1) {
//                          Dialogs.showWarning(
//                                  "Kann Datei: " + filmPath + " nicht starten.\nGrund:\n" + e1.getMessage(), this);
//                      }
//                  }
//              } else {
//                  logger.debug("Doch nicht ...");
//              }
//          }
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_DISABLE_ALWAYS_ON_TOP)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_DISABLE_ALWAYS_ON_TOP + " -> " + e + " Source: "
                    + e.getSource());
            
//          this.parent.setAlwaysOnTop(false);
//          ButtonPanelDropper.setAlwaysOnTopEnabled(false);
//          
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_ENABLE_ALWAYS_ON_TOP)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_ENABLE_ALWAYS_ON_TOP + " -> " + e + " Source: "
                    + e.getSource());
            
//          this.parent.setAlwaysOnTop(true);
//          ButtonPanelDropper.setAlwaysOnTopEnabled(true);
            
        }
        
    }
    
}
