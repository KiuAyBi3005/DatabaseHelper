package com.example.quanganhpham.databasehelper;

/**
 * Created by QuangAnhPham on 3/9/2017.
 */

public class Note {
    private long mId;
    private String mTitle;
    private String mContent;

    public Note() {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mContent = mContent;
    }

    public long getId() {
        return mId;
    }

    public Note setId(long mId) {
        this.mId = mId;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public Note setTitle(String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    public String getContent() {
        return mContent;
    }

    public Note setContent(String mContent) {
        this.mContent = mContent;
        return this;
    }


}
