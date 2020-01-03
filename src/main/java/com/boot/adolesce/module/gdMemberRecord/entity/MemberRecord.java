package com.boot.adolesce.module.gdMemberRecord.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("gd_member_record")
public class MemberRecord extends Model<MemberRecord> {
	private static final long serialVersionUID = 1L;
	@TableId
	private String id;

	@TableField("member_id")
	private String memberId;

	@TableField("client_name")
	private String clientName;

	@TableField("serial_no")
	private String serialNo;

	@TableField("req_serial_no")
	private String reqSerialNo;

	@TableField("e_serial_no")
	private String eSerialNo;

	@TableField("trade_type")
	private String tradeType;
	private String amount;
	private String balance;
	private String currency;

	@TableField("transfer_time")
	private Date transferTime;

	@TableField("transfer_date")
	private String transferDate;

	@TableField("card_no")
	private String cardNo;

	@TableField("bank_no")
	private String bankNo;

	@TableField("bank_name")
	private String bankName;
	private String status;

	@TableField("fail_reason")
	private String failReason;
	private String remark;

	@TableField("create_time")
	private Date createTime;

	@TableField("update_time")
	private Date updateTime;

	@TableField("handling_charge")
	private String handlingCharge;

	@TableField("org_id")
	private String orgId;

	@TableField("batch_date")
	private Date batchDate;

	@TableField("batch_status")
	private String batchStatus;

	@TableField("apply_id")
	private String applyId;

	@TableField("fund_roll_in_status")
	private String fundRollInStatus;
	private String way;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}