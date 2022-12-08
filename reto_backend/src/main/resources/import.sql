-- Load Affiliates
INSERT INTO AFFILIATES(name, age, mail) VALUES('first affiliate', 24,'first@mail.com');
INSERT INTO AFFILIATES(name, age, mail) VALUES('second affiliate', 30,'second@mail.com');
INSERT INTO AFFILIATES(name, age, mail) VALUES('third affiliate', 28,'third@mail.com');
-- Load Tests
INSERT INTO TESTS(name, description) VALUES('first test', 'the first test');
INSERT INTO TESTS(name, description) VALUES('second test', 'the second test');
INSERT INTO TESTS(name, description) VALUES('third test', 'the third test');
-- Load Appointments
INSERT INTO APPOINTMENTS(date_, hour_, id_Test, Id_Affiliate) VALUES(DATE '2023-02-24', TO_DATE('14:30','HH24:MI'), 2, 1);
INSERT INTO APPOINTMENTS(date_, hour_, id_Test, Id_Affiliate) VALUES(DATE '2022-07-10', TO_DATE('08:30','HH24:MI'), 3, 2);
INSERT INTO APPOINTMENTS(date_, hour_, id_Test, Id_Affiliate) VALUES(DATE '2022-01-01', TO_DATE('12:30','HH24:MI'), 2, 2);
INSERT INTO APPOINTMENTS(date_, hour_, id_Test, Id_Affiliate) VALUES(DATE '2022-07-10', TO_DATE('15:25','HH24:MI'), 1, 1);
INSERT INTO APPOINTMENTS(date_, hour_, id_Test, Id_Affiliate) VALUES(DATE '2022-07-10', TO_DATE('10:30','HH24:MI'), 3, 3);
INSERT INTO APPOINTMENTS(date_, hour_, id_Test, Id_Affiliate) VALUES(DATE '2022-07-10', TO_DATE('09:25','HH24:MI'), 3, 1);