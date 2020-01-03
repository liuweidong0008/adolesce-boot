package com.boot.adolesce.module.gdMemberRecord.service;

import com.baomidou.mybatisplus.service.IService;
import com.boot.adolesce.module.gdMemberRecord.entity.MemberRecord;

import java.util.List;

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
