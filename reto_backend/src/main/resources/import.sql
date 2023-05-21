-- -- Load Tests
-- INSERT INTO TESTS(name, description) VALUES('first test', 'the first test');
-- INSERT INTO TESTS(name, description) VALUES('second test', 'the second test');
-- INSERT INTO TESTS(name, description) VALUES('third test', 'the third test');
-- -- Load Affiliates
-- INSERT INTO AFFILIATES(name, age, mail) VALUES('first affiliate', 24,'first@mail.com');
-- INSERT INTO AFFILIATES(name, age, mail) VALUES('second affiliate', 30,'second@mail.com');
-- INSERT INTO AFFILIATES(name, age, mail) VALUES('third affiliate', 28,'third@mail.com');
-- Load Appointments
-- INSERT INTO APPOINTMENTS(date, hours, id_Test, Id_Affiliate) VALUES('2023-02-24', '14:30', 2, 1);
-- INSERT INTO APPOINTMENTS(date, hours, id_Test, Id_Affiliate) VALUES('2022-07-10', '08:30', 3, 2);
-- INSERT INTO APPOINTMENTS(date, hours, id_Test, Id_Affiliate) VALUES('2022-01-01', '12:30', 2, 2);
-- INSERT INTO APPOINTMENTS(date, hours, id_Test, Id_Affiliate) VALUES('2022-07-10', '15:25', 1, 1);
-- INSERT INTO APPOINTMENTS(date, hours, id_Test, Id_Affiliate) VALUES('2022-07-10', '10:30', 3, 3);
-- INSERT INTO APPOINTMENTS(date, hours, id_Test, Id_Affiliate) VALUES('2022-07-10', '09:25', 3, 1);

-- Load Tests
INSERT INTO tests(name, description) VALUES('first test', 'the first test');
INSERT INTO tests(name, description) VALUES('second test', 'the second test');
INSERT INTO tests(name, description) VALUES('third test', 'the third test');
-- Load Affiliates
INSERT INTO affiliates(name, age, mail) VALUES('first affiliate', 24,'first@mail.com');
INSERT INTO affiliates(name, age, mail) VALUES('second affiliate', 30,'second@mail.com');
INSERT INTO affiliates(name, age, mail) VALUES('third affiliate', 28,'third@mail.com');
-- Load Appointments
INSERT INTO appointments(date_, hour_, id_Test, Id_Affiliate) VALUES("2024-01-01", "05:00", 1, 1);
INSERT INTO appointments(date_, hour_, id_Test, Id_Affiliate) VALUES('2022-07-10', '08:30', 3, 2);
INSERT INTO appointments(date_, hour_, id_Test, Id_Affiliate) VALUES('2022-01-01', '12:30', 2, 2);
INSERT INTO appointments(date_, hour_, id_Test, Id_Affiliate) VALUES('2022-07-10', '15:25', 1, 1);
INSERT INTO appointments(date_, hour_, id_Test, Id_Affiliate) VALUES('2022-07-10', '10:30', 3, 3);
INSERT INTO appointments(date_, hour_, id_Test, Id_Affiliate) VALUES('2022-07-10', '09:25', 3, 1);