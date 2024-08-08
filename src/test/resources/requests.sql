-- clear_users
DELETE FROM users;

-- clear_auth_codes
DELETE FROM auth_codes;

-- clear_cards
DELETE FROM cards;

-- clear_card_transactions
DELETE FROM card_transactions;

-- insert_user
INSERT INTO users (id, login, password, status) VALUES (?, ?, ?, ?);

-- insert_auth_code
INSERT INTO auth_codes (id, user_id, code) VALUES (?, ?, ?);

-- select_auth_code_by_id
SELECT * FROM auth_codes WHERE id = ?;

-- insert_card1
INSERT INTO cards (id, user_id, number, balance_in_kopecks) VALUES (?, ?, ?, ?);

-- select_card1_by_id
SELECT * FROM cards WHERE id = ?;

-- insert_card2
INSERT INTO cards (id, user_id, number, balance_in_kopecks) VALUES (?, ?, ?, ?);

-- select_card2_by_id
SELECT * FROM cards WHERE id = ?;

-- insert_card_transaction
INSERT INTO card_transactions (id, source, target, amount_in_kopecks) VALUES (?, ?, ?, ?);

-- select_card_transaction_by_id
SELECT * FROM card_transactions WHERE id = ?;

-- select_user_by_login
SELECT * FROM users WHERE login = ?;

-- select_latest_auth_code
SELECT * FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1;

-- select_card_by_user_id
SELECT * FROM cards WHERE user_id = ?;
