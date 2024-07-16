INSERT IGNORE user(username, email, password, secret)
    VALUES('Tom S.', 'thoschulte@gmail.com', 'xxxx', 'abc-xyz');

INSERT IGNORE user(username, email, password, secret)
    VALUES('Thomas Schulte', 'thoschulte@googlemail.com', 'iiii', 'xyz-abc');

---

INSERT IGNORE todos(title, description, is_done, user_id)
    VALUES('Green Eggs and Ham with john', 'Test todo by Tom S.', 0, 1);

INSERT IGNORE todos(title, description, is_done, user_id)
    VALUES('Cookie with jane', 'Test foo bar baz', 0, 2);

INSERT IGNORE todos(title, description, is_done, user_id)
    VALUES('Tennis with tom', 'Lorum ipsum mama u papa', 0, 2);
