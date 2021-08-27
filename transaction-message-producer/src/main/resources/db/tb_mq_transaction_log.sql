-- auto-generated definition
create table tb_mq_transaction_log
(
    id             bigint       not null
        constraint mq_transaction_log_pk
            primary key,
    transaction_id varchar(256) not null,
    ext_info       json
);

comment
on table tb_mq_transaction_log is '事务日志';

alter table tb_mq_transaction_log
    owner to postgres;

create unique index mq_transaction_log_id_uindex
    on tb_mq_transaction_log (id);

create unique index mq_transaction_log_transaction_id_uindex
    on tb_mq_transaction_log (transaction_id);

