
-- hrbp.`department` data
INSERT INTO hrbp.department (department_id, department_name)
VALUES
(1, 'Digital Product Engineering (CAE)'),
(2, 'Intelligent Connected Products'),
(3, 'Digital Plant And Process Engineering'),
(4, 'Digital Product Engineering (H&I)'),
(5, 'Digital Product Engineering (ACE)'),
(6, 'Digital Discrete Manufacturing Engineering'),
(7, 'In-Client Services'),
(8, 'Corporate'),
(9, 'Quality Assurance and Business Excellence'),
(10, 'Talent Acquisition'),
(11, 'Finance & Accounts'),
(12, 'Infrastructure Management Group-Admin'),
(13, 'Digital Technologies'),
(14, 'Work Force Enablement'),
(15, 'Human Resources'),
(16, 'Market Research and Sales Enablement'),
(17, 'Marketing'),
(18, 'Business Development');
-- END

-- hrbp.`role` data
INSERT INTO hrbp.role (role_id, role_name)
VALUES
(100, 'HRBP'),
(200, 'Manager'),
(300, 'BU HEAD'),
(400, 'Employee');
-- END

-- hrbp.`hrbp_user_secret` data
INSERT INTO hrbp.hrbp_user_secret (user_id, password, status, is_default_password)
VALUES
(200, 'adminPass', 'Active', 'No'),
(11732, 'BkovnWt3y1NvR7hzBx9haOrGqDRobKcL+lFljmsoQBA=', 'Active', 'YES'),
(12355, 'BkovnWt3y1NvR7hzBx9haOrGqDRobKcL+lFljmsoQBA=', 'Active', 'YES');
-- END


-- hrbp.`hrbp_user_details` data
INSERT INTO hrbp.hrbp_user_details (bu_head_id, department_id, employee_id, manager_id, role_id, first_name, last_name, status)
VALUES
(NULL,6, 200,NULL , 400, 'Admin', 'User', 'Active'),
(NULL, 6, 12355, NULL, 300, 'Sujendra', 'G S', 'Active'),
(12355, 6, 11732, 12355, 200, 'Kali Prasad', 'Chakraborty', 'Active');
-- END