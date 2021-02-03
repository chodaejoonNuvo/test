package batches;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		// コネクションを生成
		Connection connection = new Connection();
		try {
			// 更新対象のユーザポイント情報取得
			UserPointInfoDao userPointInfoDao = new UserPointInfoDao(connection);
			SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DATE_FORMAT_YYYYMMDDHH24MMSS);
			Timestamp standardDate = new Timestamp(dateFormat.parse(args[0]).getTime());
			List<UserPointDto> userPointDtoList = userPointInfoDao.getUserPointInfoList(standardDate);
			
			// Commit単位
			int commitInterval = 3;
			// レベル情報DAO
			LevelInfoDao levelInfoDao = new LevelInfoDao(connection);
			// 1トランザクションに対しての更新件数
			int totalUpdateCnt = 0;
			// ループ処理開始
			for (UserPointDto userPointDto : userPointDtoList) {
				// レベル情報DTOインスタンス作成
				LevelInfoDto levelInfoDto = new LevelInfoDto();
				// レベル設定
				if (9999 < userPointDto.getPoint()) {
					throw new CheckException(MessageID.INCORRECT_DATA_ERROR);
				} else if (2000 <= userPointDto.getPoint()) {
					levelInfoDto.setLevel(2);
				} else if (500 <= userPointDto.getPoint()) {
					levelInfoDto.setLevel(1);
				} else {
					levelInfoDto.setLevel(0);
				}
				// ユーザID、更新日時設定
				levelInfoDto.setUserID(userPointDto.getUserID());
				levelInfoDto.setUpdateDate(standardDate);

				// レベル情報更新
				totalUpdateCnt += levelInfoDao.updateLevelInfo(levelInfoDto);

				// コミット
				if (totalUpdateCnt == commitInterval) {
					connection.commit();
					// トランザクションが終わったので、総Update件数を初期化
					totalUpdateCnt = 0;
				}
			}
		} catch (CheckException e) {
			e.printStackTrace();
			connection.rollback();
			throw e;
		} catch (ApplicationException e) {
			e.printStackTrace();
			connection.rollback();
			throw e;
		} catch (ParseException e) {
			e.printStackTrace();
			connection.rollback();
			throw new ApplicationException(MessageID.DATE_FORMAT_ERROR);
		} finally {
			// コネクション切断
			connection.disconnect();
		}
	}
}


