package be.ehb.Todolo.parceble;

import android.os.Parcel;
import android.os.Parcelable;

import be.ehb.Todolo.room.Entity.TodoList;

public class ListParcel implements Parcelable {

    public String title = "";
    public int id = -1;
    public int priority = -1;

    public ListParcel(TodoList list) {

        if (list != null)
        {
            this.title = list.getTitle();
            this.id = list.getId();
            this.priority = list.getPriority();
        }
    }

    protected ListParcel(Parcel in) {

        this.title = in.readString();
        this.priority = in.readInt();
        this.id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.title);
        dest.writeInt(this.priority);
        dest.writeInt(this.id);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListParcel> CREATOR = new Creator<ListParcel>() {
        @Override
        public ListParcel createFromParcel(Parcel in) {

            return new ListParcel(in);
        }

        @Override
        public ListParcel[] newArray(int size) {
            return new ListParcel[size];
        }
    };



}
