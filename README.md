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

