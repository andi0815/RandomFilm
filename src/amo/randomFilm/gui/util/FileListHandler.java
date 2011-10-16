package amo.randomFilm.gui.util;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;

import amo.randomFilm.RandomFilm;
import amo.randomFilm.gui.panels.MoviePanel;

public class FileListHandler implements Comparator {

	private ArrayList list;
	
	public FileListHandler(){
		list = new ArrayList();
	}
	
	
	public boolean insertItem ( MoviePanel item ) {
		
		if ( !contains(item) ) {
			list.add( item );
			return true;
		}
		else {
			if ( RandomFilm.DEBUG )  System.out.println(item.getFile().getAbsolutePath()+" ist schon drin !");
			return false;
		}
		
	}
	
	public boolean contains( MoviePanel item ){
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			if ( ((MoviePanel) iter.next()).getFile().equals(item.getFile()) ) 
				return true;
		}
		
		return false;
	}
	
	public void sort(){
		Collections.sort(list, this);
	}

	public int compare(Object o1, Object o2) {
		
		File file1 = ((MoviePanel) o1).getFile();
		File file2 = ((MoviePanel) o2).getFile();
		
		return file1.compareTo( file2 );
	}
	
	public ArrayList getList(){
		return list;
	}
	
	public boolean isEmpty() {
		if (list.size() == 0) return true;
		else return false;
	}
	
	public void clearList() {
		list.clear();
	}
	
	public void remove(MoviePanel item){
		list.remove( item );
	}
	
	public void debugOut(){
		Iterator iterator = list.iterator();
		
		System.out.println("Size: "+list.size()); 
		
		while (iterator.hasNext()) {
			System.out.println("> "+  ( (MoviePanel)iterator.next() ).getFile().getAbsolutePath() );
		}
	}
	
	
}

