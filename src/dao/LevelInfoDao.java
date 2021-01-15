package dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;

import common.Connection;
import common.Const;
import common.DBAccess;
import common.MessageID;
import dto.LevelInfoDto;
import exception.ApplicationException;

public class LevelInfoDao extends DBAccess {

	private static String FILE_FULLPATH = "C:\\work\\LevelInfo.txt";
	
	/**
	 * コンストラクタ
	 */
	public LevelInfoDao(Connection con) {
		super(con, FILE_FULLPATH);
	}
	
	/**
	 * ユーザポイント情報取得
	 * 
	 * @return ユーザポイント情報リスト
	 * @throws ApplicationException 
	 * ①ファイル読み込みに失敗した場合
	 * ②データの日付フォーマットがYYYYMMDDではない場合
	 */
	public int updateLevelInfo(LevelInfoDto levelInfoDto) throws ApplicationException {
		// 戻り値
		int updateCnt = 0;
		Path path = Paths.get(FILE_FULLPATH);
		SimpleDateFormat sdf  = new SimpleDateFormat(Const.DATE_FORMAT_YYYYMMDDHHMMSS);
		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		    for (int i = 0; i < lines.size(); i++) {
		    	String line = lines.get(i);
		    	String[] recode = line.split(Const.COMMA);
		    	if (levelInfoDto.getUserID().equals(recode[0])) {
		    		recode[1] = String.valueOf(levelInfoDto.getLevel());
		    		recode[2] = sdf.format(levelInfoDto.getUpdateDate());
		    		line = recode[0] + Const.COMMA + recode[1] + Const.COMMA + recode[2];
		    		updateCnt = updateCnt + 1;
		    	}
		    	line = line + Const.RFLF;
		    	lines.set(i, line);
		    }
		    if (updateCnt > 0) {
		    	super.addFileInfo(lines);
		    }
		} catch (IOException e) {
			throw new ApplicationException(MessageID.IO_EXCEPTION);
		}
		return updateCnt;
	}
}
