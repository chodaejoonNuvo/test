package dto;

import java.sql.Timestamp;

public class UserPointDto {

	/**
	 * ユーザID
	 */
	private String userID;
	
	/**
	 * ユーザ名前
	 */
	private String userNM;
	
	/**
	 * ポイント
	 */
	private int point;
	
	/**
	 * 更新日時(ユーザ情報)
	 */
	private Timestamp userInfoUpdateDate;
	
	/**
	 * 更新日時(ポイント情報)
	 */
	private Timestamp pointInfoUpdateDate;

	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserNM() {
		return userNM;
	}

	public void setUserNM(String userNM) {
		this.userNM = userNM;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Timestamp getUserInfoUpdateDate() {
		return userInfoUpdateDate;
	}

	public void setUserInfoUpdateDate(Timestamp userInfoUpdateDate) {
		this.userInfoUpdateDate = userInfoUpdateDate;
	}

	public Timestamp getPointInfoUpdateDate() {
		return pointInfoUpdateDate;
	}

	public void setPointInfoUpdateDate(Timestamp pointInfoUpdateDate) {
		this.pointInfoUpdateDate = pointInfoUpdateDate;
	}

	@Override
	public String toString() {
		return "UserPointDto [userID=" + userID + ", userNM=" + userNM + ", point=" + point + ", userInfoUpdateDate="
				+ userInfoUpdateDate + ", pointInfoUpdateDate=" + pointInfoUpdateDate + "]";
	}
}
