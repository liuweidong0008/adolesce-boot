package com.boot.gdMemberRecord.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.boot.gdMemberRecord.entity.MemberRecord;
import com.boot.gdMemberRecord.mapper.MemberRecordMapper;
import com.boot.gdMemberRecord.service.IMemberRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwd
 * @since 2018-03-02
 */
@Service
public class MemberRecordServiceImpl extends ServiceImpl<MemberRecordMapper, MemberRecord> implements IMemberRecordService {
	@Autowired
	private MemberRecordMapper recordMapper;
	@Override
	public List<MemberRecord> queryByOrgId(String orgId) {
		return this.recordMapper.queryByOrgId(orgId);
	}
}