-- --------------------------------------------------------------------
-- DML for Department Data
-- --------------------------------------------------------------------
insert into ru_department (name, status, manager_id, created_by)
values ('On Buro', 'ACTIVE', 1, 'Admin');

insert into ru_department (name, status, manager_id, created_by)
values ('Kat Hizmetleri', 'ACTIVE', 2, 'Admin');

insert into ru_department (name, status, manager_id, created_by)
values ('Yiyecek ve Icecek', 'ACTIVE', 3, 'Admin');

insert into ru_department (name, status, manager_id, created_by)
values ('Satis ve Pazarlama', 'ACTIVE', 4, 'Admin');

insert into ru_department (name, status, manager_id, created_by)
values ('Muhasebe', 'ACTIVE', 5, 'Admin');

insert into ru_department (name, status, manager_id, created_by)
values ('Insan Kaynaklari', 'ACTIVE', 6, 'Admin');

insert into ru_department (name, status, manager_id, created_by)
values ('Guvenlik', 'ACTIVE', 7, 'Admin');

insert into ru_department (name, status, manager_id, created_by)
values ('Teknik Servis', 'ACTIVE', 8, 'Admin');

insert into ru_department (name, status, manager_id, created_by)
values ('Yonetim', 'ACTIVE', 9, 'Admin');


-- ----------------------------------------------------------------------
-- DML for Employee Data
-- ----------------------------------------------------------------------
insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Ahmet', 'Yılmaz', '12345678901', 'ahmet.yilmaz@hotmail.com',
        '5421234567', '1234 Çınar Sokak, İstanbul', '1985-05-15',
        'MALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Ayşe', 'Kaya', '23456789012', 'ayse.kaya@gmail.com',
        '5352345678', '5678 Pınar Cd., Ankara', '1990-08-25',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Mehmet', 'Demir', '34567890123', 'mehmet.demir@gmail.com',
        '5053456789', '91011 Meşe Sk., İzmir', '1975-03-10',
        'MALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Fatma', 'Çelik', '45678901234', 'fatma.celik@gmail.com',
        '5064567890', '1213 Gül Sokak, Bursa', '1988-12-03',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Mustafa', 'Şahin', '56789012345', 'mustafa.sahin@mynet.com',
        '5065678901', '1415 Selvi Cd., Adana', '1993-07-19',
        'MALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Zeynep', 'Arslan', '67890123456', 'zeynep.arslan@gmail.com',
        '5436789012', '1617 Çam Sk., Konya', '1982-11-30',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Emre', 'Koç', '78901234567', 'emre.koc@hotmail.com',
        '5427890123', '1819 Mavişehir, İzmir', '1995-04-22',
        'MALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Elif', 'Yıldız', '89012345678', 'elif.yildiz@gmail.com',
        '5358901234', '2021 Altın Sokak, Antalya', '1998-09-14',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Hüseyin', 'Öztürk', '90123456789', 'huseyin.ozturk@hotmail.com',
        '5359012345', '2223 Deniz Cd., Mersin', '1970-02-18',
        'MALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Seda', 'Aydın', '11223344556', 'seda.aydin@mynet.com',
        '5551122334', '2425 Kırmızı Sk., Trabzon', '1987-06-27',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Kadir', 'Erdoğan', '22334455667', 'kadir.erdogan@yahoo.com',
        '5432233445', '2627 Yeşil Cd., Samsun', '1991-01-08',
        'MALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Emma', 'Lefèvre', '6543210987', 'emma.lefevre@gmail.com',
        '5351112233', '321 Rue de Paris, Lyon, France', '1983-03-12',
        'FEMALE', 'French', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Cem', 'Aktaş', '44556677889', 'cem.aktas@gmail.com',
        '5054455667', '3031 Şafak Cd., Kayseri', '1996-03-25',
        'MALE', 'Turkish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Carlos', 'González', '5432109876', 'carlos.gonzalez@gmail.com',
        '5350001122', '654 Calle Mayor, Madrid, Spain', '1979-05-29',
        'MALE', 'Spanish', 'admin');

insert into ru_employee (first_name, last_name, identity_number, email, phone_number, address,
                         birth_date, gender, nationality, created_by)
values ('Tolga', 'Aslan', '66778899001', 'tolga.aslan@gmail.com',
        '5366677889', '3435 Göl Cd., Eskişehir', '1980-07-16',
        'MALE', 'Turkish', 'admin');


-- ------------------------------------------------------------------------------
-- DML for Old Employee Data
-- ------------------------------------------------------------------------------
insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Derya', 'Özdemir', '77889900112', 'derya.ozdemir@gmail.com',
        '5427788990', '3637 Yıldız Sk., Denizli', '1992-04-09',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Volkan', 'Karaca', '88990011223', 'volkan.karaca@mynet.com',
        '5558899001', '3839 Bahar Cd., Malatya', '1978-11-21',
        'MALE', 'Turkish', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Büşra', 'Polat', '99001112234', 'busra.polat@hotmail.com',
        '5059900112', '4041 Güneş Sk., Kocaeli', '1986-08-12',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee_old(first_name, last_name, identity_number, email, phone_number, address,
                            birth_date, gender, nationality, created_by)
values ('Serkan', 'Avcı', '10111213141', 'serkan.avci@mynet.com',
        '5551011121', '4243 Ay Cd., Sakarya', '1994-05-03',
        'MALE', 'Turkish', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('İrem', 'Çetin', '12131415161', 'irem.cetin@hotmail.com',
        '5421213141', '4445 Yıldırım Sk., Balıkesir', '1989-02-28',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Oğuz', 'Güler', '13141516171', 'oguz.guler@mynet.com',
        '5331314151', '4647 Şimşek Cd., Manisa', '1973-09-07',
        'MALE', 'Turkish', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Pınar', 'Yavuz', '14151617181', 'pinar.yavuz@yahoo.com',
        '5321415161', '4849 Fırtına Sk., Hatay', '1997-06-18',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Levent', 'Bulut', '15161718191', 'levent.bulut@yahoo.com',
        '5051516171', '5051 Yağmur Cd., Adıyaman', '1981-04-30',
        'MALE', 'Turkish', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('John', 'Doe', '9876543210', 'john.doe@gmail.com',
        '5434445566', '123 Maple St, New York, USA', '1988-07-04',
        'MALE', 'American', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Sophia', 'Müller', '8765432109', 'sophia.muller@mynet.com',
        '5423334455', '456 Eiche Str., Berlin, Germany', '1995-09-17',
        'FEMALE', 'German', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Luca', 'Ricci', '7654321098', 'luca.ricci@yahoo.com',
        '5462223344', '789 Via Roma, Rome, Italy', '1991-11-25',
        'MALE', 'Italian', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Merve', 'Tekin', '33445566778', 'merve.tekin@mynet.com',
        '5323344556', '2828 Bulvar Sk., Gaziantep', '1984-10-11',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Aslı', 'Kaplan', '16171819202', 'asli.kaplan@gmail.com',
        '5051617181', '5253 Kar Sk., Kahramanmaraş', '1990-10-22',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Gizem', 'Kılıç', '55667788990', 'gizem.kilic@gmail.com',
        '5425566778', '3233 Ufuk Sk., Diyarbakır', '1999-12-05',
        'FEMALE', 'Turkish', 'admin');

insert into ru_employee_old (first_name, last_name, identity_number, email, phone_number, address,
                             birth_date, gender, nationality, created_by)
values ('Murat', 'Şen', '17181920212', 'murat.sen@gmail.com',
        '5351718192', '5455 Kış Cd., Van', '1976-12-14', 'MALE',
        'Turkish', 'admin');


-- --------------------------------------------------------------------
-- DML for Position Data
-- --------------------------------------------------------------------
insert into ru_position (name, department_id, status, created_by)
values ('Genel Müdür', 9, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Müdür Yardımcısı', 9, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Satış ve Pazarlama Müdürü', 4, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Ön Büro Müdürü', 1, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Kat Hizmetleri Müdürü', 2, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Muhasebe Müdürü', 5, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Insan Kaynakları Müdürü', 6, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Resepsiyonist', 1, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Bellboy', 1, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Konsiyerj', 1, 'DELETED', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Kat Görevlisi', 2, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Aşçı', 3, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Garson', 3, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Yiyecek ve İçecek Müdürü', 3, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Muhasebeci', 5, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('İnsan Kaynakları Uzmanı', 6, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Güvenlik Görevlisi', 7, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Teknik Servis Elemanı', 8, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Etkinlik Yöneticisi', 4, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Spa Terapisti', 2, 'ACTIVE', 'Admin');

insert into ru_position (name, department_id, status, created_by)
values ('Oda Servisi Görevlisi', 3, 'ACTIVE', 'Admin');


-- -----------------------------------------------------------------------------------
-- DML for Employee Experience Data
-- -----------------------------------------------------------------------------------
insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (90000.00, 1, 1, '2023-01-01', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (75000.00, 2, 2, '2023-02-15', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (65000.00, 3, 3, '2023-03-10', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (62000.00, 4, 4, '2023-04-01', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (60000.00, 5, 5, '2023-05-05', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (62000.00, 6, 6, '2023-06-20', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (63000.00, 7, 7, '2023-07-15', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (35000.00, 8, 8, '2023-08-01', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (32000.00, 9, 9, '2023-09-12', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (30000.00, 10, 10, '2023-10-03', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (45000.00, 11, 11, '2023-11-18', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (26000.00, 12, 12, '2023-12-01', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (58000.00, 13, 13, '2024-01-05', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (42000.00, 14, 14, '2024-02-20', 'Admin');

insert into ru_employee_experience (salary, employee_id, position_id, start_date, created_by)
values (45000.00, 15, 15, '2024-03-15', 'Admin');


-- -----------------------------------------------------------------------------------
-- DML for Old Employee Experience Data
-- -----------------------------------------------------------------------------------
insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (9000.00, 1, 11, '2020-01-01', '2024-01-01', 'Admin', 'flz');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (12000.00, 2, 11, '2020-02-15', '2024-01-15', 'Admin', 'flz');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (10000.00, 3, 11, '2023-03-10', '2024-01-05', 'Admin', 'flz');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (5000.00, 4, 11, '2020-05-01', '2022-01-01', 'Admin', 'Admin');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (15000.00, 5, 13, '2021-05-05', '2023-05-05', 'Admin', 'flz');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (12000.00, 6, 13, '2021-06-20', '2022-05-02', 'Admin', 'flz');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (13000.00, 7, 12, '2018-07-15', '2020-07-15', 'Admin', 'Admin');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (15000.00, 8, 13, '2020-08-01', '2021-08-01', 'Admin', 'flz');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (12000.00, 9, 15, '2020-09-12', '2020-12-12', 'Admin', 'flz');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (10000.00, 10, 15, '2019-11-03', '2021-11-05', 'Admin', 'Admin');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (15000.00, 11, 11, '2018-11-18', '2021-10-20', 'Admin', 'Admin');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (16000.00, 12, 8, '2020-12-01', '2022-01-30', 'Admin', 'flz');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (28000.00, 13, 7, '2018-01-05', '2020-02-06', 'Admin', 'flz');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (15000.00, 14, 3, '2017-02-20', '2021-10-10', 'Admin', 'Admin');

insert into ru_employee_old_experience (salary, employee_old_id, position_id, start_date, end_date, created_by,
                                        updated_by)
values (25000.00, 15, 5, '2018-03-15', '2023-10-25', 'Admin', 'flz');
