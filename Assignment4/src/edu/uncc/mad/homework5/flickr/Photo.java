package edu.uncc.mad.homework5.flickr;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Comparable<Photo>, Parcelable {

	private long photoId;
	private String title;
	private String imageURL;
	private int views;

	public int getViews() {
		return views;
	}

	public Photo(long photoId, String title, String imageURL, int views) {
		this.setPhotoId(photoId);
		this.setTitle(title);
		this.setImageURL(imageURL);
		this.setViews(views);
	}

	public void setViews(int views) {
		this.views = views;
	}

	@Override
	public int compareTo(Photo photo) {

		return photo.getViews() - getViews();
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {

		parcel.writeInt(views);
		parcel.writeLong(photoId);
		parcel.writeString(imageURL);
		parcel.writeString(title);

	}

	public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
		public Photo createFromParcel(Parcel in) {
			return new Photo(in);
		}

		public Photo[] newArray(int size) {
			return new Photo[size];
		}
	};

	private Photo(Parcel parcel){
		
		views = parcel.readInt();
		photoId = parcel.readLong();
		imageURL = parcel.readString();
		title = parcel.readString();
		
	}
	
}
