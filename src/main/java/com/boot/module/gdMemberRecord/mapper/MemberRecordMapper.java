package com.boot.module.gdMemberRecord.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.boot.module.gdMemberRecord.entity.MemberRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
