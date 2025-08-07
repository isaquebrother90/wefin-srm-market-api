-- Inserir moedas base
INSERT INTO CURRENCY (name, code) VALUES ('Ouro Real', 'GOL');
INSERT INTO CURRENCY (name, code) VALUES ('Tibar', 'TIB');

-- Inserir produtos de exemplo
INSERT INTO PRODUCT (name, origin_realm) VALUES ('Peles Raras', 'Reino do Norte');
INSERT INTO PRODUCT (name, origin_realm) VALUES ('Hidromel dos Anãos', 'Montanhas de Ferro');
INSERT INTO PRODUCT (name, origin_realm) VALUES ('Madeira de Carvalho', 'Floresta de Wefin');

-- Inserir uma taxa de câmbio inicial
-- 1 Ouro Real (ID 1) = 2.5 Tibares (ID 2)
INSERT INTO EXCHANGE_RATE (from_currency_id, to_currency_id, rate, effective_date) VALUES (1, 2, 2.5000, CURRENT_DATE);
