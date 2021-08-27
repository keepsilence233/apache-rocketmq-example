-- auto-generated definition
create table tb_account
(
    id          bigint                  not null,
    balance     numeric                 not null,
    create_time timestamp default now() not null,
    name        varchar,
    cart_no     varchar(256)
);

comment
on table tb_account is '用户';

comment
on column tb_account.balance is '余额';

comment
on column tb_account.create_time is '创建时间';

comment
on column tb_account.cart_no is '银行卡号';

alter table tb_account
    owner to postgres;

create unique index user_id_uindex
    on tb_account (id);

create unique index tb_account_cart_no_uindex
    on tb_account (cart_no);

INSERT INTO public.tb_account (id, name, cart_no, balance, create_time)
VALUES (1, '张三', '6223473345625666', 24000, '2021-08-26 16:52:00.000000');

