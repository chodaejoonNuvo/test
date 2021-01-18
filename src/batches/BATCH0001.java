package batches;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.Connection;
import common.MessageID;
import dao.LevelInfoDao;
import dao.UserPointInfoDao;
import dto.LevelInfoDto;
import dto.UserPointDto;
import exception.ApplicationException;

public class BATCH0001 {

	public static void main(String[] args) {

		Connection con = new Connection();
		List<UserPointDto> userPointDtoList = new ArrayList<>();
		try {
			
			UserPointInfoDao userPointInfoDao = new UserPointInfoDao(con);
			userPointDtoList = userPointInfoDao.getUserPointInfoList(args[0]);
		} catch (ApplicationException e1) {
			e1.printStackTrace();
		}
		try {
			Timestamp updateDate = new Timestamp((new Date().getTime()));
			for (int i = 0; i < userPointDtoList.size(); i++) {
				LevelInfoDao levelInfoDao = new LevelInfoDao(con);
				int j = 0;
				for (j = i; j < i + 3; j++) {
					if (j >= userPointDtoList.size()) {
						break;
					}
					UserPointDto userPointDto = userPointDtoList.get(j);
					if (userPointDto.getPoint() >= 500) {
						LevelInfoDto levelInfoDto = new LevelInfoDto();
						levelInfoDto.setUserID(userPointDto.getUserID());
						levelInfoDto.setUpdateDate(updateDate);
						levelInfoDto.setLevel(1);
						if (userPointDto.getPoint() >= 2000) {
							levelInfoDto.setLevel(2);	
						}
						levelInfoDao.updateLevelInfo(levelInfoDto);
					}
				}
				i = j - 1;
				con.commit();
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
