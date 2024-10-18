package edu.kh.project.common.scheduling.Service;

import java.util.List;

public interface SchedulingService {

	/** DB에 기록된 모든 파일명 조회
	 * @return List
	 */
	List<String> getDbFileNameList();

}
