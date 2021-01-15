package dto;

import java.sql.Timestamp;
import java.util.Date;

public class LevelInfoDto {

	/**
	 * ユーザID
	 */
	private String userID;
	
	/**
	 * レベル
	 */
	private int level;
	
	/**
	 * 更新日時
	 */
	private Timestamp updateDate;
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Timestamp getUpdateDate() {
		if (null == updateDate) {
			updateDate = new Timestamp((new Date()).getTime());
		}
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "LevelInfoDto [userID=" + userID + ", level=" + level + ", updateDate=" + updateDate + "]";
	}
}
