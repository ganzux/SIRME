package com.sirme.bussiness;

import java.util.Date;

import com.sirme.transform.ITransformator;
import com.sirme.transform.PhotoTransform;

public class Photo implements IBusinessObject, Cloneable {

	private int idPhoto;
	private String path;
	private String comments;
	private Date dateCreated;
	private Work work;

	@Override
	public String toString() {
		return idPhoto + "," + path + "," + comments + "," + dateCreated;
	}

	@Override
	public Class<? extends ITransformator> getTransformator() {
		return PhotoTransform.class;
	}



	public int getIdPhoto() {
		return idPhoto;
	}

	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPhoto;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Photo other = (Photo) obj;
		if (idPhoto != other.idPhoto)
			return false;
		return true;
	}

}