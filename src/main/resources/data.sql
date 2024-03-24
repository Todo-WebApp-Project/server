--// user_id, email, password, username, level, auth, status_msg
insert into user_details(id, email, password, username, level, auth, status_msg)
values('10001','gachon1@gmail.com','123','Ranga',1,0,'hi');

insert into user_details(id, email, password, username, level, auth, status_msg)
values('10002','gachon2@gmail.com','1234','Ravi',1,0,'hi');

insert into user_details(id, email, password, username, level, auth, status_msg)
values('10003','gachon2@gmail.com','12345','Sathish',2,0,'hi');


--todo_id , user_id, task, finish, no_tag. share , todo_date--
insert into todo(id, user_id, task, finish, no_tag, share , todo_date)
values(20001,'10001','I want to learn AWS',1,'empty',0,current_date());

insert into todo(id, user_id, task, finish, no_tag, share,todo_date)
values(20002,'10001','I want to learn DevOps',1,'empty',0,current_date());

insert into todo(id, user_id, task, finish, no_tag, share , todo_date)
values(20003,'10001','i want to Get AWS Certified',1,'empty',0, current_date());

insert into todo(id, user_id, task, finish, no_tag, share , todo_date)
values(20004,'10001','I want to learn Multi Cloud',1,'empty',0, current_date());

-- Sche_id, user_id, color(dcdcdc), start_date , end_date, plan 
insert into schedule(id, user_id, color, start_date, end_date, plan)
values(30001 , '10001' , 'dcdcdc',current_date(), current_date(), 'birthday');

insert into schedule(id, user_id, color, start_date, end_date, plan)
values(30002 , '10001' , 'dcdcdc','20240227', '20240301', 'travel in japan');
-- user_id, perform_date , finish

insert into performance(perform_id, user_id,perform_date,finish)
values(1,'10001',current_date(),0);
