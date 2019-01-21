DROP TABLE IF EXISTS bhge_goods;
DROP TABLE IF EXISTS loopnamecard;

create table bhge_goods  (
   orgNo             varchar(32)   not null,
   controll_id       varchar(32),
   discern_order     integer ,
   rep_tag           varchar(64)            ,
   reg_No   integer               ,
   ralate_tag   varchar(64) ,
   ralate_No   integer   ,
   repBlock           integer            ,
   loop_name          varchar(64)            ,
   pid_tag           varchar(64)            ,
   sig_type         varchar(64)            ,
   sensor_type      varchar(64)            ,
   desc_info             varchar(256)        ,
   cardNo           integer,
   isLeft            integer,
   chNumber          integer               ,

   primary key (orgNo)
);

create table loopnamecard  (
    cardNo                 integer   not null,
    sensorType          varchar(64)            ,
    sigType         varchar(64)            ,
   isRedundancy          integer           ,
   maxSigNum           integer           ,

   primary key (cardNo)
);