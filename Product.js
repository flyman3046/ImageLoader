var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var ProductSchema   = new Schema({
	img: { data: Buffer, contentType: String }
});

module.exports = mongoose.model('Product', ProductSchema);