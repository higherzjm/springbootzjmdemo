--------------------------------修改到初始状态
update  t_finance_salary_payroll_record  t set t.audit_status=null,t.approve_status=0,t.approve_end_time=null,t.approve_start_time=null,t.push_status=1,t.apply_status=0,t.has_payed=0,t.pay_status=1
where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1343456435840925697';

update t_finance_salary_payroll t set t.approve_status=1,t.confirm_status=1,t.hr_check_status=1,t.finance_check_status=1,t.apply_status=0,t.pay_status=1,t.push_status=1 
where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1343456435840925697';

--------------------------------修改到可提交审批状态-----------------

update  t_finance_salary_payroll_record  t set t.audit_status=null,t.approve_status=0,t.approve_end_time=null,t.approve_start_time=null,t.push_status=3,t.apply_status=0,t.has_payed=0,t.pay_status=1
where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1343456435840925697';

update t_finance_salary_payroll t set t.approve_status=1,t.confirm_status=2,t.hr_check_status=6,t.finance_check_status=4,t.apply_status=0,t.pay_status=1,t.push_status=3
where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1343456435840925697';

--------------------------------修改到已完成状态
update  t_finance_salary_payroll_record  t set t.audit_status=4,t.approve_status=1,t.approve_end_time=now(),t.approve_start_time=now(),t.push_status=3,t.apply_status=1,t.has_payed=1,t.pay_status=3
where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1343456435840925697';

update t_finance_salary_payroll t set t.approve_status=4,t.confirm_status=2,t.hr_check_status=6,t.finance_check_status=4,t.apply_status=1,t.pay_status=3,t.push_status=3 
where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1343456435840925697';
-------------------------------------------------





select  * from t_finance_salary_payroll t where t.`year`=2021 and t.`month`=12 and t.organization_code='ORG1343456435840925697' and t.encrypt_total_pay_amt is null;


select  * from t_finance_salary_payroll_record t where t.`year`=2021 and t.`month`=3 and t.organization_code='ORG1370291322486325250';
select  * from t_finance_salary_payroll t where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1370291322486325250'

update t_finance_salary_payroll t set t.confirm_status=2
where t.`year`=2021 and t.`month`=3 and t.organization_code='ORG1343456435840925697';



select  t.approve_status,t.approve_start_time,t.audit_status,t.push_time,t.push_status,t.update_user from  t_finance_salary_payroll_record t where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1343456435840925697';

select  t.grant_apply_duty_code,t.* from  t_finance_salary_set_grant t where   t.organization_code='ORG1343456435840925697';

update t_finance_salary_payroll t set t.confirm_status=2 where t.`year`=2021 and t.`month`=5
and t.organization_code='ORG1343456435840925697';


select   t.* from  t_finance_salary_payroll t where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1343456435840925697';

select  * from  t_finance_salary_payroll_record  t where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1343456435840925697';
select  * from  t_finance_salary_payroll  t where t.`year`=2021 and t.`month`=4 and t.organization_code='ORG1343456435840925697';



update t_finance_salary_payroll_record t set t.approve_status=0,t.audit_status=null,t.approve_start_time=null where t.`year`=2021 and t.`month`=1 and t.organization_code='ORG1343456435840925697';




select  t.approve_status,t.approve_start_time,t.audit_status,approve_end_time from  t_finance_salary_payroll_record t where t.`year`=2021 and t.`month`=1 and t.organization_code='ORG1343456435840925697';
select  t.approve_status from  t_finance_salary_payroll t where t.`year`=2021 and t.`month`=1 and t.organization_code='ORG1343456435840925697';


select  * from  t_finance_salary_payroll  t where t.`year`=2021 and t.`month`=9 and t.id='f167132aed5043c58f857e0e091122df';

select  *  from  t_finance_base_employee  t  where t.employee_code in('oa1351459498209251329','oa1351441319764365314')

select  t.* from  t_finance_salary_payroll  t where t.`year`=2021 and t.month=4
and t.organization_code='ORG1343456435840925697'  order by t.update_time  desc;


SELECT employee_code,encrypt_old_person_amt,encrypt_unemploy_person_amt,encrypt_hospital_person_amt,encrypt_fund_person_amt FROM t_finance_salary_payroll_social WHERE organization_code = 'ORG1343456435840925697' AND year = 2021 AND month = 9 AND employee_code IN ('oa1351441319764365314','oa1351459498209251329','oa1351796480522227714','oa1351831624824627201','oa1351840410092150786','oa1352155682825019394','oa1352192832165617666','oa1356124995966779394')

SELECT * FROM t_finance_salary_payroll_tax WHERE organization_code = 'ORG1343456435840925697' AND year = 2021 AND month = 9 AND employee_code IN ('oa1351441319764365314','oa1351459498209251329','oa1351796480522227714','oa1351831624824627201','oa1351840410092150786','oa1352155682825019394','oa1352192832165617666','oa1356124995966779394')


SELECT * FROM t_finance_salary_payroll WHERE year = 2021 AND month = 3 AND organization_code = 'ORG1343456435840925697';

SELECT *  FROM t_finance_salary_payroll where id='01eb321202bd44a789aca8cf4b3a64b8'



select  * from  t_finance_salary_payroll_operate_log t where t.organization_code='ORG1354251027476824065' and t.operate_type=4;

SELECT * FROM t_finance_sys_version_data WHERE version_record_id = '8faea75832c6ccd7e09fe756fe31c679';

select  * from  workflow_process_business_hi_data  w    order by w.create_time  desc

select  * from  workflow_process_business_hi_data  w  where w.id=1261;



SELECT * FROM t_finance_salary_payroll WHERE id='b37f23dcbb574523a441aab61743ebc6' year = 2021 AND month = 12 AND organization_code = 'ORG1343456435840925697'
SELECT * FROM t_finance_salary_payroll_record WHERE year = 2021 AND month =12 AND organization_code = 'ORG1343456435840925697' 


select  * from  t_finance_salary_payroll_tax WHERE year = 2021 AND month = 4 AND organization_code = 'ORG1343456435840925697' ;
select * from t_finance_salary_payroll_social  WHERE year = 2021 AND month = 4 AND organization_code = 'ORG1343456435840925697' ;
select  * from  t_finance_salary_set_social WHERE organization_code = 'ORG1343456435840925697' ;

select  * from  t_finance_salary_item t  where t.id in('11','22');

select  * from  t_finance_salary_payroll_social

select  * from  t_finance_salary_payroll_tax

SELECT department_name,count(1) FROM t_finance_salary_payroll WHERE year = 2021 AND month = 5  GROUP BY department_name

select * from t_finance_base_department  WHERE organization_code='ORG1370291322486325250' and year = 2021 AND month = 3  and parent_organization_path is  null 



select   * from  t_finance_salary_payroll_process_record;  薪资审批记录表

select * from t_finance_base_employee t  where t.employee_no='001113'
select * from t_finance_base_employee t where t.email like '%kuncheng-wang@%'




select  *  from  t_finance_base_employee  t where t.organization_code='ORG1343456435840925697';

-------2021-04-23-------------------------

select * from  t_finance_salary_set_generate
select  * from  t_finance_salary_set_work_hours
select * from  t_finance_salary_set_social_wage
select * from  t_finance_salary_set_param
select  * from  t_finance_salary_set_grant
select  * from  t_finance_sys_version_record
select  * from  t_finance_salary_set_social_person;
select   * from  t_finance_salary_set_social  t where  t.organization_code='ORG1343456435840925697'
and  t.id='63e28c9c9958409bbdd5abe0c6ab29d7'
select  * from  t_finance_salary_set_social_detail  t where  
t.organization_code='ORG1343456435840925697' and  t.social_id='63e28c9c9958409bbdd5abe0c6ab29d7'


select   * from  t_finance_salary_set_social_effect  t where  t.organization_code='ORG1343456435840925697'
and  t.id='63e28c9c9958409bbdd5abe0c6ab29d7'
select  * from  t_finance_salary_set_social_detail_effect  t where  
t.organization_code='ORG1343456435840925697' and  t.social_id='63e28c9c9958409bbdd5abe0c6ab29d7'

select  * from  t_finance_salary_set_tax_rate t where t.organization_code='ORG1343456435840925697'

select   t.param_value from  t_finance_salary_set_param t   where t.param_code='employeeSocialInitStatus' and  t.organization_code='ORG1343456435840925697'

------2021-04-28
SELECT
	organization_code AS "organizationCode",
	employee_code AS "employeeCode",
	sum( child_education_amt ) AS "childEducationAmt",
	sum( continue_education_amt ) AS "continueEducationAmt",
	sum( house_loan_interest_amt ) AS "houseLoanInterestAmt",
	sum( house_lease_amt ) AS "houseLeaseAmt",
	sum( raise_old_amt ) AS "raiseOldAmt" 
FROM
	t_finance_salary_base_special_tax 
WHERE
	organization_code = 'ORG1343456435840925697' 
	AND employee_code = 'oa1351441319764365314' 
	AND MAKEDATE(salary_year, salary_month ) BETWEEN MAKEDATE( 2020, 12 ) AND MAKEDATE( 2021, 3 )
	
----2021-04-30  权限初始化表
select  * from  vv_uac.nuac_resource where code like 'FINANCE%';
select  * from  vv_uac.nuac_resource_api where resource_code like 'FINANCE%';
select  * from  vv_uac.nuac_tenant_resource where resource_code like 'FINANCE%' OR resource_code LIKE 'APIFIN%';

---2021-05-02
--薪资生成初始化信息1
--审批信息
select  t1.employee_code,t1.* from  t_finance_salary_base_process t1 where  t1.organization_code =##  and (
      t1.salary_status = 0 or (t1.salary_status = 1  and t1.year =## and t1.month =##) ) and  t1.employee_code in('##--')

--考勤类型
select  t1.organization_code,t1.* from  t_finance_salary_base_attend_type t1 where t1.organization_code=#
--考勤记录
select t1.employee_code,t1.* from  t_finance_salary_base_attend t1 where  t1.organization_code =##
       and t1.year =## and t1.month =  and t1.employee_code in('##')
--考勤异常信息				
select t2.base_attend_id,t2.* from t_finance_salary_base_attend t1 join t_finance_salary_base_attend_detail t2 on t1.id = t2.base_attend_id
     where  t1.organization_code =##  and t1.year =## and t1.month =## and  t1.employee_code in('##')
--绩效系数		 
select t1.employee_code,t1.* from t_finance_salary_base_kpi t1
          where t1.organization_code =## and t1.year =## and t1.month =## and  t1.employee_code in('##')
--产假信息					
select t1.employee_code, t1.start_date, t1.end_date from  t_finance_salary_base_attend_maternity t1
        where  t1.organization_code = # and  t1.employee_code in('##')
								
--实际入离职人员工时信息
 select t1.organization_code,t1.* from t_finance_salary_set_work_hours t1 where  t1.organization_code = #
        and t1.work_type = 2  and t1.work_year =#  and t1.work_month = # and t1.status = 1
--薪资项
    select t1.organization_code,t3.*,t4.column_name as staffInfoColumnName
        from  t_finance_salary_template t1  join t_finance_salary_template_item t2 on t1.id = t2.template_id and t2.status = '1'
        join t_finance_salary_item t3 on t2.salary_item_id = t3.id and t3.item_level = 2  left join t_finance_salary_item_staff_info t4 on t3.staff_info_id = t4.id where  t1.organization_code = # and t1.year = #  and t1.month =# 		
--月薪资模板状态、工资单生成日期
select t1.process_status,t1.generate_date from  t_finance_salary_template t1
 where  t1.organization_code = #{organizationCode} and t1.year = #{year} and t1.month = #{month}
--薪资发放处理状态：
select t1.process_status from  t_finance_salary_set_grant t1
 where    t1.organization_code = #{organizationCode}	
--薪资变更记录
select  t1.employee_code,t1.* from  t_finance_base_employee_salary_change t1  where t1.organization_code='' and t1.employee_code in('##')
--组织变更记录(部门，职级，职位，编码)
  select t1.employee_code,t1.info_type,t1.info_code, t1.info_name,t1.effective_date
from  t_finance_base_employee_organization_change t1
where  t1.organization_code = #{organizationCode} and and t1.employee_code in('##') and t1.info_type = #{infoType} 

--薪资生成初始化信息2

--实际正常通用工时信息
 select t1.organization_code,t1.* from t_finance_salary_set_work_hours t1 where  t1.organization_code = #
        and t1.work_type = 1  and t1.work_year =#  and t1.work_month = # and t1.status = 1
--五险一金缴交基数				
select t1.employee_code,t1.* from t_finance_salary_set_social_person t1
        where t1.organization_code =# and  t1.employee_code in('##')
----上个月的五险一金			
select t1.employee_code,t1.* from t_finance_salary_payroll_social t1
 where t1.organization_code = #{organizationCode}  and t1.year = #{year} and t1.month = #{month}  and  t1.employee_code in('##')
 
 --五险一金方案
 select  t1.social_city_code 社保城市（代码）,t1.* from  t_finance_salary_set_social  t1  where t1.organization_code=#;
 --五险一金缴交比例明细
 select  t1.social_id,t1.house_place,t1.* from  t_finance_salary_set_social_detail  t1  where t1.organization_code=#
 
 
 --薪资生成初始化信息3
 
--个税设置预扣率处理状态
select t1.process_status from t_finance_salary_set_tax_rate t1
where  t1.organization_code = #{organizationCode} and t1.tax_type = #{taxType}

--个税预扣率信息
    select t2.*  from  t_finance_salary_set_tax_rate t1 join t_finance_salary_set_tax_rate_detail t2 on t1.id = t2.tax_rate_id
      where t1.organization_code = #{organizationCode} and t1.tax_type = #{taxType}
--上个月的个税信息
   select  t1.employee_code,t1.* from  t_finance_salary_payroll_tax t1 where t1.employee_code,t1.organization_code = #{organizationCode}  
	 and t1.year = #{year} and t1.month = #{month} and  t1.employee_code in('##')
--当年的个税专项扣除
 select t1.employee_code,t1.* from  t_finance_salary_base_special_tax t1 where  t1.organization_code = #{organizationCode}
  and ((t1.salary_year > #{startYear}) or (t1.salary_year = #{startYear} and t1.salary_month >= #{startMonth}))
   and ((#{endYear} > t1.salary_year) or (t1.salary_year = #{endYear} and #{endMonth} >= t1.salary_month))
		and  t1.employee_code in('##')

--离职员工初始化社平工资
select  t1.social_year_amt from  t_finance_salary_set_social_wage t1
        where  t1.organization_code = #{organizationCode}  and t1.social_city_code = #{socialCityCode}  and t1.social_year = #{socialYear}
				
				
				
