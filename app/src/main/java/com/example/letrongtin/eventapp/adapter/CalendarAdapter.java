package com.example.letrongtin.eventapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.letrongtin.eventapp.Common.Common;
import com.example.letrongtin.eventapp.R;
import com.example.letrongtin.eventapp.activity.ViewEventDayActivity;
import com.example.letrongtin.eventapp.model.EventDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Le Trong Tin on 3/27/2018.
 */

public class CalendarAdapter extends BaseAdapter {

    private Context context;

    private Calendar monthCalendar;

    private GregorianCalendar previousMonthCalendar;

    private GregorianCalendar previousMonthMaxset;

    private GregorianCalendar selectedDate;

    private int firstDate;

    String itemValue, currentDateString;

    DateFormat dateFormat;

    private ArrayList<EventDate> eventDates;

    public static ArrayList<String> calendarString;

    private View previousView;


    public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<EventDate> eventDates) {

        this.eventDates = eventDates;
        calendarString = new ArrayList<String>();

        this.monthCalendar = monthCalendar;
        this.selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        monthCalendar.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        currentDateString = dateFormat.format(selectedDate.getTime());
        refreshDays();

    }

    @Override
    public int getCount() {
        return calendarString.size();
    }

    @Override
    public Object getItem(int position) {
        return calendarString.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView txtDate;
        RelativeLayout calendarItemLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.calendar_item, null);
            holder = new ViewHolder();
            holder.txtDate = convertView.findViewById(R.id.txt_date);
            holder.calendarItemLayout = convertView.findViewById(R.id.calendar_item_layout);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        //EventDate eventDate = (EventDate) getItem(position);

        String[] separatedTime = calendarString.get(position).split("-");

        String date = separatedTime[2].replaceFirst("^0*","");

        holder.txtDate.setText(date);

        if (Integer.parseInt(date) > 1 && position < firstDate){
            holder.txtDate.setTextColor(Color.LTGRAY);
            holder.txtDate.setClickable(false);
            holder.txtDate.setFocusable(false);
        } else if (Integer.parseInt(date) < 14 && position > 28){
            holder.txtDate.setTextColor(Color.LTGRAY);
            holder.txtDate.setClickable(false);
            holder.txtDate.setFocusable(false);
        } else {
            holder.txtDate.setTextColor(Color.BLACK);
        }

        if (calendarString.get(position).equals(currentDateString)){
            holder.calendarItemLayout.setBackgroundColor(Color.CYAN);
        } else {
            holder.calendarItemLayout.setBackgroundColor(Color.TRANSPARENT);
        }


//        if (date.length() == 1) {
//            date = "0" + date;
//        }
//        String monthStr = (month.get(GregorianCalendar.MONTH) + 1) + "";
//        if (monthStr.length() == 1) {
//            monthStr = "0" + monthStr;
//        }


        setEventDay(holder, position);


        return convertView;
    }

    private void setEventDay(ViewHolder holder, int position) {
        for (EventDate e:eventDates) {
            String date = e.getDate();
            String[] separatedTime = calendarString.get(position).split("-");
            String moth = separatedTime[1];
            String date1 = separatedTime[2];
            String dateString = moth + "-" + date1;
            int len = calendarString.size();
            if (len > position) {
                if (dateString.equals(date)) {
                    holder.calendarItemLayout.setBackgroundResource(R.drawable.rounded_calender_item);
                    //holder.calendarItemLayout.setVisibility(View.VISIBLE);

                }
            }
        }

    }

    public void refreshDays(){

        calendarString.clear();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        previousMonthCalendar = (GregorianCalendar) monthCalendar.clone();

        // lấy ngày trong tuần
        firstDate = monthCalendar.get(GregorianCalendar.DAY_OF_WEEK);

        // lấy số tuần của tháng hiển thị
        //int weekOfMonth = monthCalendar.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);

        //int weeks_corr = (int)((firstDate+this.monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)+5)/7);

        // lấy số hàng tối đa cho gridview
        //int mnthlength = weeks_corr * 7;
        int mnthlength = 6*7;

        int dayOfPreMoth = getMaxDatePreviousMonth();                           // số ngày của tháng trước

        int dayOffset = dayOfPreMoth - (firstDate - 1);          // ngày bù của tháng hiển thị


        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        previousMonthMaxset = (GregorianCalendar) previousMonthCalendar.clone();
        /**
         * setting the start date as previous month's required date.
         */
        previousMonthMaxset.set(GregorianCalendar.DAY_OF_MONTH, dayOffset + 1);
        //month.set(GregorianCalendar.DAY_OF_MONTH, dayOffset + 1);


        /**
         * filling calendar gridview.
         */

        String itemCalendar;
        for (int n = 0; n < mnthlength; n++) {

            itemCalendar = dateFormat.format(previousMonthMaxset.getTime());
            previousMonthMaxset.add(GregorianCalendar.DATE, 1);
            calendarString.add(itemCalendar);

        }
    }

    private int getMaxDatePreviousMonth() {

        int count;
        // nếu tháng đang hiển thị = tháng 1 thì set tháng trước ( năm-1, 12, 1)
        // ngược lại tháng trước = tháng đang hiển thị - 1
        if (monthCalendar.get(GregorianCalendar.MONTH) == monthCalendar
                .getActualMinimum(GregorianCalendar.MONTH)) {
            previousMonthCalendar.set((monthCalendar.get(GregorianCalendar.YEAR) - 1),
                    monthCalendar.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            previousMonthCalendar.set(GregorianCalendar.MONTH,
                    monthCalendar.get(GregorianCalendar.MONTH) - 1);
        }

        count = previousMonthCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return count;
    }

    public void setSelectedDay(View view, int pos) {

//        if (previousView != null) {
//            previousView.setBackgroundColor(Color.WHITE);
//        }

        //if (!calendarString.get(pos).equals(currentDateString))
            //view.setBackgroundColor(Color.CYAN);

//        int len = calendarString.size();
//        if (len > pos) {
//            if (calendarString.get(pos).equals(currentDateString)) {
//
//            }else{
//                previousView = view;
//            }
//        }

        for (EventDate e:eventDates) {
            String[] separatedTime = calendarString.get(pos).split("-");
            String moth = separatedTime[1];
            String date = separatedTime[2];
            String dateString = moth + "-" + date;
            if (dateString.equals(e.getDate())){
                Common.EVENT_DAY_SELECT = e;
                Intent intent = new Intent(context, ViewEventDayActivity.class);
                context.startActivity(intent);
            }

        }
    }
}
