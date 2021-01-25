package batches;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import common.Connection;
import common.Const;
import common.MessageID;
import dao.LevelInfoDao;
import dao.UserPointInfoDao;
import dto.LevelInfoDto;
import dto.UserPointDto;
import exception.ApplicationException;
import exception.CheckException;

public class BATCH0001 {

	public static void main(String[] args) throws ApplicationException, CheckException {

		Connection con = new Connection();
		List<UserPointDto> userPointDtoList = new ArrayList<>();
		try {
			SimpleDateFormat sdf  = new SimpleDateFormat(Const.DATE_FORMAT_YYYYMMDDHH24MMSS);
			Timestamp updateDate = new Timestamp(sdf.parse(args[0]).getTime());
			UserPointInfoDao userPointInfoDao = new UserPointInfoDao(con);
			userPointDtoList = userPointInfoDao.getUserPointInfoList(updateDate);
			for (int i = 0; i < userPointDtoList.size(); i++) {
				LevelInfoDao levelInfoDao = new LevelInfoDao(con);
				int j = 0;
				for (j = i; j < i + 3; j++) {
					if (j >= userPointDtoList.size()) {
						break;
					}
					UserPointDto userPointDto = userPointDtoList.get(j);
					LevelInfoDto levelInfoDto = new LevelInfoDto();
					levelInfoDto.setUserID(userPointDto.getUserID());
					levelInfoDto.setUpdateDate(updateDate);
					if (userPointDto.getPoint() > 9999) {
						throw new CheckException(MessageID.INCORRECT_DATA_ERROR);
					} else if (userPointDto.getPoint() >= 2000) {
						levelInfoDto.setLevel(2);
					} else if (userPointDto.getPoint() >= 500) {
						levelInfoDto.setLevel(1);
					} else {
						levelInfoDto.setLevel(0);
					}
					levelInfoDao.updateLevelInfo(levelInfoDto);
				}
				i = j - 1;
				con.commit();
			}
		} catch (ApplicationException e) {
			con.rollback();
			throw e;
		} catch (CheckException e) {
			con.rollback();
			throw e;
		} catch (ParseException e) {
			con.rollback();
			throw new ApplicationException(MessageID.DATE_FORMAT_ERROR);
		} finally {
			con.disconnect();
		}
	}
}