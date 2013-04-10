package utils;

import android.app.Activity;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.example.project01.R;

public class FunctionsView {
	/**
	 * Make a TextView used in tableHeader
	 *
	 * @param ac		activity where the table will be show
	 * @param text		text
	 * @return          textView
	 */
	public static TextView makeTableViewHeader(Activity ac, String text){
		LayoutParams tableViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
    	tableViewParams.setMargins(1, 1, 1, 1);
		TextView tv = new TextView(ac);
		tv.setLayoutParams(tableViewParams);
        tv.setBackgroundResource(R.drawable.cell_header);
    	tv.setText(text);
    	return tv;
	}
	
	
	/**
	 * Make a TextView used in tables
	 *
	 * @param ac		activity where the table will be show
	 * @param text		text
	 * @param line		line in the table... used to paint the cell
	 * @return          textView
	 */
	public static TextView makeTableView(Activity ac, String text, int line){
		LayoutParams tableViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
    	tableViewParams.setMargins(1, 1, 1, 1);
		TextView tv = new TextView(ac);
        tv.setBackgroundColor(ac.getResources().getColor(line%2==0?R.color.bgColor1:R.color.bgColor2));
    	tv.setLayoutParams(tableViewParams);
    	tv.setBackgroundResource(R.drawable.cell_shape);
    	tv.setText(text);
    	return tv;
	}
	/**
	 * Make a TextView used in tables
	 *
	 * @param ac		activity where the table will be show
	 * @param text		text
	 * @return          textView, default color
	 */
	public static TextView makeTableView(Activity ac, String text){
		return makeTableView(ac, text, 0);
	}
}
