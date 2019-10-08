package com.boot.gdMemberRecord.mapper;

import com.boot.gdMemberRecord.entity.MemberRecord;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author lwd
 * @since 2018-03-02
 */
public interface MemberRecordMapper extends BaseMapper<MemberRecord> {
	List<MemberRecord> queryByOrgId(@Param("orgId") String orgId);
}
