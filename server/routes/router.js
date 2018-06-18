module.exports = function(app){

   const http = require('http');
   const db = require('../db');
   const url = require('url');

//메인 홈 코드
app.get('/', function(req, res) {
  res.render('index', { title: 'Express' });
});

 app.get('/error;', function (req, res) {
    res.render('error');
 });

 let users = [
   {
     id: 1,
     name: 'alice'
   },
   {
     id: 2,
     name: 'bek'
   },
   {
     id: 3,
     name: 'chris'
   }
 ]

 app.get('/users', (req, res) => {
    console.log('who get in here/users');
    res.json(users)
 });

 app.post('/post', (req, res) => {
    console.log('who get in here post /users');
    var inputData;

    req.on('data', (data) => {
        console.log(data);
      inputData = JSON.parse(data);
    });

    req.on('end', () => {
      console.log("user_id : "+inputData.user_id + " , name : "+inputData.name);
    });

    res.write("OK!");
    res.end();
 });

}
