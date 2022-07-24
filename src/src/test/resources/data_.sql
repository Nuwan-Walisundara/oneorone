/**
Test Data set
**/
INSERT INTO `company`(id,name,company_code,CHARGING_TECHNOLOGY) VALUES(1000,'Svenska Petroleum Exploration AB','SVPEAB','high volume voltage');
INSERT INTO `company`(id,name,company_code,CHARGING_TECHNOLOGY) VALUES (1001,'British petrolium AB','BPAB','fast-charge');
INSERT INTO  `company`(id,name,company_code,parent_company_id) VALUES (2000,'Svenska Petroleum Exploration AB - sub 1','SVPESUB1',1000);
INSERT INTO  `company`(id,name,company_code,parent_company_id) VALUES (2001,'British petrolium AB- sbu1','BPSUB1',1001);
INSERT INTO  `company`(id,name,company_code,parent_company_id) VALUES (2002,'British petrolium AB- sbu1- sub 2','SVPESUB1',2001);


/** stations**/
 INSERT INTO `station`(id,name,latitudeInRadian,longitudeInRadian,company_id) VALUES (5000, 'Shell',1.0351710584482758382,0.31435032986689120937,1000);
 INSERT INTO `station`(id,name,latitudeInRadian,longitudeInRadian,company_id) VALUES (5001,'Gulf Vaxholm',1.03687858994022708,0.32033045164601037236,1001);
 INSERT INTO `station`(id,name,latitudeInRadian,longitudeInRadian,company_id) VALUES (5002,'Circle K Autogas',1.0357369972964793892,0.31300809007423802122,2000);
 INSERT INTO `station`(id,name,latitudeInRadian,longitudeInRadian,company_id) VALUES (5003,'Preem',59.33285355349522,0.31490159764926267671,2001);
 INSERT INTO `station`(id,name,latitudeInRadian,longitudeInRadian,company_id) VALUES (5004,'Brommaplans Bensinstation',1.0357981007589152078,0.31313991655098027955,2002);

