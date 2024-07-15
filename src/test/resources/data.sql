INSERT IGNORE todos(title, description, is_done, user_id)
VALUES('Green Eggs and Ham with john', 'Test todo by Tom S.', 1, 1);

INSERT IGNORE user(username, email, password, secret)
VALUES('Tom S.', 'thoschulte@gmail.com', 'xxx', 'abc-xyz');
