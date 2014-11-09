package com.alcedomoreno.sirme.core.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

@Entity
@Proxy(lazy=true)
@Table(name="work", uniqueConstraints = { @UniqueConstraint( columnNames = { "albaran", "year" } ) } )
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class WorkData implements Serializable,DataObject {

private static final long serialVersionUID = 1L;
	
	public WorkData() {}
	
	public WorkData(int id) {
		this.idWork = id;
	}
	
	@Column(name="idWork", nullable=false)
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idWork;
	
	@Column(name="year", nullable=false)
	private int year;
	
	@Column(name="albaran", nullable=false)
	private int albaran;
	
	@Column(name="`date`")
	@Type(type="timestamp")
	private Date date;
	
	@ManyToOne(targetEntity=TeamData.class,fetch=FetchType.EAGER)
	@Cascade({CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="idTeam", referencedColumnName="idTeam") })	
	private TeamData team;
	
	@Column(name="[memo]", nullable=true, unique=false)	
	private String memo;
	
	@Column(name="[data]", nullable=true, unique=false)	
	private String data;
	
	@Column(name="[signpath]", nullable=true, unique=false)	
	private String signpath;
	
	@Column(name="[signName]", nullable=true, unique=false)	
	private String signName;
	
	@Column(name="[status]", nullable=false, unique=false)	
	private int status;
	
	@Column(name="[typeWork]", nullable=false, unique=false)	
	private int typeWork;
	
	@Column(name="`dateCreated`")
	@Type(type="timestamp")
	private Date dateCreated;
	
	@ManyToOne(targetEntity=AddressData.class,fetch=FetchType.EAGER)
	@Cascade({CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="idAddress", referencedColumnName="idAddress") })	
	private AddressData address;
	
	@OneToMany(mappedBy="work", targetEntity=ReplyGroupData.class)	
	@Cascade({CascadeType.SAVE_UPDATE})	
	@LazyCollection(LazyCollectionOption.FALSE)	
	private Set<ReplyGroupData> replyGroups = new HashSet<ReplyGroupData>();
	
	@OneToMany(mappedBy="work", targetEntity=PhotoData.class)	
	@Cascade({CascadeType.SAVE_UPDATE})	
	@LazyCollection(LazyCollectionOption.FALSE)	
	private Set<PhotoData> photos = new HashSet<PhotoData>();

	public int getIdWork() {
		return idWork;
	}
	public void setIdWork(int idWork) {
		this.idWork = idWork;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public TeamData getTeam() {
		return team;
	}
	public void setTeam(TeamData team) {
		this.team = team;
	}
	public AddressData getAddress() {
		return address;
	}
	public void setAddress(AddressData address) {
		this.address = address;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getAlbaran() {
		return albaran;
	}
	public void setAlbaran(int albaran) {
		this.albaran = albaran;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTypeWork() {
		return typeWork;
	}
	public void setTypeWork(int typeWork) {
		this.typeWork = typeWork;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Set<ReplyGroupData> getReplyGroups() {
		return replyGroups;
	}
	public void setReplyGroups(Set<ReplyGroupData> replyGroups) {
		this.replyGroups = replyGroups;
	}
	public String getSignpath() {
		return signpath;
	}
	public void setSignpath(String signpath) {
		this.signpath = signpath;
	}
	public String getSignName() {
		return signName;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public Set<PhotoData> getPhotos() {
		return photos;
	}
	public void setPhotos(Set<PhotoData> photos) {
		this.photos = photos;
	}
}