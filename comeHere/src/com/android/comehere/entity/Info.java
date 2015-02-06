package com.android.comehere.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

public class Info extends BmobObject implements Serializable
{
	private static final long serialVersionUID = -1010711775392052966L;
	private double latitude;
	private double longitude;
	private String provider;
	private int imgId;
	private String name;
	private String distance;
	private String geoHash;
	private int zan;
	private BmobFile photo;
	private String comment;
	private BmobDate updateTime;

	private BmobDate createTime;
	private BmobDate paiTime;
	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public BmobDate getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(BmobDate updateTime) {
		this.updateTime = updateTime;
	}

	public BmobDate getCreateTime() {
		return createTime;
	}

	public void setCreateTime(BmobDate createTime) {
		this.createTime = createTime;
	}

	public BmobDate getPaiTime() {
		return paiTime;
	}

	public void setPaiTime(BmobDate paiTime) {
		this.paiTime = paiTime;
	}

	public BmobFile getPhoto() {
		return photo;
	}

	public void setPhoto(BmobFile photo) {
		this.photo = photo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static List<Info> infos = new ArrayList<Info>();

	static
	{
//		infos.add(new Info(31.983352, 118.771171, R.drawable.a01, "Ӣ�׹���С�ù�",
//				"����209��", 1456));
//		infos.add(new Info(31.964552, 118.770171, R.drawable.a02, "ɳ�����ϴԡ����",
//				"����897��", 456));
//		infos.add(new Info(31.991152, 118.770171, R.drawable.a03, "�廷��װ��",
//				"����249��", 1456));
//		infos.add(new Info(31.979952, 118.770971, R.drawable.a04, "���׼�����С��",
//				"����679��", 1456));
	}

	public Info(double latitude, double longitude, int imgId, String name,
			String distance, int zan, String provider)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.provider = provider;
		this.imgId = imgId;
		this.name = name;
		this.distance = distance;
		this.zan = zan;
	}
	
	public Info()
	{
		
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public int getImgId()
	{
		return imgId;
	}

	public void setImgId(int imgId)
	{
		this.imgId = imgId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}

	public int getZan()
	{
		return zan;
	}

	public void setZan(int zan)
	{
		this.zan = zan;
	}
	
	public String getGeoHash() {
		return geoHash;
	}

	public void setGeoHash(String geoHash) {
		this.geoHash = geoHash;
	}

}
