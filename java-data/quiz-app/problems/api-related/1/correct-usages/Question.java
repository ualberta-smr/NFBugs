package com.developervishalsehgal.udacityscholarsapp.data.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


@IgnoreExtraProperties
public class Question {

  private long mMarks;
  
  public long getMarks() {
        return mMarks;
    }
}
