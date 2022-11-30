-- ユーザー
drop table if exists users cascade;

create table users (
 id serial primary key
 , name varchar(100) not null
 , email varchar(100) not null unique
 , password text not null
 , zipcode varchar(8) not null
 , address varchar(200) not null
 , telephone varchar(15) not null
) ;


--ユーザー登録(pass:morimori)
insert into users(name, email, password, zipcode, address, telephone) values('テストユーザ', 'test@test.co.jp', '$2a$10$Utoo6nr3XIFEh4xOZ9Zr1.n/PtEYBb8HhlLDDklaJwsj.T3uux4kq','1111111', 'テスト住所', 'テスト電話番号');


-- 商品
drop table if exists items cascade;

create table items (
  id integer primary key
  , name text not null
  , description text not null
  , price_m integer not null
  , price_l integer not null
  , image_path text not null
  , deleted boolean default false not null
) ;

insert into items values(1, 'カツカレー', '食べると勝負に勝てると言われる勝つカレー。ラクラクカレー定番の１品です', 1490, 2570, '1.jpg');
insert into items values(2, 'ポークポークカレー・ミート', 'グリーンアスパラと相性の良いベーコンにいろどりのフレッシュトマトをトッピングし特製マヨソースでまとめた商品です', 1490, 2570, '2.jpg');
insert into items values(3, '牛すじカレー', 'トロトロの牛すじとネギの風味が格別な味わいシンプルなカレーです！', 1490, 2570, '3.jpg');
insert into items values(4, '味噌カツカレー', 'マイルドな味付けのカレーに大きくカットした味噌カツをのせた、バターとチーズの風味が食欲をそそるお子様でも楽しめる商品です', 1900, 2980, '4.jpg');
insert into items values(5, 'とんかつカレーラーメン', 'カレーはライスだけではありません。ラクラクピザが開発したカレーラーメンは絶品の美味しさ！', 1900, 2980, '5.jpg');
insert into items values(6, 'ヒレカツカレー', 'やわらかくあっさりとしたヒレ肉を上質な衣で包み込みました。4種類の濃厚な味わいが一つで楽しめるカレーです', 2700, 4050, '6.jpg');
insert into items values(7, '濃厚Gorgeous4', 'こだわりのソースで、旨みとコクを堪能！濃厚Gorgeous4とは、動物由来の原材料を使用していないカレーです。ベジタリアンの方にオススメです', 2570, 3780, '7.jpg');
insert into items values(8, 'カレーうどん', 'ラクラクカレー自慢のカレーに魚介のダシ、ローストオニオンのコクが加わった絶妙なスープをお楽しみください', 2160, 3380, '8.jpg');
insert into items values(9, 'Charity4', 'にんにくとトマトの旨みが効いたスパイスカレー。食べると思わず元気が出るラクラクカレーの自信作', 2700, 4050, '9.jpg');
insert into items values(10, 'ほうれん草のカレードリア', 'カレードリアの王道！ホワイトソースとカレーのダブルソースとなす、ほうれん草、チーズのおいしい組み合わせ', 2160, 3380, '10.jpg');
insert into items values(11, 'Specialドリア4', 'ホワイトソースとカレーのダブルソースにハンバーグを合わせました', 2700, 4050, '11.jpg');
insert into items values(12, '季節の野菜カレー', '季節の野菜が一つになった4種の栄養満点カレー。ラクラクカレーオリジナル商品でベジタリアンの方にオススメです', 2160, 3380, '12.jpg');
insert into items values(13, 'バラエティー４', 'あらびきスライス牛肉とイタリアンチーズを、トマトソースと特製マヨソースの2種類のソースで召し上がって頂く商品です', 2160, 3380, '13.jpg');
insert into items values(14, 'えびナスカレー', 'デミグラスソースでじっくり煮込んだ旨味たっぷりのえびとナスのカレー', 2980, 4460, '14.jpg');
insert into items values(15, 'Family４', 'ラクラクカレー自慢「特うまプルコギ」定番「デラックス」お子様に人気「ツナマイルド」女性に好評「チーズ＆チーズ」の４種のおいしさを贅沢に組み合わせました', 2440, 3650, '15.jpg');
insert into items values(16, 'シンプルイズベスト', '人気ナンバー１！魚介の旨みたっぷり！人気の海の幸と、野菜のリッチなおいしさ', 2700, 4050, '16.jpg');
insert into items values(17, '学芸会カレー', 'みんな大好き！学芸会で作るような味を再現！定番のおいしさを味わえます', 2440, 3650, '17.jpg');
insert into items values(18, '黄金に輝くチキンカレー', 'カレーが黄金に輝く、超高級鶏肉を使用したカレーです', 2700, 4050, '18.jpg');



-- トッピング
drop table if exists toppings cascade;

create table toppings (
  id integer primary key
  , name text not null
  , price_m integer not null
  , price_l integer not null
) ;

insert into toppings values(1, 'オニオン', 200, 300);
insert into toppings values(2, 'ツナマヨ', 200, 300);
insert into toppings values(3, 'イタリアントマト', 200, 300);
insert into toppings values(4, 'イカ', 200, 300);
insert into toppings values(5, 'プルコギ', 200, 300);
insert into toppings values(6, 'アンチョビ', 200, 300);
insert into toppings values(7, 'エビ', 200, 300);
insert into toppings values(8, 'コーン', 200, 300);
insert into toppings values(9, 'ピーマン', 200, 300);
insert into toppings values(10, 'フレッシュスライストマト', 200, 300);
insert into toppings values(11, 'ベーコン', 200, 300);
insert into toppings values(12, 'ペパロニ･サラミ', 200, 300);
insert into toppings values(13, '熟成ベーコン', 200, 300);
insert into toppings values(14, '特製マヨソース', 200, 300);
insert into toppings values(15, 'カマンベールチーズ', 200, 300);
insert into toppings values(16, 'フレッシュモッツァレラチーズ', 200, 300);
insert into toppings values(17, 'イタリアンソーセージ', 200, 300);
insert into toppings values(18, 'ガーリックスライス', 200, 300);
insert into toppings values(19, 'あらびきスライスソｰセｰジ', 200, 300);
insert into toppings values(20, 'ブロッコリー', 200, 300);
insert into toppings values(21, 'グリーンアスパラ', 200, 300);
insert into toppings values(22, 'パルメザンチーズ', 200, 300);
insert into toppings values(23, 'パイナップル', 200, 300);
insert into toppings values(24, 'ハラペーニョ', 200, 300);
insert into toppings values(25, 'もち', 200, 300);
insert into toppings values(26, 'ポテト', 200, 300);
insert into toppings values(27, 'ブラックオリーブ', 200, 300);
insert into toppings values(28, 'チーズ増量', 200, 300);


-- 注文
drop table if exists orders cascade;

create table orders (
  id serial primary key
  , user_id integer not null
  , status integer not null
  , total_price integer not null
  , order_date date
  , destination_name varchar(100)
  , destination_email varchar(100)
  , destination_zipcode varchar(8)
  , destination_address varchar(200)
  , destination_tel varchar(15)
  , delivery_time timestamp
  , payment_method integer
  ) ;

-- 注文商品


drop table if exists order_items cascade;

create table order_items (
  id serial primary key
  , item_id integer not null
  , order_id integer not null
  , quantity integer not null
  , size varchar(1)
  , sub_total integer not null
) ;



-- 注文トッピング
drop table if exists order_toppings cascade;

create table order_toppings (
  id serial primary key
  , topping_id integer not null
  , order_item_id integer not null
) ;

-- 管理者機能用のテーブル（id,email,password）
drop table if exists administrators cascade;


create table administrators (
 id serial primary key
 , name varchar(100) not null
 , email varchar(100) not null unique
 , password text not null
) ;


