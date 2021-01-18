package dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import common.Connection;
import common.Const;
import common.DBAccess;
import common.MessageID;
import dto.UserPointDto;
import exception.ApplicationException;

public class UserPointInfoDao extends DBAccess {

	private static String FILE_FULLPATH = "C:\\work\\UserPointInfo.txt";
	
	/**
	 * コンストラクタ
	 */
	public UserPointInfoDao(Connection con) {
		super(con, FILE_FULLPATH);
	}
	
	/**
	 * ユーザポイント情報取得
	 * @param レベル情報更新日時
	 * @return ユーザポイント情報リスト
	 * @throws ApplicationException 
	 * ①ファイル読み込みに失敗した場合
	 * ②データの日付フォーマットがYYYYMMDDではない場合
	 */
	public List<UserPointDto> getUserPointInfoList(String levelInfoUpdateDateStr) throws ApplicationException {
		// 戻り値
		List<UserPointDto> userPointDtoList = new ArrayList<>();
		Path path = Paths.get(FILE_FULLPATH);
		SimpleDateFormat sdf  = new SimpleDateFormat(Const.DATE_FORMAT_YYYYMMDD);
		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		    for (String line : lines) {
		    	String[] recode = line.split(Const.COMMA);
		    	
		    	Timestamp levelInfoUpdateDate = new Timestamp(sdf.parse(recode[5]).getTime());
		    	Timestamp now = new Timestamp(sdf.parse(levelInfoUpdateDateStr).getTime());
		    	if (now.compareTo(levelInfoUpdateDate) < 0) {
		    		UserPointDto userPointDto = new UserPointDto();
			    	userPointDto.setUserID(recode[0]);
			    	userPointDto.setUserNM(recode[1]);
			    	userPointDto.setPoint(Integer.valueOf(recode[2]));
			    	userPointDto.setUserInfoUpdateDate(new Timestamp(sdf.parse(recode[3]).getTime()));
			    	userPointDto.setPointInfoUpdateDate(new Timestamp(sdf.parse(recode[4]).getTime()));
			    	userPointDto.setLevelInfoUpdateDate(new Timestamp(sdf.parse(recode[5]).getTime()));
			    	userPointDtoList.add(userPointDto);
		    	}
		    }
		} catch (IOException e) {
			throw new ApplicationException(MessageID.IO_EXCEPTION);
		} catch (ParseException e) {
			throw new ApplicationException(MessageID.DATE_FORMAT_ERROR);
		}
		return userPointDtoList;
	}
}
