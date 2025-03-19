insert into position (name, department_id, status, created_by)
values ('Genel Müdür', 9, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Müdür Yardımcısı', 9, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Satış ve Pazarlama Müdürü', 4, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Ön Büro Müdürü', 1, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Kat Hizmetleri Müdürü', 2, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Muhasebe Müdürü', 5, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Insan Kaynakları Müdürü', 6, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Resepsiyonist', 1, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Bellboy', 1, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Konsiyerj', 1, 'DELETED', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Kat Görevlisi', 2, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Aşçı', 3, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Garson', 3, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Yiyecek ve İçecek Müdürü', 3, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Muhasebeci', 5, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('İnsan Kaynakları Uzmanı', 6, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Güvenlik Görevlisi', 7, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Teknik Servis Elemanı', 8, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Etkinlik Yöneticisi', 4, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Spa Terapisti', 2, 'ACTIVE', 'Admin');

insert into position (name, department_id, status, created_by)
values ('Oda Servisi Görevlisi', 3, 'ACTIVE', 'Admin');


insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (90000.00, 1, 1, 1, '2023-01-01', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (75000.00, 2, 2, 1, '2023-02-15', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (65000.00, 3, 3, 1, '2023-03-10', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (62000.00, 4, 4, 1, '2023-04-01', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (60000.00, 5, 5, 1, '2023-05-05', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (62000.00, 6, 6, 1, '2023-06-20', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (63000.00, 7, 7, 1, '2023-07-15', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (35000.00, 8, 8, 4, '2023-08-01', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (32000.00, 9, 9, 4, '2023-09-12', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (30000.00, 10, 10, 5, '2023-10-03', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (45000.00, 11, 11, 13, '2023-11-18', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (26000.00, 12, 12, 13, '2023-12-01', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (58000.00, 13, 13, 1, '2024-01-05', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (42000.00, 14, 14, 6, '2024-02-20', 'Admin');

insert into employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, created_by)
values (45000.00, 15, 15, 7, '2024-03-15', 'Admin');


insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (9000.00, 1, 11, 5, '2020-01-01', '2024-01-01',
        'Admin', 'flz');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (12000.00, 2, 11, 5, '2020-02-15', '2024-01-15',
        'Admin', 'flz');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (10000.00, 3, 11, 5, '2023-03-10', '2024-01-05',
        'Admin', 'flz');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (5000.00, 4, 11, 5, '2020-05-01', '2022-01-01',
        'Admin', 'Admin');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (15000.00, 5, 13, 14, '2021-05-05', '2023-05-05',
        'Admin', 'flz');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (12000.00, 6, 13, 14, '2021-06-20', '2022-05-02',
        'Admin', 'flz');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (13000.00, 7, 12, 14, '2018-07-15', '2020-07-15',
        'Admin', 'Admin');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (15000.00, 8, 13, 14, '2020-08-01', '2021-08-01',
        'Admin', 'flz');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (12000.00, 9, 15, 6, '2020-09-12', '2020-12-12',
        'Admin', 'flz');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (10000.00, 10, 15, 6, '2019-11-03', '2021-11-05',
        'Admin', 'Admin');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (15000.00, 11, 11, 13, '2018-11-18', '2021-10-20',
        'Admin', 'Admin');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (16000.00, 12, 12, 13, '2020-12-01', '2022-01-30',
        'Admin', 'flz');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (28000.00, 13, 13, 1, '2018-01-05', '2020-02-06',
        'Admin', 'flz');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (15000.00, 14, 14, 6, '2017-02-20', '2021-10-10',
        'Admin', 'Admin');

insert into old_employee_work_status (salary, employee_id, position_id, supervisor_id, start_date, end_date,
                                      created_by, updated_by)
values (25000.00, 15, 15, 7, '2018-03-15', '2023-10-25',
        'Admin', 'flz');
