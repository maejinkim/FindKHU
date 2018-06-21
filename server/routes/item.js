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

  db.get().query(sql_insert,[address,lat,lng], function (err, result) {
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
    var item_date = request.body.item_reg_date;
    var item_reg_date = new Date();
    var cat_id = request.body.cat_id;
    var loc_id = request.body.loc_id;
  //  var pic_id = request.body.pic_id;

    var sql_insert =
    "insert into item (item_type, user_id, item_title, item_content, item_reg_date, item_date, cat_id, loc_id)"
    + " values(?,?,?,?,?,?,?,?); ";
    var value = [item_type, user_id, item_title, item_content, item_reg_date, item_date, cat_id, loc_id];
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
  console.log("info/image" + "??");

    var form = new formidable.IncomingForm();

    form.on('fileBegin', function (name, file){
      file.path = './public/img/' + file.name;
    });

    form.parse(req, function(err, fields, files) {
      var sql_insert = "insert into picture (item_type, item_id, pic_name) values (?, ?, ?);";
      console.log(sql_insert);

      db.get().query(sql_insert, [fields.item_type,  fields.item_id, files.file.name], function (err, rows) {
          console.log("성공?" + result.insertId);
          response.status(200).send(''+result.insertId);
      });
    });
  });

//item/list
router.get('/list', function(req, res, next) {
    var user_id = req.query.user_id;
    var item_type = req.query.item_type;

    console.log("member_seq : "+user_id);

    console.log("order_type : "+item_type);

    if (!user_id) {
      return res.sendStatus(400);
    }

    var start_page = current_page * LOADING_SIZE;

    var sql = "select * from item where user_id = ? and item_type = ?;";
    var params = [user_id, item_type];
    console.log("sql : "+sql);

    db.get().query(sql, params, function (err, rows) {
        if (err) return res.sendStatus(400);

        console.log("rows : " + JSON.stringify(rows));
        res.status(200).json(rows);
    });
});

// food/list/{member_seq}
router.get('/list/:item_id', function(request, response, next){
    var item_id = request.params.item_id;
    var user_id = request.query.user_id;

    console.log("member_seq : "+user_id);
    console.log("seq : "+item_id);

    var sql = "select * from item where item = ?;";
    console.log("sql : " + sql);

  db.get().query(sql, [seq], function (err, rows) {
      if (err) return response.sendStatus(400);

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
