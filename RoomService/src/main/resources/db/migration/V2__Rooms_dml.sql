insert into public.rs_asset (name, price, is_default, created_by)
values ('kahvaltı seti', '500', false, 'flz') on conflict (name) do nothing;

insert into public.rs_asset (name, price, is_default, created_by)
values ('expresso makinesi', '500', false, 'flz') on conflict (name) do nothing;

insert into public.rs_asset (name, price, is_default, created_by)
values ('ızgara seti', '1000', false, 'flz') on conflict (name) do nothing;

insert into public.rs_asset (name, price, is_default, created_by)
values ('yılbaşı seti', '2000', false, 'flz') on conflict (name) do nothing;

insert into public.rs_asset (name, price, is_default, created_by)
values ('misafir seti', '1000', false, 'agit') on conflict (name) do nothing;


insert into public.rs_room_type (name, price, person_count, description, size, created_by)
values ('standart oda tek kişilik',
        1000,
        1,
        'bu odada bir kanepe/koltuk, küvet, duş ve ayrı tuvaleti olan bir banyo, 65 inç lcd tv, ' ||
        'mp3 çalar/radyo/çalar saat, yastık menüsü, çay/kahve yapma olanakları, ve wifi mevcuttur.',
        30,
        'flz') on conflict (name) do nothing;

insert into public.rs_room_type (name, price, person_count, description, size, created_by)
values ('standart oda çift kişilik',
        2000,
        2,
        'bu odada bir kanepe/koltuk, küvet, duş ve ayrı tuvaleti olan bir banyo, 65 inç lcd tv' ||
        ', mp3 çalar/radyo/çalar saat, yastık menüsü, çay/kahve yapma olanakları, ve wifi mevcuttur.',
        50,
        'flz') on conflict (name) do nothing;

insert into public.rs_room_type (name, price, person_count, description, size, created_by)
values ('delüks oda çift kişilik',
        2500,
        5,
        'bu odada bir kanepe/koltuk, küvet, duş ve ayrı tuvaleti olan bir banyo, 65 inç lcd tv' ||
        ', mp3 çalar/radyo/çalar saat, yastık menüsü, çay/kahve yapma olanakları, ve wifi mevcuttur. balkon ' ||
        'merdivenlerinden havuza doğrudan erişim sağlayan, ayrı yatak odalı bu odada kalın.',
        60,
        'flz') on conflict (name) do nothing;
