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

public class BATCH0001sandar {

	public static void main(String[] args) throws ApplicationException, CheckException, ParseException {

		Connection con = new Connection();
		List<UserPointDto> userPointDtoList = new ArrayList<>();
		try {

			SimpleDateFormat sdf = new SimpleDateFormat(Const.DATE_FORMAT_YYYYMMDDHH24MMSS);
			//時間オブジェクトを作成する
			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
			// String str="20210118102301";
			// Date date=new Date();
			// Timestamp updateDate = new Timestamp(sdf.parse(args[0]).getTime());
			// Timestamp updateDate = new Timestamp(sdf.parse(str).getTime());
			UserPointInfoDao userPointInfoDao = new UserPointInfoDao(con);
			//時間オブジェクトを表示
			//System.out.println(updateDate);

			userPointDtoList = userPointInfoDao.getUserPointInfoList(updateDate);
			//時間オブジェクトを表示
			//System.out.println(updateDate);
			for (int i = 0; i < userPointDtoList.size(); i++) {
				LevelInfoDao levelInfoDao = new LevelInfoDao(con);
				int j = 0;
				for (j = i; j < i + 3; j++) {
					if (j >= userPointDtoList.size()) {
						break;
					}
					UserPointDto userPointDto = userPointDtoList.get(j);
					// System.out.println(userPointDto);
					LevelInfoDto levelInfoDto = new LevelInfoDto();
					// userPointDto.setUserID("aaaa");
					//userPointDto.setPoint(1234);
					userPointDto.setPointInfoUpdateDate(updateDate);
					levelInfoDto.setUserID(userPointDto.getUserID());
					levelInfoDto.setLevel(userPointDto.getPoint());
					levelInfoDto.setUpdateDate(userPointDto.getPointInfoUpdateDate());
					userPointDto.setLevelInfoUpdateDate(updateDate);
					//constructor
					//System.out.println(userPointDto);

					if (userPointDto.getPoint() >= 2000) {
						levelInfoDto.setLevel(2);
					} else if (userPointDto.getPoint() >= 500) {
						levelInfoDto.setLevel(1);
					} else if (userPointDto.getPoint() < 500) {
						levelInfoDto.setLevel(0);

					} else {
						throw new CheckException(MessageID.DATE_FORMAT_ERROR);
					}
					// System.out.println(levelInfoDto);
					levelInfoDao.updateLevelInfo(levelInfoDto);

					// levelInfoDto.setUserID("aaaaaaaaa");
					levelInfoDto.setUpdateDate(updateDate);
					levelInfoDao.commit();
					//levelInfoDto constructor return 表示
					//System.out.println(levelInfoDto);
				}
				i = j - 1;
				//connection commit
				con.commit();

			}
		} catch (ApplicationException e) {
			con.rollback();
			throw e;
		} catch (CheckException e) {
			con.rollback();
			throw e;
		} finally {
			// con.disconnect();
			con.rollback();
		}
	}
}
