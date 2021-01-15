package batches;

import java.util.List;

import common.Connection;
import dao.LevelInfoDao;
import dao.UserPointInfoDao;
import dto.LevelInfoDto;
import dto.UserPointDto;
import exception.ApplicationException;

public class BATCH0001 {

	public static void main(String[] args) {

		
		Connection con = new Connection();
		
		try {
			
			UserPointInfoDao dd = new UserPointInfoDao(con);
			
			List<UserPointDto> daf = dd.getUserPointInfoList();
			for (UserPointDto UserPointDto : daf) {
				System.out.println(UserPointDto.toString());
			}
		} catch (ApplicationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		LevelInfoDao dd = new LevelInfoDao(con);
		LevelInfoDto levelInfoDto = new LevelInfoDto();
		levelInfoDto.setUserID("eqe");
		levelInfoDto.setLevel(54);
		try {
			dd.updateLevelInfo(levelInfoDto);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
