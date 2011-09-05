package amo.randomFilm.util;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;

import amo.randomFilm.RandomFilm;
import amo.randomFilm.panels.ListItem;

public class FileListHandler implements Comparator {

	private ArrayList list;
	
	public FileListHandler(){
		list = new ArrayList();
	}
	
	
	public boolean insertItem ( ListItem item ) {
		
		if ( !contains(item) ) {
			list.add( item );
			return true;
		}
		else {
			if ( RandomFilm.DEBUG )  System.out.println(item.getFile().getAbsolutePath()+" ist schon drin !");
			return false;
		}
		
	}
	
	public boolean contains( ListItem item ){
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			if ( ((ListItem) iter.next()).getFile().equals(item.getFile()) ) 
				return true;
		}
		
		return false;
	}
	
	public void sort(){
		Collections.sort(list, this);
	}

	public int compare(Object o1, Object o2) {
		
		File file1 = ((ListItem) o1).getFile();
		File file2 = ((ListItem) o2).getFile();
		
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
	
	public void remove(ListItem item){
		list.remove( item );
	}
	
	public void debugOut(){
		Iterator iterator = list.iterator();
		
		System.out.println("Size: "+list.size()); 
		
		while (iterator.hasNext()) {
			System.out.println("> "+  ( (ListItem)iterator.next() ).getFile().getAbsolutePath() );
		}
	}
	
	
}

