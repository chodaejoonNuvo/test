package batches;

import java.sql.Timestamp;
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

public class BATCH0001 {

	public static void main(String[] args) throws Exception {

		Connection con = new Connection();
		
		List<UserPointDto> userPointDtoList = new ArrayList<>();
		
		try {

			SimpleDateFormat sdf  = new SimpleDateFormat(Const.DATE_FORMAT_YYYYMMDDHH24MMSS);		
			
			UserPointInfoDao userPointInfoDao = new UserPointInfoDao(con);
			
			// パラメータは「パラメータ設定」で設定した「バッチ実行時の日時」にする。
			Timestamp levelUpdateDate = new Timestamp(sdf.parse(args[0]).getTime());
			
			// UserPointInfoDao.getUserPointInfoList()を呼び出し、更新対象のユーザポイント情報DTOリストを取得する。
			userPointDtoList = userPointInfoDao.getUserPointInfoList(levelUpdateDate);
			
             // 取得した更新対象のユーザポイント情報をループで回す。
			for(int i=0;i<userPointDtoList.size();i++) {
				
				// ループのインデックスが取得した更新対象のユーザポイント情報の数より多い場合、「ループ終了②」に処理を移る。
				if (i > userPointDtoList.size()) {
					break;
				}

				// レベル情報DTOインスタンス作成
				LevelInfoDto levelInfoDto = new LevelInfoDto();

				// 以下のようにユーザID、レベル、更新日時を設定する。
				UserPointDto userPointDto = new UserPointDto();
				userPointDto = userPointDtoList.get(i);

				// ポイントが9999を超える場合
				if (userPointDto.getPoint() > 9999) {

					// 異常終了ログを出力する。メッセージID：MSG00003E
					// CheckExceptionをスローする。
					throw new ApplicationException(MessageID.INCORRECT_DATA_ERROR);

					// ポイントが2000以上の場合
				} else if (userPointDto.getPoint() >= 2000) {

					// レベルを2に設定する。
					levelInfoDto.setLevel(2);

					// ポイントが500以上の場合
				} else if (userPointDto.getPoint() >= 500) {

					// レベルを1に設定する。
					levelInfoDto.setLevel(1);

					// 上記以外の場合
				} else {

					// レベルを0に設定する。
					levelInfoDto.setLevel(0);
				}

				// ユーザIDを設定する
				levelInfoDto.setUserID(userPointDto.getUserID());

				// 更新日時は「パラメータ設定」で設定した「バッチ実行時の日時」にする。
				levelInfoDto.setUpdateDate(levelUpdateDate);

				// レベル情報更新
				LevelInfoDao levelInfoDa = new LevelInfoDao(con);

				// LevelInfoDao.updateLevelInfo()を呼び出し、レベル情報を更新する。
				// パラメータは「レベル情報インスタンス作成」で作成したレベル情報DTOインスタンス
				levelInfoDa.updateLevelInfo(levelInfoDto);		
				
				 // ループ終了②
				 // Connection.commit()を呼び出し、コミットを実行する。
	             if((i+1)%3==0) {
						con.commit();
	             }	                 
			}	
		} catch (ApplicationException e1) {
		 
			//処理の内、例外が発生した場合、ロールバックを行い、発生した例外のそのままスローする。
			con.rollback();	
			throw e1;
			
		}  finally {
			
			// コネクション切断
			//Connection.disconnect()を呼び出し、コネクションを切断する。
			 con.disconnect();
			
		}
	}
}
