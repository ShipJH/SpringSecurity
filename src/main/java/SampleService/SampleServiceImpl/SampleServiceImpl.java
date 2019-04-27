package SampleService.SampleServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securiry.study.dao.SampleMapper;
import com.securiry.study.vo.UserVo;

import SampleService.SampleService;

@Service
public class SampleServiceImpl implements SampleService {
	
	//#4 dao의 의존성 주입
	@Autowired
	private SampleMapper sampleMapper;
	
	/**
	 * 유저정보를 가져온다.
	 * @return
	 */
	@Override
	public UserVo getUser() {

		return sampleMapper.getUser();
	}

}
