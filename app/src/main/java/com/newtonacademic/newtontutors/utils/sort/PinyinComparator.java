package com.newtonacademic.newtontutors.utils.sort;

import com.newtonacademic.newtontutors.retrofit.model.GetCountryList;

import java.util.Comparator;

public class PinyinComparator implements Comparator<GetCountryList.Data> {

	public int compare(GetCountryList.Data o1, GetCountryList.Data o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
