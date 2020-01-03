package com.boot;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.boot.module.gdMemberRecord.entity.MemberRecord;
import com.boot.module.gdMemberRecord.service.IMemberRecordService;
import com.boot.module.user.entity.User;
import com.boot.module.user.service.IUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class) 
@SpringBootTest
public class MybatisPlusTest{
	@Autowired
    private IUserService userServiceImpl;
	@Autowired
    private IMemberRecordService memberRecordServiceImpl;
	
	@Test
	public void test1(){
		Wrapper<User> userQueryWrapper = new EntityWrapper<User>();
		userQueryWrapper.eq("age", 22);
		//userQueryWrapper.in("flag", "1");
		List<User> userList = this.userServiceImpl.selectList(userQueryWrapper);
		System.err.println(userList);
	}
	
	/**
	 * eq	
	 * ne
	 */
	@Test
	public void testEq() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.eq("serial_no", "KXD1491534271296521");
		recordQueryWrapper.eq("org_id","201608050001");
		//WHERE (serial_no = ? AND org_id = ?) 
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testNe() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.eq("serial_no", "KXD1491446816718581");
		recordQueryWrapper.ne("org_id","201612220001");
		//WHERE (serial_no = ? AND org_id <> ?) 
		this.selectList(recordQueryWrapper);
	}
	
	/**
	 * in  	
	 * notIn
	 */
	@Test
	public void testIn1() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.in("serial_no","KXD1491534271296521,KXD151022561733751,801828121515491393957641");
		//WHERE (serial_no IN (?,?,?)) 
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testIn2() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		String[] arr = {"KXD1491369102379331","KXD150675297785607","KXD1491397184622312"};
		recordQueryWrapper.in("serial_no", arr);
		//WHERE (serial_no IN (?,?,?)) 
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testIn3() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		List<String> list = new ArrayList<String>();
		list.add("KXD1491369102379331");
		list.add("KXD150675297785607");
		list.add("KXD1491397184622312");
		recordQueryWrapper.in("serial_no",list);
		//WHERE (serial_no IN (?,?,?)) 
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testNotIn() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		List<String> list = new ArrayList<String>();
		list.add("KXD1491369102379331");
		list.add("KXD150675297785607");
		list.add("KXD1491397184622312");
		recordQueryWrapper.notIn("serial_no", list);
		//WHERE (serial_no NOT IN (?,?,?)) 
		this.selectList(recordQueryWrapper);
	}
	
	/**
	 * like
	 * notLike
	 */
	@Test
	public void testlike1() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.like("serial_no", "KXD1491369102379331");
		//WHERE (serial_no LIKE ?)  	%KXD1491369102379331%
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testlike2() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.like("serial_no", "KXD1491369102379331", SqlLike.CUSTOM);
		//WHERE (serial_no LIKE ?)  	KXD1491369102379331
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testlike3() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.like("serial_no", "KXD1491369102379331", SqlLike.DEFAULT);
		//WHERE (serial_no LIKE ?)  	%KXD1491369102379331%
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testlike4() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.like("serial_no", "KXD1491369102379331", SqlLike.LEFT);
		//WHERE (serial_no LIKE ?)  	%KXD1491369102379331
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testlike5() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.like("serial_no", "KXD1491369102379331", SqlLike.RIGHT);
		//WHERE (serial_no LIKE ?)  	KXD1491369102379331%
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testNotlike1() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.notLike("currency", "CN");
		//WHERE (currency NOT LIKE ?)  	%CN%	备注:currency为 null的不会被检索出来
		this.selectList(recordQueryWrapper);
	}

	/**
	 * isNull
	 * is not null 
	 */
	@Test
	public void testIsNull() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.isNull("serial_no");
		//WHERE (serial_no IS NULL) 
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testIsNotNull() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.isNotNull("e_serial_no");
		//WHERE (e_serial_no IS NOT NULL) 
		this.selectList(recordQueryWrapper);
	}
	
	/**
	 * between  xx <= xx <= xx		notBetween
	 * gt lt    xx < xx < xx
	 * ge le    xx <= xx <= xx
	 * 
	 */
	@Test
	public void testBetween() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime = format.parse("2017-03-25 15:38:46");
		Date endTime = format.parse("2017-03-25 16:25:01");
		
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.between("transfer_time", startTime, endTime);
		// WHERE (transfer_time BETWEEN ? AND ?) 			2017-03-25 15:38:46.0(Timestamp), 2017-03-25 16:25:01.0(Timestamp)
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testGtLt() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime = format.parse("2017-03-25 15:38:46");
		Date endTime = format.parse("2017-03-25 16:25:01");
		
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.gt("transfer_time", startTime);
		recordQueryWrapper.lt("transfer_time", endTime);
		
		//WHERE (transfer_time > ? AND transfer_time < ?) 		2017-03-25 15:38:46.0(Timestamp), 2017-03-25 16:25:01.0(Timestamp)
		this.selectList(recordQueryWrapper);
	}
	@Test
	public void testGeLe() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime = format.parse("2017-03-25 15:38:46");
		Date endTime = format.parse("2017-03-25 16:25:01");
		
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.ge("transfer_time", startTime);
		recordQueryWrapper.le("transfer_time", endTime);
		
		//WHERE (transfer_time >= ? AND transfer_time <= ?)  		2017-03-25 15:38:46.0(Timestamp), 2017-03-25 16:25:01.0(Timestamp)
		this.selectList(recordQueryWrapper);
	}
	
	/**
	 * or(xx) or(xx,xx) :在原有条件括号内拼接
	 * or()  orNew(xx) orNew(xx,xx) :新建条件括号拼接
	 */
	@Test
	public void testOr() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		
		//示例一
		/*recordQueryWrapper.eq("serial_no", "KXD1491446816718581");
		recordQueryWrapper.or();
		recordQueryWrapper.eq("org_id","201612220001");
		recordQueryWrapper.eq("serial_no", "123123");*/
		//WHERE (serial_no = ?) OR (org_id = ? AND serial_no = ?)
				
		//示例二
		recordQueryWrapper.eq("serial_no", "KXD1491446816718581");
		recordQueryWrapper.or("org_id = {0}","201612220001");
		recordQueryWrapper.or("status = 4");
		
		recordQueryWrapper.orNew("card_no = '1111'"); 
		recordQueryWrapper.eq("serial_no", "123123");
		
		recordQueryWrapper.orNew("card_no = {0}","2222"); 
		//WHERE (serial_no = ? OR org_id = ? OR status = 4) OR (card_no = '1111' AND serial_no = ?) OR (card_no = ?)
		this.selectList(recordQueryWrapper);
	}
	
	
	/**
	 * and(xx) and(xx,xx) :在原有条件括号内拼接
	 * and()  andNew(xx) andNew(xx,xx) :新建条件括号拼接
	 */
	@Test
	public void testAnd() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		
		//示例一
		/*recordQueryWrapper.eq("serial_no", "KXD1491446816718581");
		recordQueryWrapper.and();
		recordQueryWrapper.eq("org_id","201612220001");
		recordQueryWrapper.eq("serial_no", "123123");*/
		//WHERE (serial_no = ?) AND (org_id = ? AND serial_no = ?)
				
		//示例二
		recordQueryWrapper.eq("serial_no", "KXD1491446816718581");
		recordQueryWrapper.and("org_id = {0}","201612220001");
		recordQueryWrapper.and("status = 4");
		
		recordQueryWrapper.andNew("card_no = '1111'"); 
		recordQueryWrapper.eq("serial_no", "123123");
		
		recordQueryWrapper.andNew("card_no = {0}","2222"); 
		//WHERE (serial_no = ? AND org_id = ? AND status = 4) AND (card_no = '1111' AND serial_no = ?) AND (card_no = ?) 
		this.selectList(recordQueryWrapper);
	}
	
	/**
	 * orderBy (false:desc  true:asc 默认)
	 */
	@Test
	public void testOrderBy() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.in("status","2");
		
		//recordQueryWrapper.orderBy("org_id,create_time");
		//WHERE (status IN (?)) ORDER BY org_id,create_time 
		
		recordQueryWrapper.orderBy("org_id,create_time",false);
		//WHERE (status IN (?)) ORDER BY org_id,create_time DESC 
		this.selectList(recordQueryWrapper);
	}
	
	/**
	 * groupBy
	 */
	@Test
	public void testGroupBy() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.in("status","0,1,2");
		recordQueryWrapper.groupBy("org_id");
		recordQueryWrapper.groupBy("trade_type");	//等价于 recordQueryWrapper.groupBy("org_id,trade_type");
		//WHERE (status IN (?,?,?)) GROUP BY org_id, trade_type 
		this.selectList(recordQueryWrapper);
	}
	
	/**
	 * having
	 */
	@Test
	public void testHaving() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.in("status","0,1,2");
		recordQueryWrapper.groupBy("org_id");
		recordQueryWrapper.having("COUNT(1)>1 and cardNo = '6200612953158062109'");	
		recordQueryWrapper.having("cardNo = {0}","6200612953158062109");
		//WHERE (status IN (?,?,?)) GROUP BY org_id HAVING (COUNT(1)>1 and cardNo = '6200612953158062109' AND cardNo = ?) 
		this.selectList(recordQueryWrapper);
	}
	
	/**
	 *where
	 */
	@Test
	public void testWhere() throws ParseException{
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.where("serial_no = {0}", "KXD1491446816718581");
		recordQueryWrapper.eq("org_id","201612220001");
		
		//WHERE (serial_no = ? AND org_id = ?) 
		this.selectList(recordQueryWrapper);
	}
	
	@Test
	public void testSelectById(){
		//MemberRecord record = this.memberRecordServiceImpl.selectById("1");
		//或
		/*User user = new User();
		user.setId("1");
		MemberRecord record = this.memberRecordServiceImpl.selectById(user);*/
		
		//WHERE id=? 
	}
	
	@Test
	public void testSelectOne(){
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.where("serial_no = {0}", "KXD1491446816718581");
		recordQueryWrapper.eq("org_id","201612220001");
		
		MemberRecord record = this.memberRecordServiceImpl.selectOne(recordQueryWrapper);
		//WHERE (serial_no = ? AND org_id = ?) 
	}
	
	@Test
	public void testSelectBatchIds(){
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			ids.add(i+"");
		}
		List<MemberRecord> record = this.memberRecordServiceImpl.selectBatchIds(ids);
		//WHERE (serial_no = ? AND org_id = ?) 
	}
	
	@Test
	public void testSelectCount(){
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.where("serial_no = {0}", "KXD1491446816718581");
		recordQueryWrapper.eq("org_id","201612220001");
		int count = this.memberRecordServiceImpl.selectCount(recordQueryWrapper);
		//SELECT COUNT(1) FROM gd_member_record WHERE (serial_no = ? AND org_id = ?) 
	}
	
	@Test
	public void testSelectByMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("serial_no","KXD1491446816718581");
		map.put("org_id","201612220001");
		//map.put("", value)
		List<MemberRecord> record = this.memberRecordServiceImpl.selectByMap(map);
		//WHERE org_id = ? AND serial_no = ? 
	}
	
	@Test
	public void testSelectMap(){
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.where("serial_no = {0}", "KXD1491446816718581");
		Map<String,Object> map = this.memberRecordServiceImpl.selectMap(recordQueryWrapper);
		//WHERE (serial_no = ?) 
		System.err.println(map);
	}
	
	@Test
	public void testSelectByPageHelper(){
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.orderBy("transfer_time", false);
		PageHelper.startPage(1,10);	//开始页(从1开始)、查询条数
		PageHelper.orderBy("transfer_time desc");//与wrapper同时设置排序，PageHper生效
		//PageHelper.startPage(1,10,"transfer_time desc");
		
        Page<MemberRecord> list = (Page<MemberRecord>) this.memberRecordServiceImpl.selectList(recordQueryWrapper);
        List<MemberRecord> recordList = list.getResult();
        //ORDER BY transfer_time DESC limit ?,? 
        for(MemberRecord record:recordList){
        	System.err.println(record);
        }
        //Page{count=true, pageNum=1, pageSize=10, startRow=0, endRow=10, total=900, pages=90, countSignal=false, orderBy='null', orderByOnly=false, reasonable=false, pageSizeZero=true}
	}
	
	@Test
	public void testSelectByPlusPage(){
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		//recordQueryWrapper.orderBy("transfer_time", false);
		
		com.baomidou.mybatisplus.plugins.Page<MemberRecord> page = new com.baomidou.mybatisplus.plugins.Page<MemberRecord>(1,10,"transfer_time");
		page.setAsc(false);
		com.baomidou.mybatisplus.plugins.Page<MemberRecord> pageResult = this.memberRecordServiceImpl.selectPage(page, recordQueryWrapper);
        List<MemberRecord> recordList = pageResult.getRecords();
        //ORDER BY transfer_time DESC limit ?,?
        for(MemberRecord record:recordList){
        	System.err.println(record);
        }
        //Page{count=true, pageNum=1, pageSize=10, startRow=0, endRow=10, total=900, pages=90, countSignal=false, orderBy='null', orderByOnly=false, reasonable=false, pageSizeZero=true}
        //Page{count=true, pageNum=0, pageSize=10, startRow=0, endRow=0, total=900, pages=90, countSignal=false, orderBy='null', orderByOnly=false, reasonable=false, pageSizeZero=true}
	}
	
	@Test
	public void testSelectMaps(){
		Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
		recordQueryWrapper.eq("org_id","201612220001");
		List<Map<String,Object>> list = this.memberRecordServiceImpl.selectMaps(recordQueryWrapper);
		//WHERE (org_id = ?) 
		for(Map<String,Object> map:list){
			System.err.println(map);
		}
	}
	
	@Test
	public void testSelectByCustom(){
		List<MemberRecord> list = this.memberRecordServiceImpl.queryByOrgId("201612220001");
		System.err.println(list);
	}
	
	@Test
	public void testInsert() throws ParseException{
		User user = new User();
		user.setId("1");
		user.setAge(15);
		user.setBirthDay(new Date());
		user.setEmail("363426466@qq.com");
		user.setFlag(1L);
		user.setHobby("1,3");
		user.setName("lwd");
		user.setPassword("000000");
		user.setSex("1");
		user.setSalt("1");
		
		//一、单条插入不为null的字段
		//this.userServiceImpl.insert(user);
		//INSERT INTO user ( id, age, birth_day, email, flag, hobby, `name`, `password`, salt, sex ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
		
		//二、单条插入所有字段
		//this.userServiceImpl.insertAllColumn(user);
		//INSERT INTO user ( id,age,birth_day,email,flag,hobby,`name`,`password`,phone,`role`,salt,saraly,sex,usernum ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,? ) 
		
		//三、批量插入字段不为null的字段
		//this.userServiceImpl.insertBatch(getUsers());
		//INSERT INTO user ( id, age, birth_day, email, flag, hobby, `name`, `password`, salt, sex ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
		
		//四、批量插入字段不为null的字段,每插入2条进行一次flush
		//this.userServiceImpl.insertBatch(getUsers()，2);
		
		//先update，成功则返回，如update不成功，再insert(老数据直接update，新数据先update，再insert)
		//this.userServiceImpl.insertOrUpdate(user);
		//UPDATE user SET age=?, birth_day=?, email=?, flag=?, hobby=?, `name`=?, `password`=?, salt=?, sex=? WHERE id=? 
		//INSERT INTO user ( id, age, birth_day, email, flag, hobby, `name`, `password`, salt, sex ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
		
		//先update，成功则返回，如update不成功，再insert(老数据直接update，新数据先update，再insert)
		//this.userServiceImpl.insertOrUpdateBatch(getUsers());
		
		//批量插入更新,每插入更新2条进行一次flush
		this.userServiceImpl.insertOrUpdateBatch(getUsers(), 2);
	}
	
	@Test
	public void testUpdate(){
		User user = new User();
		user.setId("10");
		user.setAge(10);
		user.setBirthDay(new Date());
		user.setEmail("3@qq.com");
		user.setFlag(2L);
		user.setHobby("1,5");
		user.setName("ly");
		user.setPassword("000000");
		user.setSex("10");
		user.setSalt("10");
		
		//一
		//this.userServiceImpl.updateById(user);
		//UPDATE user SET age=?, birth_day=?, email=?, flag=?, hobby=?, `name`=?, `password`=?, salt=?, sex=? WHERE id=? 
	
		//二
		//this.userServiceImpl.updateAllColumnById(user);
		//UPDATE user SET age=?,birth_day=?,email=?,flag=?,hobby=?,`name`=?,`password`=?,phone=?,`role`=?,salt=?,saraly=?,sex=?,usernum=? WHERE id=? 
	
		//三
		/*Wrapper<User> userWrapper = new EntityWrapper<User>();
		userWrapper.in("id", "1");
		userWrapper.eq("flag","1");
		this.userServiceImpl.update(user, userWrapper);*/
		//UPDATE user SET age=?, birth_day=?, email=?, flag=?, hobby=?, `name`=?, `password`=?, salt=?, sex=? WHERE (id IN (?) AND flag = ?) 	//不会更新主键以及为属性为null的字段
		
		//四  根据主键批量更新   默认每更新30条flush
		//this.userServiceImpl.updateBatchById(getUsers());
		
		//五  根据主键批量更新   可指定每更新多少条flush
		//this.userServiceImpl.updateBatchById(getUsers(),4);
	}
	
	private List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setId(i+"");
			user.setAge(15);
			user.setBirthDay(new Date());
			user.setEmail("363426466@qq.com");
			user.setFlag(1L);
			user.setHobby("1,3");
			user.setName("lwd");
			user.setPassword("000000");
			user.setSex("1");
			user.setSalt("1");
			users.add(user);
		}
		return users;
	}

	@Test
	public void testDelete() throws ParseException{
		//一
		/*User user = new User();
		user.setId("1");
		this.userServiceImpl.deleteById(user);*/	//DELETE FROM user WHERE id=? 
		
		//二
		//this.userServiceImpl.deleteById("1");	//DELETE FROM user WHERE id=? 
		
		//三
		/*Wrapper<User> userWrapper = new EntityWrapper<User>();
		userWrapper.in("id", "0,1,2,3,4,5,6,7,8,9");
		userWrapper.eq("flag","1");
		this.userServiceImpl.delete(userWrapper);*/  // DELETE FROM user WHERE (id IN (?,?,?,?,?) AND flag = ?) 
		
		//四
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			ids.add(i+"");
		}
		this.userServiceImpl.deleteBatchIds(ids);	//DELETE FROM user WHERE id IN ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ) 
		
		//五
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", "1");
		map.put("age", "15");
		map.put("birth_day", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parseObject("2017-12-19 18:19:03"));
		this.userServiceImpl.deleteByMap(map);*/ //DELETE FROM user WHERE id = ? AND age = ? AND birth_day = ? 
		
	}
	
	private void selectList(Wrapper<MemberRecord> recordQueryWrapper) {
		List<MemberRecord> records = this.memberRecordServiceImpl.selectList(recordQueryWrapper);
		for(MemberRecord record:records){
			System.err.println("------------printResult---------");
			System.err.println(record);
		}
	}
}
