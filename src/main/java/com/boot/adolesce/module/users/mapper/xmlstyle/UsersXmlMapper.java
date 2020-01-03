package com.boot.adolesce.module.users.mapper.xmlstyle;

import com.boot.adolesce.module.users.entity.Users;

import java.util.List;
import java.util.Map;

public interface UsersXmlMapper
{
  void insert(Users paramUsers);

  void batchInsert(List<Users> usersList);

  void deleteById(Long paramLong);

  void deleteByIds(Map<String, Object> ids);

  void deleteByIdsStr(String ids);

  void update(Users paramUsers);

  Users getById(Long paramLong);

  List<Users> queryByParam(Users user);
}