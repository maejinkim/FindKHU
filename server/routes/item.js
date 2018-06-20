var express = require('express');
var formidable = require('formidable');
var db = require('../db.js')
var router = express.Router();

var LOADING_SIZE = 20;
var DEFAULT_USER_LATITUDE = 37.566229;
var DEFAULT_USER_LONGITUDE = 126.977689;

//item/cat
router.get('/cat', function(req, res, next) {
      var sql = "select * from category;";

    db.get().query(sql, function (err, rows) {
        if (err) return res.sendStatus(400);

        console.log("rows : " + JSON.stringify(rows));
        res.status(200).json(rows);
    });
});


//item/loc
router.post('/loc', function(req, res) {

  var address = req.body.loc_address;
  var lat = req.body.loc_lat;
  var lng = req.body.loc_lng;


  console.log({address , lat, lng});

  var sql_insert = "insert into location (loc_address, loc_lat, loc_lng) values (?, ?, ?);";
  //var sql_get = "select *  from location order by loc_id desc limit 1;"

  db.get().query(sql_insert,[address, lat, lng], function (err, result) {
      // if (err) return res.sendStatus(400);
      //
      // db.get().query(sql_get, function (err, result) {
      //   if (err) return res.sendStatus(400);
      //
      //   res.status(200).send('' + result.loc_id);
      // });
      if(err){
          console.log(err);
      }
      res.status(200).send(''+result.insertId);
  });

});



// item/info
router.post('/info', function(request, response, next) {
    if(!request.body.user_id){
        return response.status(400).send("정보가 없습니다");
    }
    console.log("물품 등록");
    // item_type
    // item_id
    // user_id
    // item_title
    // item_content
    // item_reg_date
    // item_date
    // cat_id
    // loc_id
    // pic_id

    var item_type = request.body.item_type;
    var user_id = request.body.user_id;
    var item_title = request.body.item_title;
    var item_content = request.body.item_content;
    var item_reg_date = request.body.item_reg_date;
    var item_date = new Date();
    var cat_id = request.body.cat_id;
    var loc_id = request.body.loc_id;
    var pic_id = request.body.pic_id;

    var sql_insert =
    "insert into item (item_type, user_id, item_title, item_content, item_reg_date, item_date, cat_id, loc_id, pic_id)"
    + " values(?,?,?,?,?,?,?,?); ";
    var value = [item_type, user_id, item_title, item_content, item_reg_date, item_date, cat_id, loc_id, pic_id];
    console.log("sql_insert : "+sql_insert);

    db.get().query(sql_insert, value, function(err, result){
        if(err){
            console.log(err);
        }
        response.status(200).send(''+result.insertId);
    });
});

//food/info/image
router.post('/info/image', function (req, res) {
    var form = new formidable.IncomingForm();

    form.on('fileBegin', function (name, file){
      file.path = './public/img/' + file.name;
    });

    form.parse(req, function(err, fields, files) {
      var sql_insert = "insert into picture (item_type, item_id, pic_name) values (?, ?, ?);";

      db.get().query(sql_insert, [fields.item_type,  fields.item_id, files.file.name], function (err, rows) {
          response.status(200).send(''+result.insertId);
      });
    });
  });

//food/list
router.get('/list', function(req, res, next) {
    var member_seq = req.query.member_seq;
    var user_latitude = req.query.user_latitude || DEFAULT_USER_LATITUDE;
    var user_longitude = req.query.user_longitude || DEFAULT_USER_LONGITUDE;
    var order_type = req.query.order_type;
    var current_page = req.query.current_page || 0;

    console.log("member_seq : "+member_seq);
    console.log("user_latitude : "+user_latitude);
    console.log("user_longitude : "+user_longitude);
    console.log("order_type : "+order_type);
    console.log("current_page : "+current_page);


    if (!member_seq) {
      return res.sendStatus(400);
    }

    var order_add = '';

    if (order_type) {
      order_add = order_type + ' desc, user_distance_meter';
    } else {
      order_add = 'user_distance_meter';
    }

    var start_page = current_page * LOADING_SIZE;

    // 이 식은 내 현재 위치를 기준으로 1km 반경 을 그린다. 무슨 이유에서인지 안 되는군.
    // var sql =
    //   "select a.*, " +
    //   "  (( 6371 * acos( cos( radians(?) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?) )  " +
    //   "  + sin( radians(?) ) * sin( radians( latitude ) ) ) ) * 1000) AS user_distance_meter, " +
    //   "  if( exists(select * from bestfood_keep where member_seq = ? and info_seq = a.seq), 'true', 'false') as is_keep, " +
    //   "  (select filename from bestfood_info_image where info_seq = a.seq) as image_filename " +
    //   "from bestfood_info as a " +
    //   "order by  " + order_add + " " +
    //   "limit ? , ? ; ";
    // console.log("sql : " + sql);
    // console.log("order_add : " + order_add);

    // var params = [user_latitude, user_longitude, user_latitude, member_seq, start_page, LOADING_SIZE];

    var sql = "select * from bestfood_info where member_seq = ? and latitude = ? and longitude = ? limit ?, ?;";
    var params = [member_seq, user_latitude, user_longitude, 0, 10];
    console.log("sql : "+sql);

    db.get().query(sql, params, function (err, rows) {
        if (err) return res.sendStatus(400);

        console.log("rows : " + JSON.stringify(rows));
        res.status(200).json(rows);
    });
});

// food/list/{member_seq}
router.get('/list/:info_seq', function(request, response, next){
    var seq = request.params.info_seq;
    var member_seq = request.query.member_seq;

    console.log("member_seq : "+member_seq);
    console.log("seq : "+seq);

    // var sql =
    // "select a.*, " +
    // "  '0' as user_distance_meter, " +
    // "  if( exists(select * from bestfood_keep where member_seq = ? and a.seq = info_seq), 'true', 'false') as is_keep, " +
    // "  (select filename from bestfood_info_image where info_seq = a.seq order by seq limit 1) as image_filename " +
    // "from bestfood_info as a " +
    // "where seq = ? ; ";
    var sql = "select * from bestfood_info where member_seq = ? and seq = ?;";
    console.log("sql : " + sql);

  db.get().query(sql, [member_seq, seq], function (err, rows) {
      if (err) return response.sendStatus(400);;

      console.log("rows : " + JSON.stringify(rows));
      response.json(rows[0]);
  });
});


//food/map/list
router.get('/map/list', function(req, res, next) {
  var member_seq = req.query.member_seq;
  var latitude = req.query.latitude;
  var longitude = req.query.longitude;
  var distance = req.query.distance;
  var user_latitude = req.query.user_latitude || DEFAULT_USER_LATITUDE;
  var user_longitude = req.query.user_longitude || DEFAULT_USER_LONGITUDE;

  if (!member_seq || !latitude || !longitude) {
      return res.sendStatus(400);
  }

  // var sql =
  //   "select a.*, " +
  //   "  (( 6371 * acos( cos( radians(?) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?) ) " +
  //   "  + sin( radians(?) ) * sin( radians( latitude ) ) ) ) * 1000) AS distance_meter," +
  //   "  (( 6371 * acos( cos( radians(?) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?) ) " +
  //   "  + sin( radians(?) ) * sin( radians( latitude ) ) ) ) * 1000) AS user_distance_meter," +
  //   "  IF(EXISTS (select * from bestfood_keep where member_seq = ? and a.seq = info_seq), 'true', 'false') as is_keep," +
  //   "  (select filename from bestfood_info_image where info_seq = a.seq) as image_filename " +
  //   "from bestfood_info as a " +
  //   "having distance_meter <= ? " +
  //   "order by user_distance_meter ";
  var sql = "select from bestfood_info where member_seq = ? and latitude = ? and longitude = ?"
  console.log("sql : " + sql);

  var params = [member_seq, longitude, latitude];

  db.get().query(sql, params, function (err, rows) {
      if (err) return res.sendStatus(400);

      console.log("rows : " + JSON.stringify(rows));
      res.status(200).json(rows);
  });
});



// 하나라도 exports 설정해 주지 않으면 서버 자체가 실행되지 않는다.
module.exports = router;
