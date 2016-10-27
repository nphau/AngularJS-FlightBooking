package com.yosta.flightbooking.binding;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.yosta.flightbooking.model.GradeInfo;

/**
 * Created by Phuc-Hau Nguyen on 10/24/2016.
 */

public class GradeInfoViewModel extends BaseObservable {

    private Context mContext;
    private GradeInfo mGradeInfo;

    public GradeInfoViewModel(Context context, GradeInfo gradeInfo) {
        this.mContext = context;
        this.mGradeInfo = gradeInfo;
    }

    @Bindable
    public String getGrade() {
        return "Class: " + mGradeInfo.getGrade();
    }

    @Bindable
    public String getNumber() {
        return "Number: " + String.valueOf(mGradeInfo.getNumber());
    }

    @Bindable
    public String getPrice() {
        return "Price: " + String.valueOf(mGradeInfo.getPrice());
    }

}
