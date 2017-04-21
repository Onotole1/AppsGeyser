package com.spitchenko.appsgeyser.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

/**
 * Date: 20.04.17
 * Time: 21:23
 *
 * @author anatoliy
 */
public class ResponseTrio implements Parcelable {
    private @Getter @Setter long id;
    private @Getter @Setter String inputText;
    private @Getter @Setter String language;

    public ResponseTrio() {
    }


    private ResponseTrio(final Parcel in) {
        id = in.readLong();
        inputText = in.readString();
        language = in.readString();
    }
    public static final Creator<ResponseTrio> CREATOR = new Creator<ResponseTrio>() {
        @Override
        public ResponseTrio createFromParcel(final Parcel in) {
            return new ResponseTrio(in);
        }

        @Override
        public ResponseTrio[] newArray(final int size) {
            return new ResponseTrio[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(id);
        dest.writeString(inputText);
        dest.writeString(language);
    }
}
