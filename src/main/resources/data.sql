insert into users(user_id, email, password, name, level)
values('1','kim12@email.com', '1234', 'kim12', 1);

insert into users(user_id, email, password, name, level)
values('2','hong456@email.com', '5678', 'hong', 1);

insert into users(user_id, email, password, name, level)
values('3','park789@email.com', '0000', 'park', 3);

insert into users(user_id, email, password, name, level)
values('4','joemail@email.com', '0000', 'jo', 2);

-- Follow 테이블에 더미 팔로우 데이터 삽입
insert into follow(follower, following)
values ('1', '2'),('1', '3'),('1', '4'),('2','1');

-- Todo 더미데이터
insert into todo(todo_id, user_id, todo_date, task)
values (1, '1', current_date(), 'eat');
insert into todo(todo_id, user_id, todo_date, task)
values (2, '1', current_date(), 'go to school');
insert into todo(todo_id, user_id, todo_date, task)
values (3, '1', current_date(), 'journal');
insert into todo(todo_id, user_id, todo_date, task)
values (4, '2', current_date(), 'cooking');
insert into todo(todo_id, user_id, todo_date, task)
values (5, '2', current_date(), 'learn');
insert into todo(todo_id, user_id, todo_date, task)
values (6, '2', current_date(), 'running');

-- Like 더미데이터
insert into likes(user_id, todo_id)
values('1', 1);
insert into likes(user_id, todo_id)
values('2', 1);
insert into likes(user_id, todo_id)
values('3', 1);
insert into likes(user_id, todo_id)
values('4', 1);
insert into likes(user_id, todo_id)
values('1', 2);
insert into likes(user_id, todo_id)
values('2', 2);