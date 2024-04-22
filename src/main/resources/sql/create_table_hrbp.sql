
CREATE TABLE hrbp.`role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=402 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE hrbp.`department` (
  `department_id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE hrbp.`employee_feedback` (
  `assigned_manager_id` int DEFAULT NULL,
  `creator_id` int DEFAULT NULL,
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `areas` varchar(550) DEFAULT NULL,
  `concerns` varchar(250) DEFAULT NULL,
  `expressions` varchar(250) DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `remarks` varchar(250) DEFAULT NULL,
  `modified_by` int DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE hrbp.`employee_feedback_action` (
  `action_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `rag` varchar(10) DEFAULT NULL,
  `action_owner` int DEFAULT NULL,
  `feedback_id` int DEFAULT NULL,
  `action_owner_name` varchar(45) DEFAULT NULL,
  `action_owner_email` varchar(45) DEFAULT NULL,
  `action_owner_type` varchar(45) DEFAULT NULL,
  `comments` varchar(245) DEFAULT NULL,
  PRIMARY KEY (`action_id`),
  KEY `fk_actionitem_feedback` (`feedback_id`),
  CONSTRAINT `fk_actionitem_feedback` FOREIGN KEY (`feedback_id`) REFERENCES `employee_feedback` (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE hrbp.`employee_master` (
  `employee_id` int NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `designation` varchar(45) DEFAULT NULL,
  `business_unit_name` varchar(45) DEFAULT NULL,
  `grade` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE hrbp.`hrbp_user_details` (
  `bu_head_id` int DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  `employee_id` int NOT NULL,
  `manager_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  KEY `FK9s5e064rmbnv5ecke7se7aaxd` (`bu_head_id`),
  KEY `FKadc2hg9g2ggop4hkv59nvrcuu` (`department_id`),
  KEY `FK21pjh5pvg4lupn2ro4b7cxvif` (`manager_id`),
  KEY `FKbyubmrrt7qe211pr7621lgrd3` (`role_id`),
  CONSTRAINT `FK21pjh5pvg4lupn2ro4b7cxvif` FOREIGN KEY (`manager_id`) REFERENCES `hrbp_user_details` (`employee_id`),
  CONSTRAINT `FK9s5e064rmbnv5ecke7se7aaxd` FOREIGN KEY (`bu_head_id`) REFERENCES `hrbp_user_details` (`employee_id`),
  CONSTRAINT `FKadc2hg9g2ggop4hkv59nvrcuu` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `FKbyubmrrt7qe211pr7621lgrd3` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE hrbp.`hrbp_handled_department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_id` (`employee_id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `hrbp_handled_department_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `hrbp_user_details` (`employee_id`),
  CONSTRAINT `hrbp_handled_department_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE hrbp.`hrbp_user_secret` (
  `user_id` int NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `is_default_password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;