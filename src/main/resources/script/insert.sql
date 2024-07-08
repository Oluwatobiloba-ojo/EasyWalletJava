truncate table Account cascade;
truncate table Profile cascade;
truncate table Transaction cascade;

INSERT INTO Profile(id, email, first_name, last_name, phone_number) VALUES
    (200, 'ojot630@gmail.com', 'tobi', 'ojo', '08032389457');

INSERT INTO Account(id, account_name, account_number, pin, account_balance, profile_id) VALUES
 (100, 'customer', '08032389457', '1234', 0, 200);

INSERT INTO Transaction(id, amount, status, account_id, description, recipient_name)  VALUES
    ('e558ab7c-d536-45ac-9209-7c5b43cded7c', 1000, 'PROCESSING', 100, 'Shopping', 'tobi');
