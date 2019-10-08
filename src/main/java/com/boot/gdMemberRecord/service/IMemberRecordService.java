package com.boot.gdMemberRecord.service;

import com.boot.gdMemberRecord.entity.MemberRecord;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwd
 * @since 2018-03-02
 */
public interface IMemberRecordService extends IService<MemberRecord> {
	 List<MemberRecord> queryByOrgId(String orgId);
}
