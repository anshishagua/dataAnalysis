# TIS (Tag Index System) 
TIS可以定义标签和指标,计算任务依赖以及结果展示。

标签和指标的文法定义见`ExpressionRule.g4`,使用antlr4解析词法及语法,构建语法树。文法类似于SQL表达式,支持各种算术表达式,增加了各种函数支持:

- 四则运算
  - 加
  - 减
  - 乘
  - 除
  - 求余
- 布尔表达式
  - AND
  - OR
  - NOT
- 比较运算
  - &gt;
  - &gt;=
  - &lt;
  - &lt;=
  - !=
  - LIKE
  - NOT LIKE
  - IS NULL
  - IS NOT NULL
- 逻辑表达式
  - CASE WHEN ... THEN ... ELSE ... END
- 函数支持
  - 数学函数
    - sin
    - cos
    - floor
    - ceil
  - 字符串函数
    - concat
    - length
    - trim
    - replace
    - lower_case
    - upper_case
  - 日期及时间函数
    - current_date
    - current_timestamp
    - year
    - month
    - day
    - hour
    - minute
    - second
    - date_add
    - date_diff
  - 聚合函数
    - avg
    - sum
    - max
    - min
    - count


作为示例的模型

雇员信息  
<pre>
employee_info  
(  
  id BIGINT,  
  name STRING,  
  age INT,  
  birth_date DATE  
)  
</pre>


雇员银行账户  
<pre>
employee_account  
(  
  id BIGINT,  
  bank STRING,  
  employee_id BIGINT  
)  
</pre>


雇员交易信息  
<pre>
employee_transaction  
(  
  id BIGINT,  
  account_id BIGINT,  
  amount DOUBLE,  
  date DATE  
)  
</pre>


1  模型  
模型的概念类似于数据库中的表,模型包含模型名、创建时间、描述等基本信息,
最主要的是模型包含的字段(字段名称、类型、是否为主键)  
2  模型关系  
定义两个模型之间的关联关系  
类似于数据库中两个表的关联关系,模型间通过字段相等进行关联,关联类型包括内连接、左外连接、右外连接    
比如  
 employee_account.employee_id 左关联 employee_info.id  
 employee_account.id 左关联 employee_transaction.account_id    
3  标签  
标签用于给模型中的对象贴上一些记录,比如某个银行客户是`男性客户`,`中年人`,`VIP客户`等等属性  
基于某个模型定义标签,这里的标签其实是标签组的概念,一个标签包含标签值,标签规则定义了这个标签的对象需要满足的条件,  
模型中的某一个对象只能属于某一个标签,因此标签值存在优先级,已经计算出来属于某个标签值的对象不会属于其它标签值  
比如标签: `年龄区间` 包含3个标签值:`青年`, `中年`, `老年`  
对应的规则表达式如下:  
<pre>
employee_info.age &lt;= 30  
employee_info.age &gt;= 60 AND employee_info.age &lt;= 60  
employee_info.age &lt; 60  
</pre>
    
标签规则中还可以包含以上列出的函数等表达式以及聚合函数(sum, avg, count, max, min)  
比如假设标签: 中年雇员经济情况 包含1个标签值:富有,定义规则如下:  
<pre>
employee_info.age &lt;= 30 
AND employee_info.age &lt;= 40
AND sum(employee_transaction.amount) > 100000 
</pre> 

    
不同于SQL,配置规则不需要指定模型间的关联关系也没有HAVING子句
模型关系根据配置的模型关系表自动构建,同时支持模型间的间接关联,
比如模型关系表没有定义employee_info和employee_transaction的关系,但是可以根据已有信息构建出通过中间依赖关联起来的模型关联
因此生成SQL时,会生成如下的join条件:  
<pre>
employee_info
LEFT JOIN employee_account ON employee_account.employee_id = employee_info.id
LEFT JOIN employee_transaction ON employee_transaction.account_id = employee_account.id 
</pre>

遇到需要解决的技术问题  
1 根据模型的关联关系列表生成SQL时构建模型间的JOIN条件  

生成JOIN算法:
构建一棵从贴标模型为根的模型关联树  
算法描述
前提: 解析规则,得到规则中涉及的所有模型  
1) 从贴标对象开始,使用深度优先遍历及回溯方法找到从贴标对象到所有其它模型的路径
2) 如果到某个模型存在多条路径则报错:路径不唯一返回
3) 根据路径列表构建模型关系树
例如模型a, b, c, d
a为贴标对象
a 到 b, c, d的路径分别为[(a, b), (a, c), (a, c, d)]
则模型关系树为
<pre>
                  a  
                 / \  
                b   c  
                     \  
                      d  
</pre>                      
构建的JOIN条件也使用一棵树表达  
<pre>      
                       JOIN
                      /    \
                    JOIN    d
                   /    \
                 JOIN    c
                /    \
               a      b
</pre>  
              
                                  
4) 根据模型关系树即可构建出JOIN条件  
根据如以上例子,构建的模型关系树如下:  
<pre>
            employee_info  
                /   
           employee_account    
              /  
         employee_transaction  
</pre>  
每个模型为一个树节点,之间的关联通过斜线/表示.按层遍历这棵树,父子节点增加JOIN条件即可生成SQL        

2 模型存在复合主键的支持  
标签计算结果写入到tag_xxx表,xxx表示标签id  
<pre>
tag_xxx  
(  
  id [BIGINT|STRING],  
  tag_value_id BIGINT,  
  tag_value_value STRING
)  
</pre>

`id`字段根据各个主键的顺序通过字符串拼接方式`concat`生成,如果是复合主键则id类型为`STRING`,否则跟单主键类型一致  
`tag_value_id`为标签值id,`tag_value_value`为标签值
3 规则中存在聚合函数
不同于SQL语句,规则表达式中可以包含聚合,例如之前定义的中年雇员经济情况,传统的SQL语句中聚合条件的过滤是在HAVING子句中实现  
处理带有聚合函数的,因为聚合表达式前的布尔条件可能为OR,因此不能使用HAVING子句,解决的办法是把每个聚合表达式变成对于某个临时表字段的引用,因而消去聚合表达式  

这样 sum(employee_transaction.amount) 变成  
<pre>
CREATE TABLE IF NOT EXISTS `tmp_table_02be02f7cec24cc6904211da6c2661d4` (`id` BIGINT, `aggregation_result` DOUBLE);
INSERT OVERWRITE TABLE `tmp_table_02be02f7cec24cc6904211da6c2661d4` SELECT `employee_info`.`id`, sum(`employee_transaction`.`amount`) FROM `employee_info` LEFT JOIN `employee_account` ON (`employee_info`.`id` = `employee_account`.`employee_id`) LEFT JOIN `employee_transaction` ON (`employee_account`.`id` = `employee_transaction`.`account_id`)GROUP BY `employee_info`.`id`; 
</pre>  

这样原始的表达式对聚合做替换后变成  
<pre>
employee_info.age &lt;= 30 
AND employee_info.age &lt;= 40
AND tmp_table_02be02f7cec24cc6904211da6c2661d4.aggregation_result > 100000 
</pre>

最终生成的执行SQL列表类似
<pre>
CREATE TABLE IF NOT EXISTS tag_6 (`id` BIGINT, `tag_value_id` BIGINT, `tag_value_value` STRING);
CREATE TABLE IF NOT EXISTS `tmp_table_02be02f7cec24cc6904211da6c2661d4` (`id` BIGINT, `aggregation_result` DOUBLE);
INSERT OVERWRITE TABLE `tmp_table_02be02f7cec24cc6904211da6c2661d4` SELECT `employee_info`.`id`, sum(`employee_transaction`.`amount`) FROM `employee_info` LEFT JOIN `employee_account` ON (`employee_info`.`id` = `employee_account`.`employee_id`) LEFT JOIN `employee_transaction` ON (`employee_account`.`id` = `employee_transaction`.`account_id`)GROUP BY `employee_info`.`id`;
INSERT OVERWRITE TABLE `tag_6` SELECT `employee_info`.`id`, 15, '富有' FROM `employee_info` INNER JOIN `tmp_table_02be02f7cec24cc6904211da6c2661d4` ON (`tmp_table_02be02f7cec24cc6904211da6c2661d4`.`id` = `employee_info`.`id`) WHERE `tmp_table_02be02f7cec24cc6904211da6c2661d4`.`aggregation_result` > 10000 AND `employee_info`.`id` NOT IN (SELECT `tag_6`.`id` FROM `tag_6`);
DROP TABLE IF EXISTS `tmp_table_02be02f7cec24cc6904211da6c2661d4`; 
</pre>

4 标签值的优先级处理
根据标签值顺序处理,为了使得已经处理过的ID不再重复,增加`NOT IN (SELECT tag_xxx.id FROM tag_xxx)`条件。NOT IN这种处理不太高效,考虑改成使用CASE WHEN表达式组织SQL  
<pre>
CREATE TABLE IF NOT EXISTS tag_9 (`id` STRING, `tag_value_id` BIGINT, `tag_value_value` STRING);
INSERT OVERWRITE TABLE `tag_9` SELECT concat(`multi_primary_key_table`.`name`, '_', `multi_primary_key_table`.`age`) AS id, 18, '男' FROM `multi_primary_key_table` WHERE `multi_primary_key_table`.`sex` = '男' AND concat(`multi_primary_key_table`.`name`, '_', `multi_primary_key_table`.`age`) NOT IN (SELECT `tag_9`.`id` FROM `tag_9`);
INSERT INTO TABLE `tag_9` SELECT concat(`multi_primary_key_table`.`name`, '_', `multi_primary_key_table`.`age`) AS id, 19, '女' FROM `multi_primary_key_table` WHERE `multi_primary_key_table`.`sex` = '女' AND concat(`multi_primary_key_table`.`name`, '_', `multi_primary_key_table`.`age`) NOT IN (SELECT `tag_9`.`id` FROM `tag_9`); 
</pre>
 
               
4  指标定义 
指标包括一组维度和度量,类比SQL查询: 
<pre>
SELECT employee_info.id, employee_account.id, sum(employee_transaction.amount)
FROM employee_info
          LEFT JOIN employee_account ON employee_account.employee_id = employee_info.id
          LEFT JOIN employee_transaction ON employee_transaction.account_id = employee_account.id
GROUP BY employee_info.id, employee_account.id;
</pre>

维度就是GROUP BY中的字段:employee_info.id, employee_account.id
度量就是聚合表达式:sum(employee_transaction.amount)
维度和度量都有自己的表达式,也可以引用函数,不同只是在于维度不能引用聚合函数

指标类型可以是基础指标(表达式中的字段只引用基础模型),也可以是派生指标(表达式中的字段只引用某个基础指标)  
指标表达式也需要变换成可执行的SQL,同样需要自动构建模型间的关联关系  
指标的维度可以是某个标签,因此需要处理标签


遇到需要解决的技术问题
1 模型间的关联关系生成  
类似标签的关联关系生成,但是又不同于标签的关联关系生成,在标签中模型关联树的根总是贴标对象模型,指标表达式中不存在这样的根  
处理方式是遍历模型列表,找到一个模型,从这个模型有到各个其它模型的唯一路径,因而可以作为根,后续的处理方式与标签一致  
2 派生指标处理
这个比较简单,因为派生指标只能引用一个基础指标,如果把指标也看成一个模型的话,相当于只涉及一个模型,只需要把维度表达式放到select和group by中,度量放到select中  
3 维度中包含标签的处理
需要标签表和模型表做相应的JOIN

5  计算和调度

计算相对简单,因为生成了相应的SQL,只需要通过JDBC连接HIVE执行创建和写入相应的表即可  
调度相对复杂,因为需要执行两种调度:
1.基于时间的CRON表达式调度   
2.基于任务依赖关系的调度  
因为标签依赖于模型,因此需要模型数据到达才能计算和执行  
指标依赖于模型,也可能依赖于标签或者标签,因此需要等待相应的标签和指标计算完才能计算  
同时,任务可能执行失败,也需要监控和重启
在定义标签和指标的时候,因为可以解析出依赖的模型、标签、指标,因此任务的依赖关系就得到了,只需要保存即可  
基于时间的调度,使用quartz进行,在计算的时候查看当前任务依赖的前序任务是否执行成功,若没有,则当前任务标记为失败,等待前序任务执行完成  
若当前任务执行失败,则标记为失败,有一个定时任务不断扫描任务执行表,对于失败的任务则重新运行  


定义系统参数
![系统参数](https://github.com/anshishagua/dataAnalysis/blob/master/src/main/resources/screenshots/systemParameter.png)

定义表
![表](https://github.com/anshishagua/dataAnalysis/blob/master/src/main/resources/screenshots/tableDefine.png)

定义标签
![标签](https://github.com/anshishagua/dataAnalysis/blob/master/src/main/resources/screenshots/tagDefine.png)


定义指标
![指标](https://github.com/anshishagua/dataAnalysis/blob/master/src/main/resources/screenshots/indexDefine.png)

任务依赖
![任务依赖](https://github.com/anshishagua/dataAnalysis/blob/master/src/main/resources/screenshots/taskDependency.png)

任务监控
![任务监控](https://github.com/anshishagua/dataAnalysis/blob/master/src/main/resources/screenshots/taskExecution.png)

