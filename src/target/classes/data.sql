/**
Test Data set
**/
INSERT INTO "Order"(id, status, remarks, "shopCode", "orderReadyBy", "orderReadyTime") VALUES (?, ?, ?, ?, ?, ?);
INSERT INTO `order`(id,name,company_code,CHARGING_TECHNOLOGY) VALUES(1000,'Svenska Petroleum Exploration AB','SVPEAB','high volume voltage');
INSERT INTO `company`(id,name,company_code,CHARGING_TECHNOLOGY) VALUES (1001,'British petrolium AB','BPAB','fast-charge');
INSERT INTO  `company`(id,name,company_code,parent_company_id) VALUES (2000,'Svenska Petroleum Exploration AB - sub 1','SVPESUB1',1000);
INSERT INTO  `company`(id,name,company_code,parent_company_id) VALUES (2001,'British petrolium AB- sbu1','BPSUB1',1001);
INSERT INTO  `company`(id,name,company_code,parent_company_id) VALUES (2002,'British petrolium AB- sbu1- sub 2','BPSUB1-2',2001);