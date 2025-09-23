-- ----------------------------------------------------------------------
-- DML for Reservation Data
-- ----------------------------------------------------------------------

insert into rs_reservation (room_id, booker_user_id, customer_user_id, customer_first_name, customer_last_name,
                            customer_phone_number, customer_email, check_in_date, check_out_date, status, created_by)
values(101, 1, null, 'Fatma', 'Öztürk',
       '5321234567', 'fatma.ozturk@gmail.com', '2025-10-01',
       '2025-10-05', 'HELD', 'admin');

insert into rs_reservation (room_id, booker_user_id, customer_user_id, customer_first_name, customer_last_name,
                            customer_phone_number, customer_email, check_in_date, check_out_date, status, created_by)
values(102, 2, null, 'Ali', 'Yıldırım',
       '5332345678', 'ali.yildirim@hotmail.com', '2025-10-02',
       '2025-10-06', 'CREATED', 'admin');

insert into rs_reservation (room_id, booker_user_id, customer_user_id, customer_first_name, customer_last_name,
                            customer_phone_number, customer_email, check_in_date, check_out_date, status, created_by)
values(103, NULL, null, 'Zeynep', 'Kara',
       '5343456789', 'zeynep.kara@gmail.com', '2025-10-03',
       '2025-10-04', 'CONFIRMED', 'system');

insert into rs_reservation (room_id, booker_user_id, customer_user_id, customer_first_name, customer_last_name,
                            customer_phone_number, customer_email, check_in_date, check_out_date, status, created_by)
values(104, 1, null, 'Hasan', 'Demir',
       '5354567890', 'hasan.demir@yahoo.com', '2025-10-05',
       '2025-10-08', 'CHECKED_IN', 'admin');

insert into rs_reservation (room_id, booker_user_id, customer_user_id, customer_first_name, customer_last_name,
                            customer_phone_number, customer_email, check_in_date, check_out_date, status, created_by)
values(105, NULL, null, 'Emine', 'Çelik',
       '5365678901', 'emine.celik@gmail.com', '2025-10-06',
       '2025-10-10', 'CHECKED_OUT', 'system');

insert into rs_reservation (room_id, booker_user_id, customer_user_id, customer_first_name, customer_last_name,
                            customer_phone_number, customer_email, check_in_date, check_out_date, status, created_by)
values(106, 3, null, 'Ahmet', 'Taş',
       '5376789012', 'ahmet.tas@hotmail.com', '2025-10-07',
       '2025-10-09', 'CANCELLED', 'admin');


-- ----------------------------------------------------------------------
-- DML for Reservations History Data
-- ----------------------------------------------------------------------

insert into rs_reservation_history (reservation_id, old_status, new_status, created_by)
values(1, 'HELD', 'CREATED', 'admin');

insert into rs_reservation_history(reservation_id, old_status, new_status, created_by)
values(2, 'CREATED', 'CONFIRMED', 'admin');

insert into rs_reservation_history(reservation_id, old_status, new_status, created_by)
values(3, 'CONFIRMED', 'CHECKED_IN', 'system');

insert into rs_reservation_history(reservation_id, old_status, new_status, created_by)
values(4, 'CHECKED_IN', 'CHECKED_OUT', 'admin');

insert into rs_reservation_history(reservation_id, old_status, new_status, created_by)
values(5, 'CHECKED_IN', 'CHECKED_OUT', 'system');

insert into rs_reservation_history(reservation_id, old_status, new_status, created_by)
values(6, 'CREATED', 'CANCELLED', 'admin');
