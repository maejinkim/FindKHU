const mysql = require('mysql');
const db_config = require('./config/db-config.json');


exports.connect = function(done) {
    pool = mysql.createPool(db_config);
}

exports.get = function() {
  return pool;
}
