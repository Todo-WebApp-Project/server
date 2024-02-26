-- User 더미데이터
insert into users(user_id, email, password, name, level) values('1','kim12@email.com', '1234', 'kim12', 1);
insert into users(user_id, email, password, name, level) values('2','hong456@email.com', '5678', 'hong', 1);
insert into users(user_id, email, password, name, level) values('3','park789@email.com', '0000', 'park', 3);
insert into users(user_id, email, password, name, level) values('4','joemail@email.com', '0000', 'jo', 2);

-- Follow 테이블에 더미 팔로우 데이터 삽입
insert into follow(following, followed) values ('1', '2'),('1', '3'),('1', '4'),('2','1');

-- Post 테이블에 더미 데이터 삽입
insert into post (post_id, user_id, todo_date) values (1, '1', '2023-12-31');
insert into post (post_id, user_id, todo_date) values (2, '1', '2024-02-26');
insert into post (post_id, user_id, todo_date) values (3, '2', '2024-02-25');
insert into post (post_id, user_id, todo_date) values (4, '2', '2024-02-26');
insert into post (post_id, user_id, todo_date) values (5, '3', '2024-03-01');
insert into post (post_id, user_id, todo_date) values (6, '4', '2024-03-01');

-- Todo 더미데이터
insert into todo(todo_id, post_id, user_id, todo_date, task, finish) values (1, 1, '1', '2023-12-31', 'eat', 0);
insert into todo(todo_id, post_id, user_id, todo_date, task, finish) values (2, 2, '1', '2024-02-26', 'go to school', 1); 
insert into todo(todo_id, post_id, user_id, todo_date, task, finish) values (3, 2, '1', '2024-02-26', 'journal', 1);
insert into todo(todo_id, post_id, user_id, todo_date, task, finish) values (4, 3, '2', '2024-02-25', 'cooking', 1);
insert into todo(todo_id, post_id, user_id, todo_date, task, finish) values (5, 4, '2', '2024-02-26', 'learn', 0);
insert into todo(todo_id, post_id, user_id, todo_date, task, finish) values (6, 4, '2', '2024-02-26', 'running', 0);
insert into todo(todo_id, post_id, user_id, todo_date, task, finish) values (7, 5, '3', '2024-03-01', 'Work on project', 0);
insert into todo(todo_id, post_id, user_id, todo_date, task, finish) values (8, 5, '3', '2024-03-01', 'Read a book', 1);
insert into todo (todo_id, post_id, user_id, todo_date, task, finish) values (9, 5,'3', '2024-03-01', 'Go for a run', 1);
insert into todo(todo_id, post_id, user_id, todo_date, task, finish) values (10, 6, '4', '2024-03-01', 'Prepare presentation', 0);
insert into todo(todo_id, post_id, user_id, todo_date, task, finish) values (11, 6, '4', '2024-03-01', 'Attend workshop', 1);

-- Like 더미데이터
insert into likes(user_id, post_id) values('1', 1);
insert into likes(user_id, post_id) values('2', 1);
insert into likes(user_id, post_id) values('3', 1);
insert into likes(user_id, post_id) values('4', 1);
insert into likes(user_id, post_id) values('1', 2);
insert into likes(user_id, post_id) values('2', 2);

