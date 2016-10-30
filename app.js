// BASE SETUP
// =============================================================================

// call the packages we need
var express    = require('express');
var fs = require('fs');
var bodyParser = require('body-parser');
var app        = express();

app.use(bodyParser.json({limit: '50mb'}) );
app.use(bodyParser.urlencoded({
  limit: '50mb',
  extended: true,
  parameterLimit:50000
}));

var port = process.env.PORT || 3000; // set our port

var mongoose   = require('mongoose');
mongoose.connect('mongodb://localhost/product'); // connect to our database
var Product     = require('./Product.js');

app.get('/', function(req, res) {

  Product.find(function(err, products) {
    if (err)
      res.send(err);

    res.json(products);
  });
});

app.post('/', function(req, res) {
    
  var product = new Product();
  product.img.data = req.body.img.data.data;
  product.img.contentType = 'image/png';

  product.save(function(err) {
    if (err)
      res.send(err);

    res.json({ message: 'an image upload!' });
  });    
});

app.listen(port, function () {
  console.log('Example app listening on port: ' + port);
});
