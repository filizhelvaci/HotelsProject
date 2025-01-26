insert into rs_asset (name, price, is_default, created_by)
values ('kahvaltı seti', '500', false, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('expresso makinesi', '500', false, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('ızgara seti', '1000', false, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('yılbaşı seti', '2000', false, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('misafir seti', '1000', true, 'agit');

insert into rs_asset (name, price, is_default, created_by)
values ('çift kişilik yatak', '2500', true, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('tek kişilik yatak', '1500', true, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('klima', '0', true, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('TV', '1000', true, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('mini bar', '500', true, 'agit');

insert into rs_asset (name, price, is_default, created_by)
values ('masa - sandalye takımı', '2500', true, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('mutfak seti', '1500', true, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('koltuk', '0', true, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('buzdolabı', '1000', true, 'flz');

insert into rs_asset (name, price, is_default, created_by)
values ('extra-aydınlatma', '500', true, 'agit');


with inserted_room_type as (
insert
into rs_room_type (name, price, person_count, size, description, created_by)
values ('Deniz Manzaralı Hilton Süit', 5000, 5, 131, '131 metrekare, ayrı oturma alanı, jetli küvet, panoramik manzara, ' ||
    '65 inçlik TV, kablosuz internet Bu sakin ve gösterişli süit konfor ve geniş alan sunmaktadır. ' ||
    'Jetli küvette bir içkinin keyfini çıkarırken muhteşem panoramik deniz manzarasını hayranlıkla seyredin.' ||
    'Özel Serenity Super King Yataklı bir büyük yatak odası iki double yataklı ek bir yatak odası sunmaktadır' ||
    'Mutfak, koltuklar bulunan bir oturma odası, küvet, duş ve ayrı tuvaletleri olan iki banyo, üç 65 inç LCD TV, ' ||
    'ütü ve ütü masası, MP3 çalar/radyo/çalar saat, yastık menüsü, çay/kahve yapma olanakları ve WiFi mevcuttur.', 'flz')
    returning id)
insert
into rs_room_type_asset (asset_id, room_type_id)
values (14, (select id from inserted_room_type)), (15, (select id from inserted_room_type)), (6, (select id from inserted_room_type)), (7, (select id from inserted_room_type)), (8, (select id from inserted_room_type)), (9, (select id from inserted_room_type)), (10, (select id from inserted_room_type)), (12, (select id from inserted_room_type)), (13, (select id from inserted_room_type));


with inserted_room_type as (
insert
into rs_room_type (name, price, person_count, size, description, created_by)
values ('Bahçe Manzaralı King Yataklı Misafir Odası', 2500, 2, 36, 'Bahçe manzarası, balkon, küvet, duş ve ayrı tuvalet, ' ||
    '55-inç TV, WiFi, Ahşap Laminat Zemin Bu 36 metrekarelik rahat ve geniş bahçe manzaralı odada bir king yatak, balkon, ' ||
    'kanepe veya koltuk, küvet, duş ve ayrı tuvaleti olan bir banyo bulunmaktadır. 55-inç LCD televizyonda bir programın ' ||
    'veya MP3 çalar radyo-çalar saatte müziğin keyfini çıkarın.Diğer hizmetler arasında WiFi, yastık menüsü ve ' ||
    'çay/kahve yapma olanakları yer almaktadır.', 'flz')
    returning id)
insert
into rs_room_type_asset (asset_id, room_type_id)
values (6, (select id from inserted_room_type)), (8, (select id from inserted_room_type)), (9, (select id from inserted_room_type)), (10, (select id from inserted_room_type)), (11, (select id from inserted_room_type)), (12, (select id from inserted_room_type)), (13, (select id from inserted_room_type)), (14, (select id from inserted_room_type)), (15, (select id from inserted_room_type));


with inserted_room_type as (
insert
into rs_room_type (name, price, person_count, size, description, created_by)
values ('Deniz Manzaralı Havuz Bağlantılı King Yataklı Misafir Odası', 3000, 2, 34, '34 metrekare, balkon, ' ||
    'merdivenlerle havuz erişimi, ayrı küvet, duş ve tuvalet, Ahşap Laminat Zemin Modern bir tarzda dekore edilmiş olan ' ||
    'Havuz Erişimli Misafir Odası, huzurlu bir konaklamaya yönelik hizmetlerin yanı sıra konfor ve mahremiyet sunmaktadır. ' ||
    'Balkonda dinlenin, ardından merdivenlerden inip havuza dalın. Bu oda tek büyük Serenity yatak ve kanepe ya da koltuk içerir.' ||
    'Küvette ya da duşta tazelenin. 55-inç LCD televizyonda bir programın veya MP3 çalar radyo-çalar saatte müziğin keyfini çıkarın.' ||
    'Diğer hizmetler arasında WiFi, yastık menüsü ve çay ve kahve yapma olanakları yer almaktadır.', 'flz')
    returning id)
insert
into rs_room_type_asset (asset_id, room_type_id)
values (6, (select id from inserted_room_type)), (8, (select id from inserted_room_type)), (9, (select id from inserted_room_type)), (10, (select id from inserted_room_type)), (11, (select id from inserted_room_type)), (12, (select id from inserted_room_type)), (13, (select id from inserted_room_type)), (14, (select id from inserted_room_type)), (15, (select id from inserted_room_type));


with inserted_room_type as (
insert
into rs_room_type (name, price, person_count, size, description, created_by)
values ('Nehir Manzaralı King Yataklı Misafir Odası', 3000, 2, 36, '36 metrekare, nehir manzarası, 55 inçlik LCD TV, ' ||
    'Ahşap Laminat Zemin Bu rahat ve ferah odada bir balkon, kanepe/koltuk, küvet, duş ve ayrı tuvaleti olan bir banyo ' ||
    'bulunmaktadır. WiFi, 55 inç LCD TV, MP3 çalar/radyo/çalar saat, yastık menüsü ve çay/kahve yapma olanaklarının ' ||
    'keyfini çıkarın.', 'flz')
    returning id)
insert
into rs_room_type_asset (asset_id, room_type_id)
values (6, (select id from inserted_room_type)), (8, (select id from inserted_room_type)), (9, (select id from inserted_room_type)), (10, (select id from inserted_room_type)), (11, (select id from inserted_room_type)), (12, (select id from inserted_room_type)), (13, (select id from inserted_room_type)), (14, (select id from inserted_room_type)), (15, (select id from inserted_room_type));


with inserted_room_type as (
insert
into rs_room_type (name, price, person_count, size, description, created_by)
values ('Bahçe Manzaralı Aile Odası', 5000, 4, 76, '76 metrekare, iki ayrı yatak odası, 55 inçlik LCD TV, Ahşap Lamine Zemin' ||
    'Bahçe manzaralı bu oda aileler düşünülerek tasarlanmıştır ve bir yatak odasında özel bir Serenity King yatak ve ' ||
    'diğerinde iki küçük çift kişilik yatak bulunmaktadır. Her yatak odasında bir kanepe/koltuk bulunur ve küvet, duş ve ' ||
    'ayrı tuvaleti olan bir banyo sunmaktadır. Ayrıca WiFi, 55 inç LCD TV, ütü ve ütü masası, MP3 çalar/radyo/çalar saat, ' ||
    'yastık menüsü, çay/kahve yapma olanaklarını ve WiFi erişimini kapsamaktadır.', 'flz')
    returning id)
insert
into rs_room_type_asset (asset_id, room_type_id)
values (6, (select id from inserted_room_type)), (9, (select id from inserted_room_type)), (10, (select id from inserted_room_type)), (11, (select id from inserted_room_type)), (12, (select id from inserted_room_type)), (13, (select id from inserted_room_type)), (14, (select id from inserted_room_type)), (15, (select id from inserted_room_type));


with inserted_room_type as (
insert
into rs_room_type (name, price, person_count, size, description, created_by)
values ('Bahçe Manzaralı Aile Dubleks Süiti', 5000, 5, 96, '96 metrekare, 2 katlı, 2 odası, küçük mutfak, teras, sauna, ' ||
    'bahçe manzarası Bu seçkin ve ferah süitte aile konforunun anlamını keşfedin. Rahat bir şekilde döşenmiş olan iki katlı ' ||
    'süit birinde özel Serenity Super King yatak ve diğerinde iki adet küçük çift kişilik bulunan iki yatak odası içerir.' ||
    'Her iki katta da bir kanepe ya da koltuk, duşlu bir banyo, 65-inç LCD televizyon, ütü ve ütü masası, ' ||
    'MP3 çalar/radyo/çalar saat, yastık menüsü ve WiFi bulunur. Sauna, küçük mutfak, oturma alanının ve ' ||
    'terastaki şezlongların keyfini çıkarın.', 'flz')
    returning id)
insert
into rs_room_type_asset (asset_id, room_type_id)
values (6, (select id from inserted_room_type)), (7, (select id from inserted_room_type)), (8, (select id from inserted_room_type)), (9, (select id from inserted_room_type)), (10, (select id from inserted_room_type)), (11, (select id from inserted_room_type)), (12, (select id from inserted_room_type)), (13, (select id from inserted_room_type)), (14, (select id from inserted_room_type)), (15, (select id from inserted_room_type));


with inserted_room_type as (
insert
into rs_room_type (name, price, person_count, size, description, created_by)
values ('Havuz Manzaralı Havuza Direkt Çıkışlı Deluxe Aile Evi', 7500, 6, 112, '112 metrekare, havuz erişimi, özel teras, ' ||
    '55 inçlik LED TV, kablosuz internet Bu şık oda zemin katta yer almakta ve şezlong, bahçe mobilyası ve güneş şemsiyesi bulunan' ||
    'terastan yüzme havuzuna doğrudan erişim sağlamaktadır.Aileler için ideal olan bu oda, bir odada Serenity Super King Yatak ve ' ||
    'ikincisinde iki adet king yatağa sahiptir. Her oda bir kanepe/koltuk ve küvet, duş ve ayrı tuvaleti olan şık bir banyoya sahiptir.' ||
    ' Hizmetlere 55-inç LED TV, MP3 çalar, yastık menüsü ve WiFi dahildir.', 'flz')
    returning id)
insert
into rs_room_type_asset (asset_id, room_type_id)
values (6, (select id from inserted_room_type)), (8, (select id from inserted_room_type)), (9, (select id from inserted_room_type)), (10, (select id from inserted_room_type)), (11, (select id from inserted_room_type)), (12, (select id from inserted_room_type)), (13, (select id from inserted_room_type)), (14, (select id from inserted_room_type)), (15, (select id from inserted_room_type));


with inserted_room_type as (
insert
into rs_room_type (name, price, person_count, size, description, created_by)
values ('Havuz Manzaralı Havuz Erişimli Göl Evi Süit', 10000, 10, 142, '142 metrekare, havuz erişimi, özel teras, 65 inçlik LED TV, ' ||
    'kablosuz internet Bu şık süit Göl Evi villalarında yer almakta ve şezlong, bahçe mobilyası ve güneş şemsiyesi bulunan terasınızdan ' ||
    'yüzme havuzuna doğrudan erişim sağlamaktadır.Aileler için ideal olan bu süit, bir odada Serenity Super King Yatak ve ikincisinde ' ||
    'iki yatağa sahiptir. Bu süitte oturma odası, küçük mutfak, yemek masası, bir kanepe/koltuk ve küvet, duş ve ayrı tuvalet içeren ' ||
    'seçkin bir banyo bulunmaktadır. Hizmetlere 65-inç LED TV, MP3 çalar, yastık menüsü ve WiFi dahildir.', 'flz')
    returning id)
insert
into rs_room_type_asset (asset_id, room_type_id)
values (6, (select id from inserted_room_type)), (7, (select id from inserted_room_type)), (8, (select id from inserted_room_type)), (9, (select id from inserted_room_type)), (10, (select id from inserted_room_type)), (11, (select id from inserted_room_type)), (12, (select id from inserted_room_type)), (13, (select id from inserted_room_type)), (14, (select id from inserted_room_type)), (15, (select id from inserted_room_type));

insert into rs_room (number, floor, status, room_type_id, created_by)
values (101, 1, 'IN_MAINTENANCE', 1, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (102, 1, 'FULL', 2, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (103, 1, 'EMPTY', 1, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (104, 1, 'FULL', 2, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (105, 1, 'FULL', 3, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (106, 1, 'EMPTY', 4, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (107, 1, 'FULL', 3, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (108, 1, 'EMPTY', 4, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (201, 2, 'IN_MAINTENANCE', 5, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (202, 2, 'FULL', 6, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (203, 2, 'EMPTY', 5, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (204, 2, 'FULL', 6, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (205, 2, 'FULL', 5, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (206, 2, 'EMPTY', 6, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (207, 2, 'FULL', 5, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (208, 2, 'EMPTY', 6, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (1001, 1, 'IN_MAINTENANCE', 7, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (1002, 1, 'IN_MAINTENANCE', 7, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (1003, 1, 'EMPTY', 7, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (1004, 1, 'FULL', 7, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (1005, 1, 'FULL', 8, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (1006, 1, 'EMPTY', 8, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (1007, 1, 'FULL', 8, 'flz');

insert into rs_room (number, floor, status, room_type_id, created_by)
values (1008, 1, 'EMPTY', 8, 'flz');