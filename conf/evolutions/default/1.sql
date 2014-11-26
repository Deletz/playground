# --- First database schema

# --- !Ups

create table media (
  id                        bigint not null,
  name                      varchar(255),
  primary key (id))
;
create sequence media_seq start with 1000;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists media;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists media_seq;

