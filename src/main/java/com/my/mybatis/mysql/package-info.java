package com.my.mybatis.mysql;

/**
 *  -- 内链接写法
 * SELECT t1.id, t1.nickname, t1.nickname, t2.role_id FROM land_admin_user t1, land_admin_user_role t2 where t1.id = t2.admin_id;
 * SELECT t1.id, t1.nickname, t1.nickname, t2.role_id FROM land_admin_user t1 INNER JOIN land_admin_user_role t2 on t1.id = t2.admin_id;
 * SELECT t1.id, t1.nickname, t1.nickname, t2.role_id FROM land_admin_user t1 JOIN land_admin_user_role t2 on t1.id = t2.admin_id;
 * SELECT t1.id, t1.nickname, t1.nickname, t2.role_id FROM land_admin_user t1 CROSS JOIN land_admin_user_role t2 on t1.id = t2.admin_id;
 * -- 也是内链接，区别是，这个强制左侧为驱动表，不会被mysql给优化
 * SELECT t1.id, t1.nickname, t1.nickname, t2.role_id FROM land_admin_user t1 STRAIGHT_JOIN land_admin_user_role t2 on t1.id = t2.admin_id;
 *
 * -- 外链接
 * EXPLAIN SELECT t1.id, t1.nickname, t1.nickname, t2.role_id FROM land_admin_user t1 LEFT JOIN land_admin_user_role t2 on t1.id = t2.admin_id;
 * SELECT t1.id, t1.nickname, t1.nickname, t2.role_id FROM land_admin_user t1 LEFT JOIN land_admin_user_role t2 on 1=1 where t1.id = t2.admin_id;
 *
 * SELECT t1.id, t1.nickname, t1.nickname, t2.role_id FROM land_admin_user t1 RIGHT JOIN land_admin_user_role t2 on t1.id = t2.admin_id;
 *
 * EXPLAIN format=json SELECT * from land_admin_user;
 *
 * -- 查看join buffer 大小
 * SHOW VARIABLES LIKE 'join_buffer_size';
 *
 * -- 查看表信息
 * show TABLE status like 'land_admin_user';
 * -- 我们来算算成本，以'SELECT * from land_admin_user where id >= 1372827476627161088 and id <= 1372828773623398400;'为例子
 * EXPLAIN SELECT * from land_admin_user where id >= 1372827476627161088 and id <= 1372828773623398400;
 * -- date_length/16/1024 就是页面数量 ，adminuser算出来是1个页面数
 * EXPLAIN format=json SELECT * from land_admin_user where id >= 1372827476627161088 and id <= 1372828773623398400;
 * -- 记录数为13
 *
 * -- io成本  1.0       cpu成本   0.2     1.1是mysql里面io成本的微调数，1.0是cpu成本的微调数
 * -- 全表扫描成本就出来咯。成本=1*1.0 + 1.1 + 13*0.2 + 1.0 = 5.7
 * -- 计算index_admin_id索引的成本
 * -- 一个扫描区间就会有一次io成本，我们可以知道扫描区间只有1个，记录数有2条。io成本=1*1.0=1，cpu成本 2*0.2 + 0.01（微调数）= 0.41
 * -- 回表 成本 io成本=2*1.0=2  cpu成本=2*0.2=0.4
 * -- 成本总计：1+0.41+2+0.4=3.81
 *
 * -- 计算uk_id索引的成本
 * -- 一个扫描区间就会有一次io成本，我们可以知道扫描区间只有1个，记录数有2条。io成本=1*1.0=1，cpu成本 2*0.2 + 0.01（微调数）= 0.41
 * -- 回表 成本 io成本=2*1.0=2  cpu成本=2*0.2=0.4
 * -- 成本总计：1+0.41+2+0.4=3.81
 *
 * -- mysql 在遇到普通索引和唯一索引时，有限使用唯一索引，所以使用的uk_id。注意mysql在比较成本的时候会扣除掉回表的cpu成本。比较完后会再算一次，就会加上这个成本
 *
 *
 * -- 查看in条件中，多少条以内可以精确查找记录数，如果大于这个值则用估算值。怎么估算呢？使用show index from land_admin_user;中的Cardinality值，索引列的条数/Cardinality*in中条件个数=估算值
 * show VARIABLES like '%dive%';
 *
 * show index from land_admin_user;
 */